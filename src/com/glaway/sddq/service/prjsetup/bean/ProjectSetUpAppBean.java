package com.glaway.sddq.service.prjsetup.bean;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.glaway.mro.controller.AppBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;

/**
 * 
 * 项目信息AppBean
 * 
 * @author ygao
 * @version [版本号, 2017-10-18]
 * @since [产品/模块版本]
 */
public class ProjectSetUpAppBean extends AppBean {

	/**
	 * 配置数据导出方法
	 * 
	 * @throws MroException
	 * @throws IOException
	 *             [参数说明]
	 * 
	 */
	public void CONFIFIEXPORT() throws MroException, IOException {
		HttpServletRequest request = getMroSession().getRequest();
		String path = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + request.getContextPath()
				+ "/";
		// 1、 获取项目编号
		String PmNO = this.getJpo().getString("PROJECTNUM");
		Date actiondate = MroServer.getMroServer().getDate();
		String person = getUserInfo().getLoginID();
		String status = this.getJpo().getString("status");
		IJpoSet boLineSet = MroServer.getMroServer().getSysJpoSet("BOMOUTLINE");
		IJpo boLine = boLineSet.addJpo();

		// 2、创建后台执行任务记录

		/*
		 * IJpoSet boLineSet =
		 * MroServer.getMroServer().getSysJpoSet("BOMOUTLINE"); IJpo boLine =
		 * boLineSet.addJpo();
		 * MroServer.getMroServer().getSystemUserServer().getUserInfo()
		 * .setDefaultOrg("CRRC");
		 * MroServer.getMroServer().getSystemUserServer().getUserInfo()
		 * .setDefaultSite("ELEC"); boLine.setValue("PROJECTNUM", PmNO); //
		 * 项目、人员、时间、状态... boLine.setValue("ACTIONPERSON", person);
		 * boLine.setValue("ACTIONDATE", actiondate); boLine.setValue("STATUS",
		 * "未完成"); boLine.setValue("ORGID", "CRRC"); boLine.setValue("SITEID",
		 * "ELEC");
		 */
		// boLineSet.save();
		// 3、提示信息
		this.showMsgbox("", "配置数据导出任务将在后台执行，请稍后查看并下载文件");
		// 4、开始任务，
		BomOutToFile(boLine, path, PmNO, person);

	}

	public void BomOutToFile(IJpo boLine, String path, String PmNO,
			String person) throws MroException, IOException {

		// 1 查询项目编号下的车辆信息和操作表的jposet
		IJpoSet assetset = this.getJpo().getJpoSet("ASSET");
		IJpoSet bomoutlineset = this.getJpo().getJpoSet("BOMOUTLINE");
		HttpServletRequest request = this.getMroSession().getRequest();
		HttpSession session = request.getSession();
		session.setAttribute("assetset", assetset);
		session.setAttribute("bomoutlineset", bomoutlineset);
		session.setAttribute("PmNO", PmNO);
		session.setAttribute("person", person);

		path += "configdowmload?PmNO='" + PmNO + "'&person='" + person + "'";
		openurl(path);

	}

}
