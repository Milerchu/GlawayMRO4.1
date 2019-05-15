/*
 * 文 件 名:  AssetModel.java
 * 版    权:  Glaway Technologies Co., Ltd. Copyright 2010-2016,  All rights reserved
 * 描    述:  装备型号JPO
 * 修 改 人:  hyhe
 * 修改时间:  2016-4-25
 */
package com.glaway.sddq.config.sbom.data;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;

import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.DBManager;
import com.glaway.mro.jpo.HierarchicalJpo;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.IStatusJpo;
import com.glaway.mro.logging.FixedLoggers;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.tools.SddqConstant;

/**
 * 装备型号JPO
 * 
 * @author hyhe
 * @version [MRO4.0, 2016-4-25]
 * @since [MRO4.0/模块版本]
 */
public class AssetModel extends HierarchicalJpo implements IStatusJpo,
		FixedLoggers {

	/**
	 * 默认序列化Id
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 为新增记录，设置初始化值
	 * 
	 * @throws MroException
	 */
	@Override
	public void add() throws MroException {
		super.add();
		// 设置站点和地点
		setValue("CHANGEBY", getUserInfo().getPersonId(),
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		setValue("CHANGEDATE", MroServer.getMroServer().getDate());

		if (this.getParent() != null && this.getParent() instanceof AssetModel) {
			String assettmpnum = getChildAssetNum();
			setValue("ASSETTMPNUM", assettmpnum);
			setValue("ASSETTMPLEVEL", "SYSTEM");
			setValue("ANCESTOR", getParent().getString("ANCESTOR"));
			setValue("PARENT", getParent().getString("ASSETTMPNUM"),
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			// this.getParent().setValue("HASCHILDREN", "1",
			// GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			getParent().setValue("ISOPEN", true,
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		} else {
			setValue("VERSION", "V1.0");
			setValue("ASSETTMPLEVEL", "ASSET");
			setValue("ANCESTOR", getString("ASSETTMPNUM"));
		}
		// createAssetAncestor(this, getString("ASSETTMPNUM"), 0,
		// getString("SITEID"), getString("ORGID"));
	}

	/**
	 * 
	 * 获取最新的 装备序列号
	 * 
	 * @return
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public String getChildAssetNum() throws MroException {
		String parantNum = getParent().getString("ASSETTMPNUM");
		int maxNum = this.getJpoSet().count();
		if (!this.getJpoSet().isEmpty() && this.getJpoSet().count() > 1) {
			for (int index = 0; index < getJpoSet().count(); index++) {
				String assetNum = getJpoSet().getJpo(index).getString(
						"ASSETTMPNUM");
				if (assetNum != null) {
					if (assetNum.lastIndexOf(".") != -1) {
						String num = assetNum.substring(assetNum
								.lastIndexOf(".") + 1);
						if (num != null && Integer.valueOf(num) >= maxNum) {
							maxNum = Integer.valueOf(num) + 1;
						}
					}
				}

			}
		}
		String num = checkAssetNum(parantNum, maxNum);
		return num;
	}

	/**
	 * 
	 * 判断ASSETNUM是否存在，不存在则返回，存在则继续加1
	 * 
	 * @param num
	 * @return [参数说明]
	 * 
	 */
	public String checkAssetNum(String parantNum, int maxNum)
			throws MroException {
		String num = parantNum + "." + maxNum;
		IJpoSet jposet = this.getUserServer().getJpoSet("ASSETTMP",
				"ASSETTMPNUM ='" + StringUtil.getSafeSqlStr(num) + "'");
		if (!jposet.isEmpty()) {
			return checkAssetNum(parantNum, maxNum + 1);
		} else {
			return num;
		}
	}

	/**
	 * 
	 * 创建型号结构关系树
	 * 
	 * @param assetModel
	 * @param assetTmpNum
	 * @param level
	 * @param siteid
	 * @param orgid
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public void createAssetAncestor(AssetModel assetModel, String assetTmpNum,
			int level, String siteid, String orgid) throws MroException {
		AssetModel owner = (AssetModel) assetModel.getParent();
		IJpoSet ancestorset = getJpoSet("ASSETTMPANCESTOR");
		IJpo ancestor = ancestorset.addJpo();
		ancestor.setValue("ASSETTMPNUM", assetTmpNum);
		ancestor.setValue("HIERARCHYLEVELS", level);
		ancestor.setValue("SITEID", siteid);
		ancestor.setValue("ORGID", orgid);
		ancestor.setValue("ANCESTOR", assetModel.getString("ASSETTMPNUM"));
		if (!assetModel.isNull("PARENT")) {
			createAssetAncestor(owner, assetTmpNum, level + 1, siteid, orgid);
		}
	}

	/**
	 * 对数据进行初始化设置
	 * 
	 * @throws MroException
	 */
	@Override
	public void init() throws MroException {
		super.init();

		if (hasChildren()) {
			setValue("HASCHILDREN", "1", GWConstant.P_NOUPDATE);
		} else {
			setValue("HASCHILDREN", "0", GWConstant.P_NOUPDATE);
		}

		// 状态为可用时，设置某些字段为只读，并设置新增按钮为只读
		if (StringUtils.isNotEmpty(this.getAppName())
				&& "SBOMCONFIG".equals(this.getAppName())) {
			if (!this.isNew()
					&& getString("STATUS") != null
					&& getAppBeanJpo(this) != null
					&& (getAppBeanJpo(this).getString("STATUS").equals(
							SddqConstant.ASSET_MODEL_STATUS_KY) || getAppBeanJpo(
							this).getString("STATUS").equals(
							SddqConstant.ASSET_MODEL_STATUS_SD))) {
				String[] fields = new String[] { "NEWSTATUS", "NP_STATUSMEMO" };
				this.setNoFieldFlag(fields, GWConstant.S_READONLY, true);
				this.getJpoSet("ASSETMODELPART").setFlag(GWConstant.S_READONLY,
						true);
				this.getJpoSet("JSCS").setFlag(GWConstant.S_READONLY, true);
			} else {
				setFlag(GWConstant.S_READONLY, false);
			}
			if (!this.isNew() && getAppBeanJpo(this) != null) {
				this.setFieldFlag("ITEMNUM", GWConstant.S_READONLY, true);
			}
		}
	}

	/**
	 * 删除判断
	 * 
	 * @param flag
	 * @throws MroException
	 */
	@Override
	public void delete(long flag) throws MroException {
		if (SddqConstant.ASSET_MODEL_STATUS_KY.equals(getAppBeanJpo(this)
				.getString("STATUS"))
				|| SddqConstant.ASSET_MODEL_STATUS_SD
						.equals(getAppBeanJpo(this).getString("STATUS"))) {
			throw new AppException("assettmp", "cannotDel");
		}
		super.delete(flag);
	}

	/**
	 * 
	 * 获取顶级JPO
	 * 
	 * @param jpo
	 * @return
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public IJpo getAppBeanJpo(IJpo jpo) throws MroException {
		if (jpo.getString("ASSETTMPLEVEL") != null
				&& !jpo.getString("ASSETTMPLEVEL").equals("ASSET")) {
			IJpoSet ancestorJpoSet = jpo.getJpoSet("ANCESTOR");
			if (ancestorJpoSet != null && ancestorJpoSet.count() > 0) {
				return ancestorJpoSet.getJpo(0);
			}
		}
		return jpo;
	}

	/**
	 * 删除ASSETTMPTREE表的结构关系
	 * 
	 * @param assetModel
	 *            [参数说明]
	 * @throws MroException
	 * 
	 */
	public void deleteAssetTmpAncestor(AssetModel assetModel)
			throws MroException {

		String assettmpnum = assetModel.getString("ASSETTMPNUM");

		Connection conn = getUserServer().getConnection();
		String deleModelPartSql = "delete from ASSETMODELPART where assetnum in (select assettmpnum from assettmp start with assettmpnum='"
				+ assettmpnum
				+ "' connect by parent = PRIOR assettmpnum) and datatype = '1'";

		String deleSql = "delete from ASSETTMP where assettmpnum in (select assettmpnum from assettmp start with assettmpnum='"
				+ assettmpnum + "' connect by parent = PRIOR assettmpnum)";

		try {
			DBManager.getDBManager().executeBatch(conn,
					new String[] { deleModelPartSql, deleSql });
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

	}

	/**
	 * 保存之前，判断是否删除状态，删除结构树关系
	 * 
	 * @throws MroException
	 */
	@Override
	public void beforeSave() throws MroException {
		if (this.toBeDeleted()) {
			deleteAssetTmpAncestor(this);
		}
	}

	/**
	 * 
	 * 创建出所台帐
	 * 
	 * @throws MroException
	 *             [参数说明]
	 * @throws SQLException
	 * 
	 */
	public void createCs() throws MroException, SQLException {
		String equNum = getString("EQUNUM");// 机器编号
		String equBatnum = getString("EQUBATNUM");// 生产批次号
		String assettmpnum = getString("ASSETTMPNUM");
		if (equNum != null && !equNum.trim().equals("")) {
			equNum = equNum.toUpperCase();
		} else {
			return;
		}
		if (equBatnum != null) {
			equBatnum = equBatnum.toUpperCase();
		}
		// 在执行sql语句之前先判断是否 已存在相同的装备序列号
		IJpoSet jposet = MroServer.getMroServer().getJpoSet("ASSETCS",
				this.getUserServer());
		jposet.setUserWhere("ASSETCSNUM ='" + StringUtil.getSafeSqlStr(equNum)
				+ "'");
		jposet.reset();

		if (jposet.isEmpty()) {
			Connection conn = getUserServer().getConnection();

			try {
				// 同步assettmp表 基本信息到 assetcs 表
				String assetCsSql = "insert into assetcs cs "
						+ "(ASSETCSNUM,ANCESTOR, PARENT, ASSETBATNUM,NUM,RADARNUM, ARMYNO,SUBSYSTEM,UNIT,AMOUNT,UNITPRICE,FACTORY,IMPORTANTPART,REMARK,PARTNUMBER,ARMYTYPE,ASSETCSID, ASSETCSLEVEL,  ASSETCSTAG, ASSETCSTYPE, CHANGEBY, CHANGEDATE, CREATEDATE, CUSTNUM, DESCRIPTION, DRAWNO, FAILURECODE, HASLD, ISRUNNING, ITEMNUM, ITEMSETID, LANGCODE, LOCATION, MANUFACTURER, MODEL, MOVED, ORGID, OUTDATE,PLATFORMNO, PLUSCLPLOC, PRIORITY, PRODUCTATTR, PRODUCTCODE, PRODUCTORDERNO, QAPERIOD, QCNUM, RADARDRAWNO, RECEIVE, SENDERSYSID, SITEID, STATUS, STATUSDATE, TEMPLATEID, TEMPNUM, TYPE, USAGE, VENDOR, VERSION, WARRANTYEXPDATE,PURCHASEPRICE,TOTUNCHARGEDCOST,TOTDOWNTIME,DISABLED,MAINTHIERCHY,PLUSCPMEXTDATE,PLUSCSOLUTION,SECRETLEVEL,SECRETLEVELDES) "
						+ "(select replace(ASSETTMPNUM,'"
						+ StringUtil.getSafeSqlStr(assettmpnum)
						+ "','"
						+ StringUtil.getSafeSqlStr(equNum)
						+ "'),'"
						+ StringUtil.getSafeSqlStr(equNum)
						+ "',replace(PARENT,'"
						+ StringUtil.getSafeSqlStr(assettmpnum)
						+ "','"
						+ StringUtil.getSafeSqlStr(equNum)
						+ "'),'"
						+ StringUtil.getSafeSqlStr(equBatnum)
						+ "',replace(ASSETTMPNUM,'"
						+ StringUtil.getSafeSqlStr(assettmpnum)
						+ "','"
						+ StringUtil.getSafeSqlStr(equNum)
						+ "'),'"
						+ StringUtil.getSafeSqlStr(equNum)
						+ "',ARMYNO, SUBSYSTEM,UNIT,AMOUNT,UNITPRICE,FACTORY,IMPORTANTPART,REMARK,PARTNUMBER,ARMYTYPE, ASSETCSSEQ.NEXTVAL, ASSETTMPLEVEL, ASSETTMPTAG,ASSETTMPTYPE, CHANGEBY, CHANGEDATE, CREATEDATE, CUSTNUM, DESCRIPTION, DRAWNO, FAILURECODE, HASLD, ISRUNNING, ITEMNUM, ITEMSETID,'"
						+ StringUtil.getSafeSqlStr(this.getUserInfo()
								.getDefaultLang())
						+ "', LOCATION, MANUFACTURER, MODEL, MOVED, ORGID, OUTDATE,PLATFORMNO, PLUSCLPLOC, PRIORITY, PRODUCTATTR, PRODUCTCODE, PRODUCTORDERNO, QAPERIOD, QCNUM, RADARDRAWNO, RECEIVE, SENDERSYSID, SITEID, '未出所', STATUSDATE, TEMPLATEID, TEMPNUM, TYPE, USAGE, VENDOR, VERSION, WARRANTYEXPDATE,0.0,0.0,0,0,0,0,0,0,'非密' from assettmp "
						+ " where ASSETTMPNUM ='"
						+ StringUtil.getSafeSqlStr(assettmpnum)
						+ "' OR ANCESTOR='"
						+ StringUtil.getSafeSqlStr(assettmpnum) + "') ";
				// 同步assettmptree表数据 到 assetcstree表
				String assetCsTreeSql = "insert into ASSETCSTREE (ASSETCSTREEID, ASSETCSNUM, ANCESTOR, SITEID, ORGID, HIERARCHYLEVELS) "
						+ "(select ASSETCSTREESEQ.NEXTVAL, replace(ASSETTMPNUM,'"
						+ StringUtil.getSafeSqlStr(assettmpnum)
						+ "','"
						+ StringUtil.getSafeSqlStr(equNum)
						+ "'), replace(ANCESTOR,'"
						+ StringUtil.getSafeSqlStr(assettmpnum)
						+ "','"
						+ StringUtil.getSafeSqlStr(equNum)
						+ "'), SITEID, ORGID, HIERARCHYLEVELS  from assettmptree where assettmpnum in "
						+ "(select assettmpnum from assettmp where assettmpnum ='"
						+ StringUtil.getSafeSqlStr(assettmpnum)
						+ "' or ancestor ='"
						+ StringUtil.getSafeSqlStr(assettmpnum) + "'))";

				// 同步ASSETMODELPART(型号管理-产品随机备件表)数据 到 ASSETCSPART表
				String assetCsPartSql = "insert into ASSETCSPART(ASSETCSPARTID,ASSETNUM,ASSETDRAWNO,DELIVERYDATE,DESCRIPTION,DRAWNO,HASLD,ITEMNUM,LOTNUM,ORGID,PRODUCTORDERNO,QTY,RADARDRAWNO,RADARNUM,SITEID,SOFTVERSION)"
						+ "(select ASSETCSPARTSEQ.NEXTVAL,'"
						+ StringUtil.getSafeSqlStr(equNum)
						+ "',ASSETDRAWNO,DELIVERYDATE,DESCRIPTION,DRAWNO,HASLD,ITEMNUM,LOTNUM,ORGID,PRODUCTORDERNO,QTY,RADARDRAWNO,RADARNUM,SITEID,SOFTVERSION from ASSETMODELPART where ASSETNUM ='"
						+ StringUtil.getSafeSqlStr(assettmpnum) + "')";

				// 同步ASSETMODELTOOL(型号管理-产品随机工具)数据到 ASSETCSTOOL表
				String assetCsToolSql = "insert into ASSETCSTOOL(ASSETCSTOOLID,ASSETNUM,ASSETDRAWNO,DELIVERYDATE,DESCRIPTION,DRAWNO,HASLD,ORGID,QTY,RADARDRAWNO,RADARNUM,SITEID)"
						+ "(select ASSETCSTOOLSEQ.NEXTVAL,'"
						+ StringUtil.getSafeSqlStr(equNum)
						+ "',ASSETDRAWNO,DELIVERYDATE,DESCRIPTION,DRAWNO,HASLD,ORGID,QTY,RADARDRAWNO,RADARNUM,SITEID from ASSETMODELTOOL where ASSETNUM ='"
						+ StringUtil.getSafeSqlStr(assettmpnum) + "')";

				// 同步ASSETMODELEQ(型号管理-产品随机设备)数据到ASSETCSEQ表
				String assetCsEqSql = "insert into ASSETCSEQ(ASSETCSEQID,ASSETNUM,ASSETDRAWNO,DELIVERYDATE,DESCRIPTION,DRAWNO,HASLD,ORGID,QTY,RADARDRAWNO,RADARNUM,SITEID)"
						+ "(select ASSETCSEQSEQ.NEXTVAL,'"
						+ StringUtil.getSafeSqlStr(equNum)
						+ "',ASSETDRAWNO,DELIVERYDATE,DESCRIPTION,DRAWNO,HASLD,ORGID,QTY,RADARDRAWNO,RADARNUM,SITEID from ASSETMODELEQ where ASSETNUM ='"
						+ StringUtil.getSafeSqlStr(assettmpnum) + "')";

				// 同步ASSETMODELDOC(型号管理-产品随机文档)数据到ASSETCSDOC表
				String assetCsDocSql = "insert into ASSETCSDOC(ASSETCSDOCID,ASSETNUM,MODELDOCID,ASSETDRAWNO,DELIVERYDATE,DESCRIPTION,DOCNAME,DRAWNO,HASLD,ISDELIVERY,ORGID,RADARDRAWNO,RADARNUM,SITEID,VERSION)"
						+ "(select ASSETCSDOCSEQ.NEXTVAL,'"
						+ StringUtil.getSafeSqlStr(equNum)
						+ "',ASSETMODELDOCID,ASSETDRAWNO,DELIVERYDATE,DESCRIPTION,DOCNAME,DRAWNO,HASLD,ISDELIVERY,ORGID,RADARDRAWNO,RADARNUM,SITEID,VERSION from ASSETMODELDOC where ASSETNUM ='"
						+ StringUtil.getSafeSqlStr(assettmpnum) + "')";

				// 同步随机文档的附件(sys_doclinks表)
				String assetCsDoc_DocLinksSql = "insert into sys_doclinks(SYS_DOCLINKSID,OWNERTABLE,OWNERID,changeby,createdate,changedate,createby,description,docinfoid,doctype,document,docversion,getlatestversion)"
						+ "(select SYS_DOCLINKSSEQ.NEXTVAL,'ASSETCSDOC',(select assetcsdocid from assetcsdoc csdoc where assetnum ='"
						+ StringUtil.getSafeSqlStr(equNum)
						+ "' and  csdoc.modeldocid = doc.ownerid),"
						+ "changeby,createdate,changedate,createby,description,docinfoid,doctype,document,docversion,getlatestversion "
						+ " from sys_doclinks doc where ownertable ='ASSETMODELDOC' and doc.ownerid in (select modeldocid from assetcsdoc where assetnum ='"
						+ StringUtil.getSafeSqlStr(equNum) + "'))";

				DBManager.getDBManager().executeBatch(
						conn,
						new String[] { assetCsSql, assetCsTreeSql,
								assetCsPartSql, assetCsToolSql, assetCsEqSql,
								assetCsDocSql, assetCsDoc_DocLinksSql });
			} catch (SQLException e) {
				e.printStackTrace();
				conn.rollback();
				EXCEPTIONLOGGER.error(e);
				throw new AppException("ASSET", "createCsError");
			} finally {
				if (conn != null) {
					conn.close();
				}
			}
		} else {
			throw new AppException("ASSETTMP", "numrepeat");
		}
	}

	@Override
	public IJpo changeStatus(String status, String newStatus, String memo,
			long flag) throws MroException {
		IJpo jpo = super.changeStatus(status, newStatus, memo, flag);
		jpo.setValue("ASSETTMPNUM", getString("ASSETTMPNUM"));
		return jpo;
	}

	@Override
	public IJpoSet getStatusHistorySet() throws MroException {
		// 默认状态历史表的关系名称是表名后缀STATUS
		String relationName = getName().toUpperCase() + "STATUS";
		if (getJpoSet().getTableInfo().getRelation(relationName) == null) {
			return null;
		}
		return getJpoSet(relationName);
	}
}
