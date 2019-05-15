package com.glaway.sddq.material.transfer.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.tools.ItemUtil;

/**
 * 
 * <移动类型字段绑定类>
 * 
 * @author public2795
 * @version [版本号, 2019-1-9]
 * @since [产品/模块版本]
 */
public class FldTransferMoveType extends JpoField {
	/**
	 * 根据登陆人部门过滤不通的移动类型
	 * 
	 * @return
	 * @throws MroException
	 */
	@Override
	public IJpoSet getList() throws MroException {
		IJpoSet domainSet = null;
		String type = this.getJpo().getString("type");
		String loginid = getUserServer().getUserInfo().getLoginID();
		loginid = loginid.toUpperCase();
		IJpoSet personset = MroServer.getMroServer().getJpoSet("PERSONDEPTMAP",
				MroServer.getMroServer().getSystemUserServer());
		personset.setQueryWhere("personid='" + loginid + "'");

		if (!personset.isEmpty()) {
			if (type.equalsIgnoreCase("GZZXD")) {
				String deptnum = personset.getJpo(0).getString("deptnum");
				if (!deptnum.equalsIgnoreCase("")) {
					domainSet = getUserServer().getJpoSet("SYS_ALNDOMAIN",
							"domainid='TRANSFERMOVETYPE'");
					domainSet.setUserWhere(domainSet.getUserWhere()
							+ " and VALUE in ('"
							+ ItemUtil.TRANSFERMOVETYPE_XTOZ + "')");
					domainSet.reset();
				} else {
					domainSet = getUserServer().getJpoSet("SYS_ALNDOMAIN",
							"domainid='TRANSFERMOVETYPE'");
					domainSet.setUserWhere(domainSet.getUserWhere()
							+ " and VALUE in ('"
							+ ItemUtil.TRANSFERMOVETYPE_ZTOX + "','"
							+ ItemUtil.TRANSFERMOVETYPE_ZTOZ + "')");// 过滤-库房类型为常规
					domainSet.reset();
				}
			} else {
				String deptnum = personset.getJpo(0).getString("deptnum");
				if (!deptnum.equalsIgnoreCase("")) {
					domainSet = getUserServer().getJpoSet("SYS_ALNDOMAIN",
							"domainid='TRANSFERMOVETYPE'");
					domainSet.setUserWhere(domainSet.getUserWhere()
							+ " and VALUE in ('"
							+ ItemUtil.TRANSFERMOVETYPE_XTOX + "','"
							+ ItemUtil.TRANSFERMOVETYPE_XTOZ + "')");
					domainSet.reset();
				} else {
					domainSet = getUserServer().getJpoSet("SYS_ALNDOMAIN",
							"domainid='TRANSFERMOVETYPE'");
					domainSet.setUserWhere(domainSet.getUserWhere()
							+ " and VALUE in ('"
							+ ItemUtil.TRANSFERMOVETYPE_ZTOX + "','"
							+ ItemUtil.TRANSFERMOVETYPE_ZTOZ + "')");// 过滤-库房类型为常规
					domainSet.reset();
				}
			}

			return domainSet;
		} else {
			domainSet = getUserServer().getJpoSet("SYS_ALNDOMAIN",
					"domainid='TRANSFERMOVETYPE'");
			domainSet.setUserWhere(domainSet.getUserWhere()
					+ " and VALUE in ('" + ItemUtil.TRANSFERMOVETYPE_ZTOX
					+ "','" + ItemUtil.TRANSFERMOVETYPE_ZTOZ + "')");// 过滤-库房类型为常规
			domainSet.reset();
			return domainSet;
		}

	}

