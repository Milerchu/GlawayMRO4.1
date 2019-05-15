<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>		
<%!
	String CURR_TASK_STATUS = "'进行中', '已下发'"; //当前任务的状态范围（'关闭', '草稿', '待审批'）
	String TASK_ACCOMP_STATUS = "'关闭', '完成'"; 
	String RUN_LEDGER_STATUS = "select value from SYNONYMDOMAIN where domainid='LOCASSETSTATUS' and maxvalue='OPERATING'";
	String UNRUN_SPARE_STATUS = "'陈旧的'";
%>