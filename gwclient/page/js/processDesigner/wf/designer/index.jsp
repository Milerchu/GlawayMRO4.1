<%@ include file="../../../../util/controlheader.jsp"%>
<%@page import="psdi.server.MXServer"%>
<%
	String showcanvas = request.getParameter("showcanvas");
	if (showcanvas != null) {
		if (showcanvas.equals("true")) {
			WfCanvas ctrl = (WfCanvas) session.getAttribute("WFCANVAS");
			String baseurl = ctrl.getPage().getClientSession()
					.getMroClientPageMgr().getMroBaseUrl();
			String width = StringUtil.isStrNotEmpty(ctrl
					.getProp("width")) ? "width="
					+ ctrl.getProp("width") : "width=\"300px\"";
			String height = StringUtil.isStrNotEmpty(ctrl
					.getProp("height")) ? "height="
					+ ctrl.getProp("height") : "height=\"300px\"";
			String id = ctrl.getId();
			ClientPage cp = ctrl.getPage();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Process Diagram</title>
		<!-- framework CSS -->
		<link
			href="<%=PAGE_PATH%>/js/processDesigner/themes/default/css/style.css"
			type="text/css" rel="stylesheet" title="blue" />

		<!-- JQuery EasyUi CSS-->
		<link type="text/css"
			href="<%=PAGE_PATH%>/js/processDesigner/js/jquery-easyui/themes/default/easyui.css"
			rel="stylesheet" title="blue">
		<link
			href="<%=PAGE_PATH%>/js/processDesigner/js/jquery-easyui/themes/icon.css"
			type="text/css" rel="stylesheet" />

		<!-- JQuery validate CSS-->
		<link
			href="<%=PAGE_PATH%>/js/processDesigner/js/validate/jquery.validate.extend.css"
			type="text/css" rel="stylesheet" />

		<!-- JQuery AutoComplete -->
		<link rel="stylesheet" type="text/css"
			href="<%=PAGE_PATH%>/js/processDesigner/js/jquery-autocomplete/jquery.autocomplete.css" />
		<!--<link rel="stylesheet" type="text/css" href="<%=PAGE_PATH%>/js/processDesigner/js/jquery-autocomplete/lib/thickbox.css" />-->

		<!-- JQuery-->
		<script src="<%=PAGE_PATH%>/js/processDesigner/js/jquery-1.4.4.min.js"
			type="text/javascript"></script>
		<!--<script src="<%=PAGE_PATH%>/js/processDesigner/js/jquery-1.6.min.js" type="text/javascript"></script>-->

		<!-- JQuery EasyUi JS-->
		<script
			src="<%=PAGE_PATH%>/js/processDesigner/js/jquery-easyui/jquery.easyui.min.js"
			type="text/javascript"></script>
		<!-- JQuery validate JS-->
		<script
			src="<%=PAGE_PATH%>/js/processDesigner/js/validate/jquery.validate.js"
			type="text/javascript"></script>
		<script
			src="<%=PAGE_PATH%>/js/processDesigner/js/validate/jquery.metadata.js"
			type="text/javascript"></script>
		<script
			src="<%=PAGE_PATH%>/js/processDesigner/js/validate/jquery.validate.method.js"
			type="text/javascript"></script>
		<script
			src="<%=PAGE_PATH%>/js/processDesigner/js/validate/jquery.validate.extend.js"
			type="text/javascript"></script>

		<!-- JQuery form Plugin -->
		<script src="<%=PAGE_PATH%>/js/processDesigner/js/jquery.form.js"
			type="text/javascript"></script>

		<!-- JSON JS-->
		<script src="<%=PAGE_PATH%>/js/processDesigner/js/json2.js"
			type="text/javascript"></script>

		<!-- JQuery AutoComplete -->
		<script type='text/javascript'
			src='<%=PAGE_PATH%>/js/processDesigner/js/jquery-autocomplete/lib/jquery.bgiframe.min.js'></script>
		<script type='text/javascript'
			src='<%=PAGE_PATH%>/js/processDesigner/js/jquery-autocomplete/lib/jquery.ajaxQueue.js'></script>
		<!--<script type='text/javascript' src='<%=PAGE_PATH%>/js/processDesigner/js/jquery-autocomplete/lib/thickbox-compressed.js'></script>-->
		<script type='text/javascript'
			src='<%=PAGE_PATH%>/js/processDesigner/js/jquery-autocomplete/jquery.autocomplete.min.js'></script>

		<script type="text/javascript" src="<%=PAGE_PATH%>/js/var_library.js"></script>
		<!-- framework JS -->
		<script src="<%=PAGE_PATH%>/js/processDesigner/js/skin.js"
			type="text/javascript"></script>
		<link
			href="<%=PAGE_PATH%>/js/processDesigner/js/designer/designer.css"
			type="text/css" rel="stylesheet" />
		<!-- common, all times required, imports -->
		<SCRIPT
			src='<%=PAGE_PATH%>/js/processDesigner/js/draw2d/wz_jsgraphics.js'></SCRIPT>
		<SCRIPT src='<%=PAGE_PATH%>/js/processDesigner/js/draw2d/mootools.js'></SCRIPT>
		<SCRIPT src='<%=PAGE_PATH%>/js/processDesigner/js/draw2d/moocanvas.js'></SCRIPT>
		<SCRIPT src='<%=PAGE_PATH%>/js/processDesigner/js/draw2d/draw2d.js'></SCRIPT>


		<!-- example specific imports -->
		<SCRIPT
			src="<%=PAGE_PATH%>/js/processDesigner/js/designer/MyCanvas.js"></SCRIPT>
		<SCRIPT
			src="<%=PAGE_PATH%>/js/processDesigner/js/designer/ResizeImage.js"></SCRIPT>
		<SCRIPT
			src="<%=PAGE_PATH%>/js/processDesigner/js/designer/event/Start.js"></SCRIPT>
		<SCRIPT
			src="<%=PAGE_PATH%>/js/processDesigner/js/designer/event/End.js"></SCRIPT>
		<SCRIPT
			src="<%=PAGE_PATH%>/js/processDesigner/js/designer/connection/MyInputPort.js"></SCRIPT>
		<SCRIPT
			src="<%=PAGE_PATH%>/js/processDesigner/js/designer/connection/MyOutputPort.js"></SCRIPT>
		<SCRIPT
			src="<%=PAGE_PATH%>/js/processDesigner/js/designer/connection/DecoratedConnection.js"></SCRIPT>
		<SCRIPT
			src="<%=PAGE_PATH%>/js/processDesigner/js/designer/task/Task.js"></SCRIPT>
		<SCRIPT
			src="<%=PAGE_PATH%>/js/processDesigner/js/designer/task/UserTask.js"></SCRIPT>
		<SCRIPT
			src="<%=PAGE_PATH%>/js/processDesigner/js/designer/task/ManualTask.js"></SCRIPT>
		<SCRIPT
			src="<%=PAGE_PATH%>/js/processDesigner/js/designer/task/ServiceTask.js"></SCRIPT>
		<SCRIPT
			src="<%=PAGE_PATH%>/js/processDesigner/js/designer/task/ScriptTask.js"></SCRIPT>
		<SCRIPT
			src="<%=PAGE_PATH%>/js/processDesigner/js/designer/task/MailTask.js"></SCRIPT>
		<SCRIPT
			src="<%=PAGE_PATH%>/js/processDesigner/js/designer/task/ReceiveTask.js"></SCRIPT>
		<SCRIPT
			src="<%=PAGE_PATH%>/js/processDesigner/js/designer/task/BusinessRuleTask.js"></SCRIPT>
		<SCRIPT
			src="<%=PAGE_PATH%>/js/processDesigner/js/designer/task/CallActivity.js"></SCRIPT>
		<SCRIPT
			src="<%=PAGE_PATH%>/js/processDesigner/js/designer/gateway/ExclusiveGateway.js"></SCRIPT>
		<SCRIPT
			src="<%=PAGE_PATH%>/js/processDesigner/js/designer/gateway/ParallelGateway.js"></SCRIPT>
		<SCRIPT
			src="<%=PAGE_PATH%>/js/processDesigner/js/designer/designer.js"></SCRIPT>
	</head>
	<script type="text/javascript"><!--

var processDefinitionId="";
var processDefinitionName="";
var processDefinitionVariables="";
var _process_def_provided_listeners="";
var is_open_properties_panel = false;
var task;
var line;
jq(function(){
	try{
		_task_obj = jq('#task');
		_designer = jq('#designer');
		_task_context_menu = jq('#task-context-menu').menu({});
		_designer.layout('collapse','center');
		<%
			if(!ctrl.isDisabled()){
		%>			
		jq('.easyui-linkbutton').draggable({
					proxy:function(source){
						var n = jq('<div class="draggable-model-proxy"></div>');
						n.html(jq(source).html()).appendTo('body');
						return n;
					},
					deltaX:0,
					deltaY:0,
					revert:true,
					cursor:'auto',
					onStartDrag:function(){
						jq(this).draggable('options').cursor='not-allowed';
					},
					onStopDrag:function(){
						jq(this).draggable('options').cursor='auto';
					}	
		});
		jq('#<%=ctrl.getId()%>').droppable({
					accept:'.easyui-linkbutton',
					onDragEnter:function(e,source){
						jq(source).draggable('options').cursor='auto';
						//console.log("--------------onDragEnter:function");
					},
					onDragLeave:function(e,source){
						jq(source).draggable('options').cursor='not-allowed';
						//console.log("--------------onDragLeave:function");
					},
					onDrop:function(e,source){
						//console.log("--------------onDrop:function");
						var wfModel = jq(source).attr('wfModel');
						var shape = jq(source).attr('iconImg');
						if(wfModel){
							var method = null;
							var x=jq(source).draggable('proxy').offset().left;
							var y=jq(source).draggable('proxy').offset().top;
							var xOffset    = workflow.getAbsoluteX();
							var yOffset    = workflow.getAbsoluteY();
							var scrollLeft = workflow.getScrollLeft();
							var scrollTop  = workflow.getScrollTop();
							var xcoordinate = x-xOffset+scrollLeft;
							var ycoordinate = y-yOffset+scrollTop;
							var nodeType = wfModel;
							if(wfModel=="EndNode"){
								method = "addFigure";
								nodeType = "End";
							}else{
								method = "addModel";
							}
							window.parent.parseClick(wfcanvasId,true,'addNode',"{\"method\":\""+method+"\",\"nodetype\":\""+nodeType+"\",\"x\":\""+xcoordinate+"\",\"y\":\""+ycoordinate+"\"}");
						}
					}
				});
	<%
		}
	%>
	}catch(e){
		//alert(e.message);
	};
	jq(window).unload( function () {
		/*if(window.opener!==null){
			if(window.opener._list_grid_obj!==null){
				window.opener._list_grid_obj.datagrid('reload');
			}
		}*/
	} );
});
function addModel(name,x,y,icon){
	icon = "<%=PAGE_PATH%>/js/processDesigner/js/designer/icons/"+icon;
	var model = null;
	if(icon!=null&&icon!=undefined){
		model = eval("new draw2d."+name+"('"+icon+"')");
	}else{
		model = eval("new draw2d."+name+"(openTaskProperties)");
	}
	//console.log("addModel-------------"+icon);
	model.generateId();
	workflow.addModel(model,x,y);
	return model;
}
function addFigure(name,x,y,icon){
	icon = "<%=PAGE_PATH%>/js/processDesigner/js/designer/icons/"+icon;
	var fObj = null;
	if(icon!=null&&icon!=undefined){
		fObj = eval("new draw2d."+name+"('"+icon+"')");
	}
	//console.log("addFigure-------------"+icon);
	workflow.addFigure(fObj,x,y);
	return fObj;
}
function openTaskProperties(t){}
function openProcessProperties(id){}
function openFlowProperties(l){}
function deleteModel(id){
	var task = workflow.getFigure(id);
	workflow.removeFigure(task);
}
function redo(){
	workflow.getCommandStack().redo();
}
function undo(){
	workflow.getCommandStack().undo();
}
function saveProcessDef(){
	var xml = workflow.toXML();
	jq.ajax({
		url:"${ctx}/wf/procdef/procdef!saveProcessDescriptor.action",
		type: 'POST',
		data:{
			processDescriptor:xml,
			processName:workflow.process.name,
			processVariables:workflow.process.getVariablesJSONObject()
		},
		dataType:'json',
		error:function(){
			return "";
		},
		success:function(data){
			if(data.result){
				jq.messager.alert('Info','Save Successfully!','info');
			}else{
				jq.messager.alert('Error',data.message,'error');
			}
		}	
	}); 
	
}
//
--></script>
	<body id="designer" class="easyui-layout">
		<div id="wftoolbar" region="west" split="true" iconCls="palette-icon"
			title="Palette" style="width: 150px;">
			<a href="##" class="easyui-linkbutton" plain="true"
				iconCls="start-event-icon"><%=ctrl.getNodeLabel("WFSTART")%></a>
			<br>
			<a href="##" class="easyui-linkbutton" plain="true"
				iconCls="end-event-icon" wfModel="EndNode"><%=ctrl.getNodeLabel("WFSTOP")%></a>
			<br>
			<a href="##" class="easyui-linkbutton" plain="true"
				iconCls="user-task-icon" wfModel="UserTask"><%=ctrl.getNodeLabel("WFTASK")%></a>
			<br>
			<a href="##" class="easyui-linkbutton" plain="true"
				iconCls="manual-task-icon" wfModel="ManualTask"><%=ctrl.getNodeLabel("WFINTERACTION")%></a>
			<br>
			<a href="##" class="easyui-linkbutton" plain="true"
				iconCls="subprocess-icon"><%=ctrl.getNodeLabel("WFSUBPROCESS")%></a>
			<br>
			<a href="##" class="easyui-linkbutton" plain="true"
				iconCls="callactivity-icon" wfModel="CallActivity"><%=ctrl.getNodeLabel("WFINPUT")%></a>
			<br>
			<a href="##" class="easyui-linkbutton" plain="true"
				iconCls="parallel-gateway-icon" wfModel="ParallelGateway"
				iconImg="<%=PAGE_PATH%>/js/processDesigner/js/designer/icons/type.gateway.parallel.png"><%=ctrl.getNodeLabel("WFCONDITION")%></a>
			<br>
			<a href="##" class="easyui-linkbutton" plain="true"
				iconCls="exclusive-gateway-icon" wfModel="ExclusiveGateway"
				iconImg="<%=PAGE_PATH%>/js/processDesigner/js/designer/icons/type.gateway.exclusive.png"><%=ctrl.getNodeLabel("WFWAIT")%></a>
			<br>
		</div>
		<div id="process-panel" region="center" split="true"
			iconCls="process-icon" title="Process">
			<div id="designer-area" title="Diagram"
				style="POSITION: absolute; width: 100%; height: 97%; padding: 0; border: none; overflow: auto;">
				<div id="<%=ctrl.getId()%>"
					style="POSITION: absolute; WIDTH: 2000px; HEIGHT: 1000px"
					readonly="<%=ctrl.isDisabled()%>"></div>
			</div>
			<script type="text/javascript">
					<!--
					var workflow;
					function openProcessDef(){
						jq.ajax({
							url:"<%=PAGE_PATH%>/js/processDesigner/wf/procdef/procdef!getProcessDefXML.action?procdefId="+processDefinitionId,
							type: 'POST',
							/*
							data:{
										moduleId:"${moduleId}",
										_request_json_fields:json4params
								},
							*/
							dataType:'xml',
							error:function(){
								$.messager.alert("<s:text name='label.common.error'></s:text>","System Error","error");
								return "";
							},
							success:parseProcessDescriptor	
						}); 
					}
				
					function createCanvas(disabled){
						try{
							//initCanvas();
							workflow  = new draw2d.MyCanvas("<%=ctrl.getId()%>");
							workflow.scrollArea=document.getElementById("designer-area");
							var id = "process"+Sequence.create();
							workflow.process.category='demo_wf_process_def';
							workflow.process.id=id;
							workflow.process.name=id;
							<%if(ctrl.isDisabled()){%>
							workflow.setDisabled();
							<%}%>
							<%
								//if(!ctrl.isNewCanvas()){
									List<Map<String,String>> nl = ctrl.getNodeList();
									if(nl!=null){
										for(Map<String,String> node : nl){
											String nodeType = node.get("NODETYPE");
											String mboType = node.get("MBOTYPE");
											String nodeIcon = node.get("ICON");
											String nodeTitle = node.get("TITLE");
											String nodeDesc = node.get("DESCRIPTION");
											String nodeId = node.get("NODEID");
											String nodeUid = node.get("WFNODEID");
											String dialogid = node.get("DIALOGID");
											int X = Integer.parseInt(node.get("X"));
											int Y = Integer.parseInt(node.get("Y"));
							%>
											var startObj<%=nodeId%> = new draw2d.<%=nodeType%>("<%=PAGE_PATH%>/js/processDesigner/js/designer/icons/<%=nodeIcon%>");
											startObj<%=nodeId%>.setId('<%=nodeType + nodeId%>');
											startObj<%=nodeId%>.setPropDailogId('<%=dialogid%>');
											startObj<%=nodeId%>.setNodeId('<%=nodeUid%>');
							<%
											if(nodeType.equals("Start") || nodeType.equals("End")){
							%>
											workflow.addFigure(startObj<%=nodeId%>,<%=X%>,<%=Y%>);
							<%
											}else{
												if(nodeType.equals("UserTask") || nodeType.equals("CallActivity") || nodeType.equals("ManualTask")){
							%>
											startObj<%=nodeId%>.setContent('<%=nodeTitle%>');
							<%
												}
							%>
											workflow.addModel(startObj<%=nodeId%>,<%=X%>,<%=Y%>);
							<%
											}
										}
										List<Map<String,String>> al = ctrl.getActionList();
										if(al!=null){
											for(Map<String,String> action : al){
												String snodeid = action.get("SNODEID");
												String tnodeid = action.get("TNODEID");
												String label = action.get("LABEL");
												String sport = action.get("SPORT");
												String tport = action.get("TPORT");
												String dialogid = action.get("DIALOGID");
												String wfactionid = action.get("WFACTIONID");
												String nodeUid = action.get("WFNODEID");
												String ispositive = action.get("ISPOSITIVE");
												String linecount = action.get("LINECOUNT");
							%>
											//console.log('<%=snodeid%>=<%=sport%>,<%=tport%>');
											var sourcenode_<%=snodeid%> = workflow.getFigure('<%=snodeid%>');
											var targetnode_<%=snodeid%> = workflow.getFigure('<%=tnodeid%>');
											var sports_<%=snodeid%> = sourcenode_<%=snodeid%>.getPorts();
								  			var tports_<%=snodeid%> = targetnode_<%=snodeid%>.getPorts();
								  			var startPort_<%=snodeid%> = sports_<%=snodeid%>.get(<%=sport%>);
								  			var targetPort_<%=snodeid%> = tports_<%=snodeid%>.get(<%=tport%>);
								  			if(startPort_<%=snodeid%> != null && targetPort_<%=snodeid%>!=null){
												sourcenode_<%=snodeid%>.lineCount = sourcenode_<%=snodeid%>.lineCount + 1;
												var cmd_<%=snodeid%>=new draw2d.CommandConnect(workflow,startPort_<%=snodeid%>,targetPort_<%=snodeid%>);
												var connection_<%=snodeid%> = new draw2d.DecoratedConnection(<%=ispositive%>);
												connection_<%=snodeid%>.setActionId('<%=nodeUid + "_" + wfactionid%>');
												connection_<%=snodeid%>.setPropDailogId('<%=dialogid%>');
												connection_<%=snodeid%>.setInitFlag(true);
												//connection_<%=snodeid%>.setLineCount(<%=linecount%>);
												//connection_<%=snodeid%>.setLabel('<%=label%>');
												cmd_<%=snodeid%>.setConnection(connection_<%=snodeid%>);
												workflow.getCommandStack().execute(cmd_<%=snodeid%>);
											}
							<%
											}
										}
									}
								//}
							%>
						}catch(e){
							alert(e.message);
						}
					}
					//-->
				</script>
		</div>
		<!-- task context menu -->
		<div id="task-context-menu" class="easyui-menu" style="width: 120px;">
			<div id="properties-task-context-menu" iconCls="properties-icon">
				Properties
			</div>
			<div id="delete-task-context-menu" iconCls="icon-remove">
				Delete
			</div>
		</div>
		<!-- form configuration window -->
		<div id="form-win" title="Form Configuration"
			style="width: 750px; height: 500px;">
		</div>
		<!-- listener configuration window -->
		<div id="listener-win" title="Listener Configuration"
			style="width: 750px; height: 500px;">
		</div>
		<!-- candidate configuration window -->
		<div id="task-candidate-win" title=""
			style="width: 750px; height: 500px;">
		</div>
	</body>
</html>
<script type="text/javascript">
	createCanvas(false);
	jq("#wftoolbar").attr("title",COMM_CONSTANT.WFCANVAS.PNAME);
	jq("#process-panel").attr("title",COMM_CONSTANT.WFCANVAS.NAME);
</script>
<%
	}
	}
%>