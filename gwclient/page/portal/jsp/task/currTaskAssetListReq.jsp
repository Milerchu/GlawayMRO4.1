<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*,psdi.mbo.*,psdi.server.*,psdi.util.*,org.w3c.dom.*,psdi.mbo.*,psdi.util.*,psdi.webclient.system.controller.*,psdi.webclient.system.beans.*,psdi.webclient.system.runtime.*,psdi.webclient.servlet.*,psdi.webclient.system.session.*,psdi.webclient.controls.*,psdi.webclient.components.*,java.util.*,java.io.*"
	errorPage=""%>
<%@page import="java.text.NumberFormat"%>
<%@page import="com.ibm.json.java.*"%>
<%!
	/**
	 * 将MBO数据转换为JSON字符串
	 * 可指定prefix值，以增加属性标识的前缀
	 * 表属性名称为指定属性列表中所提供的属性名称，且均为字母小写
	*/
	StringBuffer mboToJsonString(MboRemote mbo, String[] props, String prefix) throws MXException, java.rmi.RemoteException {
		StringBuffer jsonStrTmp = new StringBuffer(); //json 字符串缓存对象
		if(mbo == null) { 
			return jsonStrTmp;
		}
		
		if(props != null && props.length > 0) { //但传入的指定属性非空时，以指定的属性列为封装对象
			//根据给定的属性队列，获取并生成与属性队列相对应的json字符串
			for(int j = 0, LENGTH = props.length; j < LENGTH; j++) {
				if(j == 0) {
					jsonStrTmp.append("\"" + prefix + props[j].toLowerCase() + "\":\"" + mbo.getString(props[j]) + "\"");
				} else {
					jsonStrTmp.append(", \"" + prefix + props[j].toLowerCase() + "\":\"" + mbo.getString(props[j]) + "\"");
				}
			}
		} else { //当传入的属性为空时，以MBOSET的属性列为封装对象
			Enumeration e;	
				
			//根据MboSet自身的属性队列，获取并生成与属性队列相对应的json字符串
			e = mbo.getThisMboSet().getMboSetInfo().getMboValuesInfo();
			boolean isFirst = true;
			while (e.hasMoreElements()) {
				MboValueInfo mv = (MboValueInfo)e.nextElement();
				String columnName = mv.getAttributeName();
				if(isFirst) {
					jsonStrTmp.append("\"" + prefix + columnName.toLowerCase() + "\":\"" + mbo.getString(columnName) + "\"");
					isFirst = false;
				} else {
					jsonStrTmp.append(", \"" + prefix + columnName.toLowerCase() + "\":\"" + mbo.getString(columnName) + "\"");
				}
			}		
		}
		
		return jsonStrTmp;
	}
	
	/**
	 * 将MBOSET中的MBO数据转换为JSON字符串
	 * 转换范围为MBOSET[fromPos, toPos)
	 * 表属性名称为MBOSET中所获取的属性名称，且均为字母小写
	*/
	StringBuffer mboToJsonString(MboSetRemote msr, int fromPos, int toPos) throws MXException, java.rmi.RemoteException {
		StringBuffer jsonStrTmp = new StringBuffer(); //json 字符串缓存对象
		if(msr == null || msr.count() <= 0 || fromPos < 0 || fromPos >= toPos || toPos > msr.count()) { //异常参数
			jsonStrTmp.append("[]");
			return jsonStrTmp;
		}
		
		jsonStrTmp.append("[");
		int END_POS = toPos - 1; //MboSet最后一个需要处理的元素的位置
		for (int i = fromPos; i < toPos; i++) {  //遍历顶层MboSet中的Mbo
			Mbo mbo = (Mbo)msr.getMbo(i);
		
			jsonStrTmp.append("{");
			jsonStrTmp.append(mboToJsonString(mbo, null, "")); //将MBO数据转换为JSON字符串
		
			if(i == END_POS) { //已遍历至最后一个MboSet元素
				jsonStrTmp.append("}");
			} else {
				jsonStrTmp.append("}, ");
			}
		}
		jsonStrTmp.append("]"); 
		return jsonStrTmp;
	}
	
	/**
	 * 将MBOSET中的MBO数据转换为JSON字符串
	 * 转换范围为MBOSET[fromPos, toPos)
	 * 表属性名称为指定属性列表中所提供的属性名称，且均为字母小写
	*/
	StringBuffer mboToJsonString(MboSetRemote msr, String[] props, int fromPos, int toPos) throws MXException, java.rmi.RemoteException {
		StringBuffer jsonStrTmp = new StringBuffer(); //json 字符串缓存对象
		if(msr == null || msr.count() <= 0 || props == null || props.length <= 0 || fromPos < 0 || fromPos >= toPos || toPos > msr.count()) { //异常参数
			jsonStrTmp.append("[]");
			return jsonStrTmp;
		}
		
		jsonStrTmp.append("[");
		int END_POS = toPos - 1; //MboSet最后一个需要处理的元素的位置
		for (int i = fromPos; i < toPos; i++) {  //遍历顶层MboSet中的Mbo
			Mbo mbo = (Mbo)msr.getMbo(i);
			jsonStrTmp.append("{");
			
			jsonStrTmp.append(mboToJsonString(mbo, props, "")); //将MBO数据转换为JSON字符串
			
			if(i == END_POS) { //已遍历至最后一个MboSet元素
				jsonStrTmp.append("}");
			} else {
				jsonStrTmp.append("}, ");
			}
		}
		jsonStrTmp.append("]"); 
		return jsonStrTmp;
	}
	
