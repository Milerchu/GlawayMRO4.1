package com.glaway.sddq.material.itemreq.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.tools.ItemUtil;
import com.glaway.sddq.tools.WorkorderUtil;

/**
 * 
 * 领料单中领用库房字段类
 * 
 * @author zzx
 * @version [版本号, 2018-6-11]
 * @since [物资/领料单]
 */
public class FldMprstoreroom extends JpoField {
	private static final long serialVersionUID = 1L;

	/**
	 * 映射赋值
	 * 
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
	 * 根据类型获取库房数据
	 * 
	 * @return
	 * @throws MroException
	 */
	@Override
	public IJpoSet getList() throws MroException {
		setListObject("LOCATIONS");
		String listSql = "";
		String mrptype = this.getJpo().getString("mprtype");
		String type = this.getJpo().getString("type");
		String APPLICANTBY=this.getJpo().getString("APPLICANTBY");
		APPLICANTBY=APPLICANTBY.toUpperCase();
		if (mrptype.equalsIgnoreCase("GZ")) {
			if (type.equalsIgnoreCase("领料")) {
				listSql = listSql
						+ "location not in (select location from locations where ISSAPSTORE=1) and erploc='"
						+ ItemUtil.ERPLOC_1020
						+ "' and STOREROOMLEVEL not in('"
						+ ItemUtil.STOREROOMLEVEL_ZXK + "','"
						+ ItemUtil.STOREROOMLEVEL_DWXK
						+ "') and LOCATIONTYPE='" + ItemUtil.LOCATIONTYPE_CG
						+ "'";
			}
			if (type.equalsIgnoreCase("退料")) {
				listSql = listSql
						+ "location not in (select location from locations where ISSAPSTORE=1) and erploc='"
						+ ItemUtil.ERPLOC_QTGZ + "' and STOREROOMLEVEL='"
						+ ItemUtil.STOREROOMLEVEL_XCK + "' and LOCATIONTYPE='"
						+ ItemUtil.LOCATIONTYPE_CG + "'";
			}
		} else if (mrptype.equalsIgnoreCase("CB")) {
			listSql = listSql
					+ "location not in (select location from locations where ISSAPSTORE=1) and LOCATIONTYPE='"
					+ ItemUtil.LOCATIONTYPE_CG + "'";
			// listSql = listSql +
			// "location not in (select location from locations where ISSAPSTORE=1) and erploc='"
			// + ItemUtil.ERPLOC_1020 + "' and LOCATIONTYPE='" +
			// ItemUtil.LOCATIONTYPE_CG + "'";
		} else if (mrptype.equalsIgnoreCase("JS")) {// 关 --入库单过滤
			IJpoSet deptset = MroServer.getMroServer().getJpoSet(
					"SYS_DEPT",
					MroServer.getMroServer().getSystemUserServer());
			deptset.setQueryWhere("DEPTNUM in (select DEPARTMENT from sys_person where PERSONID='"
					+ APPLICANTBY + "')");
			deptset.reset();
			if (!deptset.isEmpty()) {
				String deptnum = deptset.getJpo(0).getString("deptnum");
				String retrun = WorkorderUtil
						.getAbbreviationByDeptnum(deptnum);
				if (retrun.equalsIgnoreCase("PJGL")) {
					listSql = listSql
							+ "location not in (select location from locations where ISSAPSTORE=1) and ERPLOC in ('"
							+ ItemUtil.ERPLOC_QTDCL + "','" + ItemUtil.ERPLOC_QTGZ+ "') and STOREROOMLEVEL='"+ItemUtil.STOREROOMLEVEL_ZXK+"'";
				} else {
					listSql = listSql
							+ "location not in (select location from locations where ISSAPSTORE=1) and ERPLOC in ('"
							+ ItemUtil.ERPLOC_QTDCL + "','" + ItemUtil.ERPLOC_QTGZ+ "') and locsite in (select deptnum from PERSONDEPTMAP where personid='"+APPLICANTBY+"')";
				}
			} else {
				listSql = listSql
						+ "location not in (select location from locations where ISSAPSTORE=1) and ERPLOC in ('"
						+ ItemUtil.ERPLOC_QTDCL + "','" + ItemUtil.ERPLOC_QTGZ+ "') and STOREROOMLEVEL='"+ItemUtil.STOREROOMLEVEL_ZXK+"'";
			}
			
		} else if (mrptype.equalsIgnoreCase("TK")) {// 退库单
			IJpoSet deptset = MroServer.getMroServer().getJpoSet(
					"SYS_DEPT",
					MroServer.getMroServer().getSystemUserServer());
			deptset.setQueryWhere("DEPTNUM in (select DEPARTMENT from sys_person where PERSONID='"
					+ APPLICANTBY + "')");
			deptset.reset();
			if (!deptset.isEmpty()) {
				String deptnum = deptset.getJpo(0).getString("deptnum");
				String retrun = WorkorderUtil
						.getAbbreviationByDeptnum(deptnum);
				if (retrun.equalsIgnoreCase("PJGL")) {
					listSql = listSql
							+ "location not in (select location from locations where ISSAPSTORE=1) and ERPLOC in ('"
							+ ItemUtil.ERPLOC_QTDCL + "','" + ItemUtil.ERPLOC_QTGZ+ "') and STOREROOMLEVEL='"+ItemUtil.STOREROOMLEVEL_ZXK+"'";
				} else {
					listSql = listSql
							+ "location not in (select location from locations where ISSAPSTORE=1) and ERPLOC in ('"
							+ ItemUtil.ERPLOC_QTDCL + "','" + ItemUtil.ERPLOC_QTGZ+ "') and locsite in (select deptnum from PERSONDEPTMAP where personid='"+APPLICANTBY+"')";
				}
			} else {
				listSql = listSql
						+ "location not in (select location from locations where ISSAPSTORE=1) and ERPLOC in ('"
						+ ItemUtil.ERPLOC_QTDCL + "','" + ItemUtil.ERPLOC_QTGZ+ "') and STOREROOMLEVEL='"+ItemUtil.STOREROOMLEVEL_ZXK+"'";
			}
			
		}else if (mrptype.equalsIgnoreCase("QT")) {// 客户料退库领料单
			IJpoSet deptset = MroServer.getMroServer().getJpoSet(
					"SYS_DEPT",
					MroServer.getMroServer().getSystemUserServer());
			deptset.setQueryWhere("DEPTNUM in (select DEPARTMENT from sys_person where PERSONID='"
					+ APPLICANTBY + "')");
			deptset.reset();
			if (!deptset.isEmpty()) {
				String deptnum = deptset.getJpo(0).getString("deptnum");
				String retrun = WorkorderUtil
						.getAbbreviationByDeptnum(deptnum);
				if (retrun.equalsIgnoreCase("PJGL")) {
					listSql = listSql
							+ "location not in (select location from locations where ISSAPSTORE=1) and ERPLOC in ('"
							+ ItemUtil.ERPLOC_1020 + "') and LOCATIONTYPE='"+ItemUtil.LOCATIONTYPE_CG+"'  and STOREROOMLEVEL='"+ItemUtil.STOREROOMLEVEL_ZXK+"'";
				} else {
					listSql = listSql
							+ "location not in (select location from locations where ISSAPSTORE=1) and ERPLOC in ('"
							+ ItemUtil.ERPLOC_1020 + "') and LOCATIONTYPE='"+ItemUtil.LOCATIONTYPE_CG+"' and locsite in (select deptnum from PERSONDEPTMAP where personid='"+APPLICANTBY+"')";
				}
			} else {
				listSql = listSql
						+ "location not in (select location from locations where ISSAPSTORE=1) and ERPLOC in ('"
						+ ItemUtil.ERPLOC_1020 + "') and LOCATIONTYPE='"+ItemUtil.LOCATIONTYPE_CG+"'  and STOREROOMLEVEL='"+ItemUtil.STOREROOMLEVEL_ZXK+"'";
			}
			
		}else {
			listSql = listSql
					+ "location not in (select location from locations where ISSAPSTORE=1) and erploc='"
					+ ItemUtil.ERPLOC_1030 + "'";
		}

		if (!listSql.equals("")) {
			listSql=listSql+"and status='正常'";
			setListWhere(listSql);
		}

		return super.getList();
	}

