package com.glaway.sddq.tools;

import java.util.ArrayList;
import java.util.List;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;

/**
 * 
 * 项目工具类
 * 
 * @author zhuhao
 * @version [版本号, 2018年9月19日]
 * @since [产品/模块版本]
 */
public class ProjectUtil {

	/**
	 * 
	 * 根据人员id获取项目中角色
	 * 
	 * @param personId
	 *            人员ID
	 * @param prjectNum
	 *            项目编号
	 * @return [参数说明]
	 * @throws MroException
	 * 
	 */
	public static List<String> getPrjRoleByPersonId(String projectNum,
			String personId) throws MroException {

		IJpoSet prjTeamSet = MroServer.getMroServer().getSysJpoSet(
				"PROJECTTEAMEMBER", "PROJECTNUM='" + projectNum + "'");
		if (prjTeamSet.isEmpty() || prjTeamSet == null) {
			throw new MroException("当前人员在该项目中无角色！");
		}
		List<String> returnList = new ArrayList<String>();

		for (int index = 0; index < prjTeamSet.count(); index++) {

			IJpo prjTeam = prjTeamSet.getJpo(index);
			returnList.add(prjTeam.getString("projectrole"));

		}

		return returnList;
	}

	/**
	 * 
	 * 判断某人在某项目中是否是某个角色
	 * 
	 * @param personId
	 *            人员id
	 * @param prjNum
	 *            项目编号
	 * @param prjRole
	 *            项目角色
	 * @return
	 * @throws MroException
	 *             [参数说明]
	 * 
	 */
	public static boolean isSomeRoleInTheProject(String personId,
			String prjNum, String prjRole) throws MroException {

		boolean flag = false;

		IJpoSet prjTeamSet = MroServer.getMroServer().getSysJpoSet(
				"PROJECTTEAMEMBER",
				"PROJECTNUM='" + prjNum + "' AND PROJECTROLE='" + prjRole
						+ "' AND PERSONID='" + personId + "'");
		if (!prjTeamSet.isEmpty()) {
			flag = true;
		}

		return flag;
	}

}
