<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*,psdi.mbo.*,psdi.server.*,psdi.util.*,org.w3c.dom.*,psdi.mbo.*,psdi.util.*,psdi.webclient.system.controller.*,psdi.webclient.system.beans.*,psdi.webclient.system.runtime.*,psdi.webclient.servlet.*,psdi.webclient.system.session.*,psdi.webclient.controls.*,psdi.webclient.components.*,java.util.*,java.io.*"
	errorPage=""%>
<%@page import="java.text.NumberFormat"%>
<%@page import="com.ibm.json.java.*"%>
<%!
	String[] props = {"assetnum", "description", "location", "radarnum", "drawno", "productcode", "productorderno", 
						"outdate", "deliverydate", "custnum", "receive", "armytype", "whicharmy", 
						"platformno", "qaperiod", "model", "attachstatus", "itemnum", "status"};
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
		Enumeration e;	
		int END_POS = toPos - 1; //MboSet最后一个需要处理的元素的位置
		for (int i = fromPos; i < toPos; i++) {  //遍历顶层MboSet中的Mbo
			jsonStrTmp.append("{");
			
			//根据MboSet自身的属性队列，获取并生成与属性队列相对应的json字符串
			e = msr.getMboSetInfo().getMboValuesInfo();
			Mbo mbo = (Mbo)msr.getMbo(i);
			boolean isFirst = true;
			while (e.hasMoreElements()) {
				MboValueInfo mv = (MboValueInfo)e.nextElement();
				String columnName = mv.getAttributeName();
				if(isFirst) {
					jsonStrTmp.append("\"" + columnName.toLowerCase() + "\":\"" + mbo.getString(columnName) + "\"");
					isFirst = false;
				} else {
					jsonStrTmp.append(", \"" + columnName.toLowerCase() + "\":\"" + mbo.getString(columnName) + "\"");
				}
			}		
			
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
			//根据给定的属性队列，获取并生成与属性队列相对应的json字符串
			for(int j = 0, LENGTH = props.length; j < LENGTH; j++) {
				if(j == 0) {
					jsonStrTmp.append("\"" + props[j].toLowerCase() + "\":\"" + mbo.getString(props[j]) + "\"");
				} else {
					jsonStrTmp.append(", \"" + props[j].toLowerCase() + "\":\"" + mbo.getString(props[j]) + "\"");
				}
			}
			
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
	 * 子层/相关业务关系信息存储结构
	*/
	class SubRla {
		String relation = ""; //关系名称
		String[] props;        //业务属性
		
		//封装区间[fromPos, toPos)：[0, 1)默认取首个MBO
		int fromPos = 0;      //位置起点[fromPos
		int toPos = 1;        //位置终点toPos)
	}
	/**
	 * 将MBOSET中的MBO数据转换为JSON字符串
	 * 转换范围为MBOSET[fromPos, toPos)
	 * 表属性名称为指定属性列表中所提供的属性名称，且均为字母小写
	 * 针对提供的子层/相关业务关系名称，可将子层/相关业务数据以json字符串格式封装，并有此属性名称指向
	*/
	StringBuffer mboToJsonString(MboSetRemote msr, String[] props, int fromPos, int toPos, SubRla[] subRlas) throws MXException, java.rmi.RemoteException {
		StringBuffer jsonStrTmp = new StringBuffer(); //json 字符串缓存对象
		if(msr == null || msr.count() <= 0 || props == null || props.length <= 0 || fromPos < 0 || fromPos >= toPos || toPos > msr.count()) { //异常参数
			jsonStrTmp.append("[]");
			return jsonStrTmp;
		}
		boolean IS_SUB_JSON_REQUIRED = (subRlas == null || subRlas.length <= 0)? false: true; //是否需要装载子层/相关业务json
		
		jsonStrTmp.append("[");
		int END_POS = toPos - 1; //MboSet最后一个需要处理的元素的位置
		for (int i = fromPos; i < toPos; i++) { //遍历顶层MboSet中的Mbo
			Mbo mbo = (Mbo)msr.getMbo(i);
			jsonStrTmp.append("{");
			//根据给定的属性队列，获取并生成与属性队列相对应的json字符串
			for(int propIndex = 0, LENGTH = props.length; propIndex < LENGTH; propIndex++) { 
				if(propIndex == 0) {
					jsonStrTmp.append("\"" + props[propIndex].toLowerCase() + "\":\"" + mbo.getString(props[propIndex]) + "\"");
				} else {
					jsonStrTmp.append(", \"" + props[propIndex].toLowerCase() + "\":\"" + mbo.getString(props[propIndex]) + "\"");
				}
			}
			
			if(IS_SUB_JSON_REQUIRED) { //需要装载子层/相关业务json
				//根据给定的子层/相关业务关系队列，获取并生成与其相对应的json字符串
				for(int relIndex = 0, REL_LENGTH = subRlas.length; relIndex < REL_LENGTH; relIndex++) {
					jsonStrTmp.append(", \"" + subRlas[relIndex].relation.toLowerCase() + "\"" 
											+ ":" + mboToJsonString(mbo.getMboSet(subRlas[relIndex].relation), subRlas[relIndex].props, subRlas[relIndex].fromPos, subRlas[relIndex].toPos));
				}
			}
			
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
	//设置回响为json格式数据输出
	response.setContentType("application/json");
	response.setCharacterEncoding("UTF-8");
	response.setHeader("Cache-Control", "no-cache");
	java.io.PrintWriter pw = response.getWriter();
	
	/* 查询结果集 */
	MboSetRemote msr = MXServer.getMXServer().getMboSet("ASSET", MXServer.getMXServer().getUserInfo("MAXADMIN"));	
	String sqlWhere = "ASSETLEVEL='ASSET'"; //ASSET系统级，SYSTEM分系统级别
	//sqlWhere = " and STATUS='活动'";
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
		pageNumber = (pageNumber >= 1? pageNumber: 1);
	}
	String pageSizeStr = request.getParameter("pageSize");     //每页多少条
	if(pageSizeStr == null || "".equals(pageSizeStr)) {
		pageSize = 10;
	} else {
		pageSize = Integer.parseInt(pageSizeStr);
		pageSize = (pageSize >= 1? pageSize: 10);
	}
	
	/* 计算当前可提供的分页起始以及终止位置 */
	int fromPos, endPos, pageTotal;//[fromPos, toPos)
	pageTotal = (COUNT<=pageSize? 1: (COUNT%pageSize==0? (int)(COUNT/pageSize): ((int)(COUNT/pageSize)+1)));
	if(pageNumber <= pageTotal) {
		fromPos = (pageNumber - 1) * pageSize;
		fromPos = (fromPos < 0? 0: fromPos);
		endPos = (pageNumber*pageSize>COUNT)? COUNT: pageNumber*pageSize;
	} else {
		fromPos = (pageTotal - 1) * pageSize;
		fromPos = (fromPos < 0? 0: fromPos);
		endPos = COUNT;
	}

	/* 输出符合条件的结果集 */
	pw.write("{\"total\":" + COUNT);
	pw.write(", \"rows\": ");
	pw.write(mboToJsonString(msr, fromPos, endPos).toString());
	pw.write("}");   
%>