	/**
	 * 赋值接收人
	 * 
	 * @throws MroException
	 */
	@Override
	public void action() throws MroException {
		// TODO Auto-generated method stub
		super.action();
		IJpoSet locset = this.getJpo().getJpoSet("MPRSTOREROOM");
		if (!locset.isEmpty()) {
			String KEEPER = locset.getJpo(0).getString("KEEPER");
			this.getJpo().setValue("endby", KEEPER,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			this.getJpo().setValue("RECIVEBY", KEEPER,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);

		}
		String APPLICANTBY=this.getJpo().getString("APPLICANTBY");
		APPLICANTBY=APPLICANTBY.toUpperCase();
		String mrptype = this.getJpo().getString("mprtype");
		String TYPE = this.getJpo().getString("TYPE");
		if (mrptype.equalsIgnoreCase("QT")) {// 客户料退库领料单
			String MPRSTOREROOM=this.getValue();
			String locdesc="";
			IJpoSet locationset = this.getJpo().getJpoSet("MPRSTOREROOM");
			if(!locationset.isEmpty()){
				locdesc=locationset.getJpo(0).getString("description");
			}
			IJpoSet deptset = MroServer.getMroServer().getJpoSet(
					"SYS_DEPT",
					MroServer.getMroServer().getSystemUserServer());
			deptset.setQueryWhere("DEPTNUM in (select DEPARTMENT from sys_person where PERSONID='"
					+ APPLICANTBY + "')");
			deptset.reset();
			if (!deptset.isEmpty()) {
				String deptnum = deptset.getJpo(0).getString("deptnum");
				String retrun = WorkorderUtil
						.getAbbreviationByDeptnum(deptnum);
				if (retrun.equalsIgnoreCase("PJGL")) {
					if(locdesc.equalsIgnoreCase("")){
						this.getJpo().setValue("instoreroom", "QT1080",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					}else{
						IJpoSet inlocationset = MroServer.getMroServer().getJpoSet("locations",MroServer.getMroServer().getSystemUserServer());
						inlocationset.setUserWhere("location not in (select location from locations where ISSAPSTORE=1) and " +
								"ERPLOC='"+ItemUtil.ERPLOC_QTDCL+"' and LOCATIONTYPE='"+ItemUtil.LOCATIONTYPE_CG+"'  " +
										"and STOREROOMLEVEL='"+ItemUtil.STOREROOMLEVEL_ZXK+"' and description like '%"+locdesc+"%'");
						if(inlocationset.isEmpty()){
							this.getJpo().setValue("instoreroom", "QT1080",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						}else{
							String instoreroom=inlocationset.getJpo(0).getString("location");
							this.getJpo().setValue("instoreroom", instoreroom,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						}
					}
				} else {
					IJpoSet inlocationset = MroServer.getMroServer().getJpoSet("locations",MroServer.getMroServer().getSystemUserServer());
					inlocationset.setUserWhere("location not in (select location from locations where ISSAPSTORE=1) and " +
							"ERPLOC='"+ItemUtil.ERPLOC_QTDCL+"' and LOCATIONTYPE='"+ItemUtil.LOCATIONTYPE_CG+"'  " +
									"and locsite in (select deptnum from PERSONDEPTMAP where personid='"+APPLICANTBY+"')");
					if(inlocationset.isEmpty()){
						throw new MroException("找不到对应的其他库");
					}else{
						String instoreroom=inlocationset.getJpo(0).getString("location");
						this.getJpo().setValue("instoreroom", instoreroom,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					}
				}
			} else {
				IJpoSet storeroomset = MroServer.getMroServer().getJpoSet("locations",MroServer.getMroServer().getSystemUserServer());
				storeroomset.setUserWhere("location='"+MPRSTOREROOM+"'");
				if(!storeroomset.isEmpty()){
					String STOREROOMLEVEL=storeroomset.getJpo(0).getString("STOREROOMLEVEL");
					if(STOREROOMLEVEL.equalsIgnoreCase(ItemUtil.STOREROOMLEVEL_ZXK)){
						if(locdesc.equalsIgnoreCase("")){
							this.getJpo().setValue("instoreroom", "QT1080",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						}else{
							IJpoSet inlocationset = MroServer.getMroServer().getJpoSet("locations",MroServer.getMroServer().getSystemUserServer());
							inlocationset.setUserWhere("location not in (select location from locations where ISSAPSTORE=1) and " +
									"ERPLOC='"+ItemUtil.ERPLOC_QTDCL+"' and LOCATIONTYPE='"+ItemUtil.LOCATIONTYPE_CG+"'  " +
											"and STOREROOMLEVEL='"+ItemUtil.STOREROOMLEVEL_ZXK+"' and description like '%"+locdesc+"%'");
							if(inlocationset.isEmpty()){
								this.getJpo().setValue("instoreroom", "QT1080",GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							}else{
								String instoreroom=inlocationset.getJpo(0).getString("location");
								this.getJpo().setValue("instoreroom", instoreroom,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							}
						}
					}
				}
			}
			
		}
		if (mrptype.equalsIgnoreCase("JX")) {
			if(TYPE.equalsIgnoreCase("领料")){
				String location=this.getValue();
				IJpoSet mprlineset = this.getJpo().getJpoSet("mprline");
				if(!mprlineset.isEmpty()){
					for(int i=0;i<mprlineset.count();i++){
						IJpo mprline=mprlineset.getJpo(i);
						String itemnum=mprline.getString("itemnum");
						IJpoSet inventoryset = MroServer.getMroServer().getJpoSet("sys_inventory",MroServer.getMroServer().getSystemUserServer());
						inventoryset.setUserWhere("itemnum='"+itemnum+"' and location='"+location+"'");
						if(inventoryset.isEmpty()){
							mprline.setValue("curbal", 0,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						}else{
							double curbal=inventoryset.getJpo(0).getDouble("curbal");
							mprline.setValue("curbal", curbal,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
						}
					}
				}
			}
		}
	}

}
