/* 
 * 初始化主导航
 * */
function initAppNav(options) {
	if($.browser.msie && $.browser.version <= 6) {
		initIE6Layout();
	}
	
	NAV_MENU_CACHE = NavMenuJsonCache([]);
	ctrlMgrSys.put("NAV_BAR", NAV_MENU_CACHE);
	
	NAV_APP = $('#' + options.appNavId).accordion({
		id: options.appNavId,
		defaultAccordId: options.currNavId
	});
	ctrlMgrSys.put(options.appNavId, NAV_APP);
	
	NAV_MENU_MGR = NavLinkMenu();
	ctrlMgrSys.put("NAV_MENU_MGR", NAV_MENU_MGR);
	isNavMenuLocked = false; //主导航已可用
}

/* ie环境，window.resize后，布局脚本定位延迟调用方法 */
var initIE6LayoutTimeouter = 0;
function initIE6LayoutDelay() {
	if(!$.browser.msie || $.browser.version > 6) {
		return;
	}
	
	if(initIE6LayoutTimeouter) {
		clearTimeout(initIE6LayoutTimeouter);			
	}
	initIE6LayoutTimeouter = setTimeout(initIE6Layout, 10);
}

/* ie环境布局脚本定位  */
function initIE6Layout() {
	if(!$.browser.msie || $.browser.version > 6) {
		return;
	}
	//alert($("#page_header").height());
	//alert($("#page_container").height());
	$("#page_container").height($(window).height() - $("#page_header").height());
	//alert($("#page_container").height());
	var navWidth = $("#page_nav").width();
	if(undef(navWidth)) {
		navWidth = 0;
	}
	$("#page_center").width($(window).width() - navWidth);
	//alert(navWidth + "  " + $("#page_center").width());
	
	var operHeight = $("#page_oper").height();
	if(undef(operHeight)) {
		operHeight = 0;
	}
	$("#page_center_center").height($("#page_center").height() - operHeight);
	//alert(operHeight + " " + $("#page_center_center").height());
	
	$("#page_nav_container").height($("#page_nav").height() - $("#page_nav_header").height());
}

/* 触发西区展开和关闭 */
function toggleWest() {
	var contObj = $("#page_container");
	if(contObj.hasClass("page_nav_collapse")) {
		contObj.removeClass("page_nav_collapse");
	} else {
		contObj.addClass("page_nav_collapse");
	}
}


/* 字符串解析：特殊字符，单双引号 */
function html2Str(str) {
	if(str == null || str == "" || str == "null" || str == "NULL") {
		return ""; //此返回值用于处理表格中显示可能包含html标签的内容(其他类似于)
	}
	str = str + "";
	return str.replace(/[&"'\<\>]/g, function (c) {
		switch (c) {
			case "&":
				return "&amp;";
			case "'":
				return "&#39;";
			case '"':
				return "&quot;";
			case "<":
				return "&lt;";
			case ">":
				return "&gt;";
		}
	});
}
	
/* 校验参数是否为undefied、null、空字符串 */
function undef(p) {
    var bUndef = false;
    switch(typeof(p)) {
        case "undefined" :
        	bUndef = true;
    	    break;
        case "null" :
	        bUndef = true;
        	break;
        case "object" :
        	if(p == null)
        		bUndef = true;
       		break;
        case "number" :
	        if(null == p)
	        	bUndef = true;
    	    break;
        case "string" :
        	if("" == p)
        		bUndef = true;
	        else if("null" == p)
	        	bUndef = true;
	        else if("undefined" == p)
	        	bUndef = true;
	        break;
    }
    return bUndef;
}

/**
 * 判断参数是否为undefined还是null
 * @return true为undefined或null
 */
function undefStrictly(p) {
	var bUndef = false;
    switch(typeof(p)) {
        case "undefined" :
        	bUndef = true;
    	    break;
        case "null" :
	        bUndef = true;
        	break;
    }
    return bUndef;
}

/**
 * 读取html字符串中指定标签的内容<tag>...</tag>
 * @param htmlStr - html字符串
 * @param tagName - 标签名称
 * @return
 */
function getTagInnerHtmlStr(htmlStr, tagName) {
	if(undef(htmlStr) || undef(tagName)) {
		return "";
	}
	var start = htmlStr.indexOf("<" + tagName + ">");
	if(start < 0) {
		return "";
	}
	var end = htmlStr.indexOf("</" + tagName + ">");
	if(end < 0 || start >= end) {
		return "";
	}
	return htmlStr.substring(start + tagName.length + 2, end);//substring(x,y)截取从x起（包括x），到y前（不包括y）的字符串
}

