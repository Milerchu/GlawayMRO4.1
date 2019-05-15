package com.glaway.sddq.config.zcconfig.data;

import io.netty.util.internal.StringUtil;

import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;

/**
 * 
 * 车厢号字段类
 * 
 * @author hyhe
 * @version [版本号, 2018-5-8]
 * @since [产品/模块版本]
 */
public class FldCarRiageNum extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void validate() throws MroException {
		// 对车厢号进行校验，唯一性
		String carriagenum = this.getInputMroType().getStringValue();
		if(StringUtil.isNullOrEmpty(carriagenum)){
			throw new AppException("assetcs", "cxisnull");
		}
		IJpo parent = this.getJpo().getParent();
		if (parent != null) {
			String ancestor = parent.getString("ancestor");
			// 找到该车结构下的所有车厢
			IJpoSet zcJpoSet = MroServer.getMroServer().getSysJpoSet(
					"ASSET",
					"assetlevel = 'CAR' and ANCESTOR = '" + ancestor
							+ "' and carriagenum='" + carriagenum
							+ "' and assetnum != '"
							+ this.getJpo().getString("assetnum") + "'");
			if (zcJpoSet != null && zcJpoSet.count() > 0) {
				throw new AppException("assetcs", "cxisrepeat");
			} else {
				IJpoSet jposet = this.getJpo().getJpoSet();
				if (jposet != null && jposet.count() > 0) {
					boolean flag = false;
					for (int index = 0; index < jposet.count(); index++) {
						if(carriagenum.equals(jposet.getJpo(index).getString("carriagenum")) && !this.getJpo().getString("assetnum").equals(jposet.getJpo(index).getString("assetnum"))){
							flag = true;
							break;
						}
					}
					if(flag){
						throw new AppException("assetcs", "cxisrepeat");
					}
				}
			}
		}
	}

	// @Override
	// public void init() throws MroException {
	// this.setLookupMap(new String[] { "CARRIAGENUM" },
	// new String[] { "CARNUM" });
	// }

	// @Override
	// public IJpoSet getList() throws MroException {
	/*
	 * this.setListObject("ASSETCSCARRIAGE"); String cmodel =
	 * this.getJpo().getParent().getField("CMODEL").getValue(); IJpoSet jposet =
	 * getUserServer().getJpoSet("ASSETCS",
	 * "ASSETCSLEVEL = 'ASSET' and CMODEL='" + cmodel + "'"); jposet.reset(); if
	 * (jposet != null && jposet.count() > 0) { String ancestor =
	 * jposet.getJpo(0).getString("ANCESTOR"); this.setListWhere("ASSETCSNUM='"
	 * + ancestor + "'"); return super.getList(); } else {
	 * this.setListWhere("1=2"); return super.getList(); }
	 */

	// String ancestor = this.getField("ancestor").getValue();

	// this.setListObject("ASSETCSCARRIAGE");
	// String cmodel = this.getJpo().getParent().getField("CMODEL").getValue();
	// String parent = this.getField("PARENT").getValue();
	// String where = "";
	// String carnum = null;// 车厢编号
	// IJpoSet jposet = getUserServer().getJpoSet("ASSET");
	// jposet.setUserWhere("PARENT='" + parent + "'");
	// jposet.reset();
	// IJpoSet assetcsset = getUserServer().getJpoSet("ASSETCS",
	// "ASSETCSLEVEL = 'ASSET' and CMODEL='" + cmodel + "'");
	//
	// if (assetcsset != null && assetcsset.count() > 0) {
	// String ancestor = assetcsset.getJpo(0).getString("ANCESTOR");
	// if (jposet != null && jposet.count() > 0) {
	// // String ancestor = jposet.getJpo(0).getString("ANCESTOR");
	//
	// for (int j = 0; j < jposet.count(); j++) {
	// IJpo asset = jposet.getJpo(j);
	// if (carnum == null) {
	// carnum = "'" + asset.getString("CARRIAGENUM") + "'";
	// } else {
	// carnum = carnum + ",'" + asset.getString("CARRIAGENUM")
	// + "'";
	// }
	// }
	//
	// if (carnum != null) {
	// where = where + " ASSETCSNUM='" + ancestor
	// + "' and carnum not in(" + carnum + ")";
	//
	// this.setListWhere(where);
	// }
	// if (!StringUtil.isStrEmpty(where)) {
	// this.setListWhere(where);
	// }
	//
	// return super.getList();
	// } else {
	//
	// this.setListWhere("ASSETCSNUM='" + ancestor + "'");
	// return super.getList();
	// }
	// }else{
	// this.setListWhere("1=2");
	// }
	// return super.getList();

	// }

	// @Override
	// public void action() throws MroException {
	// super.action();
	// this.getJpo().setValue("itemnum",
	// SddqConstant.CAR_ITEMNUM,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
	// }

}
