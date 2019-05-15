package com.glaway.sddq.config.csbom.bean;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.controller.TreeBean;
import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.DBManager;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.page.control.TreeNode;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.StringUtil;

/**
 * @ClassName SelectProductDataBean
 * @Description 选择产品DataBean
 * @author public2175
 * @Date 2018-8-15 下午2:07:57
 * @version 1.0.0
 */
public class SelectProductForSbomDataBean extends DataBean {

	@Override
	public int execute() throws MroException, IOException {

		// 暂定从产品SBOM表ASSETTMP表中取装机部件，有ANCESTOR
		String itemnum = this.getJpo().getString("SBOMITEM");
		String assettmpnum = this.getJpo().getString("assetnum");
		// 到ASSETTMP表中获取assettmpnum
		IJpoSet jposet = MroServer.getMroServer().getSysJpoSet("ASSETTMP");
		jposet.setUserWhere("ASSETTMPNUM ='" + assettmpnum
				+ "' and assettmplevel = 'ASSET'");
		jposet.reset();
		if (jposet != null && jposet.count() > 0) {
			IJpo jpo = jposet.getJpo(0);
			String installNum = jpo.getString("ancestor");
			// 装机目标部件,只能选择顶层节点进行装机，toParent = ancestor
			DataBean databean = this.getDataBean("1375344856389");
			if (databean != null) {
				super.checkSave();
				TreeBean sbomTreeBean = (TreeBean) databean;
				TreeNode treenode = sbomTreeBean.getCurrNode();
				String toAncestor = treenode.getJpo().getString("ancestor");
				String toParent = treenode.getJpo().getString("ASSETCSNUM");
				String rassettmpnum = getMaxnum(toAncestor, toParent);
				Connection conn = this.getMroSession().getUserServer()
						.getConnection();
				try {
					String assetCsSql = "insert into assetcs cs "
							+ "(ASSETCSNUM, PARENT,ANCESTOR,NUM,PRODUCTCODE,SOFTNAME,SOFTVERSION,SOFTUPDATE,IMAGEAPP,IMAGEID,LINENUM,RNUM,ITEMNUM,CONFIGNUM,LOC,LOCDESC,REMARK,ASSETCSID, ASSETCSLEVEL, CHANGEBY, CHANGEDATE, CREATEDATE, DESCRIPTION, DRAWNO, LANGCODE, LOCATION,MODEL,ORGID,SITEID,STATUS,STATUSDATE,TYPE,VERSION,SECRETLEVEL,SECRETLEVELDES) "
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
							+ "'),PRODUCTCODE,SOFTNAME,SOFTVERSION,SOFTUPDATE,'ASSETTMP',assettmpid,LINENUM,RNUM,ITEMNUM,CONFIGNUM,LOC,LOCDESC,REMARK, ASSETCSSEQ.NEXTVAL,'SYSTEM', CHANGEBY, CHANGEDATE, CREATEDATE, DESCRIPTION, DRAWNO,'"
							+ StringUtil.getSafeSqlStr(this.getUserInfo()
									.getDefaultLang())
							+ "', LOCATION, MODEL, ORGID,  SITEID, STATUS, STATUSDATE, TYPE, VERSION,0,'非密' from assettmp "
							+ " where  ANCESTOR='"
							+ StringUtil.getSafeSqlStr(installNum)
							+ "' and assettmpnum = '"
							+ StringUtil.getSafeSqlStr(installNum) + "')";

					String assetChildrenCsSql1 = "insert into assetcs cs "
							+ "(ASSETCSNUM, PARENT,ANCESTOR,NUM,PRODUCTCODE,SOFTNAME,SOFTVERSION,SOFTUPDATE,IMAGEAPP,IMAGEID,LINENUM,RNUM,ITEMNUM,CONFIGNUM,LOC,LOCDESC,REMARK,ASSETCSID, ASSETCSLEVEL, CHANGEBY, CHANGEDATE, CREATEDATE, DESCRIPTION, DRAWNO, LANGCODE, LOCATION,MODEL,ORGID,SITEID,STATUS,STATUSDATE,TYPE,VERSION,SECRETLEVEL,SECRETLEVELDES) "
							+ "(select concat('"
							+ StringUtil.getSafeSqlStr(rassettmpnum)
							+ ".',ASSETTMPNUM),'"
							+ StringUtil.getSafeSqlStr(rassettmpnum)
							+ "','"
							+ StringUtil.getSafeSqlStr(toAncestor)
							+ "',concat('"
							+ StringUtil.getSafeSqlStr(rassettmpnum)
							+ ".',NUM),PRODUCTCODE,SOFTNAME,SOFTVERSION,SOFTUPDATE,'ASSETTMP',assettmpid,LINENUM,RNUM,ITEMNUM,CONFIGNUM,LOC,LOCDESC,REMARK, ASSETCSSEQ.NEXTVAL,'SYSTEM', CHANGEBY, CHANGEDATE, CREATEDATE, DESCRIPTION, DRAWNO,'"
							+ StringUtil.getSafeSqlStr(this.getUserInfo()
									.getDefaultLang())
							+ "', LOCATION, MODEL, ORGID,  SITEID, STATUS, STATUSDATE, TYPE, VERSION,0,'非密' from assettmp "
							+ " where  ANCESTOR='"
							+ StringUtil.getSafeSqlStr(installNum)
							+ "' and parent = '"
							+ StringUtil.getSafeSqlStr(installNum) + "')";

					String assetChildrenCsSql2 = "insert into assetcs cs "
							+ "(ASSETCSNUM, PARENT,ANCESTOR,NUM,PRODUCTCODE,SOFTNAME,SOFTVERSION,SOFTUPDATE,IMAGEAPP,IMAGEID,LINENUM,RNUM,ITEMNUM,CONFIGNUM,LOC,LOCDESC,REMARK,ASSETCSID, ASSETCSLEVEL, CHANGEBY, CHANGEDATE, CREATEDATE, DESCRIPTION, DRAWNO, LANGCODE, LOCATION,MODEL,ORGID,SITEID,STATUS,STATUSDATE,TYPE,VERSION,SECRETLEVEL,SECRETLEVELDES) "
							+ "(select concat('"
							+ StringUtil.getSafeSqlStr(rassettmpnum)
							+ ".',ASSETTMPNUM),"
							+ "concat('"
							+ StringUtil.getSafeSqlStr(rassettmpnum)
							+ ".',PARENT),'"
							+ StringUtil.getSafeSqlStr(toAncestor)
							+ "',concat('"
							+ StringUtil.getSafeSqlStr(rassettmpnum)
							+ ".',NUM),PRODUCTCODE,SOFTNAME,SOFTVERSION,SOFTUPDATE,'ASSETTMP',assettmpid,LINENUM,RNUM,ITEMNUM,CONFIGNUM,LOC,LOCDESC,REMARK, ASSETCSSEQ.NEXTVAL,'SYSTEM', CHANGEBY, CHANGEDATE, CREATEDATE, DESCRIPTION, DRAWNO,'"
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
							+ "',CONFIGNUM='"
							+ this.getJpo().getString("CONFIGNUM")
							+ "',STATUS='" + this.getJpo().getString("STATUS")
							+ "' where assetcsnum='" + rassettmpnum + "'";

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
				} catch (SQLException e) {
					System.out.println(e);
					throw new AppException("assettmp", "backerror");
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
		return super.getAppBean().SAVE();
	}

	public String getMaxnum(String ancestor, String parent) throws MroException {
		IJpoSet jposet = MroServer.getMroServer().getSysJpoSet("ASSETCS");
		// jposet.setUserWhere("assetcsnum=(select max(assetcsnum) from assetcs where parent = '"
		// + parent
		// + "' and ancestor = '" + ancestor + "')");

		jposet.setUserWhere("parent = '" + parent + "' and ancestor = '"
				+ ancestor + "'");
		jposet.reset();
		// if (jposet != null && !jposet.isEmpty()) {
		// String assettmpnum = jposet.getJpo(0).getString("assetcsnum");
		// if (assettmpnum.lastIndexOf(".") < 0) {
		// return assettmpnum + "." + 1;
		// } else {
		// String num = assettmpnum.substring(assettmpnum.lastIndexOf(".") + 1);
		// int newnum = Integer.valueOf(num) + 1;
		// return String.valueOf(parent + "." + newnum);
		// }
		//
		// } else {
		// return String.valueOf(parent + ".1");
		// }
		int max = 0;
		for (int index = 0; index < jposet.count(); index++) {
			IJpo jpo = jposet.getJpo(index);
			String assettmpnum = jpo.getString("assetcsnum");
			if (assettmpnum.lastIndexOf(".") < 0) {
				if (max == 0) {
					max = 1;
				}
			} else {
				String num = assettmpnum
						.substring(assettmpnum.lastIndexOf(".") + 1);
				if (num.contains("TMP")) {
					if (max == 0) {
						max = 1;
					}
				} else {
					if (Integer.valueOf(num) + 1 > max) {
						max = Integer.valueOf(num) + 1;
					}
				}
			}
		}
		return String.valueOf(parent + "." + max);
	}
}