/**
 * 鼠标移动至DOM时的样式变换
 * 服务于所有控件，且样式必须是hover
 * @param dom - dom对象
 * @return
 */
function hoverDom(dom, domType) {
	if(domType == "TABLE_TR") {
		if($(dom).hasClass("trselect") || $(dom).hasClass("trseldelline") || $(dom).hasClass("trhovdelline")) {
			return;
		}
		if($(dom).hasClass("delline")) {
			$(dom).addClass("trhovdelline").removeClass("delline");
			return;
		}
	}
	$(dom).addClass("hover");
}
/**
 * 鼠标移出表格DOM时的样式变换
 * 服务于所有控件，且样式必须是hover
 * @param dom - dom对象
 * @return
 */
function unhoverDom(dom, domType) {
	if(domType == "TABLE_TR") {
		if($(dom).hasClass("trhovdelline")) {
			$(dom).removeClass("trhovdelline").addClass("delline");
			return;
		}
	}
	$(dom).removeClass("hover");
}

function callCtrlMthd(key, fun, args, defVal) {
	ctrlMgrSys.callMethod(key, fun, args, defVal);
}

/**
 * ztree config
 */
var setting = {
	data: {
		simpleData: {
			enable: true,
           	idKey: "id", 
           	pIdKey: "pId", 
           	rootPid: "" 
		},
		keep:{
			parent:true
		}
	},
	edit:{
		enable:false,
		showRenameBtn:false,
		showRemoveBtn:false,
		drag:{
			isMove:false,
			prev:false,
			inner:false,
			next:false
		}
	},
	callback:{
		beforeClick: clickNode, 
        beforeExpand: expandNode, 
        beforeCollapse: collapseNode,
        onRightClick: rightClickNode,
        beforeDrop:beforeDrop
	},
	view: {
		showIcon: showIconForTree,
		fontCss: showFontCss
	}
};
var editsetting = {};

function clickNode(treeId, treeNode, clickFlag) { 
	//点击的时候记住节点的数据库id
   sessionStorage.setItem("treeNode.id",treeNode.id);
   parseClick(treeNode.treenodeid, true, 'clickNode');
   return false; 
} 

function expandNode(treeId, treeNode) { 
   //alert("treeNode.treenodeid=" + treeNode.treenodeid); 
   sessionStorage.setItem("treeNode.id",treeNode.id);
   parseClick(treeNode.treenodeid, true, 'expandNode');
   return false; 
} 

function collapseNode(treeId, treeNode) { 
   sessionStorage.setItem("treeNode.id",treeNode.id);
   parseClick(treeNode.treenodeid, true, 'collapseNode');
   return true; 
} 

function beforeDrop(treeId, dropNodes, targetNode, moveType) {
	if(!targetNode){
  		return false; 
	}
	parseClick(dropNodes[0].treenodeid, true, 'moveNode', dropNodes[0].id + "_&" + targetNode.id + "_&" + moveType);
  	return false; 
}

function showIconForTree(treeId, treeNode) {
    var showicon = true;
	if(treeNode.showIcon=="false"){
		showicon = false;
	}
	return showicon;
}

function showFontCss(treeId, treeNode) {
    var fontcss = {};
	if(treeNode.style!=""){
		fontcss = $.parseJSON(treeNode.style);
	}
	return fontcss;
}

function rightClickNode(event,treeId, treeNode) {

	var x = event.clientX+document.body.scrollLeft;
	var y = event.clientY+document.body.scrollTop;
	if(!undef(treeNode)){
		var treeattr = {"treeId":treeId,"tId":treeNode.tId,"X":x,"Y":y,"treeNodeId":treeNode.treenodeid};
	    if(treeNode && treeNode.noR=="false"){
			if(treeNode.MENUAJAX){
				if(treeNode.MENUAJAX=="true"){
					sendEvent("rightClickNode", "X:"+x+",Y:"+y, treeattr.treeNodeId, "", "", "");
				}else{
					openTreeRightMenu(treeattr);
				}
			}else{
				openTreeRightMenu(treeattr);
			}
	    }
	}
}

function openTreeRightMenu(treeattr){
	$("#"+treeattr.tId).rightmenu({
		menu:$("#"+treeattr.treeId+"_menu"),
			X:treeattr.X,
			Y:treeattr.Y
	});
}


