package com.glaway.sddq.tools;

import io.netty.util.internal.StringUtil;

import java.util.ArrayList;
import java.util.List;

import com.glaway.mro.app.system.role.data.Role;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * 消息工具类
 * 
 * @author public2175
 * @version [版本号, 2018-9-12]
 * @since [产品/模块版本]
 */
public class MsgUtil {
	
	public final static String SQNMSG = "产品序列号已存在";
	public final static String PERSONADD = "新增人员，请及时处理";
	// 配置管理员角色
	public final static String PZROLE = "PZGLY";
	
	// 系统管理员组角色

	public final static String GWADMINPERSON = "MROADMIN";
	
	/**
	 * 
	 * <功能描述>
	 * 
	 * @param app
	 * @param id
	 * @param subject
	 * @param content
	 * @param roleName
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public static void addMsg(String app, long id, String subject,
			String content, String roleName) throws MroException {

		if (!StringUtil.isNullOrEmpty(roleName)) {

			List<String> userList = new ArrayList<String>();
			IJpoSet roleSet = MroServer.getMroServer().getSysJpoSet("sys_role",
					"maxrole='" + roleName + "'");
			Role roleJpo = (Role) roleSet.getJpo(0);
			roleJpo.getPersonByRole(roleJpo, userList);

			MroServer.getMroServer().getSystemUserServer().getUserInfo()
					.setDefaultOrg("CRRC");
			MroServer.getMroServer().getSystemUserServer().getUserInfo()
					.setDefaultSite("ELEC");
			IJpoSet msgJposet = MroServer.getMroServer().getJpoSet("MSGMANAGE",
					MroServer.getMroServer().getSystemUserServer());
			for (int index = 0; index < userList.size(); index++) {
				IJpo msgjpo = msgJposet.addJpo(GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				msgjpo.setValue("appid", id);
				msgjpo.setValue("app", app);
				msgjpo.setValue("subject", subject);
				msgjpo.setValue("content", content);
				msgjpo.setValue("receiver", userList.get(index));
			}
			msgJposet.save();
		}
	}
	
	/**
	 * 
	 * 为单个人员新建消息
	 * @param app
	 * @param id
	 * @param subject
	 * @param content
	 * @param personid
	 * @throws MroException [参数说明]
	 *
	 */
	public static void addMsgByPersonid(String app, long id, String subject,	String content, 
			String personid) throws MroException{
		
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
		.setDefaultOrg("CRRC");
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
				.setDefaultSite("ELEC");
		IJpoSet msgJposet = MroServer.getMroServer().getJpoSet("MSGMANAGE",
				MroServer.getMroServer().getSystemUserServer());
		IJpo msgjpo = msgJposet.addJpo(GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		msgjpo.setValue("appid", id);
		msgjpo.setValue("app", app);
		msgjpo.setValue("subject", subject);
		msgjpo.setValue("content", content);
		msgjpo.setValue("receiver", personid);
		msgJposet.save();
	}
	
	/**
	 * 
	 * 为多个人员新建消息
	 * @param app
	 * @param id
	 * @param subject
	 * @param content
	 * @param persons
	 * @throws MroException [参数说明]
	 *
	 */
	public static void addMsgByPersons(String app, long id, String subject,	String content, 
			String persons) throws MroException{
		
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
		.setDefaultOrg("CRRC");
		MroServer.getMroServer().getSystemUserServer().getUserInfo()
				.setDefaultSite("ELEC");
		IJpoSet msgJposet = MroServer.getMroServer().getJpoSet("MSGMANAGE",
				MroServer.getMroServer().getSystemUserServer());
		IJpoSet personSet = MroServer.getMroServer().getSysJpoSet("SYS_PERSON");
		personSet.setUserWhere("personid in (" + persons + ")");
		personSet.reset();
		if(personSet != null && personSet.count() > 0){
			for (int index = 0; index < personSet.count(); index++) {
				IJpo msgjpo = msgJposet.addJpo(GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
				msgjpo.setValue("appid", id);
				msgjpo.setValue("app", app);
				msgjpo.setValue("subject", subject);
				msgjpo.setValue("content", content);
				msgjpo.setValue("receiver", personSet.getJpo(index));
			}
			msgJposet.save();
		}
	}
}
