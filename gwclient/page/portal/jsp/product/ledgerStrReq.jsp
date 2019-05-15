<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*,psdi.mbo.*,psdi.server.*,psdi.util.*,org.w3c.dom.*,psdi.mbo.*,psdi.util.*,psdi.webclient.system.controller.*,psdi.webclient.system.beans.*,psdi.webclient.system.runtime.*,psdi.webclient.servlet.*,psdi.webclient.system.session.*,psdi.webclient.controls.*,psdi.webclient.components.*,java.util.*,java.io.*"
	errorPage=""%>
<%@page import="java.text.NumberFormat"%>
<%@page import="com.ibm.json.java.*"%>
<%!
	String[] props = {"assetnum", "description", "radarnum", "drawno", "armytype", "qaperiod", "outdate", "deliverydate", "siteid", "parent"};
	/**
	 * 将MBOSET中的MBO数据转换为JSON字符串
	 * 转换范围为MBOSET[fromPos, toPos)
	 * 表属性名称为指定属性列表中所提供的属性名称，且均为字母小写
	*/
	StringBuffer mboToJsonString(MboSetRemote msr, int fromPos, int toPos) throws MXException, java.rmi.RemoteException {
		StringBuffer jsonStrTmp = new StringBuffer(); //json 字符串缓存对象
		if(msr == null || msr.count() <= 0 || props == null || props.length <= 0 || fromPos < 0 || fromPos >= toPos || toPos > msr.count()) { //异常参数
			return jsonStrTmp;
		}
		
		int END_POS = toPos - 1; //MboSet最后一个需要处理的元素的位置
		for (int i = fromPos; i < toPos; i++) {  //遍历顶层MboSet中的Mbo
			Mbo mbo = (Mbo)msr.getMbo(i);
			
			jsonStrTmp.append("{");
			//根据给定的属性队列，获取并生成与属性队列相对应的json字符串
			for(int j = 0, LENGTH = props.length; j < LENGTH; j++) {
				if(j == 0) {
					jsonStrTmp.append("\"" + props[j].toLowerCase() + "\":\"" + mbo.getString(props[j]) + "\"");
				} else {
					jsonStrTmp.append(", \"" + props[j].toLowerCase() + "\":\"" + mbo.getString(props[j]) + "\"");
				}
			}
			jsonStrTmp.append(", \"isParent\":\"" + (((psdi.app.asset.Asset) mbo).hasChildren()? true: false) + "\"");
			jsonStrTmp.append(", \"nodeName\":\"" + mbo.getString(props[0]) + ": " + mbo.getString(props[1]) + "\"");
			
			if(i == END_POS) { //已遍历至最后一个MboSet元素
				jsonStrTmp.append("}");
			} else {
				jsonStrTmp.append("}, ");
			}
		}
		return jsonStrTmp;
	}
	
	/**
	 * 将MBOSET中的MBO数据转换为JSON字符串
	 * 转换范围为MBOSET[fromPos, toPos)
	 * 表属性名称为指定属性列表中所提供的属性名称，且均为字母小写
	*/
	StringBuffer mboToJsonString(MboSetRemote msr, boolean isChildrenNeeded) throws MXException, java.rmi.RemoteException {
		StringBuffer jsonStrTmp = new StringBuffer(); //json 字符串缓存对象
		if(msr == null || msr.count() <= 0) { //异常参数
			jsonStrTmp.append("[]");
			return jsonStrTmp;
		}
		Mbo mbo = (Mbo)msr.getMbo(0);
		
		jsonStrTmp.append("[");
		if(isChildrenNeeded) {
			jsonStrTmp.append("{");
			//根据给定的属性队列，获取并生成与属性队列相对应的json字符串
			for(int j = 0, LENGTH = props.length; j < LENGTH; j++) {
				if(j == 0) {
					jsonStrTmp.append("\"" + props[j].toLowerCase() + "\":\"" + mbo.getString(props[j]) + "\"");
				} else {
					jsonStrTmp.append(", \"" + props[j].toLowerCase() + "\":\"" + mbo.getString(props[j]) + "\"");
				}
			}
			jsonStrTmp.append(", \"open\":\"true\""); //根节点初始默认成打开状态
			jsonStrTmp.append(", \"nodeName\":\"" + mbo.getString(props[0]) + ": " + mbo.getString(props[1]) + "\"");
			jsonStrTmp.append("}");
			
			MboSetRemote children = ((psdi.app.asset.Asset) mbo).getChildren(); 
			children.setOrderBy("ASSETNUM");
			if(children.count() > 0) {
				jsonStrTmp.append("," + mboToJsonString(children, 0, children.count()));
			}
		} else {
			MboSetRemote children = ((psdi.app.asset.Asset) mbo).getChildren(); 
			children.setOrderBy("ASSETNUM");
			if(children.count() > 0) {
				jsonStrTmp.append(mboToJsonString(children, 0, children.count()));
			}
		}
		jsonStrTmp.append("]"); 
		return jsonStrTmp;
	}
%>
<%
	//设置回响为json格式数据输出
	response.setContentType("application/json");
	response.setCharacterEncoding("UTF-8");
	response.setHeader("Cache-Control", "no-cache");
	java.io.PrintWriter pw = response.getWriter();

	String assetnum = request.getParameter("assetnum"); //以autoParam参数为优先选择对象
	String siteid = "";
	String assetLevel = ""; //判断是否是根节点的依据
	if(assetnum != null && !"".equals(assetnum)) {//内层节点
		siteid = request.getParameter("siteid");
	} else {//根节点
		assetnum = request.getParameter("o_assetnum"); 
		siteid = request.getParameter("o_siteid");
		assetLevel = request.getParameter("o_assetlevel");
	}
	
	MboSetRemote msr = MXServer.getMXServer().getMboSet("ASSET", MXServer.getMXServer().getUserInfo("MAXADMIN"));
	String sqlWhere = "";
	if("".equals(assetLevel)) { //内层节点
		sqlWhere = "ASSETNUM='" + assetnum + "' AND SITEID='" + siteid + "' "; //ASSET系统级，SYSTEM分系统级别
	} else { //根节点
		sqlWhere = "ASSETLEVEL='ASSET' AND ASSETNUM='" + assetnum + "' AND SITEID='" + siteid + "' "; //ASSET系统级，SYSTEM分系统级别
	}
	
	msr.setWhere(sqlWhere);
	msr.reset();
	if(msr.count() <= 0) {
		pw.write("[]");
	} else {
		if(!"".equals(assetLevel) && "ASSET".equals(assetLevel.toUpperCase())) { //根节点
			StringBuffer sb = mboToJsonString(msr, true);
			pw.write(sb.toString());
		} else {
			StringBuffer sb = mboToJsonString(msr, false); //内层节点
			pw.write(sb.toString());
		}
	}
%>