%>
<%
	String[] props = {"assetnum", "description", "location", "radarnum", "drawno", "productcode", "productorderno", 
					"outdate", "deliverydate", "custnum", "receive", 
					"platformno", "qaperiod", "model", "attachstatus", "itemnum", "status", "siteid",
					"qcnum", "version"};

	//设置回响为json格式数据输出
	response.setContentType("application/json");
	response.setCharacterEncoding("UTF-8");
	response.setHeader("Cache-Control", "no-cache");
	java.io.PrintWriter pw = response.getWriter();

	String fieldtasknum = request.getParameter("fieldtasknum"); //外场任务编号
	String siteid = request.getParameter("siteid"); //siteid
	
	/* 查询结果集 */
	MboSetRemote msr = MXServer.getMXServer().getMboSet("ASSET", MXServer.getMXServer().getUserInfo("MAXADMIN"));	
	String sqlWhere = "ASSETNUM in (select ASSETNUM from FTASSET where fieldtasknum='" + fieldtasknum + "' and siteid='" + siteid + "')"; 
	sqlWhere += " and siteid='" + siteid + "'";
	msr.setWhere(sqlWhere);
	msr.reset();
	int COUNT = msr.count(); //总结果集数量
	
	/* 获取前端对分页数据的需求 */
	int pageNumber, pageSize;
	String pageNumberStr = request.getParameter("pageNumber"); //第几页
	if(pageNumberStr == null || "".equals(pageNumberStr)) {
		pageNumber = 1;
	} else {
		pageNumber = Integer.parseInt(pageNumberStr);
		pageNumber = (pageNumber >= 0? pageNumber: 0);
	}
	String pageSizeStr = request.getParameter("pageSize");     //每页多少条
	if(pageSizeStr == null || "".equals(pageSizeStr)) {
		pageSize = 10;
	} else {
		pageSize = Integer.parseInt(pageSizeStr);
		pageSize = (pageSize >= 1? pageSize: 10);
	}
	
	/* 计算当前可提供的分页起始以及终止位置 */
	int fromPos, endPos, pageTotal;
	pageTotal = (COUNT<=pageSize? 1: (COUNT%pageSize==0? (int)(COUNT/pageSize): ((int)(COUNT/pageSize)+1)));
	if(pageNumber <= pageTotal) {
		fromPos = pageNumber * pageSize;
		fromPos = (fromPos < 0? 0: fromPos);
		endPos = ((pageNumber+1)*pageSize>COUNT)? COUNT: (pageNumber+1)*pageSize;
	} else {
		fromPos = (pageTotal - 1) * pageSize;
		fromPos = (fromPos < 0? 0: fromPos);
		endPos = COUNT;
	}

	/* 输出符合条件的结果集 */
	pw.write("{\"total\":" + COUNT);
	pw.write(", \"rows\": ");
	pw.write(mboToJsonString(msr, props, fromPos, endPos).toString());
	pw.write("}");   
%>
