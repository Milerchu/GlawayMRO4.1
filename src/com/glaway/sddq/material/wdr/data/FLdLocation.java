package com.glaway.sddq.material.wdr.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.tools.ItemUtil;
import com.glaway.sddq.tools.WorkorderUtil;
/**
 * 
 * <处置管理-库房字段类>
 * 
 * @author  public2795
 * @version  [版本号, 2019-3-18]
 * @since  [产品/模块版本]
 */
public class FLdLocation extends JpoField {
/**
 * 库房映射赋值
 * @throws MroException
 */
	@Override
	public void init() throws MroException {
		super.init();
		String[] thisAttrs = { this.getFieldName() };
		String[] srcAttrs = { "LOCATION" };
		setLookupMap(thisAttrs, srcAttrs);
	}
/**
 * 根据处置类型过滤库房数据
 * @return
 * @throws MroException
 */
@Override
public IJpoSet getList() throws MroException {
	// TODO Auto-generated method stub
	setListObject("LOCATIONS");
	String listSql = "";
	String dealtype = this.getJpo().getString("dealtype");
	String createby=this.getJpo().getString("createby");
	createby=createby.toUpperCase();
	if(dealtype.equalsIgnoreCase("报废")){
		listSql = listSql
				+ "location in ('QT2013','Y1081','GZ1957')";
	}
	if(dealtype.equalsIgnoreCase("再利用")){
		IJpoSet deptset = MroServer.getMroServer().getJpoSet(
				"SYS_DEPT",
				MroServer.getMroServer().getSystemUserServer());
		deptset.setUserWhere("DEPTNUM in (select DEPARTMENT from sys_person where PERSONID='"
				+ createby + "')");
		deptset.reset();
		if (!deptset.isEmpty()) {
			String deptnum = deptset.getJpo(0).getString("deptnum");
			String retrun = WorkorderUtil
					.getAbbreviationByDeptnum(deptnum);
			if (retrun.equalsIgnoreCase("PJGL")) {
				listSql = listSql
						+ "location not in (select location from locations where ISSAPSTORE=1) and ERPLOC in ('"
						+ ItemUtil.ERPLOC_1020 + "') and STOREROOMLEVEL='"+ItemUtil.STOREROOMLEVEL_ZXK+"' and LOCATIONTYPE='"+ItemUtil.LOCATIONTYPE_CG+"'";
			} else {
				listSql = listSql
						+ "location not in (select location from locations where ISSAPSTORE=1) and ERPLOC in ('"
						+ ItemUtil.ERPLOC_1020 + "') and locsite in (select deptnum from PERSONDEPTMAP where personid='"+createby+"') and LOCATIONTYPE='"+ItemUtil.LOCATIONTYPE_CG+"'";
			}
		} else {
			listSql = listSql
					+ "location not in (select location from locations where ISSAPSTORE=1) and ERPLOC in ('"
					+ ItemUtil.ERPLOC_1020 + "') and STOREROOMLEVEL='"+ItemUtil.STOREROOMLEVEL_ZXK+"' and LOCATIONTYPE='"+ItemUtil.LOCATIONTYPE_CG+"'";
		}
	}
	if (!listSql.equals("")) {
		listSql=listSql+"and status='正常'";
		setListWhere(listSql);
	}
	return super.getList();
}
@Override
public void action() throws MroException {
	// TODO Auto-generated method stub
	String erploc=this.getJpo().getJpoSet("location").getJpo().getString("erploc");
	if(erploc.equalsIgnoreCase("改造物料库")){
		this.getJpo().setFieldFlag("WORKORDERNUM", GWConstant.S_REQUIRED, false);
		this.getJpo().setValue("WORKORDERNUM", "",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		this.getJpo().setValue("COSTCENTER", "",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		this.getJpo().setFieldFlag("WORKORDERNUM", GWConstant.S_READONLY, true);
		this.getJpo().setFieldFlag("TRANSPLANNUM", GWConstant.S_READONLY, false);
		this.getJpo().setValue("TRANSPLANNUM", "",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		this.getJpo().setFieldFlag("TRANSPLANNUM", GWConstant.S_REQUIRED, true);
	}else{
		this.getJpo().setFieldFlag("WORKORDERNUM", GWConstant.S_READONLY, false);
		this.getJpo().setValue("WORKORDERNUM", "",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		this.getJpo().setValue("COSTCENTER", "",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		this.getJpo().setFieldFlag("WORKORDERNUM", GWConstant.S_REQUIRED, true);
		this.getJpo().setFieldFlag("TRANSPLANNUM", GWConstant.S_REQUIRED, false);
		this.getJpo().setValue("TRANSPLANNUM", "",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		this.getJpo().setFieldFlag("TRANSPLANNUM", GWConstant.S_READONLY, true);
	}
	super.action();
}
	

}
