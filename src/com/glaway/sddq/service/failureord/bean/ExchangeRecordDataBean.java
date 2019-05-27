package com.glaway.sddq.service.failureord.bean;

import java.io.IOException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.tools.SddqConstant;
import com.glaway.sddq.tools.WorkorderUtil;

/**
 * 
 * 故障工单 上下车记录 databean
 * 
 * @author zhuhao
 * @version [版本号, 2018年8月1日]
 * @since [产品/模块版本]
 */
public class ExchangeRecordDataBean extends DataBean {

	@Override
	public int addrow() throws MroException, IOException {

		String status = getAppBean().getJpo().getString("status");
		if (SddqConstant.WO_STATUS_JSZGSH.equals(status)) {
			throw new MroException("当前状态不可添加！");
		}
		int ret = super.addrow();
		if (getJpoSet().count() < 2) {
			// 没有上下车记录时，新增第一条记录将故障件设为主故障件
			getJpo().setValue("ISMAINFAULT", "1", GWConstant.P_NOVALIDATION);
		}
		// 根据故障后果对是否邮件通报和是否技术分析字段勾选
		IJpo parent = this.getParent().getJpo();
		// 获取故障后果的值
		String faultconseq = parent.getString("FAULTCONSEQ");
		if (!faultconseq.isEmpty()) {
			if (WorkorderUtil.isImpFault(faultconseq)) {// 判断是否是高级故障
				this.getJpo().setValue("ISAPPNOTICE", "1",
						GWConstant.P_NOVALIDATION);
				this.getJpo().setValue("ISTECHAANALYZE", "1",
						GWConstant.P_NOVALIDATION);
				this.getJpo().setFieldFlag("ISAPPNOTICE",
						GWConstant.S_READONLY, true);
				this.getJpo().setFieldFlag("ISTECHAANALYZE",
						GWConstant.S_READONLY, true);

			}
		}
		return ret;
	}

	@Override
	public void toggleEditRow() throws MroException, IOException {

		super.toggleEditRow();
		// 点击查看详细按钮后设置是否技术分析、是否app通报字段的权限
		// 故障后果
		String faultconseq = parent.getString("FAULTCONSEQ");
		// String isappnotice = this.getJpo().getString("ISAPPNOTICE");
		// String istechaanalyze = this.getJpo().getString("ISTECHAANALYZE");
		if (WorkorderUtil.isImpFault(faultconseq)) {
			this.getJpo().setFieldFlag("ISAPPNOTICE", GWConstant.S_READONLY,
					true);
			this.getJpo().setFieldFlag("ISTECHAANALYZE", GWConstant.S_READONLY,
					true);

		}

	}

