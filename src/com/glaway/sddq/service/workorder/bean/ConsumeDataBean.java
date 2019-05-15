package com.glaway.sddq.service.workorder.bean;

import java.io.IOException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.tools.WorkorderUtil;

/**
 * 
 * 故障/改造工单-耗损件记录databean
 * 
 * @author zhuhao
 * @version [版本号, 2018年8月21日]
 * @since [产品/模块版本]
 */
public class ConsumeDataBean extends DataBean {

	@Override
	public void addEditRowCallBackOk() throws IOException, MroException {
		super.addEditRowCallBackOk();

		if (!getJpoSet().isFlagSet(GWConstant.S_READONLY)) {// 非只读状态才能操作

			int amount = getJpo().getInt("AMOUNT");// 上车数量
			String assetpartnum = getJpo().getString("DOWNPARTNUM");
			String itemnum = getJpo().getString("itemnum");// 上车件物料编码
			String uploc = getJpo().getString("uploc");// 上车件库房
			String lotnum = getJpo().getString("lotnum");// 上车件批次号
			// 操作锁定数量
			WorkorderUtil.operateQty(assetpartnum, amount, itemnum, uploc,
					lotnum, "+");

			getAppBean().SAVE();
		}

	}

	@Override
	public int addrow() throws MroException, IOException {
		int ret = super.addrow();
		IJpo consume = getJpo();

		// 根据故障后果对是否邮件通报和是否技术分析字段勾选

		if (getJpo().getParent() != null) {
			if ("故障".equals(getJpo().getParent().getString("TYPE"))) {
				// 获取故障后果的值
				String faultconseq = parent.getString("FAILURELIB.FAULTCONSEQ");

				if (!faultconseq.isEmpty()) {
					if (WorkorderUtil.isImpFault(faultconseq)) {// 是否是高级故障

						consume.setValue("ISNOTICE", "1");
						consume.setValue("ISTECHAANALYZE", "1");
						consume.setFieldFlag("ISNOTICE", GWConstant.S_READONLY,
								true);
						consume.setFieldFlag("ISTECHAANALYZE",
								GWConstant.S_READONLY, true);

					}
				}
			}
		}

		return ret;
	}

	@Override
	public void toggleEditRow() throws MroException, IOException {

		super.toggleEditRow();
		if (getJpo().getParent() != null) {

			if ("故障".equals(getJpo().getParent().getString("TYPE"))) {

				String faultconseq = parent.getString("FAILURELIB.FAULTCONSEQ");

				if (WorkorderUtil.isImpFault(faultconseq)) {

					getJpo().setFieldFlag("ISNOTICE", GWConstant.S_READONLY,
							true);
					getJpo().setFieldFlag("ISTECHAANALYZE",
							GWConstant.S_READONLY, true);

				}
			}
		}

	}

	@Override
	public synchronized void delete() throws MroException {

		super.delete();

		if (!getJpoSet().isFlagSet(GWConstant.S_READONLY)) {// 非只读状态才能操作

			int amount = getJpo().getInt("AMOUNT");// 上车数量
			String assetpartnum = getJpo().getString("DOWNPARTNUM");
			String itemnum = getJpo().getString("itemnum");// 上车件物料编码
			String uploc = getJpo().getString("uploc");// 上车件库房
			String lotnum = getJpo().getString("lotnum");// 上车件批次号

			// 操作锁定数量
			WorkorderUtil.operateQty(assetpartnum, amount, itemnum, uploc,
					lotnum, "-");

			try {
				getAppBean().SAVE();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public synchronized void undelete() throws MroException {

		super.undelete();

		if (!getJpoSet().isFlagSet(GWConstant.S_READONLY)) {// 非只读状态才能操作

			int amount = getJpo().getInt("AMOUNT");// 上车数量
			String assetpartnum = getJpo().getString("DOWNPARTNUM");
			String itemnum = getJpo().getString("itemnum");// 上车件物料编码
			String uploc = getJpo().getString("uploc");// 上车件库房
			String lotnum = getJpo().getString("lotnum");// 上车件批次号

			// 操作锁定数量
			WorkorderUtil.operateQty(assetpartnum, amount, itemnum, uploc,
					lotnum, "+");

			try {
				getAppBean().SAVE();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
