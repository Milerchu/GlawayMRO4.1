package com.glaway.sddq.config.zcconfig.bean;

import java.io.IOException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.sddq.tools.ItemUtil;

/**
 * 备件申请匹配库存弹出框dataBean
 * 
 * @author zzx
 * @version [版本号, 2018-7-30]
 * @since [整车BOM]
 */
public class CompareInentryDataBean extends DataBean {

	public int compare() throws MroException, IOException {
		IJpoSet assetmodelpartSet = this.getJpoSet();

		if (assetmodelpartSet.count() > 0) {

			for (int i = 0; i < assetmodelpartSet.count(); i++) {
				IJpo modelpart = assetmodelpartSet.getJpo(i);
				String itemnum = modelpart.getString("ITEMNUM");

				// zzx add start 11.8
				String lottype = ItemUtil.getItemLottype(itemnum);

				if (lottype != null
						&& (lottype.equals(ItemUtil.SQN_ITEM) || lottype
								.equals(ItemUtil.LOT_I_ITEM))) {
					if (isInvent(itemnum)) {
						modelpart.setValue("APPLY", true);
					}
				}
				// zzx add end
			}
			this.reloadPage();
		}

		return 1;
	}

	private boolean isInvent(String itemnum) throws MroException {
		IJpoSet inventorySet = MroServer.getMroServer().getSysJpoSet(
				"SYS_INVENTORY");
		inventorySet.setUserWhere("itemnum ='" + itemnum + "' ");
		inventorySet.reset();
		return !inventorySet.isEmpty();
	}

//	@Override
//	public int addrow() throws MroException, IOException {
//
//		if ("SBOMCONFIG".equals(this.getAppName())
//				&& this.getJpo().getParent() != null) {
//			if (SddqConstant.ASSET_MODEL_STATUS_KY.equals(getAppBeanJpo(
//					this.getJpo().getParent()).getString("STATUS"))
//					|| SddqConstant.ASSET_MODEL_STATUS_SD.equals(getAppBeanJpo(
//							this.getJpo().getParent()).getString("STATUS"))) {
//				throw new AppException("assettmp", "canotAdd");
//			}
//		}
//		if ("ZCSBOM".equals(this.getAppName())
//				&& this.getParent() != null) {
//			if (SddqConstant.ASSET_CS_STATUS_KY.equals(getAppBeanJpo(
//					this.getJpo().getParent()).getString("STATUS"))
//					|| SddqConstant.ASSET_CS_STATUS_SD.equals(getAppBeanJpo(
//							this.getJpo().getParent()).getString("STATUS"))) {
//				throw new AppException("assetcs", "canotAdd");
//			}
//		}
//
//		return super.addrow();
//	}
//
//	public IJpo getAppBeanJpo(IJpo jpo) throws MroException {
//		if ("SBOMCONFIG".equals(this.getAppName())
//				&& this.getJpo() != null) {
//			if (jpo.getString("ASSETTMPLEVEL") != null
//					&& !jpo.getString("ASSETTMPLEVEL").equals("ASSET")) {
//				IJpoSet ancestorJpoSet = jpo.getJpoSet("ANCESTOR");
//				if (ancestorJpoSet != null && ancestorJpoSet.count() > 0) {
//					return ancestorJpoSet.getJpo(0);
//				}
//			}
//		}
//
//		if ("ZCSBOM".equals(this.getAppName())
//				&& this.getJpo() != null) {
//			if (jpo.getString("ASSETCSLEVEL") != null
//					&& !jpo.getString("ASSETCSLEVEL").equals("ASSET")) {
//				IJpoSet ancestorJpoSet = jpo.getJpoSet("ANCESTOR");
//				if (ancestorJpoSet != null && ancestorJpoSet.count() > 0) {
//					return ancestorJpoSet.getJpo(0);
//				}
//			}
//		}
//		return jpo;
//
//	}
//
//	@Override
//	public int editrow() throws MroException, IOException {
//
//		if ("SBOMCONFIG".equals(this.getAppName())
//				&& this.getJpo().getParent() != null) {
//			if (SddqConstant.ASSET_MODEL_STATUS_KY.equals(getAppBeanJpo(
//					this.getJpo().getParent()).getString("STATUS"))
//					|| SddqConstant.ASSET_MODEL_STATUS_SD.equals(getAppBeanJpo(
//							this.getJpo().getParent()).getString("STATUS"))) {
//				throw new AppException("assettmp", "cannotEdit");
//			}
//		}
//		if ("ZCSBOM".equals(this.getAppName())
//				&& this.getJpo().getParent() != null) {
//			if (SddqConstant.ASSET_CS_STATUS_KY.equals(getAppBeanJpo(
//					this.getJpo().getParent()).getString("STATUS"))
//					|| SddqConstant.ASSET_CS_STATUS_SD.equals(getAppBeanJpo(
//							this.getJpo().getParent()).getString("STATUS"))) {
//				throw new AppException("assetcs", "cannotEdit");
//			}
//		}
//		return super.editrow();
//	}
//
//	@Override
//	public synchronized void delete() throws MroException {
//
//		if ("SBOMCONFIG".equals(this.getAppName())
//				&& this.getJpo().getParent() != null) {
//			if (SddqConstant.ASSET_MODEL_STATUS_KY.equals(getAppBeanJpo(
//					this.getJpo().getParent()).getString("STATUS"))
//					|| SddqConstant.ASSET_MODEL_STATUS_SD.equals(getAppBeanJpo(
//							this.getJpo().getParent()).getString("STATUS"))) {
//				throw new AppException("assettmp", "cannotDel");
//			}
//		}
//		if ("ZCSBOM".equals(this.getAppName())
//				&& this.getJpo().getParent() != null) {
//			if (SddqConstant.ASSET_CS_STATUS_KY.equals(getAppBeanJpo(
//					this.getJpo().getParent()).getString("STATUS"))
//					|| SddqConstant.ASSET_CS_STATUS_SD.equals(getAppBeanJpo(
//							this.getJpo().getParent()).getString("STATUS"))) {
//				throw new AppException("assetcs", "cannotdel");
//			}
//		}
//
//		super.delete();
//	}
}
