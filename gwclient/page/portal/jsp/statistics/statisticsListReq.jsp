<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*,psdi.mbo.*,psdi.server.*,psdi.util.*,org.w3c.dom.*,psdi.mbo.*,psdi.util.*,psdi.webclient.system.controller.*,psdi.webclient.system.beans.*,psdi.webclient.system.runtime.*,psdi.webclient.servlet.*,psdi.webclient.system.session.*,psdi.webclient.controls.*,psdi.webclient.components.*,java.util.*,java.io.*"
	errorPage="" pageEncoding="utf-8" %>
<%@page import="java.text.NumberFormat"%>
<%@page import="com.ibm.json.java.*"%>
<%@include file="../util/CONSTANT.jsp"%>
<%!
	int getResultSetCount(Statement st, StringBuffer condStr) throws SQLException  {
		String sql = "select count(*) as count from"
					+ "(" 
							+ "select distinct fieldtasknum from"
							+ "("
									+ "select fulltask.*, p.displayname, p.deptnum, p.department from " 
									+ "( "
										+ " select ledgerFt.*, locations.description as locationname "  
										+ " from "   
										+ " ( "
											+ " select distinct a.assetnum, a.productcode, a.productorderno, a.radarnum, a.drawno, a.description as assetname, t.fieldtasknum, t.description, t.status, t.custnum, t.header, t.tasktype, t.location, t.siteid, t.createby, t.createdate "  
											+ " from ( " 
												+ " select distinct fa.assetnum, ft.fieldtasknum, ft.description, ft.status, ft.custnum, ft.header, ft.tasktype, ft.location, ft.siteid, ft.createby, ft.createdate "      
												+ " from fieldtask ft, ftasset fa "  
												+ " where ft.siteid=fa.siteid and ft.fieldtasknum=fa.fieldtasknum and ft.parentnum is null " 
											+ " )t, asset a, assetancestor aa " 
											+ " where (t.assetnum=a.assetnum and a.assetlevel='ASSET') or (t.assetnum=aa.assetnum and aa.ancestor=a.assetnum and a.assetlevel='ASSET') " 
										    + " order by t.createdate desc "
									    + " ) ledgerFt " 
									    + " left join locations " 
									    + " on ledgerFt.location=locations.location"
									+ ") fulltask " 
									+ "left join " 
									+ "( "
										+ "select person.personid, person.displayname, dept.deptnum, dept.description as department " 
										+ "from person " 
										+ "left join dept "
										+ "on person.department=dept.deptnum "
									+ ") p "
									+ "on fulltask.header=p.personid" 
							+ ") where " + condStr.toString()
					+ ")";
		int count = 0;
		ResultSet rs = st.executeQuery(sql);
		while(rs.next()) {
			count = rs.getInt("count");
			break;
		}
		rs.close();
		return count;
	}
	/**
	 * 将MBOSET中的MBO数据转换为JSON字符串
	 * 转换范围为MBOSET[fromPos, toPos)
	 * 表属性名称为指定属性列表中所提供的属性名称，且均为字母小写
	 * 针对提供的子层/相关业务关系名称，可将子层/相关业务数据以json字符串格式封装，并有此属性名称指向
	*/
	StringBuffer mboToJsonString(Statement st, int fromPos, int toPos, String[] props, StringBuffer condStr) throws SQLException {
		StringBuffer jsonStrTmp = new StringBuffer(); //json 字符串缓存对象
		if(st == null || fromPos < 0 || fromPos >= toPos || props.length <= 0) { //异常参数
			jsonStrTmp.append("[]");
			return jsonStrTmp;
		}
		jsonStrTmp.append("[");
		String sql = "select * from "
					+ "("
						+ "select rownum no, t.* from "
						+ "("
								+ "select distinct fieldtasknum, description, tasktype, department, custname, locationname, status from"
								+ "("
										+ "select t.*, custinfo.custname from "
										+ "("
												+ "select fulltask.*, p.displayname, p.deptnum, p.department from " 
												+ "( "
													+ " select ledgerFt.*, locations.description as locationname "  
													+ " from "   
													+ " ( "
														+ " select distinct a.assetnum, a.productcode, a.productorderno, a.radarnum, a.drawno, a.description as assetname, t.fieldtasknum, t.description, t.status, t.custnum, t.header, t.tasktype, t.location, t.siteid, t.createby, t.createdate "  
														+ " from ( " 
															+ " select distinct fa.assetnum, ft.fieldtasknum, ft.description, ft.status, ft.custnum, ft.header, ft.tasktype, ft.location, ft.siteid, ft.createby, ft.createdate "      
															+ " from fieldtask ft, ftasset fa "  
															+ " where ft.siteid=fa.siteid and ft.fieldtasknum=fa.fieldtasknum and ft.parentnum is null " 
														+ " )t, asset a, assetancestor aa " 
														+ " where (t.assetnum=a.assetnum and a.assetlevel='ASSET') or (t.assetnum=aa.assetnum and aa.ancestor=a.assetnum and a.assetlevel='ASSET') " 
													    + " order by t.createdate desc "
												    + " ) ledgerFt " 
												    + " left join locations " 
												    + " on ledgerFt.location=locations.location"
												+ ") fulltask " 
												+ "left join " 
												+ "( "
													+ "select person.personid, person.displayname, dept.deptnum, dept.description as department " 
													+ "from person " 
													+ "left join dept "
													+ "on person.department=dept.deptnum "
												+ ") p "
												+ "on fulltask.header=p.personid" 
										+ ") t " 
										+ "left join custinfo "
										+ "on t.custnum=custinfo.custnum "
								+ ") where " + condStr.toString() 
								+ "order by fieldtasknum desc "
						+ ") t " 
						+ "where rownum < " + (toPos + 1)
					+ ") where no >= " + (fromPos + 1);
					
		ResultSet rs = st.executeQuery(sql);
		boolean isCommaOutNeed = false;
		while(rs.next()) {
			if(isCommaOutNeed) {
				jsonStrTmp.append(", {");
			} else {
				jsonStrTmp.append("{");
				isCommaOutNeed = true;
			}
			boolean isCommaNeed = false;
			for(String prop: props) {
				String tmp = rs.getString(prop);
				if(isCommaNeed) {
					jsonStrTmp.append(", \"" + prop + "\":\"" + tmp + "\"");
				} else {
					jsonStrTmp.append("\"" + prop + "\":\"" + tmp + "\"");
					isCommaNeed = true;
				}
			}
			jsonStrTmp.append("}");
		}
		jsonStrTmp.append("]");
		return jsonStrTmp;
	}
	
	
	
	
	StringBuffer mboToJsonString(String pageNumberStr, String pageSizeStr, String[] props, StringBuffer condStr) throws MXException,
				java.rmi.RemoteException, SQLException {
		StringBuffer jsonStrTmp = new StringBuffer(); //json 字符串缓存对象
		/* 获取质保期内、外资产的外场任务数量 */
		psdi.security.ConnectionKey ck = MXServer.getMXServer().getDBManager().getSystemConnectionKey();
		Connection connection = MXServer.getMXServer().getDBManager().getConnection(ck);
		Statement st = connection.createStatement();
		
		int COUNT = getResultSetCount(st, condStr); //总结果集数量
		/* 获取前端对分页数据的需求 */
		int pageNumber, pageSize;
		if(pageNumberStr == null || "".equals(pageNumberStr)) {
			pageNumber = 0;
		} else {
			pageNumber = Integer.parseInt(pageNumberStr);
			pageNumber = (pageNumber >= 0? pageNumber: 0);
		}
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
			fromPos = pageNumber * pageSize;
			fromPos = (fromPos < 0? 0: fromPos);
			endPos = ((pageNumber+1)*pageSize>COUNT)? COUNT: (pageNumber+1)*pageSize;
		} else {
			fromPos = (pageTotal - 1) * pageSize;
			fromPos = (fromPos < 0? 0: fromPos);
			endPos = COUNT;
		}
		
		jsonStrTmp.append("{\"total\":" + COUNT);
		jsonStrTmp.append(", \"rows\": ");
		jsonStrTmp.append(mboToJsonString(st, fromPos, endPos, props, condStr));
		jsonStrTmp.append("}");
		st.close();
		MXServer.getMXServer().getDBManager().freeConnection(ck);		
		return jsonStrTmp;
	}
	