	/**
	 * 触发相关字段只读必填
	 * 
	 * @throws MroException
	 */
	@Override
	public void action() throws MroException {
		// TODO Auto-generated method stub
		super.action();
		String transfermovetype = this.getValue();
		String type = this.getJpo().getString("type");
		// 装箱单移动类型为现场到中心，不可选择配件申请
		String CREATEBY = this.getJpo().getString("CREATEBY");
		this.getJpo().setValue("PACKBY", "",
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		this.getJpo().setValue("CHECKBY", "",
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		this.getJpo().setValue("SENDBY", "",
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		if (transfermovetype.equalsIgnoreCase(ItemUtil.TRANSFERMOVETYPE_XTOZ)) { // 现场到中心
			if (type.equalsIgnoreCase("GZZXD")) {
				this.getJpo().setValue("ISSUESTOREROOM", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setValue("RECEIVESTOREROOM", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			} else {
				this.getJpo().setFieldFlag("mrnum", GWConstant.S_REQUIRED,
						false);
				this.getJpo().setValue("mrnum", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo()
						.setFieldFlag("mrnum", GWConstant.S_READONLY, true);
				this.getJpo().setValue("RECEIVESTOREROOM", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setValue("RECEIVEBY", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setValue("RECEIVEADDRESS", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setFieldFlag("ISSUESTOREROOM",
						GWConstant.S_READONLY, false);
				this.getJpo().setValue("ISSUESTOREROOM", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setFieldFlag("ISSUESTOREROOM",
						GWConstant.S_REQUIRED, true);
				this.getJpo().setFieldFlag("ISSUEADDRESS",
						GWConstant.S_READONLY, false);
				this.getJpo().setValue("ISSUEADDRESS", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setFieldFlag("ISSUEADDRESS",
						GWConstant.S_REQUIRED, true);
				this.getJpo()
						.setFieldFlag("ismr", GWConstant.S_REQUIRED, false);
				this.getJpo().setValue("ismr", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setFieldFlag("ismr", GWConstant.S_READONLY, true);

				this.getJpo().setFieldFlag("RECEIVESTOREROOM",
						GWConstant.S_READONLY, false);
				this.getJpo().setValue("RECEIVESTOREROOM", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setFieldFlag("RECEIVESTOREROOM",
						GWConstant.S_REQUIRED, true);
				this.getJpo().setFieldFlag("RECEIVEADDRESS",
						GWConstant.S_READONLY, false);
				this.getJpo().setValue("RECEIVEADDRESS", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setFieldFlag("RECEIVEADDRESS",
						GWConstant.S_REQUIRED, true);
				this.getJpo().setFieldFlag("isfxfy", GWConstant.S_REQUIRED,
						false);
				this.getJpo().setFieldFlag("isfxfy", GWConstant.S_READONLY,
						true);

				// 装箱单移动类型是现场到中心的，装箱人、核对人、发运人都是申请人
				this.getJpo().setValue("PACKBY", CREATEBY,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setValue("CHECKBY", CREATEBY,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setFieldFlag("SENDBY", GWConstant.S_READONLY,
						false);
				this.getJpo().setValue("SENDBY", CREATEBY,
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setFieldFlag("SENDBY", GWConstant.S_REQUIRED,
						true);
				this.getJpo().setFieldFlag("sxtype", GWConstant.S_READONLY,
						false);
				this.getJpo().setValue("sxtype", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setFieldFlag("sxtype", GWConstant.S_REQUIRED,
						true);
			}

		}
		if (transfermovetype.equalsIgnoreCase(ItemUtil.TRANSFERMOVETYPE_XTOX)) { // 现场到现场
			this.getJpo().setValue("mrnum", "",
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			this.getJpo().setValue("RECEIVESTOREROOM", "",
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			this.getJpo().setValue("RECEIVEADDRESS", "",
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			this.getJpo().setValue("RECEIVEBY", "",
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			this.getJpo().setFieldFlag("RECEIVESTOREROOM",
					GWConstant.S_READONLY, false);
			this.getJpo().setFieldFlag("ISSUESTOREROOM", GWConstant.S_READONLY,
					false);
			this.getJpo().setValue("ISSUESTOREROOM", "",
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			this.getJpo().setFieldFlag("ISSUESTOREROOM", GWConstant.S_REQUIRED,
					true);
			this.getJpo().setFieldFlag("ISSUEADDRESS", GWConstant.S_READONLY,
					false);
			this.getJpo().setValue("ISSUEADDRESS", "",
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			this.getJpo().setFieldFlag("ISSUEADDRESS", GWConstant.S_REQUIRED,
					true);
			this.getJpo().setFieldFlag("ismr", GWConstant.S_REQUIRED, false);
			this.getJpo().setValue("ismr", "",
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			this.getJpo().setFieldFlag("ismr", GWConstant.S_READONLY, true);
			this.getJpo().setFieldFlag("RECEIVESTOREROOM",
					GWConstant.S_READONLY, false);
			this.getJpo().setValue("RECEIVESTOREROOM", "",
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			this.getJpo().setFieldFlag("RECEIVESTOREROOM",
					GWConstant.S_REQUIRED, true);
			this.getJpo().setFieldFlag("RECEIVEADDRESS", GWConstant.S_READONLY,
					false);
			this.getJpo().setValue("RECEIVEADDRESS", "",
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			this.getJpo().setFieldFlag("RECEIVEADDRESS", GWConstant.S_REQUIRED,
					true);
			// 装箱单移动类型是现场到现场的，装箱人、核对人、发运人都是申请人
			this.getJpo().setValue("PACKBY", CREATEBY,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			this.getJpo().setValue("CHECKBY", CREATEBY,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			this.getJpo().setFieldFlag("SENDBY", GWConstant.S_READONLY, false);
			this.getJpo().setValue("SENDBY", CREATEBY,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			this.getJpo().setFieldFlag("SENDBY", GWConstant.S_REQUIRED, true);
			this.getJpo().setFieldFlag("sxtype", GWConstant.S_REQUIRED, false);
			this.getJpo().setValue("sxtype", "",
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			this.getJpo().setFieldFlag("sxtype", GWConstant.S_READONLY, true);
			this.getJpo().setFieldFlag("isfxfy", GWConstant.S_REQUIRED, false);
			this.getJpo().setFieldFlag("isfxfy", GWConstant.S_READONLY, true);
		}
		if (transfermovetype.equalsIgnoreCase(ItemUtil.TRANSFERMOVETYPE_ZTOX)) { // 中心到现场
			if (type.equalsIgnoreCase("GZZXD")) {
				this.getJpo().setValue("ISSUESTOREROOM", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setValue("RECEIVESTOREROOM", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			} else {
				this.getJpo().setValue("RECEIVESTOREROOM", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setValue("RECEIVEADDRESS", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setValue("PACKBY", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setValue("CHECKBY", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setFieldFlag("SENDBY", GWConstant.S_READONLY,
						false);
				this.getJpo().setValue("SENDBY", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setValue("RECEIVEBY", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				// this.getJpo().setValue("ISSUESTOREROOM",
				// ItemUtil.ISSUESTOREROOM_ZXWXK);
				// this.getJpo().setFieldFlag("ISSUESTOREROOM",
				// GWConstant.S_READONLY, true);
				this.getJpo().setFieldFlag("RECEIVESTOREROOM",
						GWConstant.S_READONLY, false);
				this.getJpo().setValue("mrnum", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setFieldFlag("mrnum", GWConstant.S_READONLY,
						false);
				this.getJpo().setFieldFlag("SENDBY", GWConstant.S_REQUIRED,
						true);
				this.getJpo().setFieldFlag("ISSUESTOREROOM",
						GWConstant.S_REQUIRED, false);
				this.getJpo().setValue("ISSUESTOREROOM", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setFieldFlag("ISSUESTOREROOM",
						GWConstant.S_READONLY, true);
				this.getJpo().setFieldFlag("ISSUEADDRESS",
						GWConstant.S_REQUIRED, false);
				this.getJpo().setValue("ISSUEADDRESS", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setFieldFlag("ISSUEADDRESS",
						GWConstant.S_READONLY, true);
				this.getJpo()
						.setFieldFlag("ismr", GWConstant.S_READONLY, false);
				this.getJpo().setValue("ismr", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setFieldFlag("ismr", GWConstant.S_REQUIRED, true);
				this.getJpo().setFieldFlag("sxtype", GWConstant.S_REQUIRED,
						false);
				this.getJpo().setValue("sxtype", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setFieldFlag("sxtype", GWConstant.S_READONLY,
						true);
				this.getJpo().setFieldFlag("RECEIVESTOREROOM",
						GWConstant.S_READONLY, false);
				this.getJpo().setValue("RECEIVESTOREROOM", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setFieldFlag("RECEIVESTOREROOM",
						GWConstant.S_REQUIRED, true);
				this.getJpo().setFieldFlag("RECEIVEADDRESS",
						GWConstant.S_READONLY, false);
				this.getJpo().setValue("RECEIVEADDRESS", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setFieldFlag("RECEIVEADDRESS",
						GWConstant.S_REQUIRED, true);
				this.getJpo().setFieldFlag("isfxfy", GWConstant.S_READONLY,
						false);
				this.getJpo().setFieldFlag("isfxfy", GWConstant.S_REQUIRED,
						true);
			}

		}
		if (transfermovetype.equalsIgnoreCase(ItemUtil.TRANSFERMOVETYPE_ZTOZ)) { // 中心到中心
			if (type.equalsIgnoreCase("GZZXD")) {
				this.getJpo().setValue("ISSUESTOREROOM", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setValue("RECEIVESTOREROOM", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			} else {
				this.getJpo().setValue("RECEIVESTOREROOM", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setValue("RECEIVEADDRESS", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setValue("PACKBY", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setValue("CHECKBY", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setValue("SENDBY", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setValue("RECEIVEBY", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setFieldFlag("RECEIVESTOREROOM",
						GWConstant.S_READONLY, false);
				this.getJpo().setFieldFlag("mrnum", GWConstant.S_REQUIRED,
						false);
				this.getJpo().setValue("mrnum", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo()
						.setFieldFlag("mrnum", GWConstant.S_READONLY, true);
				this.getJpo().setFieldFlag("SENDBY", GWConstant.S_REQUIRED,
						false);
				this.getJpo().setFieldFlag("SENDBY", GWConstant.S_READONLY,
						true);
				this.getJpo().setFieldFlag("ISSUESTOREROOM",
						GWConstant.S_READONLY, false);
				this.getJpo().setValue("ISSUESTOREROOM", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setFieldFlag("ISSUESTOREROOM",
						GWConstant.S_REQUIRED, true);
				this.getJpo().setFieldFlag("ISSUEADDRESS",
						GWConstant.S_READONLY, false);
				this.getJpo().setValue("ISSUEADDRESS", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setFieldFlag("ISSUEADDRESS",
						GWConstant.S_REQUIRED, true);
				this.getJpo()
						.setFieldFlag("ismr", GWConstant.S_REQUIRED, false);
				this.getJpo().setValue("ismr", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setFieldFlag("ismr", GWConstant.S_READONLY, true);
				this.getJpo().setFieldFlag("sxtype", GWConstant.S_REQUIRED,
						false);
				this.getJpo().setValue("sxtype", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setFieldFlag("sxtype", GWConstant.S_READONLY,
						true);
				this.getJpo().setFieldFlag("RECEIVESTOREROOM",
						GWConstant.S_READONLY, false);
				this.getJpo().setValue("RECEIVESTOREROOM", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setFieldFlag("RECEIVESTOREROOM",
						GWConstant.S_REQUIRED, true);
				this.getJpo().setFieldFlag("RECEIVEADDRESS",
						GWConstant.S_READONLY, false);
				this.getJpo().setValue("RECEIVEADDRESS", "",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setFieldFlag("RECEIVEADDRESS",
						GWConstant.S_REQUIRED, true);
				this.getJpo().setFieldFlag("isfxfy", GWConstant.S_REQUIRED,
						false);
				this.getJpo().setValue("isfxfy", "否",
						GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				this.getJpo().setFieldFlag("isfxfy", GWConstant.S_READONLY,
						true);
			}

		}
	}

}