	@Override
	public void addEditRowCallBackOk() throws IOException, MroException {
		super.addEditRowCallBackOk();
		// 将主故障件信息记录到故障记录中
		String status = getAppBean().getJpo().getString("status");

		if (SddqConstant.WO_STATUS_CLZ.equals(status)
				|| SddqConstant.WO_STATUS_KGYBH.equals(status)
				|| SddqConstant.WO_STATUS_JSZGBH.equals(status)
				|| SddqConstant.WO_STATUS_ZZKGYBH.equals(status)
				|| SddqConstant.WO_STATUS_SHBH.equals(status)) {// 处理中\审核驳回

			IJpo exchangeRcd = getJpo();
			if (exchangeRcd.getBoolean("ISMAINFAULT")) {
				IJpo fl = exchangeRcd.getParent();
				if (fl != null) {
					// 故障件信息
					fl.setValue("FAULTCOMPONENTNUM",
							exchangeRcd.getString("sqn"));
					fl.setValue("ASSETNUM", exchangeRcd.getString("ASSETNUM"));
					fl.setValue("PRODUCTCODE", exchangeRcd.getString("ITEMNUM"));
					fl.setValue("FAULTCOMPONENTNAME",
							exchangeRcd.getString("ITEM.DESCRIPTION"));
					// 更换件信息
					fl.setValue("CHANGEASSETNUM",
							exchangeRcd.getString("newsqn"),
							GWConstant.P_NOVALIDATION);
					fl.setValue("NEWASSETNUM",
							exchangeRcd.getString("NEWASSETNUM"));
					fl.setValue("CHANGEPRODUCTCODE",
							exchangeRcd.getString("NEWITEMNUM"));
					fl.setValue("CHANGEASSETNAME",
							exchangeRcd.getString("NEWITEM.DESCRIPTION"));

					// 设备简称设必填
					fl.setFieldFlag("PRODUCTNICKNAME", GWConstant.S_REQUIRED,
							true);

					// zzx add 8.23
					String assetnums = exchangeRcd.getString("ASSETNUM");

					IJpoSet assetSet = MroServer.getMroServer().getSysJpoSet(
							"ASSET", "assetnum='" + assetnums + "'");
					if (assetSet != null && assetSet.count() > 0) {
						String loc = assetSet.getJpo().getString("LOC");
						fl.setValue("FAULTPOSITION", loc,
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					}
					// zzx add end

				}
			}
			// 锁定下车件
			IJpoSet assetSet = exchangeRcd.getJpoSet("ASSET");
			if (assetSet != null && assetSet.count() > 0) {
				assetSet.getJpo(0).setValue("islocked", 1);
			}
			// 锁定上车件
			IJpoSet newAssetSet = exchangeRcd.getJpoSet("NEWASSET");
			if (newAssetSet != null && newAssetSet.count() > 0) {
				newAssetSet.getJpo(0).setValue("islocked", 1);
			}
		}

	}

	@Override
	public synchronized void delete() throws MroException {

		String status = getAppBean().getJpo().getString("status");
		if (SddqConstant.WO_STATUS_JSZGSH.equals(status)) {
			throw new MroException("当前状态不可删除！");
		}
		super.delete();

		if (SddqConstant.WO_STATUS_CLZ.equals(status)
				|| SddqConstant.WO_STATUS_KGYBH.equals(status)
				|| SddqConstant.WO_STATUS_JSZGBH.equals(status)
				|| SddqConstant.WO_STATUS_ZZKGYBH.equals(status)
				|| SddqConstant.WO_STATUS_SHBH.equals(status)) {// 处理中\审核驳回

			IJpo exchangeRcd = getJpo();

			// 释放锁定的下车件
			IJpoSet assetSet = exchangeRcd.getJpoSet("ASSET");
			if (assetSet != null && assetSet.count() > 0) {
				assetSet.getJpo(0).setValue("islocked", 0);
			}
			// 释放锁定的上车件
			IJpoSet newAssetSet = exchangeRcd.getJpoSet("NEWASSET");
			if (newAssetSet != null && newAssetSet.count() > 0) {
				newAssetSet.getJpo(0).setValue("islocked", 0);
			}
		}

	}

	@Override
	public synchronized void undelete() throws MroException {
		super.undelete();

		// 工单状态
		String status = getAppBean().getJpo().getString("status");

		if (SddqConstant.WO_STATUS_CLZ.equals(status)
				|| SddqConstant.WO_STATUS_KGYBH.equals(status)
				|| SddqConstant.WO_STATUS_JSZGBH.equals(status)
				|| SddqConstant.WO_STATUS_ZZKGYBH.equals(status)
				|| SddqConstant.WO_STATUS_SHBH.equals(status)) {// 处理中\审核驳回

			IJpo exchangeRcd = getJpo();

			// 锁定下车件
			IJpoSet assetSet = exchangeRcd.getJpoSet("ASSET");
			if (assetSet != null && assetSet.count() > 0) {
				assetSet.getJpo(0).setValue("islocked", 1);
			}
			// 锁定上车件
			IJpoSet newAssetSet = exchangeRcd.getJpoSet("NEWASSET");
			if (newAssetSet != null && newAssetSet.count() > 0) {
				newAssetSet.getJpo(0).setValue("islocked", 1);
			}
		}
	}
}
