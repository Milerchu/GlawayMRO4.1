/*
 * 文 件 名:  AssetCs.java
 * 版    权:  Glaway Technologies Co., Ltd. Copyright 2010-2016,  All rights reserved
 * 描    述:  出所台帐JPO
 * 修 改 人:  hyhe
 * 修改时间:  2016-4-27
 */
package com.glaway.sddq.config.csbom.data;

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
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.tools.SddqConstant;

/**
 * 整车SBOM JPO
 * 
 * @author hyhe
 * @version [版本号, 2016-4-27]
 * @since [产品/模块版本]
 */
public class AssetCs extends HierarchicalJpo implements IStatusJpo {

	/**
	 * 默认序列化ID
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws MroException {
		super.init();

		if (hasChildren()) {
			setValue("HASCHILDREN", "1", GWConstant.P_NOUPD_NOACTION_NOVALIDAT);
		} else {
			setValue("HASCHILDREN", "0", GWConstant.P_NOUPD_NOACTION_NOVALIDAT);
		}

		// 状态为可用时，设置某些字段为只读，并设置新增按钮为只读
		if (StringUtils.isNotEmpty(this.getAppName())
				&& "ZCSBOM".equals(this.getAppName())) {
			if (!this.isNew()
					&& getString("STATUS") != null
					&& getAppBeanJpo(this) != null
					&& (getAppBeanJpo(this).getString("STATUS").equals(
							SddqConstant.ASSET_CS_STATUS_KY) || getAppBeanJpo(
							this).getString("STATUS").equals(
							SddqConstant.ASSET_CS_STATUS_SD))) {
				String[] fields = new String[] { "NEWSTATUS", "NP_STATUSMEMO" };
				this.setNoFieldFlag(fields, GWConstant.S_READONLY, true);
				this.getJpoSet("ASSETMODELPART").setFlag(GWConstant.S_READONLY,
						true);
			} else {
				setFlag(GWConstant.S_READONLY, false);
			}
			if (!this.isNew()) {
				this.setFieldFlag("CMODEL", GWConstant.S_READONLY, true);
			}
		}
	}

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
		setValue("LANGCODE", this.getUserInfo().getDefaultLang());
		if (this.getParent() != null && this.getParent() instanceof AssetCs) {
			if (this.getParent().getString("ASSETCSLEVEL").equals("ASSET")) {
				setValue("ASSETCSLEVEL", "CAR");
			} else {
				setValue("ASSETCSLEVEL", "SYSTEM");
			}
			String assetcsnum = getChildAssetNum();
			setValue("ASSETCSNUM", assetcsnum);
			setValue("ANCESTOR", getParent().getString("ANCESTOR"));
			setValue("PARENT", getParent().getString("ASSETCSNUM"),
					GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			// this.getParent().setValue("HASCHILDREN", "1",
			// GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			getParent().setValue("ISOPEN", true,
					GWConstant.P_NOUPD_NOACTION_NOVALIDAT);
		} else {
			setValue("ASSETCSLEVEL", "ASSET");
			setValue("ANCESTOR", getString("ASSETCSNUM"));
		}
		setValue("NUM", getString("ASSETCSNUM"),
				GWConstant.P_NOUPD_NOACTION_NOVALIDAT);
		// createAssetCsAncestor(this, getString("ASSETCSNUM"), 0,
		// getString("SITEID"), getString("ORGID"));

	}

	/**
	 * 获取最新的 装备序列号
	 * 
	 * @return
	 * @throws MroException
	 *             [参数说明]
	 */
	public String getChildAssetNum() throws MroException {
		String parantNum = getParent().getString("ASSETCSNUM");
		int maxNum = this.getJpoSet().count();
		if (!this.getJpoSet().isEmpty() && this.getJpoSet().count() > 1) {
			for (int index = 0; index < getJpoSet().count(); index++) {
				String assetNum = getJpoSet().getJpo(index).getString(
						"ASSETCSNUM");
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
	 * 判断ASSETNUM是否存在，不存在则返回，存在则继续加1
	 * 
	 * @param num
	 * @return [参数说明]
	 */
	public String checkAssetNum(String parantNum, int maxNum)
			throws MroException {
		String num = parantNum + "." + maxNum;
		IJpoSet jposet = this.getUserServer().getJpoSet("ASSETCS",
				"ASSETCSNUM ='" + StringUtil.getSafeSqlStr(num) + "'");
		if (!jposet.isEmpty()) {
			return checkAssetNum(parantNum, maxNum + 1);
		} else {
			return num;
		}
	}

	/**
	 * 创建台帐结构树
	 * 
	 * @param assetCs
	 * @param assetCsNum
	 * @param level
	 * @param siteid
	 * @param orgid
	 * @throws MroException
	 *             [参数说明]
	 */
	public void createAssetCsAncestor(AssetCs assetCs, String assetCsNum,
			int level, String siteid, String orgid) throws MroException {
		AssetCs owner = (AssetCs) assetCs.getParent();
		IJpoSet ancestorset = getJpoSet("ASSETCSANCESTOR");
		IJpo ancestor = ancestorset.addJpo();
		ancestor.setValue("ASSETCSNUM", assetCsNum);
		ancestor.setValue("HIERARCHYLEVELS", level);
		ancestor.setValue("SITEID", siteid);
		ancestor.setValue("ORGID", orgid);
		ancestor.setValue("ANCESTOR", assetCs.getString("ASSETCSNUM"));
		if (!assetCs.isNull("PARENT")) {
			createAssetCsAncestor(owner, assetCsNum, level + 1, siteid, orgid);
		}
	}

	/**
	 * @param 删除判断
	 * @throws MroException
	 */
	@Override
	public void delete(long flag) throws MroException {
		if (SddqConstant.ASSET_CS_STATUS_KY.equals(getAppBeanJpo(this)
				.getString("STATUS"))
				|| SddqConstant.ASSET_CS_STATUS_SD.equals(getAppBeanJpo(this)
						.getString("STATUS"))) {
			throw new AppException("assetcs", "cannotdel");
		}
		super.delete(flag);
	}

	/**
	 * 保存之前，判断是否删除状态，删除结构树关系
	 * 
	 * @throws MroException
	 */
	@Override
	public void beforeSave() throws MroException {
		if (this.toBeDeleted()) {
			deleteAssetCsAncestor(this);
		}
	}

	/**
	 * 获取顶级JPO
	 * 
	 * @param jpo
	 * @return
	 * @throws MroException
	 *             [参数说明]
	 */
	public IJpo getAppBeanJpo(IJpo jpo) throws MroException {
		// IJpo parent = jpo.getParent();
		// if (parent != null &&
		// parent.getString("ASSETCSLEVEL").equals("ASSET"))
		// {
		// return parent;
		// }
		// else if (parent != null)
		// {
		// return getAppBeanJpo(parent);
		// }
		// else
		// {
		// return jpo;
		// }
		if (jpo.getString("ASSETCSLEVEL") != null
				&& !jpo.getString("ASSETCSLEVEL").equals("ASSET")) {
			IJpoSet ancestorJpoSet = jpo.getJpoSet("ANCESTOR");
			if (ancestorJpoSet != null && ancestorJpoSet.count() > 0) {
				return ancestorJpoSet.getJpo(0);
			}
		}
		return jpo;
	}

	/**
	 * 生成实时台帐
	 * 
	 * @throws MroException
	 * @throws SQLException
	 *             [参数说明]
	 */
	public void createJz() throws MroException, SQLException {
		String assetCsNum = this.getString("ASSETCSNUM");
		if (assetCsNum == null || assetCsNum.trim().equals("")) {
			return;
		}
		// 在执行sql语句之前先判断是否 已存在相同的装备序列号
		IJpoSet jposet = MroServer.getMroServer().getJpoSet("ASSET",
				this.getUserServer());
		jposet.setUserWhere("ASSETNUM ='"
				+ StringUtil.getSafeSqlStr(assetCsNum) + "'");
		jposet.reset();
		if (jposet.isEmpty()) {
			Connection conn = getUserServer().getConnection();
			try {
				// 同步assetcs表 基本信息到 asset 表
				String assetSql = "insert into asset"
						+ "(ASSETNUM,ANCESTOR, PARENT,LOCSITE, ASSETBATNUM,NUM,BUILDTIME,ASSETDOMAIN,CONTRANUM,RADARNUM,ARMYNO,SUBSYSTEM,UNIT,AMOUNT,UNITPRICE,FACTORY,IMPORTANTPART,REMARK,PARTNUMBER,ARMYTYPE,ASSETID, ASSETLEVEL,  ASSETTAG, CHANGEBY, CHANGEDATE, CREATEDATE, CUSTNUM, DESCRIPTION, DRAWNO, FAILURECODE, HASLD, ISRUNNING, ITEMNUM, ITEMSETID, LANGCODE, LOCATION, MANUFACTURER, MODEL, MOVED, ORGID, OUTDATE,PLATFORMNO, PLUSCLPLOC, PRIORITY, PRODUCTATTR, PRODUCTCODE, PRODUCTORDERNO, QAPERIOD, QCNUM, RADARDRAWNO, RECEIVE, SENDERSYSID, SITEID, STATUS, STATUSDATE, TEMPNUM, USAGE, VENDOR, VERSION, WARRANTYEXPDATE,PURCHASEPRICE,TOTDOWNTIME,DISABLED,MAINTHIERCHY,PLUSCPMEXTDATE,PLUSCSOLUTION,SECRETLEVEL,SECRETLEVELDES) "
						+ "(select ASSETCSNUM,ANCESTOR,PARENT,LOCSITE,ASSETBATNUM,NUM,BUILDTIME,ASSETDOMAIN,CONTRANUM,RADARNUM,ARMYNO,SUBSYSTEM,UNIT,AMOUNT,UNITPRICE,FACTORY,IMPORTANTPART,REMARK,PARTNUMBER,ARMYTYPE, ASSETSEQ.NEXTVAL, ASSETCSLEVEL, ASSETCSTAG, CHANGEBY, CHANGEDATE, CREATEDATE, CUSTNUM, DESCRIPTION, DRAWNO, FAILURECODE, HASLD, ISRUNNING, ITEMNUM, ITEMSETID,'"
						+ this.getUserInfo().getDefaultLang()
						+ "', LOCATION, MANUFACTURER, MODEL, MOVED, ORGID, OUTDATE,PLATFORMNO, PLUSCLPLOC, PRIORITY, PRODUCTATTR, PRODUCTCODE, PRODUCTORDERNO, QAPERIOD, QCNUM, RADARDRAWNO, RECEIVE, SENDERSYSID, SITEID, '正常', STATUSDATE, TEMPNUM, USAGE, VENDOR, VERSION, WARRANTYEXPDATE,0.0,0,0,0,0,0,0,'非密' from assetcs "
						+ " where ASSETCSNUM ='"
						+ StringUtil.getSafeSqlStr(assetCsNum)
						+ "' OR ANCESTOR='"
						+ StringUtil.getSafeSqlStr(assetCsNum) + "') ";

				// 同步assetcstree表数据 到 assettree表
				String assetTreeSql = "insert into ASSETTREE (ASSETTREEID, ASSETNUM, ANCESTOR, SITEID, ORGID, HIERARCHYLEVELS) "
						+ "(select ASSETTREESEQ.NEXTVAL, ASSETCSNUM,ANCESTOR, SITEID, ORGID, HIERARCHYLEVELS  from assetcstree where assetcsnum in "
						+ "(select assetcsnum from assetcs where assetcsnum ='"
						+ StringUtil.getSafeSqlStr(assetCsNum)
						+ "' or ancestor ='"
						+ StringUtil.getSafeSqlStr(assetCsNum) + "'))";

				// 同步ASSETPART(出所台帐-产品随机备件表)数据 到 ASSETPART表
				String assetPartSql = "insert into ASSETPART(ASSETPARTID,ASSETNUM,ASSETDRAWNO,DELIVERYDATE,DESCRIPTION,DRAWNO,ITEMNUM,LOTNUM,ORGID,PRODUCTORDERNO,QTY,RADARDRAWNO,RADARNUM,SITEID,SOFTVERSION)"
						+ "(select ASSETPARTSEQ.NEXTVAL,ASSETNUM,ASSETDRAWNO,DELIVERYDATE,DESCRIPTION,DRAWNO,ITEMNUM,LOTNUM,ORGID,PRODUCTORDERNO,QTY,RADARDRAWNO,RADARNUM,SITEID,SOFTVERSION from ASSETCSPART where ASSETNUM ='"
						+ StringUtil.getSafeSqlStr(assetCsNum) + "')";

				// 同步ASSETTOOL(出所台帐-产品随机工具)数据到 ASSETTOOL表
				String assetToolSql = "insert into ASSETTOOL(ASSETTOOLID,ASSETNUM,ASSETDRAWNO,DELIVERYDATE,DESCRIPTION,DRAWNO,ORGID,QTY,RADARDRAWNO,RADARNUM,SITEID)"
						+ "(select ASSETTOOLSEQ.NEXTVAL,ASSETNUM,ASSETDRAWNO,DELIVERYDATE,DESCRIPTION,DRAWNO,ORGID,QTY,RADARDRAWNO,RADARNUM,SITEID from ASSETCSTOOL where ASSETNUM ='"
						+ StringUtil.getSafeSqlStr(assetCsNum) + "')";

				// 同步ASSETEQ(出所台帐-产品随机设备)数据到ASSETEQ表
				String assetEqSql = "insert into ASSETEQ(ASSETEQID,ASSETNUM,ASSETDRAWNO,DELIVERYDATE,DESCRIPTION,DRAWNO,ORGID,QTY,RADARDRAWNO,RADARNUM,SITEID)"
						+ "(select ASSETEQSEQ.NEXTVAL,ASSETNUM,ASSETDRAWNO,DELIVERYDATE,DESCRIPTION,DRAWNO,ORGID,QTY,RADARDRAWNO,RADARNUM,SITEID from ASSETCSEQ where ASSETNUM ='"
						+ StringUtil.getSafeSqlStr(assetCsNum) + "')";

				// 同步ASSETDOC(出所台帐-产品随机文档)数据到ASSETDOC表
				String assetDocSql = "insert into ASSETDOC(ASSETDOCID,ASSETNUM,CSDOCID,ASSETDRAWNO,DELIVERYDATE,DESCRIPTION,DOCNAME,DRAWNO,ORGID,RADARDRAWNO,RADARNUM,SITEID,VERSION)"
						+ "(select ASSETDOCSEQ.NEXTVAL,ASSETNUM,ASSETCSDOCID,ASSETDRAWNO,DELIVERYDATE,DESCRIPTION,DOCNAME,DRAWNO,ORGID,RADARDRAWNO,RADARNUM,SITEID,VERSION from ASSETCSDOC where ASSETNUM ='"
						+ StringUtil.getSafeSqlStr(assetCsNum) + "')";

				// 同步随机文档的附件(sys_doclinks表)
				String assetDoc_DocLinksSql = "insert into sys_doclinks(SYS_DOCLINKSID,OWNERTABLE,OWNERID,changeby,createdate,changedate,createby,description,docinfoid,doctype,document,docversion,getlatestversion)"
						+ "(select SYS_DOCLINKSSEQ.NEXTVAL,'ASSETDOC',(select assetdocid from assetdoc csdoc where assetnum ='"
						+ StringUtil.getSafeSqlStr(assetCsNum)
						+ "' and  csdoc.csdocid = doc.ownerid),"
						+ "changeby,createdate,changedate,createby,description,docinfoid,doctype,document,docversion,getlatestversion "
						+ " from sys_doclinks doc where ownertable ='ASSETCSDOC' and doc.ownerid in (select csdocid from assetdoc where assetnum ='"
						+ StringUtil.getSafeSqlStr(assetCsNum) + "'))";

				DBManager.getDBManager().executeBatch(
						conn,
						new String[] { assetSql, assetTreeSql, assetPartSql,
								assetToolSql, assetEqSql, assetDocSql,
								assetDoc_DocLinksSql });

			} catch (SQLException e) {
				throw new AppException("ASSET", "createCsError");
			} finally {
				if (conn != null) {
					conn.close();
				}
			}
		} else {
			throw new AppException("ASSET", "numrepeat");
		}
	}

	@Override
	public IJpo changeStatus(String status, String newStatus, String memo,
			long flag) throws MroException {
		IJpo jpo = super.changeStatus(status, newStatus, memo, flag);
		jpo.setValue("ASSETCSNUM", getString("ASSETCSNUM"));
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

	/**
	 * 删除ASSETCSTREE表的结构关系
	 * 
	 * @param assetCs
	 * @throws MroException
	 *             [参数说明]
	 */
	private void deleteAssetCsAncestor(AssetCs assetCs) throws MroException {
		// assetCs.getJpoSet("ASSETCSANCESTOR").deleteAll(GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		// IJpoSet childRenSet = assetCs.getJpoSet("CHILDREN");
		// if (!childRenSet.isEmpty())
		// {
		// for (int i = 0; i < childRenSet.count(); i++)
		// {
		// AssetCs childJpo = (AssetCs)childRenSet.getJpo(i);
		// childJpo.delete(GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		// deleteAssetCsAncestor(childJpo);
		// }
		// }
		String assetcsnum = assetCs.getString("ASSETCSNUM");

		Connection conn = getUserServer().getConnection();

		String deleModelPartSql = "delete from ASSETMODELPART where assetnum in (select assetcsnum from assetcs start with assetcsnum ='"
				+ assetcsnum
				+ "' connect by parent = PRIOR assetcsnum) and datatype = '2'";

		String deleSql = "delete from assetcs where assetcsnum in (select assetcsnum from assetcs start with assetcsnum ='"
				+ assetcsnum + "' connect by parent = PRIOR assetcsnum)";
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

		// IJpoSet assetchildren = assetCs.getJpoSet("$assetcschildTmp",
		// "ASSETCS",
		// "assetcsnum in (select assetcsnum from asset  start with assetcsnum ='"
		// + assetcsnum
		// + "' connect by parent = PRIOR assetcsnum)");
		// assetchildren.reset();
		// assetchildren.deleteAll(0);

		// IJpoSet assettree = assetCs.getJpoSet("$assetcstreeTmp",
		// "ASSETCS","assetcsnum in (select assetcsnum from assetcstree where ancestor ='"
		// + assetcsnum
		// + "')");
		// assettree.reset();
		// assettree.deleteAll();
	}

	@Override
	public String getStatusListName() throws MroException {
		return "ASSETCSSTATUS";
	}

}