function uploadfile(domId) {
	$("#" + domId + "_upfileform").attr("action");
		try {
			if (!undef(changedCtrlId)) {
				sendSyncEvent("", "", "", changedCtrlId, changedCtrlVal, "");
				clearChangedCtrlVal(); // 在控件变更内容被受理之后，要及时清除变更缓存
			}
			var pro = null;
			$("#" + domId + "_upfileform").submit();
			pro = setInterval(function getFileUploadProcess(){
				$.ajax({
					type:"GET",
					url:BASE_PATH+"fps",
					dataType:"text",
					cache:false,
					success:function(data){
						//console.log("data:"+data);
						if(data=='uploadfileSuccess'){
							//设置500毫秒延迟
							setTimeout(function(){
								pageWaitMarks.removeMark();
							},500);
							clearInterval(pro);
						}
						else{
						   // console.log("data22:"+data);
						   /* setTimeout(function(){
								pageWaitMarks.removeMark();
							},500);
							clearInterval(pro);*/
						}
					}
				});
			},200);
			pageWaitMarks.addMark();
		} catch (e) {
		}
}


/**
 * 展示等待圈
 */
function showWaite()
{
	pageWaitMarks.addMark();
}

/**
 * 去掉悬浮层
 */
function reMoveWaite()
{
	pageWaitMarks.removeMark();
}

function uploadreportfile(domId) {//上传报表文件
	$("#" + domId + "_upfileform").attr("action");
		try {
			if (!undef(changedCtrlId)) {
				sendSyncEvent("", "", "", changedCtrlId, changedCtrlVal, "");
				clearChangedCtrlVal(); // 在控件变更内容被受理之后，要及时清除变更缓存
			}
			var pro = null;
			$("#" + domId + "_upfileform").submit();
			pro = setInterval(function getFileUploadProcess(){
				$.ajax({
					type:"GET",
					url:BASE_PATH+"fps",
					dataType:"text",
					cache:false,
					success:function(data){
						//console.log("data:"+data);
						if(data=='uploadfileSuccess'){
							//设置500毫秒延迟
							setTimeout(function(){
								pageWaitMarks.removeMark();
							},500);
							clearInterval(pro);
						}
						else{
						    //console.log("bbbbbdata22:"+data);
						    setTimeout(function(){
								pageWaitMarks.removeMark();
							},1500);
							clearInterval(pro);
						}
					}
				});
			},200);
			pageWaitMarks.addMark();
		} catch (e) {
		}
}





/**
 * 阻止点击等事件冒泡（blur、focus等事件不会冒泡）
 * 目前表格中的单元点击事件基于此功能
 * @param e - 事件对象
 * @return void
 */
function stopBubble(e) {
	var e = e ? e : window.event;
	if(window.event) { //IE
		e.cancelBubble = true;

	} else { //FF
		e.stopPropagation();
	}
}
	
/**
 * 判断对象是否为非空数组
 * @param arr - 数组对象
 * @return boolean
 */
function isArrayNotEmpty(arr) {
	if(arr && arr.length > 0) {
		return true;
	}
	return false;
}

/**
 * 在新窗口内打开链接（from hyperlink.jsp）
 * @param url - 链接地址
 * @return void
 */
function openURL(url,windowId,options) {
	var wid = "newWindow";
	var ops = "left=100,top=100,height=500,width=800,location=yes,status=yes,menubar=yes,scrollbars=yes,resizable=yes";
	while(url.search("#") >- 1) {
		url = url.replace("#", "%23");
	}
	stopFocus = true;
	if(windowId){
		wid = windowId;
	}
	if(options){
		ops = options;
	}
	window.open(url,wid,ops)
	//setTimeout(window.open(url,wid,ops), 3000);
}

/* 判断当前浏览器是否为老版本（<=6）的IE */
function isMsie6() {
	if ($.browser.msie) {
		return parseInt($.browser.version, 0) <= 6;
	}
	return false;
} 

/* 判断当前浏览器是否为IE7 */
function isMsie7() {
	if ($.browser.msie) {
		return parseInt($.browser.version, 0) == 7;
	}
	return false;
}

/**
 * 返回应用程序后，重新展开dialog
 * @param key dialog控件标识
 * @param propJson dialog控件属性
 * */
