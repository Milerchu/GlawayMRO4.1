<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"
	import="com.glaway.mro.page.*,com.glaway.mro.page.control.*,psdi.mbo.*,psdi.util.*,com.glaway.mro.system.session.*,com.glaway.mro.system.bean.*,java.util.*,java.io.*,com.glaway.mro.system.utils.*"
	errorPage=""%>
<%@ page import="java.text.NumberFormat"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String PAGE_PATH = basePath + "page";
	String IMAGE_PATH = new StringBuilder().append(basePath).append("webclient/skins/tivoli09/images/").toString();
	String MRO_IMG_PTH = new StringBuilder().append(PAGE_PATH).append("/skin/blue00/image/").toString();
%>
