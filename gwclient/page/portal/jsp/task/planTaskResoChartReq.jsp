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
	StringBuffer mboToJsonString(String fieldtasknum, String siteid) throws MXException, java.rmi.RemoteException, SQLException {
		StringBuffer jsonStrTmp = new StringBuffer(); //json 字符串缓存对象
		
		psdi.security.ConnectionKey ck = MXServer.getMXServer().getDBManager().getSystemConnectionKey();
		Connection connection = MXServer.getMXServer().getDBManager().getConnection(ck);
		Statement st = connection.createStatement();
		
		jsonStrTmp.append("{");
		jsonStrTmp.append("\"spareChart\": " + createSpareChartData(st, fieldtasknum, siteid));
		jsonStrTmp.append(", \"personChart\": " + createPersonChartData(st, fieldtasknum, siteid));
		jsonStrTmp.append(", \"equipChart\": " + createEquipChartData(st, fieldtasknum, siteid));
		jsonStrTmp.append("}");

		st.close();
		MXServer.getMXServer().getDBManager().freeConnection(ck);	
		
		return jsonStrTmp;
	}
		
	/* 查询外场任务所需备件结果集 */
	StringBuffer createSpareChartData(Statement st, String fieldtasknum, String siteid) throws SQLException {
		StringBuffer jsonStrTmp = new StringBuffer(); //json 字符串缓存对象
		jsonStrTmp.append("{");
		
		StringBuffer categories = new StringBuffer();
		StringBuffer datas = new StringBuffer();
		StringBuffer itemnums = new StringBuffer();
		
		categories.append("[");
		datas.append("[");
		itemnums.append("[");
		
		String sql = "select t.itemnum, item.description as name, t.count "
					+ "from "
					+ "( "
						+ "select itemnum, sum(quantity) as count " 
						+ "from FTINVENTORY " 
						+ "where fieldtasknum='" + fieldtasknum + "' and siteid='" + siteid + "' " 
						+ "group by itemnum "
					+ ") t " 
					+ "left join " 
					+ "item " 
					+ "on t.itemnum=item.itemnum "
					+ "order by t.count desc "; 
		
		ResultSet rs = st.executeQuery(sql);
		
		boolean isCommNeed = false;
		while(rs.next()) {
			String itemnum = rs.getString("itemnum");
			String name = rs.getString("name");
			int count = rs.getInt("count");
			
			if(name == null || "".equals(name)) {
				name = itemnum;
			} 
			if(isCommNeed) {
				categories.append(", \"" + name + "\"");
				datas.append(", " + count);
				itemnums.append(", \"" + itemnum + "\"");
			} else {
				categories.append("\"" + name + "\"");
				datas.append(count);
				itemnums.append("\"" + itemnum + "\"");
				isCommNeed = true;
			}
		}
		
		categories.append("]");
		datas.append("]");
		itemnums.append("]");
		
		jsonStrTmp.append("\"categories\": " + categories);
		jsonStrTmp.append(", \"datas\": " + datas);
		jsonStrTmp.append(", \"itemnums\": " + itemnums);
		
		jsonStrTmp.append("}");		
		
		rs.close();
		return jsonStrTmp;
	}
	
	/* 查询外场任务所需人员结果集 */
	StringBuffer createPersonChartData(Statement st, String fieldtasknum, String siteid) throws MXException, java.rmi.RemoteException, SQLException {
		StringBuffer jsonStrTmp = new StringBuffer(); //json 字符串缓存对象
		
		String sql = "SELECT TI.*, CRAFT.DESCRIPTION AS CRAFT_NAME " 
					+ "FROM ( "
						 + "SELECT DISTINCT TISTANEEDS.SITEID,TISTANEEDS.DEPTNUM,TISTANEEDS.CRAFT,TISTANEEDS.PERSONID, DEPT.DESCRIPTION AS DEPT_NAME "
						 + "FROM TISTANEEDS "
						 + "LEFT JOIN DEPT "
						 + "ON   TISTANEEDS.DEPTNUM=DEPT.DEPTNUM AND TISTANEEDS.SITEID=DEPT.SITEID "
						 + "WHERE TISTANEEDS.FIELDTASKNUM='" + fieldtasknum + "' AND TISTANEEDS.SITEID='" + siteid + "' "
					 + ") TI  "
					 + "LEFT JOIN CRAFT "
					 + "ON TI.CRAFT=CRAFT.CRAFT";
		
		ResultSet rs = st.executeQuery(sql);
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		while(rs.next()) {
			String craft_name = rs.getString("craft_name");
			if(map.containsKey(craft_name)) {
				int value = map.get(craft_name) + 1;
				map.put(craft_name, value);
			} else {
				map.put(craft_name, 1);
			}
		}
		
		jsonStrTmp.append("[");
		Object[] keys = map.keySet().toArray();
		boolean isCommNeed = false;
		for(Object key: keys) {
			if(isCommNeed) {
				jsonStrTmp.append(",[\"" + (String)key + "\", " + map.get(key) + "]");
			} else {
				jsonStrTmp.append("[\"" + (String)key + "\", " + map.get(key) + "]");
				isCommNeed = true;
			}
		}
		jsonStrTmp.append("]");
		
		rs.close();	
		
		return jsonStrTmp;
	}
	
	
	/* 查询外场任务所需设备结果集 */
	StringBuffer createEquipChartData(Statement st, String fieldtasknum, String siteid) throws MXException, java.rmi.RemoteException, SQLException {
		StringBuffer jsonStrTmp = new StringBuffer(); //json 字符串缓存对象

		String sql = "SELECT DISTINCT FTDEVICEINFO.DEVICENUM, FTDEVICEINFO.QUANTITY, DEVICEINFO.DESCRIPTION AS EQUIP_NAME "
					 + "FROM FTDEVICEINFO "
					 + "LEFT JOIN DEVICEINFO "
					 + "ON   FTDEVICEINFO.DEVICENUM=DEVICEINFO.DEVICENUM AND FTDEVICEINFO.SITEID=DEVICEINFO.SITEID "
					 + "WHERE FTDEVICEINFO.FIELDTASKNUM='" + fieldtasknum + "' AND FTDEVICEINFO.SITEID='" + siteid + "' ";
		
		ResultSet rs = st.executeQuery(sql);
		HashMap<String, Double> map = new HashMap<String, Double>();
		while(rs.next()) {
			String equip_name = rs.getString("equip_name");
			if(map.containsKey(equip_name)) {
				double value = map.get(equip_name) + rs.getDouble("quantity");
				map.put(equip_name, value);
			} else {
				map.put(equip_name, rs.getDouble("quantity"));
			}
		}
		
		jsonStrTmp.append("[");
		Object[] keys = map.keySet().toArray();
		boolean isCommNeed = false;
		for(Object key: keys) {
			if(isCommNeed) {
				jsonStrTmp.append(",[\"" + (String)key + "\", " + map.get(key) + "]");
			} else {
				jsonStrTmp.append("[\"" + (String)key + "\", " + map.get(key) + "]");
				isCommNeed = true;
			}
		}
		jsonStrTmp.append("]");
		
		rs.close();
		
		return jsonStrTmp;
	}
%>
<%
	//设置回响为json格式数据输出
	response.setContentType("application/json");
	response.setCharacterEncoding("UTF-8");
	response.setHeader("Cache-Control", "no-cache");
	java.io.PrintWriter pw = response.getWriter();

	String fieldtasknum = request.getParameter("fieldtasknum"); //外场任务编号
	String siteid = request.getParameter("siteid"); //siteid
	
	pw.write(mboToJsonString(fieldtasknum, siteid).toString());
%>
