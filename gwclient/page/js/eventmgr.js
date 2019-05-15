var changedCtrlId = ""; //记录最新被更新值的控件的标识，此属性需及时处理，及时清空
var changedCtrlVal = "";//记录最新被更新值的控件的内容，此属性需及时处理，及时清空

var isLocking = false; //交互锁

var delayRefocusId = ""; //需延迟聚焦的控件标识（通常是控件内部的每个dom需聚焦）

/**
 * body点击事件
 * 此事件由底层内容点击后上传至此
 * 目前主要处理值变更
 */
function bodyClick() {
	if(isLocking) {
		return; //当前已有事件传递提交，无需处理
	}
	if(!undef(changedCtrlId)) {
		parseClick();
		clearChangedCtrlVal(); //在控件变更内容被受理之后，要及时清除变更缓存
	}
}

/**
 * 解析控件点击事件
 * @param ctrlId - 发生点击事件的控件id
 * @param isEffective - 事件控件在初步受理点击后，判断点击是否有效（如导航按钮是否重复选择）
 */
function parseClick(ctrlId, isEffective, eventType, value, uid) {
	if(eventType != "filtertable") { //表格过滤后不需要聚焦
		setDelayRefocusId(ctrlId); //当前操作/聚焦的控件标识
	} else {
		setDelayRefocusId("");
	}
	if(undef(eventType)) {
		eventType = "";
	}
	if(isEffective) { //控件点击事件需受理
		if(undef(changedCtrlId)) {
			//有变更事件需受理
			sendEvent(eventType, value, ctrlId, "", "", "", uid);
		} else {
			sendEvent(eventType, value, ctrlId, "", changedCtrlId, changedCtrlVal, uid);
			clearChangedCtrlVal(); //在控件变更内容被受理之后，要及时清除变更缓存
		}
		return;
	}
	
	if(undef(changedCtrlId)) {
		//有变更事件需受理
		//sendEvent("", "", "", "", "", "");
	} else {
		sendEvent("", value, "", "", changedCtrlId, changedCtrlVal, uid);
		clearChangedCtrlVal(); //在控件变更内容被受理之后，要及时清除变更缓存
	}
}

/**
 * 事件发送中心
 * @param eventType - 事件类型
 * @param value - 事件值
 * @param targetId - 发起事件的控件标识
 * @return
 */
function sendEvent(eventType, value, targetId, async, chgCtrlId, chgCtrlVal, uid) {
	var reqData = {};
	if(!undef(eventType)) {
		reqData.event = eventType;
	}	
	reqData.targetid = targetId;
	reqData.value = value;
	if(!undef(changedCtrlId)) {
		reqData.changedctrlid = chgCtrlId;
		reqData.changedctrlval = undefStrictly(chgCtrlVal)? "": chgCtrlVal;
	}
	if(!undef(uid)) {
		reqData.uid = uid;
	}
	reqData = getReqData(reqData);
	if(undef(async)) {
		async = true;
		reqData.isHXR = true;//标记是否是异步请求
	}
	var returnStatus = true;
	if(!isLocking) {
		isLocking = true;
		pageWaitMarks.addMark();
		resetTimeOut();
		$.ajax({ 
			async: async,
			type: "POST",
			//url: REQ_ACTION, 
			url: REQ_ACTION+"?loginstamp="+LOGINSTAMP,
			cache: false,
			dataType: "text",
			data: reqData, 
			error: function() {
				alert("error");
			},
			success: function(dataResp){    
				
				//alert(getTagInnerHtmlStr(dataResp, "ctrloper"));
				if(getTagInnerHtmlStr(dataResp, "ctrloper")!="changeapp")
				{
					pageWaitMarks.removeMark(); //先关闭旋转等待
				}
				//$("#DIRECT_JS_CONTAINER").html(dataResp);	
				try {
		    		readResponse(dataResp);
				} catch(e) {
				}
				
			},
			complete: function(xhRequest, textStatus) {
			    isLocking = false;
			    //  alert("xhRequest  : "+xhRequest.responseText);
			    if(getTagInnerHtmlStr(xhRequest.responseText, "ctrloper")!="changeapp")
				{
					pageWaitMarks.removeMark(); //先关闭旋转等待
				}
			    // pageWaitMarks.removeMark();
			    delayRefocus(); //重新聚焦输入框
			}
		});
	}
	if(!async) { //同步获取信息时，部分控件需要此返回值
		return returnStatus;
	}
}

