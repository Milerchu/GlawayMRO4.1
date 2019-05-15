/*
 * 文 件 名:  AssetCsChildrenBean.java
 * 版    权:  Glaway Technologies Co., Ltd. Copyright 2010-2016,  All rights reserved
 * 描    述:  出所台帐 结构某节点下的子级DataBean
 * 修 改 人:  hyhe
 * 修改时间:  2016-4-28
 */
package com.glaway.sddq.config.csbom.bean;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.controller.TreeBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.DBManager;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.StringUtil;

/**
 * 出所台帐 结构某节点下的子级DataBean
 * 
 * @author hyhe
 * @version [MRO4.0, 2016-4-28]
 * @since [MRO4.0/模块版本]
 */
public class AssetCsChildrenBean extends DataBean {
	@Override
	public void addEditRowCallBackOk() throws MroException, IOException {
		super.addEditRowCallBackOk();
		if (this.getAppName() != null && ("ZCSBOM").equals(this.getAppName())
				&& this.getJpo().isNew()) {
			// 暂定从产品SBOM表ASSETTMP表中取装机部件，有ANCESTOR
			String itemnum = this.getJpo().getString("ITEMNUM");
			// 到ASSETTMP表中获取assettmpnum
			IJpoSet jposet = MroServer.getMroServer().getSysJpoSet("ASSETTMP");
			jposet.setUserWhere("ITEMNUM ='" + itemnum
					+ "'  and assettmplevel = 'ASSET'");
			jposet.reset();
			if (jposet != null && !jposet.isEmpty()) {
				IJpo jpo = jposet.getJpo(0);
				String installNum = jpo.getString("ancestor");
				// 装机目标部件,只能选择顶层节点进行装机，toParent = ancestor
				String toAncestor = this.getJpo().getParent()
						.getString("ancestor");
				String toParent = this.getJpo().getParent()
						.getString("ASSETCSNUM");
				String rassettmpnum = getMaxnum(toAncestor, toParent);
				Connection conn = this.getMroSession().getUserServer()
						.getConnection();
				try {
					String assetCsSql = "insert into assetcs cs "
							+ "(ASSETCSNUM, PARENT,ANCESTOR,NUM,PRODUCTCODE,SOFTNAME,SOFTVERSION,SOFTUPDATE,LINENUM,RNUM,ITEMNUM,CONFIGNUM,LOC,LOCDESC,REMARK,ASSETCSID, ASSETCSLEVEL, CHANGEBY, CHANGEDATE, CREATEDATE, DESCRIPTION, DRAWNO, LANGCODE, LOCATION,MODEL,ORGID,SITEID,STATUS,STATUSDATE,TYPE,VERSION,SECRETLEVEL,SECRETLEVELDES) "
							+ "(select replace(ASSETTMPNUM,'"
							+ StringUtil.getSafeSqlStr(installNum)
							+ "','"
							+ StringUtil.getSafeSqlStr(rassettmpnum)
							+ "'),replace(PARENT,'"
							+ StringUtil.getSafeSqlStr(installNum)
							+ "','"
							+ StringUtil.getSafeSqlStr(rassettmpnum)
							+ "'),'"
							+ StringUtil.getSafeSqlStr(toAncestor)
							+ "',replace(NUM,'"
							+ StringUtil.getSafeSqlStr(installNum)
							+ "','"
							+ StringUtil.getSafeSqlStr(rassettmpnum)
							+ "'),PRODUCTCODE,SOFTNAME,SOFTVERSION,SOFTUPDATE,LINENUM,RNUM,ITEMNUM,CONFIGNUM,LOC,LOCDESC,REMARK, ASSETCSSEQ.NEXTVAL,'SYSTEM', CHANGEBY, CHANGEDATE, CREATEDATE, DESCRIPTION, DRAWNO,'"
							+ StringUtil.getSafeSqlStr(this.getUserInfo()
									.getDefaultLang())
							+ "', LOCATION, MODEL, ORGID,  SITEID, STATUS, STATUSDATE, TYPE, VERSION,0,'非密' from assettmp "
							+ " where  ANCESTOR='"
							+ StringUtil.getSafeSqlStr(installNum)
							+ "' and assettmpnum = '"
							+ StringUtil.getSafeSqlStr(installNum) + "')";

					String assetChildrenCsSql1 = "insert into assetcs cs "
							+ "(ASSETCSNUM, PARENT,ANCESTOR,NUM,PRODUCTCODE,SOFTNAME,SOFTVERSION,SOFTUPDATE,LINENUM,RNUM,ITEMNUM,CONFIGNUM,LOC,LOCDESC,REMARK,ASSETCSID, ASSETCSLEVEL, CHANGEBY, CHANGEDATE, CREATEDATE, DESCRIPTION, DRAWNO, LANGCODE, LOCATION,MODEL,ORGID,SITEID,STATUS,STATUSDATE,TYPE,VERSION,SECRETLEVEL,SECRETLEVELDES) "
							+ "(select concat('"
							+ StringUtil.getSafeSqlStr(rassettmpnum)
							+ ".',ASSETTMPNUM),'"
							+ StringUtil.getSafeSqlStr(rassettmpnum)
							+ "','"
							+ StringUtil.getSafeSqlStr(toAncestor)
							+ "',concat('"
							+ StringUtil.getSafeSqlStr(rassettmpnum)
							+ ".',NUM),PRODUCTCODE,SOFTNAME,SOFTVERSION,SOFTUPDATE,LINENUM,RNUM,ITEMNUM,CONFIGNUM,LOC,LOCDESC,REMARK, ASSETCSSEQ.NEXTVAL,'SYSTEM', CHANGEBY, CHANGEDATE, CREATEDATE, DESCRIPTION, DRAWNO,'"
							+ StringUtil.getSafeSqlStr(this.getUserInfo()
									.getDefaultLang())
							+ "', LOCATION, MODEL, ORGID,  SITEID, STATUS, STATUSDATE, TYPE, VERSION,0,'非密' from assettmp "
							+ " where  ANCESTOR='"
							+ StringUtil.getSafeSqlStr(installNum)
							+ "' and parent = '"
							+ StringUtil.getSafeSqlStr(installNum) + "')";

					String assetChildrenCsSql2 = "insert into assetcs cs "
							+ "(ASSETCSNUM, PARENT,ANCESTOR,NUM,PRODUCTCODE,SOFTNAME,SOFTVERSION,SOFTUPDATE,LINENUM,RNUM,ITEMNUM,CONFIGNUM,LOC,LOCDESC,REMARK,ASSETCSID, ASSETCSLEVEL, CHANGEBY, CHANGEDATE, CREATEDATE, DESCRIPTION, DRAWNO, LANGCODE, LOCATION,MODEL,ORGID,SITEID,STATUS,STATUSDATE,TYPE,VERSION,SECRETLEVEL,SECRETLEVELDES) "
							+ "(select concat('"
							+ StringUtil.getSafeSqlStr(rassettmpnum)
							+ ".',ASSETTMPNUM),"
							+ "concat('"
							+ StringUtil.getSafeSqlStr(rassettmpnum)
							+ ".',PARENT),'"
							+ StringUtil.getSafeSqlStr(toAncestor)
							+ "',concat('"
							+ StringUtil.getSafeSqlStr(rassettmpnum)
							+ ".',NUM),PRODUCTCODE,SOFTNAME,SOFTVERSION,SOFTUPDATE,LINENUM,RNUM,ITEMNUM,CONFIGNUM,LOC,LOCDESC,REMARK, ASSETCSSEQ.NEXTVAL,'SYSTEM', CHANGEBY, CHANGEDATE, CREATEDATE, DESCRIPTION, DRAWNO,'"
							+ StringUtil.getSafeSqlStr(this.getUserInfo()
									.getDefaultLang())
							+ "', LOCATION, MODEL, ORGID,  SITEID, STATUS, STATUSDATE, TYPE, VERSION,0,'非密' from assettmp "
							+ " where  ANCESTOR='"
							+ StringUtil.getSafeSqlStr(installNum)
							+ "' and parent != '"
							+ StringUtil.getSafeSqlStr(installNum) + "')";

					// 更新装机顶层节点的父级节点
					String updatesql1 = "update assetcs set parent ='"
							+ toParent + "',assetcslevel='SYSTEM',LINENUM='"
							+ this.getJpo().getString("LINENUM") + "',RNUM='"
							+ this.getJpo().getString("RNUM") + "',LOC='"
							+ this.getJpo().getString("LOC") + "',LOCDESC='"
							+ this.getJpo().getString("LOCDESC")
							+ "',SOFTNAME='"
							+ this.getJpo().getString("SOFTNAME")
							+ "',SOFTVERSION='"
							+ this.getJpo().getString("SOFTVERSION")
							+ "',SOFTUPDATE=to_date('"
							+ this.getJpo().getString("SOFTUPDATE")
							+ "','yyyy-mm-dd hh24:mi:ss'),CONFIGNUM='"
							+ this.getJpo().getString("CONFIGNUM")
							+ "',STATUS='" + this.getJpo().getString("STATUS")
							+ "' where assetcsnum='" + rassettmpnum + "'";

					// String updatesql2 =
					// "update assetcs set HASCHILDREN = '1' where  assetcsnum ='"
					// + toAncestor + "'";

					// 同步assettmptree表数据 到 assetcstree表
					// String assetCsTreeSql1 =
					// "insert into ASSETCSTREE (ASSETCSTREEID, ASSETCSNUM, ANCESTOR, SITEID, ORGID, HIERARCHYLEVELS) "
					// + "(select ASSETCSTREESEQ.NEXTVAL, replace(ASSETTMPNUM,'"
					// + StringUtil.getSafeSqlStr(installNum) + "','" +
					// StringUtil.getSafeSqlStr(rassettmpnum)
					// + "'), replace(ANCESTOR,'" +
					// StringUtil.getSafeSqlStr(installNum) + "','"
					// + StringUtil.getSafeSqlStr(rassettmpnum)
					// +
					// "'), SITEID, ORGID, HIERARCHYLEVELS  from assettmptree where assettmpnum='"
					// + StringUtil.getSafeSqlStr(installNum) + "')";

					// String assetCsTreeSql1 =
					// "insert into ASSETCSTREE (ASSETCSTREEID, ASSETCSNUM, ANCESTOR, SITEID, ORGID, HIERARCHYLEVELS) "
					// +
					// "(select ASSETCSTREESEQ.NEXTVAL, (case when ASSETTMPNUM ='"
					// + StringUtil.getSafeSqlStr(installNum)
					// + "' then '"
					// + StringUtil.getSafeSqlStr(rassettmpnum)
					// + "' when ASSETTMPNUM !='"
					// + StringUtil.getSafeSqlStr(installNum)
					// + "' then to_char(concat('"
					// + StringUtil.getSafeSqlStr(rassettmpnum)
					// +
					// ".',ASSETTMPNUM)) else null end), (case when ANCESTOR ='"
					// + StringUtil.getSafeSqlStr(installNum)
					// + "' then '"
					// + StringUtil.getSafeSqlStr(rassettmpnum)
					// + "' when ANCESTOR != '"
					// + StringUtil.getSafeSqlStr(installNum)
					// + "' then to_char(concat('"
					// + StringUtil.getSafeSqlStr(rassettmpnum)
					// +
					// ".',ANCESTOR)) else null end), SITEID, ORGID, HIERARCHYLEVELS  from assettmptree where assettmpnum in "
					// + "(select assettmpnum from assettmp where ancestor ='"
					// + StringUtil.getSafeSqlStr(installNum) + "'))";
					//
					// //建立与顶层节点的关系
					// String assetCsTreeSql2 =
					// "insert into ASSETCSTREE (ASSETCSTREEID, ASSETCSNUM, ANCESTOR, SITEID, ORGID, HIERARCHYLEVELS) "
					// +
					// "(select ASSETCSTREESEQ.NEXTVAL,(case when ASSETTMPNUM ='"
					// + StringUtil.getSafeSqlStr(installNum) + "' then '"
					// + StringUtil.getSafeSqlStr(rassettmpnum) +
					// "' when ASSETTMPNUM !='"
					// + StringUtil.getSafeSqlStr(installNum) +
					// "' then to_char(concat('"
					// + StringUtil.getSafeSqlStr(rassettmpnum) +
					// ".',ASSETTMPNUM)) else null end), '"
					// + toAncestor +
					// "', SITEID, ORGID, HIERARCHYLEVELS+1  from assettmptree where  ancestor ='"
					// + StringUtil.getSafeSqlStr(installNum) + "')";

					// 复制服务备件清单数据
					String insertAssetModelPartSql = "insert into ASSETMODELPART t (ASSETMODELPARTID,ASSETNUM,ITEMNUM,QTY,APPLY,ORGID,SITEID,DATATYPE)(select ASSETMODELPARTSEQ.NEXTVAL,(case when ASSETNUM ='"
							+ StringUtil.getSafeSqlStr(installNum)
							+ "' then '"
							+ StringUtil.getSafeSqlStr(rassettmpnum)
							+ "' else to_char(concat('"
							+ StringUtil.getSafeSqlStr(rassettmpnum)
							+ ".',ASSETNUM)) end),ITEMNUM,QTY,APPLY,ORGID,SITEID,'2' from ASSETMODELPART where assetnum in (select ASSETTMPNUM from assettmp where ancestor = '"
							+ installNum + "'))";

					DBManager.getDBManager().executeBatch(
							conn,
							new String[] { assetCsSql, assetChildrenCsSql1,
									assetChildrenCsSql2, updatesql1,
									insertAssetModelPartSql });

					this.getJpo().getJpoSet().reset();
					this.reloadPage();
				} catch (SQLException e) {
					System.out.println(e);
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
		}
		// 重新加载节点的子层节点
		((TreeBean) parent).reloadNodeWithoutReset();
	}

	/**
	 * 
	 * 获取最新的assettmpnum
	 * 
	 * @param ancestor
	 * @return
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public String getMaxnum(String ancestor, String parent) throws MroException {
		IJpoSet jposet = MroServer.getMroServer().getSysJpoSet("ASSETCS");
		jposet.setUserWhere("assetcsnum=(select max(assetcsnum) from assetcs where parent = '"
				+ parent + "' and ancestor = '" + ancestor + "')");
		jposet.reset();
		if (jposet != null && !jposet.isEmpty()) {
			String assettmpnum = jposet.getJpo(0).getString("assetcsnum");
			String num = assettmpnum
					.substring(assettmpnum.lastIndexOf(".") + 1);
			int newnum = Integer.valueOf(num) + 1;
			return String.valueOf(parent + "." + newnum);
		} else {
			return String.valueOf(parent + ".1");
		}
	}

	/**
	 * 新建窗口，点击取消按钮后，重新加载节点的子层节点
	 * 
	 * @throws MroException
	 */
	@Override
	public void resetAndFixPos() throws MroException {
		super.resetAndFixPos();
		try {
			((TreeBean) parent).reloadNodeWithoutReset();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 重新刷新树
	 * 
	 * @throws MroException
	 */
	@Override
	public synchronized void reset() throws MroException {
		super.reset();
		try {
			((TreeBean) parent).reloadNodeWithoutReset();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
