package com.glaway.sddq.service.prjsetup.bean;

import java.io.IOException;
import java.util.List;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;

/**
 * 
 * 项目信息-选择组织内部人员 databean
 * 
 * @author ygao
 * @version [版本号, 2017-10-18]
 * @since [产品/模块版本]
 */
public class SelectPersonDataBean extends DataBean {

	@Override
	public void initialize() throws MroException {
		super.initialize();
		IJpo project = this.getAppBean().getJpo();
		IJpoSet roleset = MroServer.getMroServer().getJpoSet("SYS_ROLE",
				MroServer.getMroServer().getSystemUserServer());
		IJpoSet persongroupset = MroServer.getMroServer().getJpoSet(
				"SYS_PERSONGROUP",
				MroServer.getMroServer().getSystemUserServer());
		roleset.setQueryWhere("MAXROLE = 'PRJSETUP'");
		roleset.reset();
		String username = "";
		if (!roleset.isEmpty()) {
			IJpo role = roleset.getJpo();
			String usergroupnum = role.getString("VALUE");
			persongroupset
					.setQueryWhere("PERSONGROUP = '" + usergroupnum + "'");
			if (!persongroupset.isEmpty()) {
				IJpo persongroup = persongroupset.getJpo();
				IJpoSet pmset = persongroup
						.getJpoSet("PERSONGROUP_PRIMARYMEMBERS");
				username = pmset.getJpo().getString("RESPPARTYGROUP");
			}
		}
		if (project.getString("STATUS").equals("关闭")
				|| project.getString("STATUS").equals("已立项")) {
			// setFlag(GWConstant.S_READONLY, true);
			// throw new AppException("", "当前状态下无法编辑客户");
		} else if (project.getString("STATUS").equals("待审批")) {
			if (!getUserInfo().getUserName().equalsIgnoreCase(username)) {
				throw new AppException("", "当前状态下无法编辑客户");
			}
		} else if (project.getString("STATUS").equals("未通过")
				|| project.getString("STATUS").equals("不予立项")) {
			if (!getString("CREATEPERSON").equalsIgnoreCase(
					getUserInfo().getUserName())) {
				throw new AppException("", "当前状态下无法编辑客户");
			}
		}

		IJpoSet jposet = getJpoSet();
		String selected = "";
		// 过滤已选人员
		String where = "STATUS = '活动' and personid not in(select personid from sys_user where userid in "
				+ "(select userid from sys_groupuser, sys_group where sys_groupuser.groupname = sys_group.groupname and sys_group.groupname in"
				+ "('GWADMIN','MROAUDADMIN','MROSECADMIN','MROSYSADMIN')))";
		// 干系人jposet
		IJpoSet relatedSet = MroServer.getMroServer().getJpoSet(
				"RELATEDPEOPLEREG", project.getUserServer());
		// 排除外部人员
		relatedSet.setUserWhere("projectnum='"
				+ project.getString("projectnum") + "' and ISINSIDE=1");
		relatedSet.reset();
		if (relatedSet.count() > 0) {
			for (int index = 0; index < relatedSet.count(); index++) {
				IJpo rltdPl = relatedSet.getJpo(index);
				selected += "'" + rltdPl.getString("RELATEDPEOPLENUM") + "',";
			}
			// 去除字符串末尾逗号
			selected = selected.substring(0, selected.length() - 1);
			// 去除已经选择的人员
			where += "and personid not in (" + selected + ")";
		}
		jposet.setQueryWhere(where);
		jposet.reset();

	}

	@Override
	public int dialogok() throws IOException, MroException {
		DataBean gxrdataBean = this.page.getAppBean().getDataBean(
				"1508147231790");

		IJpoSet relatedpeople_1 = MroServer.getMroServer().getJpoSet(
				"RELATEDPEOPLEREG",
				MroServer.getMroServer().getSystemUserServer());

		String prjnum = this.page.getAppBean().getJpo().getString("PROJECTNUM");
		// 获取当前选择的人员
		List<IJpo> list = getJpoSet().getSelections();
		if (!list.isEmpty()) {
			for (int i = 0; i < list.size(); i++) {
				IJpo personjpo = list.get(i);
				IJpo rpjpo = relatedpeople_1.addJpo();
				rpjpo.setValue("RELATEDPEOPLENAME",
						personjpo.getString("displayname"));
				rpjpo.setValue("POSITION", personjpo.getString("post.postname"));
				rpjpo.setValue("RELATEDPEOPLENUM",
						personjpo.getString("personid"));
				rpjpo.setValue("PROJECTNUM", prjnum);
				rpjpo.setValue("TELEPHONE", personjpo.getString("PRIMARYPHONE"));
				rpjpo.setValue("SITEID", this.page.getAppBean().getJpo()
						.getString("SITEID"));
				rpjpo.setValue("ORGID", this.page.getAppBean().getJpo()
						.getString("ORGID"));
				rpjpo.setValue("ISINSIDE", 1);

			}
		}
		relatedpeople_1.save();
		gxrdataBean.resetAndReload();
		return super.dialogok();
	}
}
