package com.glaway.sddq.material.transfer.data;

import java.util.Calendar;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.tools.ItemUtil;

/**
 * 
 * <修造类别字段绑定类>
 * 
 * @author public2795
 * @version [版本号, 2019-1-9]
 * @since [产品/模块版本]
 */
public class FldSxtype extends JpoField {
	/**
	 * 根据修造类别设置送修单编号
	 * 
	 * @throws MroException
	 */
	@Override
	public void action() throws MroException {
		// TODO Auto-generated method stub
		super.action();
		Calendar date = Calendar.getInstance();
		String year = String.valueOf(date.get(Calendar.YEAR));
		String sxtype = this.getValue();
		String type = this.getJpo().getString("type");
		String TRANSFERMOVETYPE = this.getJpo().getString("TRANSFERMOVETYPE");
		// 杨毅 修改自动编号
		if (type.equalsIgnoreCase("SX")) {
			String transfernum = this.getJpo().getString("TRANSFERNUM");
			if (!StringUtil.isStrEmpty(transfernum)
					&& transfernum.startsWith("SX")) {
				int n = transfernum.indexOf("-", 3);
				transfernum = transfernum.substring(n + 1);
			}
			transfernum = "SX-" + sxtype + "-" + transfernum;
			this.getJpo().setValue("TRANSFERNUM", transfernum,
					GWConstant.P_NOACTION);
			if (sxtype.equalsIgnoreCase("GZ")) {
				this.getJpo().setValue("REPAIRORG", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setValue("ISSUEADDRESS", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setValue("RECEIVESTOREROOM", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setValue("RECEIVEADDRESS", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setValue("ISSUESTOREROOM",
						ItemUtil.ISSUESTOREROOM_ZXWXK);
				this.getJpo().setFieldFlag("PROJECTNUM", GWConstant.S_REQUIRED,
						false);
				this.getJpo().setValue("PROJECTNUM", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setFieldFlag("PROJECTNUM", GWConstant.S_READONLY,
						true);
			} else if (sxtype.equalsIgnoreCase("YXX")) {
				this.getJpo().setValue("REPAIRORG", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setValue("ISSUEADDRESS", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setValue("RECEIVESTOREROOM", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setValue("RECEIVEADDRESS", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setValue("ISSUESTOREROOM",
						ItemUtil.ISSUESTOREROOM_ZXWXK);
				this.getJpo().setFieldFlag("PROJECTNUM", GWConstant.S_REQUIRED,
						false);
				this.getJpo().setValue("PROJECTNUM", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setFieldFlag("PROJECTNUM", GWConstant.S_READONLY,
						true);
			} else if (sxtype.equalsIgnoreCase("KH")) {
				this.getJpo().setValue("ISSUESTOREROOM", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setValue("ISSUEADDRESS", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setValue("RECEIVESTOREROOM", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setValue("RECEIVEADDRESS", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setFieldFlag("PROJECTNUM", GWConstant.S_REQUIRED,
						false);
				this.getJpo().setValue("PROJECTNUM", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setFieldFlag("PROJECTNUM", GWConstant.S_READONLY,
						true);
			} else {
				this.getJpo().setValue("ISSUESTOREROOM", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setValue("ISSUEADDRESS", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setValue("RECEIVESTOREROOM", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setValue("RECEIVEADDRESS", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setFieldFlag("PROJECTNUM", GWConstant.S_REQUIRED,
						false);
				this.getJpo().setValue("PROJECTNUM", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setFieldFlag("PROJECTNUM", GWConstant.S_READONLY,
						true);
			}
		} else if (type.equalsIgnoreCase("ZXD")) {
			if (!TRANSFERMOVETYPE.equalsIgnoreCase("中心到中心")) {
				this.getJpo().setValue("ISSUESTOREROOM", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setValue("ISSUEADDRESS", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setValue("RECEIVESTOREROOM", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setValue("RECEIVEADDRESS", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			}
			if (sxtype.equalsIgnoreCase("GAIZ")) {
				this.getJpo().setFieldFlag("TRANSWORKORDERNUM",
						GWConstant.S_READONLY, false);
				this.getJpo().setValue("TRANSWORKORDERNUM", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setFieldFlag("TRANSWORKORDERNUM",
						GWConstant.S_REQUIRED, true);
			} else {

				this.getJpo().setFieldFlag("TRANSWORKORDERNUM",
						GWConstant.S_REQUIRED, false);
				this.getJpo().setValue("TRANSWORKORDERNUM", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setFieldFlag("TRANSWORKORDERNUM",
						GWConstant.S_READONLY, true);
			}
		}
	}

}