/**
 * 同步事件发送中心
 * @param eventType - 事件类型
 * @param value - 事件值
 * @param targetId - 发起事件的控件标识
 * @return 为返回值，直接获取进行处理即可
 */
function sendSyncEvent(eventType, value, targetId, chgCtrlId, chgCtrlVal, uid) {
	setDelayRefocusId(targetId); //当前操作/聚焦的控件标识
	var reqData = {};
	var returnval = {};
	if(!undef(eventType)) {
		reqData.event = eventType;
	}	
	reqData.targetid = targetId;
	reqData.value = value;
	if(!undef(changedCtrlId)) {
		reqData.changedctrlid = chgCtrlId;
		reqData.changedctrlval = undefStrictly(chgCtrlVal)? "": chgCtrlVal;
	}
	if(!undef(uid)) {
		reqData.uid = uid;
	}
	reqData = getReqData(reqData);
	async = false;
	reqData.isHXR = true;//标记是否是异步请求【同步请求做异步处理】
	var returnStatus = true;
	if(!isLocking) {
		isLocking = true;
		pageWaitMarks.addMark();
		$.ajax({ 
			async: async,
			type: "POST",
			//url: REQ_ACTION, 
			url: REQ_ACTION+"?loginstamp="+LOGINSTAMP, 
			cache: false,
			dataType: "text",
			data: reqData, 
			error: function() {
				alert("error");
			},
			success: function(dataResp){    		
		    	pageWaitMarks.removeMark(); //先关闭旋转等待
		    	returnval = dataResp;
			},
			complete: function(xhRequest, textStatus) {
			    isLocking = false;
			    pageWaitMarks.removeMark();
			    delayRefocus();
			}
		});
	}
	if(!async) { //同步获取信息时，部分控件需要此返回值
		return returnval;
	}
}

/**
 * 读取并解析事件回响内容
 * @param dataResp - 事件回响内容
 *					<ctrlelem>
 *						 <ctrlid>GWC10</ctrlid>
 *						 <ctrloper>changeStyle</ctrloper>
 *						 <ctrldata><tabid>GWC26</tabid><tabcontent>...</tabcontent></ctrldata>
 *					 </ctrlelem>
 * 					<ctrlelem>...</ctrlelem>
 * @return
 */
function readResponse(dataResp) {
	var start = dataResp.indexOf("<ctrlelem>");
	var end = dataResp.indexOf("</ctrlelem>");
	if(start >= 0 && end >= 0 && start < end) {
		while(start >= 0 && end >= 0 && start < end) {
			//substring(x,y)截取从x起（包括x），到y前（不包括y）的字符串
			//ctrlElemStr: <ctrlid>GWC10</ctrlid><ctrloper>changestyle</ctrloper><ctrldata><tabid>GWC26</tabid><tabcontent>...</tabcontent></ctrldata>
			var ctrlElemStr = dataResp.substring(start + 10, end);
			var ctrlId = getTagInnerHtmlStr(ctrlElemStr, "ctrlid");
			var ctrlOper = getTagInnerHtmlStr(ctrlElemStr, "ctrloper");
			ctrlMgrSys.update(ctrlId, ctrlOper, getTagInnerHtmlStr(ctrlElemStr, "ctrldata"));
			
			dataResp = dataResp.substring(end + 11);
			start = dataResp.indexOf("<ctrlelem>");
			end = dataResp.indexOf("</ctrlelem>");
		}
	} else {
		$("#DIRECT_JS_CONTAINER").html(dataResp);	
	}
   	resizeTopDialog(); //重置顶层对话框大小
   	
   	//列表行编辑时，使tabletext的样式不会覆盖列表编辑的报错提示样式 modify by ygao 2019年1月23日17:26:22
   	var inputErrBorder = "border: 1px solid red";
   	var inputErrTag = "1px solid red";
   	$(".txbxinputtd").each(function(i,val){
   		if($(val).hasClass("inputerror")){
   			$(this).find("input").css({
   				"border":inputErrTag
   			});
   		}else{
   			//TODO 由于元素中存在其他的style样式，所以直接置空会影响到元素其他的样式，所有只需要修改变红的boder信息就好。
   			var styleTemp = $(val).find("input").attr("style");
   			if(styleTemp != undefined && styleTemp != "undefined" && styleTemp != null && styleTemp.indexOf(inputErrBorder) > -1){
   				//正常表格需去除掉之前判断必填项的红色边界
   				styleTemp = styleTemp.replace(inputErrBorder,"");
   				$(val).find("input").attr({
   	   				"style":styleTemp	
   	   			});
   			}
   		}
   	});
   	
   	return true;
}