%>
<% 
	String[] props = {"assetnum", "productcode", "productorderno", "radarnum", "drawno", "assetname", "fieldtasknum", "description", "status", "custnum", "header", 
						"tasktype", "location", "locationname", "siteid", "createby", "createdate", "displayname", "deptnum", "department"};
	
	//设置回响为json格式数据输出
	response.setContentType("application/json");
	response.setCharacterEncoding("UTF-8");
	response.setHeader("Cache-Control", "no-cache");
	java.io.PrintWriter pw = response.getWriter();
	
	/* 获取前端对分页数据的需求 */
	String pageNumberStr = request.getParameter("pageNumber"); //第几页
	String pageSizeStr = request.getParameter("pageSize");     //每页多少条
	
	StringBuffer condStr = new StringBuffer(); //json 字符串缓存对象
	String[] condProps = {"fieldtasknum", "description", "tasktype", "department", "custname", "locationname", "status"};
	condStr.append(" 1=1 ");
	for(int i = 0, LENGTH = condProps.length; i < LENGTH; i++) {
		String propVal = request.getParameter(condProps[i]);
		if(propVal != null && !"".equals(propVal)) {
			propVal = new String(propVal.getBytes("ISO8859-1"), "UTF-8");
			condStr.append(" and " + condProps[i] + " like '%" + propVal + "%' ");
		}
	}
	
	/* 输出符合条件的结果集 */
	pw.write(mboToJsonString(pageNumberStr, pageSizeStr, condProps, condStr).toString());   
%>
