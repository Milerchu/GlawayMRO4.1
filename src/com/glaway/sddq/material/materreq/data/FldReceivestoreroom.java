package com.glaway.sddq.material.materreq.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.tools.ItemUtil;

/**
 * 
 * 配件申请选择库房字段类
 * 
 * @author qhsong
 * @version [GlawayMro4.0, 2017-11-7]
 * @since [GlawayMro4.0/配件申请]
 */
public class FldReceivestoreroom extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 映射赋值
	 * 
	 * @throws MroException
	 */
	@Override
	public void init() throws MroException {
		super.init();
		String[] thisAttrs = { this.getFieldName(), "RECEIVEKEEPER",
				"RECEIVEPHONE" };
		String[] srcAttrs = { "LOCATION", "KEEPER", "PRIMARYPHONE" };
		setLookupMap(thisAttrs, srcAttrs);
	}

	/**
	 * 过滤库房数据
	 * 
	 * @return
	 * @throws MroException
	 */
	@Override
	public IJpoSet getList() throws MroException {
		setListObject("LOCATIONS");
		String listSql = "";
		String loginid = getUserServer().getUserInfo().getLoginID();
		loginid = loginid.toUpperCase();
		IJpoSet PERSONDEPTMAPset = MroServer.getMroServer()
				.getJpoSet("PERSONDEPTMAP",
						MroServer.getMroServer().getSystemUserServer());
		PERSONDEPTMAPset.setUserWhere("personid='" + loginid + "'");
		if (!PERSONDEPTMAPset.isEmpty()) {
			String deptnum = PERSONDEPTMAPset.getJpo(0).getString("deptnum");
			if (!deptnum.equalsIgnoreCase("")) {
				listSql = listSql
						+ "keeper='"
						+ loginid
						+ "' and ERPLOC IN ('"
						+ ItemUtil.ERPLOC_1020
						+ "','"
						+ ItemUtil.ERPLOC_1030
						+ "') AND STOREROOMGRADE='二级' and LOCATIONTYPE in('"
						+ ItemUtil.LOCATIONTYPE_CG
						+ "','"
						+ ItemUtil.LOCATIONTYPE_CGJX
						+ "','"
						+ ItemUtil.LOCATIONTYPE_DCJX
						+ "','"
						+ ItemUtil.LOCATIONTYPE_JCJX
						+ "') or LOCSITE in(select deptnum from PERSONDEPTMAP where personid='"
						+ loginid + "') and ERPLOC IN ('"
						+ ItemUtil.ERPLOC_1020 + "','" + ItemUtil.ERPLOC_1030
						+ "') AND STOREROOMGRADE='二级' and LOCATIONTYPE in('"
						+ ItemUtil.LOCATIONTYPE_CG + "','"
						+ ItemUtil.LOCATIONTYPE_CGJX + "','"
						+ ItemUtil.LOCATIONTYPE_DCJX + "','"
						+ ItemUtil.LOCATIONTYPE_JCJX + "')";
			} else {
				listSql = listSql + "ERPLOC IN ('" + ItemUtil.ERPLOC_1020
						+ "','" + ItemUtil.ERPLOC_1030
						+ "') AND STOREROOMGRADE='二级' and LOCATIONTYPE in('"
						+ ItemUtil.LOCATIONTYPE_CG + "','"
						+ ItemUtil.LOCATIONTYPE_CGJX + "','"
						+ ItemUtil.LOCATIONTYPE_DCJX + "','"
						+ ItemUtil.LOCATIONTYPE_JCJX + "')";
			}
		}
		if (!listSql.equals("")) {
			listSql=listSql+"and status='正常'";
			setListWhere(listSql);
		}

		return super.getList();
	}

	/**
	 * 触发赋值库房地址，配件专员
	 * 
	 * @throws MroException
	 */
	@Override
	public void action() throws MroException {
		// TODO Auto-generated method stub
		super.action();
		String RECEIVESTOREROOM = this.getValue();
		if (!RECEIVESTOREROOM.isEmpty()) {
			String erploc = this.getJpo().getJpoSet("RECEIVESTOREROOM")
					.getJpo().getString("erploc");
			if (erploc.equalsIgnoreCase(ItemUtil.ERPLOC_1020)) {
				IJpoSet SYS_PERSONGROUPTEAM = MroServer.getMroServer()
						.getJpoSet("SYS_PERSONGROUPTEAM",
								MroServer.getMroServer().getSystemUserServer());
				SYS_PERSONGROUPTEAM.setUserWhere("persongroup='PJZY1020'");
				SYS_PERSONGROUPTEAM.reset();
				if (!SYS_PERSONGROUPTEAM.isEmpty()) {
					this.getJpo().setValue(
							"LOCPERSON",
							SYS_PERSONGROUPTEAM.getJpo(0).getString(
									"RESPPARTYGROUP"),
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				}
			}
			if (erploc.equalsIgnoreCase(ItemUtil.ERPLOC_1030)) {
				if (RECEIVESTOREROOM.equalsIgnoreCase("X108")) {
					IJpoSet SYS_PERSONGROUPTEAM = MroServer.getMroServer()
							.getJpoSet(
									"SYS_PERSONGROUPTEAM",
									MroServer.getMroServer()
											.getSystemUserServer());
					SYS_PERSONGROUPTEAM.setUserWhere("persongroup='PJZY108'");
					SYS_PERSONGROUPTEAM.reset();
					if (!SYS_PERSONGROUPTEAM.isEmpty()) {
						this.getJpo().setValue(
								"LOCPERSON",
								SYS_PERSONGROUPTEAM.getJpo(0).getString(
										"RESPPARTYGROUP"),
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					}
				} else {
					IJpoSet SYS_PERSONGROUPTEAM = MroServer.getMroServer()
							.getJpoSet(
									"SYS_PERSONGROUPTEAM",
									MroServer.getMroServer()
											.getSystemUserServer());
					SYS_PERSONGROUPTEAM.setUserWhere("persongroup='PJZY1030'");
					SYS_PERSONGROUPTEAM.reset();
					if (!SYS_PERSONGROUPTEAM.isEmpty()) {
						this.getJpo().setValue(
								"LOCPERSON",
								SYS_PERSONGROUPTEAM.getJpo(0).getString(
										"RESPPARTYGROUP"),
								GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					}
				}
			}
			/* 选择库房代入默认地址 */
			IJpoSet locaddressSet = this.getJpo().getJpoSet("$LOCADDRESS",
					"locaddress",
					"LOCATION='" + RECEIVESTOREROOM + "' and isaddress='是'");
			if (!locaddressSet.isEmpty()) {
				IJpo locaddress = locaddressSet.getJpo(0);
				this.getJpo().setValue("RECEIVEADDRESS",
						locaddress.getString("address"),
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			}
		}

	}

}