/**
 * 消息处理
 * @param msg - 消息对象数据
 * 			  - 构成：
 *					msg {
 *						type:     类型confirm, errorinfo, warninginfo, errormark, msginfo
 *						info:     消息
 *						targetId: 响应对象（for confirm, errormark）
 *					}
 * @return 
 */
function msgCenter(msg) {

}

/**
 * 向请求内容中追加参数
 */
function getReqData(reqData) {
    //reqData.uisessionid = PAGE_SESSIONID;
    return reqData;
}

/**
 * 在控件变更内容被受理之后，要及时清除变更缓存
 */
function clearChangedCtrlVal() {
	changedCtrlId = ""; //记录最新被更新值的控件的标识，此属性需及时处理，及时清空
	changedCtrlVal = "";//记录最新被更新值的控件的内容，此属性需及时处理，及时清空
}

/**
 * 带值控件（如输入框）在值发生变化时，需调用此方法，以注册变更的值
 * 事件受理后，此变更记录会被清除
 * @param ctrlId - 变更控件标识
 * @param val - 变更控件的新值
 */
function setChangedCtrlVal(ctrlId, val) {
	changedCtrlId = ctrlId;
	changedCtrlVal = val;
}
/* 获取被变更控件的标识 */
function getChangedCtrlId() {
	return changedCtrlId;
}
/* 获取被变更控件的值 */
function getChangedCtrlVal() {
	return changedCtrlVal;
}
/*重置超时时间*/
function resetTimeOut(){
	timeoutDialog = null;
	openTimeoutDialog = true;
	timeoutCountdown = WARNINGTIMEOUT;
	MROCLIENT_TIMECOUNTER = 0;
}

/* 
 * 延迟重新聚焦输入框，处理当前聚焦的输入框被解除只读后无法输入问题。窗口弹出后，取消此功能
 * */
function delayRefocus() {
	setTimeout(refocus, 200);
}

/* 
 * 重新聚焦输入框 
 * 1. 后台请求处理后必须调用此方法以重新聚焦输入框
 * 2. 部分浏览器（如IE）中，如聚焦发生在可读写性控制前，则后续控制将无效
 * 3. 目前出现此需求的场景为：输入框前端变值后，直接聚焦另一输入框
 * */
function refocus() {
	if(!undef(delayRefocusId)) {
		var ctrlObj = ctrlMgrSys.get(delayRefocusId);
		if(!undef(ctrlObj)) {
			if(!undef(ctrlObj.getType) && ctrlObj.getType() == "textbox") {
				var domObj = document.getElementById(ctrlObj.getFocusDomId());
				if(!undef(domObj)) {
					document.body.focus();
					domObj.focus();
				}
			}
		}
	}
	setDelayRefocusId(""); //当前操作/聚焦的控件标识
}