function reopenDialog(key, propJson) {
	var content = $("#" + key).html();
	content = content.replace(/mroscript/ig, "script"); //更换为script标签
	ctrlMgrSys.reopenDialog(key, propJson, content);
}

/**
 * 获取字段提示内容
 * @param attrObject 表名
 * @param attr 字段
 * @param attrName 字段名称
 * @param attrDesp 字段描述
 * */
function getFieldTipCont(options) {
	var attrObject = (options.attrObject == null? "": options.attrObject);
	var attr = (options.attr == null? "": options.attr.toUpperCase());
	var attrName = (options.attrName == null? "": options.attrName);
	var attrDesp = html2Str(options.attrDesp);
	var attrType = (options.attrType == null? "": options.attrType);
	var attrLength = options.attrLength;
	var attrScale = options.attrScale;
	
	var cont = "<table class=\"fieldtip\" cellspacing=\"0\" cellpadding=\"0\">";
	cont = cont + "<tr><td class=\"tdr tdnowrap\">表名：</td><td class=\"tdl tdnowrap\">" + attrObject + "</td>";
	cont = cont + "<td width=\"70px\" class=\"tdr tdnowrap\">属性：</td><td class=\"tdl\"><div style=\"width:80px;\" class=\"tdwrap\">" + attr + "</div></td></tr>";
	
	cont = cont + "<tr><td class=\"tdr tdnowrap\">名称：</td><td class=\"tdl tdnowrap\">" + attrName + "</td>";
	cont = cont + "<td width=\"70px\" class=\"tdr tdnowrap\">类型：</td><td class=\"tdl tdnowrap\">" + attrType + "</td></tr>";
	
	cont = cont + "<tr><td class=\"tdr tdnowrap\">长度：</td><td class=\"tdl tdnowrap\">" + attrLength + "</td>";
	cont = cont + "<td width=\"70px\" class=\"tdr tdnowrap\">小数位数：</td><td class=\"tdl tdnowrap\">" + attrScale + "</td></tr>";
	
	cont = cont + "<tr><td colspan=\"4\"><hr /></td></tr>";
	cont = cont + "<tr><td class=\"tdr tdnowrap\">描述：</td><td colspan=\"3\" class=\"tdl tdwrap\"><div style=\"width:260px;\" class=\"tdwrap\">" + attrDesp + "</div></td></tr>";
	cont = cont + "</table>";
	return cont;
}

/**
 * 将json格式字符串转换为json
 * @param jsonStr json格式字符串
 * */
function str2Json(jsonStr) {
	return jQuery.parseJSON(jsonStr);
}

/**
 * 应用程序设计器中新增控件的方法
 * @param ctrlType 新增的控件类型
 * @param ctrlObj 需要放置新增控件的目标控件
 * @param newCtrlHtml 新控件的html
 * */
function appendNewCtrl(ctrlType, ctrlObj, newCtrlHtml) {
	if(ctrlObj.attr("etype") == "section") {
		if(ctrlType == "sectionrow"){
			newCtrlHtml = "<table style=\"width:100%;\" cellspacing=\"0\" cellpadding=\"0\">" + newCtrlHtml;
			newCtrlHtml = newCtrlHtml + "</table>";
		}
		ctrlObj.find("td.sectioncontent:first").append(newCtrlHtml);
	} else {
		ctrlObj.append(newCtrlHtml);
	}
}

/**
 * 重置延迟重聚焦dom标识
 * @param domId 新的待重聚焦的dom标识
 */
function setDelayRefocusId(domId) {
	delayRefocusId = domId;
}

/**
 * 后台交互事件结束后，需自适应设置对话框大小
 * 目前仅针对高度自适应
 */
function resizeTopDialog() {
	var dialogObj = ctrlMgrSys.getTopDialog();
	//无对话框，或对话框已被设置为固定宽度，或者对话框中无空间动态变化的控件，则无需自适应调整大小
	if(dialogObj == null || (dialogObj.isWidthFixed && dialogObj.isHeightFixed) 
		|| !dialogObj.dynContent) {
		return;
	}
	
	if(!dialogObj.isHeightFixed) {
		dialogObj.resizeHeight();
		dialogObj.resizeHeight();
	}
}

/**
 * 初始创建对话框后，延迟重置对话框的位置
 * 目前由于窗口可能会自动调整高度，导致其位置不能自动居中
 */
function reposTopDialog() {
	var dialogObj = ctrlMgrSys.getTopDialog();
	if(dialogObj != null) {
		dialogObj.reposition();
	}
}
