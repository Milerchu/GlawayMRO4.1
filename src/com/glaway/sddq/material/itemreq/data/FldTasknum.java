package com.glaway.sddq.material.itemreq.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;

/**
 * 检修领料单中工单编号字段类
 * 
 * @author zzx
 * @version [版本号, 2018-6-8]
 * @since [物资/领料单]
 */
public class FldTasknum extends JpoField {
	/**
	 * 映射赋值
	 * 
	 * @throws MroException
	 */
	@Override
	public void init() throws MroException {
		super.init();
		String[] thisAttrs = { this.getFieldName() };
		String[] srcAttrs = { "JXTASKNUM" };
		setLookupMap(thisAttrs, srcAttrs);
	}

	/**
	 * 过滤工单信息
	 * 
	 * @return
	 * @throws MroException
	 */
	@Override
	public IJpoSet getList() throws MroException {
		setListObject("JXTASKORDER");
		String listSql = "";
		listSql = "status!='已完成'";
		setListWhere(listSql);
		return super.getList();
	}

	/**
	 * 触发赋值
	 * 
	 * @throws MroException
	 */
	@Override
	public void action() throws MroException {
		// TODO Auto-generated method stub
		super.action();
		if (this.getJpo().getAppName().equals("JXMPR")) {
			String tasknum = this.getValue();
			IJpoSet JXTASKORDERSET = MroServer.getMroServer().getJpoSet(
					"JXTASKORDER",
					MroServer.getMroServer().getSystemUserServer());
			JXTASKORDERSET.setQueryWhere("JXTASKNUM='" + tasknum + "'");
			String PRODUCTIONORDERNUM = JXTASKORDERSET.getJpo(0).getString(
					"PRODUCTIONORDERNUM");
			String PROJECTNUM = JXTASKORDERSET.getJpo(0)
					.getString("PROJECTNUM");
			String WHICHOFFICE = JXTASKORDERSET.getJpo(0).getString(
					"WHICHOFFICE");
			if (!PROJECTNUM.isEmpty()) {
				IJpoSet PROJECTINFOSET = MroServer.getMroServer().getJpoSet(
						"PROJECTINFO",
						MroServer.getMroServer().getSystemUserServer());
				PROJECTINFOSET.setQueryWhere("PROJECTNUM='" + PROJECTNUM + "'");
				String WORKORDERNUM = PROJECTINFOSET.getJpo(0).getString(
						"WORKORDERNUM");
				this.getJpo().setValue("PROJECTNUM", PROJECTNUM,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setValue("WORKORDERNUM", WORKORDERNUM,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			}
			this.getJpo().setValue("PRODUCTIONORDERNUM", PRODUCTIONORDERNUM,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			this.getJpo().setValue("WHICHOFFICE", WHICHOFFICE,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		}
	}

}
