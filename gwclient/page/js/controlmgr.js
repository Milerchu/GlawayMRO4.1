/*
 * 页面控件管理对象
 * 页面jquery控制管理中心
 * 重要参数：
 * 对象方法：
 *		
 **/ 
function ControlMgr() {
	var ctrls = new Array(); //控件id与控件jquery对象的存储数组。采用数组方式，为了更好的处理dialog及其内容控件
	var domMgr = DomMgr(); //dom元素的直接控制，服务于无控件对象的操作调用
	var dialogList = new Array();//对话框表示key缓存队列
	var dialogCount = 0; //当前页面内的对话框数量
	var resizes = new Array(); //当布局大小变化时，内部需更新空间的控件的标识队列（目前表格的minwidth功能基于此实现）
	
	/* 内部控件对象 */
	function InnerCtrl(key, ctrl) {
		this.key = key;
		this.ctrl = ctrl;
	}
	
	/* 添加控件id与控件jquery对象映射 */
	function put(key, ctrl) {
		ctrls.push(new InnerCtrl(key, ctrl));
	}
	
	/* 将原控件映射关系替换为新控件 */
	function replace(key, ctrl) {
		for(var i = 0, LENGTH = ctrls.length; i < LENGTH; i++) {
			if(ctrls[i].key == key) {
				ctrls[i].ctrl = ctrl;
				return;
			}
		}
	}
	
	/* 
	 * 移除控件id与控件jquery对象映射
	 * 1. 移除屏幕尺寸调整队列中的元素
	 * @param key 待移除的控件标识
	 * */
	function remove(key) {
		removeFromResizes(key);
		for(var i = 0; i < ctrls.length; i++) {
			if(ctrls[i].key == key) {
				ctrls.splice(i, 1);
			}
		}
	}
	
	/* 
	 * 移除屏幕尺寸调整队列中的元素
	 * @param key 待移除的控件标识
	 * */
	function removeFromResizes(key) {
		for(var i = 0; i < resizes.length; i++) {
			if(resizes[i].key == key) {
				resizes.splice(i, 1);
			}
		}
	}
	
	/* 
	 * 移除多个控件id与控件jquery对象映射 
	 * @param fromIndex 起始删除位置
	 * @param howMany 删除的数量
	 * */
	function removeCtrls(fromIndex, howMany) {
		for(var i = fromIndex, SIZE = (fromIndex + howMany); i < SIZE; i++) {
			removeFromResizes(ctrls[i].key);
		}
		ctrls.splice(fromIndex, howMany);
	}
	
	/* 获取控件id与控件jquery对象映射 */
	function get(key) {
		for(var i = 0, LENGTH = ctrls.length; i < LENGTH; i++) {
			if(ctrls[i].key == key) {
				return ctrls[i].ctrl;
			}
		}
		return null;
	}
	
	/* 更新控件数据 */
	function ctrlLoadData(key, data) {
		var ctrl = get(key);
		if(!undef(ctrl)) {
			ctrl.loadData(data); //loadData方法为所有控件通用方法
		}
	}
	
	/* 更新控件样式 */
	function ctrlChangeStyle(key, data) {
		var ctrl = get(key);
		if(!undef(ctrl)) {
			ctrl.changeStyle(data); //changeStyle方法为所有控件通用方法
		}
	}
	
	/* 控件相关操作入口 */
	function update(key, oper, data) {
		if(!undef(dialogMgr[oper])) { //dialog控件相关受理
			dialogMgr[oper](key, data);
			return;
		}
		
		var ctrl = get(key);
		if(!undef(ctrl)) { //缓存中存在控件对象
			if(!undef(ctrl[oper])) {
				ctrl[oper](data); //loadData or changeStyle or anything
			} 
		} else { //针对dom的直接控制
			if(!undef(domMgr[oper])) {
				domMgr[oper](key, data);
			}
		}
	}
	
	/* 
	 * 调用指定控件方法
	 * 推荐在控件事件提交前采用此种方式调用控件内部的检验 
	 * @param key 被调用控件标识
	 * @param fun 被调用控件内部方法名称
	 * @param args 被调用控件fun方法所需的参数数组
	 * @param defVal 此次调用的缺省返回值
	 * */
	function callMethod(key, fun, args, defVal) {
		var ctrl = get(key);
		if(!undef(ctrl)) {
			return ctrl[fun].apply(ctrl, args);
		}
		return defVal;
	}
	
	/* 
	 * 获得对话框数目
	 **/
	function getDialogCount() {
		return dialogCount;
	}
	
	/* 
	 * 设置对话框数目
	 **/
	function setDialogCount(dcount) {
		dialogCount = dcount;
	}
	
	/* 获取当前顶层对话框控件 */
	function getTopDialog() {
		if(dialogCount <= 0) {
			return null;
		}
		for(var i = dialogList.length; i >= 0; i--) {
			if(!undef(dialogList[i])) {
				return get(dialogList[i]);
			}
		}
		return null;
	}
	
	
	//对话框管理器
	var dialogMgr = {
		/*
		 * 加载dialog
		 * @param key dialog控件标识
		 * @param content 对话框内容和按钮<dialogcontent>对话框内容部分</dialogcontent><dialogbuttons>对话框按钮</dialogbuttons>
		 * */
		putDialog: function(key, content) {
			setDelayRefocusId(""); //当前操作/聚焦的控件标识
			var dialog = null;
			var title = getTagInnerHtmlStr(content, "title");
			var targetid = getTagInnerHtmlStr(content, "targetid");
			var width = getTagInnerHtmlStr(content, "width")!=""?getTagInnerHtmlStr(content, "width"):null;
			var height = getTagInnerHtmlStr(content, "height")!=""?getTagInnerHtmlStr(content, "height"):null;
			var cancelfn = getTagInnerHtmlStr(content, "cancelfn");
			var dialogname = getTagInnerHtmlStr(content, "dialogname");
			var opacity = 0;
			var dialogcontent = getTagInnerHtmlStr(content, "dialogcontent");
			var isDesignerModel = getTagInnerHtmlStr(content, "designermodel");
			isDesignerModel = parseInt(isDesignerModel + "", 10) == 1;
			var dynContent = false;
			if(getTagInnerHtmlStr(content, "dyncontent")=="true") {
				dynContent = true;
			}
			var lock = true;
			if(getTagInnerHtmlStr(content, "lock")=="false") {
				lock = false;
			}
			var jsonobj = {};
			if(!isDesignerModel) {
				var jsonstr = getTagInnerHtmlStr(content, "dialogbuttons");
				jsonobj = $.parseJSON(jsonstr);
			}
			var showcancelbutton = false;
			$.each(jsonobj,function(idx,item){
				item.callback = eval(item.callback);
			});
			if(dialogCount==0){
				opacity = 0.3;
			}
			var dialogProp = {
				opacity: opacity,
				cancel: new function(){
					function run(){
						if(!undef(cancelfn) && cancelfn != "undefined"){
							parseClick(targetid, true, cancelfn);
						}else{
							parseClick(targetid, true, 'dialogcancel');
						}
						return false;
					}
					return {run:run};
				},
				showcancelbutton: showcancelbutton,
				lock: lock,
				dialogname: dialogname,
				padding: ""
			};
			if(!isDesignerModel) {
				dialogProp.button = jsonobj;
			}
			dialog = $.dialog(dialogProp);
			dialog.hide();
			dialogCount = dialogCount + 1;
			put(key, dialog);
			dialog.content(dialogcontent);
			dialog.show();
			dialogList.push(key);
			if(title!=""){
				dialog.title(title);
			}
			if(!undef(width) && !undef(height)) {
				dialog.isWidthFixed = true;
				dialog.isHeightFixed = true;
				dialog.reviewSize(parseInt(width, 10), parseInt(height, 10)); 
				reposTopDialog();
				return;
			}
			dialog.dynContent = dynContent;
			if(!dialog.dynContent) {
				dialog.reviewSize(parseInt(width, 10), parseInt(height, 10)); 
				reposTopDialog();
				return;
			}
			if(undef(width)) {
				width = 500;
			} else {
				width = parseInt(width, 10);
			}
			dialog.isWidthFixed = true;
			dialog.xmlWidth = width;
			
			if(!undef(height)) {
				height = parseInt(height, 10);
				dialog.isHeightFixed = true;
				dialog.xmlHeight = height;
			}
			dialog.reviewSize(width, height);
			reposTopDialog();
			
			/*if(dynContent) {
				if(!undef(width) && undef(height)) {
					height = parseInt(width, 10) * 0.60;
					dialog.reviewSize(parseInt(width, 10), parseInt(height, 10)); 
					
				} else if(undef(width) && !undef(height)) {
					width = parseInt(height, 10) * 1.5;
					dialog.reviewSize(parseInt(width, 10), parseInt(height, 10)); 
					
				} else {
					dialog.reviewSize(600, 450);
				}
			} else {
				if(!undef(width) && undef(height)) {
					dialog.reviewSize(parseInt(width, 10), height); 
					
				} else if(undef(width) && !undef(height)) {
					dialog.reviewSize(width, parseInt(height, 10)); 
					
				} else {
					dialog.reviewSize(500);
				}
			}*/
			/*
			if(!undef(width) || !undef(height)) {
				var setHeight = parseInt(height, 10);
				var setWidth = parseInt(width, 10);
				var docheight = document.documentElement.clientHeight-200; //浏览器？
				var $thisObj = $(".aui_main");
				var dcheight = $thisObj.height();
				var dcwidth = $thisObj.width();
				var dctop = $thisObj.position().top;
				var h = dcheight+dctop;
				if(h>=docheight){
					setHeight = docheight;
				}else{
					setHeight = h;
				}
				dialog.reviewSize(setWidth, setHeight);
			} else {
				dialog.reviewSize(500);
			}*/
		},
		/*
		 * 关闭dialog
		 * @param key dialog控件标识
		 * */
		closeDialog: function(key) {
			var ctrl = get(key);
			if(!undef(ctrl)) {
				ctrl.close();
				this.removeDialog(key);
			}
		},
		
		/*
		 * 移除dialog
		 * @param key dialog控件标识
		 * */
		removeDialog: function(key) {
			var dialogIndex = -1;
			var SIZE = ctrls.length;
			for(var i = 0; i < SIZE; i++) {
				if(ctrls[i].key == key) {
					dialogIndex = i;
					break;
				}
			}
			if(dialogIndex >= 0) {
				var isDeleted = false;
				for(var i = dialogIndex + 1; i < SIZE; i++) {
					if(!undef(ctrls[i].ctrl["isdialog"])) {
						removeCtrls(dialogIndex, i - dialogIndex);
						isDeleted = true;
						break;
					}
				}
				if(!isDeleted) {
					removeCtrls(dialogIndex, SIZE - dialogIndex);
				}
			}
			
			for(var i = 0; i < dialogList.length; i++) {
				if(dialogList[i] == key) {
					dialogList.splice(i, 1);
				}
			}
			dialogCount = dialogCount - 1;
		},
		/*
		 * 返回应用程序后，重新展开dialog
		 * @param key dialog控件标识
		 * @param propJson dialog控件属性
		 * @param content dialog控件内容
		 * */
		reopenDialog: function(key, propJson, content) {
			var dialog = null;
			var targetid = propJson.targetid;
			var width = propJson.width!=""? propJson.width: null;//var width = propJson.width!=""? propJson.width: 'auto';
			var height = propJson.height!=""? propJson.height: null;//var height = propJson.height!=""? propJson.height: 'auto';
			var cancelfn = propJson.cancelfn;
			var dialogname = propJson.dialogname;
			var opacity = 0;
			var showcancelbutton = false;
			$.each(propJson.btns, function(idx, item){
				item.callback = eval(item.callback);
			});
			if(dialogCount==0){
				opacity = 0.3;
			}
			dialog = $.dialog({
				title: (propJson.title? propJson.title: ""),
				opacity: opacity,
				cancel: new function(){
					function run(){
						if(!undef(cancelfn) && cancelfn != "undefined"){
							parseClick(targetid, true, cancelfn);
						}else{
							parseClick(targetid, true, 'dialogcancel');
						}
						return false;
					}
					return {run:run};
				},
				showcancelbutton: showcancelbutton,
				button: propJson.btns,
				lock: true,
				dialogname: dialogname,
				padding: ""
			});
			dialogCount = dialogCount + 1;
			put(key, dialog);
			dialog.content(content);
			dialogList.push(key);
			//获得内容（dialog.DOM.content[0]）的真实宽度和高度
//			if(navigator.userAgent.indexOf("MSIE")>0){
//				dialog.reviewSize(dialog.DOM.content[0].scrollWidth+6,dialog.DOM.wrap[0].offsetHeight+9);
//			}else{
//				dialog.reviewSize(dialog.DOM.content[0].scrollWidth+6,dialog.DOM.content[0].scrollHeight);
//			}
			dialog.reviewSize(dialog.DOM.content[0].scrollWidth+6,null);
			dialog.reposition();
		}
	}
	
	/* 添加受分区空间变化，自身显示空间待更新的控件标识 */
	function addResize(key) {
		resizes.push(key);
	}
	/* 更新受分区影响的控件 */
	function resize() {
		for(var i = 0; i < resizes.length; i++) {
			var ctrl = get(resizes[i]);
			if(!undef(ctrl) && !undef(ctrl.resize)) { //必须是resize方法
				ctrl.resize();
			}
		}
	}
	
	/**
	 * 返回应用程序后，重新展开dialog
	 * @param key dialog控件标识
	 * @param propJson dialog控件属性
	 * @param content dialog控件内容
	 * */
	function reopenDialog(key, propJson, content) {
		dialogMgr.reopenDialog(key, propJson, content);
	}
	
	return {
		put: put,  //添加控件id与控件jquery对象映射
		replace: replace, //将原控件映射关系替换为新控件
		remove: remove, //移除控件id与控件jquery对象映射
		get: get, //获取控件id与控件jquery对象映射
		ctrlLoadData: ctrlLoadData,  //更新控件数据
		ctrlChangeStyle: ctrlChangeStyle,  //更新控件样式
		update: update,   //控件相关操作入口
		callMethod: callMethod,   //调用控件方法
		getDialogCount: getDialogCount,
		getTopDialog: getTopDialog,  //获取顶层对话框
		addResize: addResize,   //添加空间待刷控件标识
		resize: resize, //更新受分区影响的控件
		ctrls: ctrls,
		reopenDialog: reopenDialog //返回应用程序后，重新展开dialog
	}
}

/*
 * 页面DOM管理对象
 * 此对象用于私人定制针对页面dom直接控制的控制方法
 * 重要参数：
 * 对象方法：
 *		
 **/ 
function DomMgr() {
	var uploadDialog = null;
	/*
	 * <img />重新指定图片文件
	 * @domId "GWC110"
	 * @newImage "aa.gif"
	 **/ 
	function changeImage(domId, newImage) {
		$("#" + domId).attr("src", MRO_IMG_PTH + "event/" + newImage);
	}
	function runScriptDirectly(domId, srciptStr) {
		eval(srciptStr);
	}
	function showMsgBox(domId, msg) {
		var dialog = null;
		var dialogCount = ctrlMgrSys.getDialogCount();
		var opacity = 0;
		var targetid = getTagInnerHtmlStr(msg, "targetid");
		if(dialogCount==0){
			opacity = 0.3;
		}
		dialog = $.dialog({
			icon: "warning",
			lock: true,
			okVal: "确定",
			ok: new function(){
				function run(){
					parseClick(targetid,true,"msgDialogOk");
					return true;
				}
				return {run:run};
			},
			content: getTagInnerHtmlStr(msg, "msg"),
			opacity: opacity,
			title: getTagInnerHtmlStr(msg, "title"),
			showcancelbutton: true
		});
	}
	function showErrorMsgBox(domId, msg) {
		//弹出错误信息之前先取消等待样式
		pageWaitMarks.removeMark();
		var dialog = null;
		var dialogCount = ctrlMgrSys.getDialogCount();
		var opacity = 0;
		if(dialogCount==0){
			opacity = 0.3;
		}
		dialog = $.dialog({
			icon: "warning",
			lock: true,
			okVal: "确定",
			ok: new function(){
				function run(){
					return true;
				}
				return {run:run};
			},
			content: msg,
			opacity: opacity,
			title: "警告",
			focus: true,
			showcancelbutton: true
		});
		dialog.position("50%", "50%");
	}
	
	/** 
	 * @description 在应用顶部悬浮显示后台提示信息 
	 * @param key 控件标识，此处为空
	 * @param msg 消息信息
	 */
	function showOperInfo(key, msg) {
		var $oper = $("#operinfo");
		$oper.html(msg);
		var winWidth = $(window).width();
		var operWidth = $oper.width();
		$oper.animate({top: 10, left: (winWidth/2 - operWidth/2)}); //每次在显示前需定位，避免anmiate出现滑动现象
		$oper.animate({opacity:'show'}, "slow", "linear");
		setTimeout(hideOperInfo, 4000);
	}
	/** 
	 * @description 显示警告信息 
	 * @param key 控件标识，此处为空
	 * @param msg 消息信息
	 */
	function showWarnings(key, msg) {
		//弹出错误信息之前先取消等待样式
		pageWaitMarks.removeMark();
		var dialog = null;
		var dialogCount = ctrlMgrSys.getDialogCount();
		var targetid = getTagInnerHtmlStr(msg, "targetid");
		var msgstr = getTagInnerHtmlStr(msg, "msg");
		var opacity = 0;
		if(dialogCount==0){
			opacity = 0.3;
		}
		dialog = $.dialog({
			title: "提示",
			opacity: opacity,
			lock: true,
			okVal: "确定",
			ok: new function(){
				function run(){
					parseClick(targetid,true,"msgDialogOk");
					return true;
				}
				return {run:run};
			},
			showcancelbutton: true,
			width: 500,
			padding: ""
		});
		//dialog.position("50%", "50%");
		dialog.content(msg);
		dialog.reposition();
	}
	/** 
	 * @description 显示输入框错误信息 
	 * @param key 控件标识，此处为空
	 * @param msg 消息信息
	 */
	function showFieldError(key, msg) {
		var dialog = null;
		var dialogCount = ctrlMgrSys.getDialogCount();
		var opacity = 0;
		if(dialogCount==0){
			opacity = 0.3;
		}
		dialog = $.dialog({
			lock: true,
			okVal: "确定",
			ok: new function(){
				function run(){
					return true;
				}
				return {run:run};
			},
			content: "",
			opacity: opacity,
			title: "错误",
			focus: true, 
			showcancelbutton: true,
			padding: ""
		});
		dialog.content(msg);
		dialog.reviewSize(500, 300);//设置内容显示高度和宽度
		dialog.position("50%", "50%");
	}
	/* 隐藏操作提示信息框 */
	function hideOperInfo() {
		$("#operinfo").animate({opacity:'hide'}, "slow"); //使用动画隐藏
	}
	function getTimeoutDialogContent(countdown){
		return "距系统超时还剩<span style=\"text-align:center;display:inline-block;width:20px\">"+countdown+"</span>秒";
	}
	function showTimeoutDialog(domId, msg) {
		var dialog = null;
		var dialogCount = ctrlMgrSys.getDialogCount();
		var opacity = 0;
		if(dialogCount==0){
			opacity = 0.3;
		}
		dialog = $.dialog({
			icon: "warning",
			lock: true,
			width: 200,
			okVal: "确定",
			ok: new function(){
				function run(){
					resetTimeOut();
					parseClick("",true,"");
					return true;
				}
				return {run:run};
			},
			opacity: opacity,
			title: "警告",
			showcancelbutton: true
		});
		openTimeoutDialog = false;
		return dialog;
	}
	/*切换应用程序*/
	function changeapp(domId, msg){
		var baseurl = getTagInnerHtmlStr(msg, "baseurl");
		var targetid = getTagInnerHtmlStr(msg, "targetid");
		var loginstamp = getTagInnerHtmlStr(msg, "loginstamp");
		window.location.href=baseurl+"?targetid="+targetid+"&loginstamp="+loginstamp;
	}
	function gotoapp(domId, linkContent) {
		openrecord(domId, linkContent);
	}
	/* 首页结果集跳转应用程序使用*/
	function openrecord(domId, linkContent) {
		var baseurl = getTagInnerHtmlStr(linkContent, "baseurl");
		var targetid = getTagInnerHtmlStr(linkContent, "targetid");
		var event = getTagInnerHtmlStr(linkContent, "event");
		var value = getTagInnerHtmlStr(linkContent, "value");
		var uid = getTagInnerHtmlStr(linkContent, "uid");
		var loginstamp = getTagInnerHtmlStr(linkContent, "loginstamp");
		window.location.href = baseurl + "?targetid=" + targetid + "&event=" + event + "&uid=" + uid + "&value=" + value+"&loginstamp="+loginstamp;
	}
	/* 首页图表跳转应用程序使用*/
	function clickchart(domId, linkContent) {
		var baseurl = getTagInnerHtmlStr(linkContent, "baseurl");
		var targetid = getTagInnerHtmlStr(linkContent, "targetid");
		var event = getTagInnerHtmlStr(linkContent, "event");
		var value = getTagInnerHtmlStr(linkContent, "value");
		var uid = getTagInnerHtmlStr(linkContent, "uid");
		var loginstamp = getTagInnerHtmlStr(linkContent, "loginstamp");
		window.location.href = baseurl + "?targetid=" + targetid + "&event=" + event + "&uid=" + uid + "&value=" + encodeURI(encodeURI(value))+"&loginstamp="+loginstamp;
	}
	
	function clickkpi(domId, linkContent) {
		var baseurl = getTagInnerHtmlStr(linkContent, "baseurl");
		var targetid = getTagInnerHtmlStr(linkContent, "targetid");
		var event = getTagInnerHtmlStr(linkContent, "event");
		var value = getTagInnerHtmlStr(linkContent, "value");
		var uid = getTagInnerHtmlStr(linkContent, "uid");
		var loginstamp = getTagInnerHtmlStr(linkContent, "loginstamp");
		window.location.href = baseurl + "?targetid=" + targetid + "&event=" + event + "&uid=" + uid + "&value=" + encodeURIComponent(value)+"&loginstamp="+loginstamp;
	}
	/* 跳转到首页*/
	function startcntr(domId, linkContent) {
		var baseurl = getTagInnerHtmlStr(linkContent, "baseurl");
		var targetid = getTagInnerHtmlStr(linkContent, "targetid");
		var event = getTagInnerHtmlStr(linkContent, "event");
		var loginstamp = getTagInnerHtmlStr(linkContent, "loginstamp");
		window.location.href = baseurl + "?targetid=" + targetid + "&event=" + event+"&loginstamp="+loginstamp;
	}
	function rtnapp(domId, linkContent) {
		openrecord(domId, linkContent);
	}
	function resetTimeOut(){
		timeoutDialog = null;
		openTimeoutDialog = true;
		timeoutCountdown = WARNINGTIMEOUT;
		MROCLIENT_TIMECOUNTER = 0;
	}
	function showYesNoDialog(domId, msg){
		var dialog = null;
		var dialogCount = ctrlMgrSys.getDialogCount();
		var targetid = getTagInnerHtmlStr(msg, "targetid");
		var content = getTagInnerHtmlStr(msg, "content");
		var yesfn = getTagInnerHtmlStr(msg, "yesfn");
		var nofn = getTagInnerHtmlStr(msg, "nofn");
		var opacity = 0;
		if(dialogCount==0){
			opacity = 0.3;
		}
		dialog = $.dialog({
			icon:'warning',
			lock:true,
			content:content,
			opacity:0.3,
			/*okVal:'是',
			cancelVal:'否',
			ok:function(){
				parseClick(targetid,true,yesfn);
				return true;
			},
			cancel:function(){
				parseClick(targetid,true,nofn);
				return true;
			},*/
			style:'confirm',
			showcancelbutton: true
		});
		var jsonobj = [{name:"是",focus:true,callback:new function(){
			function run(){
				parseClick(targetid,true,yesfn);
				return true;
			}
			return {run:run};
		}},{name:"否",focus:true,callback:new function(){
			function run(){
				parseClick(targetid,true,nofn);
				return true;
			}
			return {run:run};
		}}];
		dialog.button(jsonobj);
	}
	function signOut(domId, msg){
		eval(msg);
	}
	function clearUF(domId, msg){
		
	}
	/*刷新当前页*/
	function reloadPage(domId, msg){
		window.location.reload();
	}
	function licenseError(){
		alert("License权限不足,操作已被禁止！");
	}
	function reloadwfcanvas(domId, msg){
		var src = $("#wfcanvasiframe").attr("src");
		src = src.substr(0,src.indexOf("time="));
		var time = getTagInnerHtmlStr(msg, "time");
		var showcanvas = getTagInnerHtmlStr(msg, "showcanvas");
		//console.log(src + "time=" + time);
		$("#wfcanvasiframe").attr("src",src + "time=" + time+"&showcanvas="+showcanvas);
	}
	function addWfNode(domId, msg){
		var iframe = window.frames['wfcanvasiframe'];
		var method = getTagInnerHtmlStr(msg, "method");
		var nodetype = getTagInnerHtmlStr(msg, "nodetype");
		var nodeicon = getTagInnerHtmlStr(msg, "nodeicon");
		var nodeid = getTagInnerHtmlStr(msg, "nodeid");
		var wfnodeid = getTagInnerHtmlStr(msg, "wfnodeid");
		var dialogid = getTagInnerHtmlStr(msg, "dialogid");
		var x = getTagInnerHtmlStr(msg, "x");
		var y = getTagInnerHtmlStr(msg, "y");
		var nodeobj = eval("iframe."+method+"('"+nodetype+"',"+x+","+y+",'"+nodeicon+"')");
		nodeobj.setId(nodetype+nodeid);
		nodeobj.setPropDailogId(dialogid);
		nodeobj.setNodeId(wfnodeid);
		//console.log(method+"---"+nodetype);
	}
	function reloadNode(domId, msg){
		var iframe = window.frames['wfcanvasiframe'];
		var nodetype = getTagInnerHtmlStr(msg, "nodetype");
		var dialogid = getTagInnerHtmlStr(msg, "dialogid");
		var nodeid = getTagInnerHtmlStr(msg, "nodeid");
		var title = getTagInnerHtmlStr(msg, "title");
		var nodeobj = iframe.workflow.getFigure(nodetype+nodeid);
		if(nodetype!="ParallelGateway" && nodetype!="ExclusiveGateway" && nodetype!="End"){
			nodeobj.setContent(title);
		}
		nodeobj.setPropDailogId(dialogid);
		//console.log(nodetype+nodeid);
	}
	function setWfActionId(domId, msg){
		var iframe = window.frames['wfcanvasiframe'];
		var lineid = getTagInnerHtmlStr(msg, "lineid");
		var wfactionid = getTagInnerHtmlStr(msg, "wfactionid");
		var ispositive = getTagInnerHtmlStr(msg, "ispositive");
		var nodeobj = iframe.workflow.getLine(lineid);
		nodeobj.setActionId(wfactionid);
		var black = new iframe.draw2d.Color(0,0,0);
		var red = new iframe.draw2d.Color(255,0,0);
		if(ispositive=="true"){
			nodeobj.getTargetDecorator().setBackgroundColor(black);
			nodeobj.getTargetDecorator().setColor(black);
			nodeobj.setColor(black);
		}else{
			nodeobj.getTargetDecorator().setBackgroundColor(red);
			nodeobj.getTargetDecorator().setColor(red);
			nodeobj.setColor(red);
		}
		nodeobj.setPropDailogId("wfactionproperties");
		//console.log(nodeobj.getId());
	}
	
	function linkUrl(key, url) {
		if(!key) {
			key = "";
		}
		//window.location.href = url;
		//var newWindow = window.open(url, '', "toolbar=yes,location=yes,directories=yes,status=yes,menubar=yes,scrollbars=yes,resizable=yes");
		var newWindow = window.open(url, key, "left=0,top=0,height=" + $(window).height() + ",width=" + $(window).width() + ",toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes");
		//var newWindow = window.open(url, '', "left=0,top=0,height=" + $(window).height() + ",width=" + $(window).width() + ",location=yes,status=yes,menubar=yes,scrollbars=yes,resizable=yes");
		if(newWindow) {
			newWindow.location.href=url;
			newWindow.focus();
		}
	}
	
	function submitForm(key, url) {
		$("#mroform").attr("action", url);
		document.getElementById("mroform").submit();
		return;
	}
	
	function linkReport11(key, url) {
		var times = 0;
		var height = $(window).height();
		var width = $(window).width();
		//var newWindow = window.open(BASE_PATH + "report", '', "left=0,top=0,height=" + $(window).height() + ",width=" + $(window).width() + ",toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes");
		
		
		//var newWindow = window.open(BASE_PATH + "report", '', "left=0,top=0,height="+height+",width="+width+",toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes");
		var newWindow = window.open(url, key, "left=0,top=0,height="+height+",width="+width+",toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes");
        
	
		if(newWindow) {
			//newWindow.location.href=BASE_PATH + "report";
			//newWindow.location.href=url;
			setTimeout(linkReportTimeout, 50);
		}	
		
		function linkReportTimeout() {
			if(newWindow) {
				if(!newWindow.document.getElementsByTagName("div")) {
					times = times + 1;
					if(times <= 3) {
						setTimeout(linkReportTimeout, 50);
						return;
					}
				} 
				newWindow.document.getElementsByTagName("body")[0].innerHTML = "";
				//$(newWindow).height(height);
				//$(newWindow).width(width);
				newWindow.location.href=url;
				newWindow.focus();
			}
		}
	}
	//reportform ：    presentation中的form的id
	function linkReport(key, url) {
		var value = url.split(",");
		/*alert(value.length);*/
		var form =document.getElementById("reportform");
		/*var input = document.createElement("input");
		input.type="hidden";
		input.name=value[0];
		input.id=value[0];
		input.value=value[1];
		form.appendChild(input);
		
		var input2 = document.createElement("input");
		input2.type="hidden";
		input2.name="username";
		input2.id="username";
		input2.value=value[2];
		form.appendChild(input2);*/
		var input = document.getElementById("rptParm");
		input.name=value[0];
		input.value=value[1];
		var input = document.getElementById("userName");
		input.value=value[2];
		var input = document.getElementById("idColName");
		input.value=value[3];
		form.submit();
	}
	
	function showOrHideCtrl(domId, msg){
		var id = getTagInnerHtmlStr(msg, "id");
		var type = getTagInnerHtmlStr(msg, "type");
		if(type=="1"){
			$('#'+id).show();
		}else if(type=="0"){
			$('#'+id).hide();
		}
	}
	
	function submiteReport(domId, msg){
		var printjob = getTagInnerHtmlStr(msg, "printjob");
		var reporttype = getTagInnerHtmlStr(msg, "reporttype");
		var reportform = getTagInnerHtmlStr(msg, "reportform");
		var urltoopen = getTagInnerHtmlStr(msg, "urltoopen");
		var targetvalue = getTagInnerHtmlStr(msg, "targetvalue");
		//console.log("------printjob------"+printjob);
		//console.log("------targetvalue------"+targetvalue);
		//console.log("------urltoopen------"+urltoopen);
		//console.log("------msg------"+msg);
		if (printjob=="true"){
			printW = window.open("urltoopen","",'toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no,left=450,top=350,width=30,height=10');
 			printW.resizeTo(300,175);  		
 			printW.focus();
		}else{
			var newWin = window.open("rptvw",targetvalue);		
			newWin.document.write("<html><body>"+reportform+"</body></html>");
			var reportForm = newWin.document.forms[(newWin.document.forms.length - 1)];
			reportForm.submit();
		}
	}
	
	/**
	 * 在新窗口打开url
	 * @param url - 链接地址
	 * @return void
	 */
	function openUrl(domId, msg){
		var url = getTagInnerHtmlStr(msg, "url");
		openURL(url);
	}
	
	/**
	 * 使用a标签链接下载没有弹出页面，但在大量数据下载时，页面没有反馈。
	 * @param {} domId
	 * @param {} msg 后端返回的消息，包含url
	 */
	function download(domId, msg) {
		// debugger;
		var first = true;
		pageWaitMarks.addMark();
		var pro = setInterval(function getFiledownloadProcess() {
			$.ajax({
				type : "GET",
				url : BASE_PATH + "fdps",
				dataType : "text",
				cache : false,
				success : function(data) {
					if (data == 'DownLoadSuccess') {
						// 设置500毫秒延迟
						setTimeout(function() {
							pageWaitMarks.removeMark();
						}, 500);
						clearInterval(pro);
					} 
				}
			});
		}, 200);

		var url = getTagInnerHtmlStr(msg, "url");

		while (url.search("#") > -1) {
			url = url.replace("#", "%23");
		}

		var download_a = document.createElement("a");
		download_a.setAttribute("href", url);
		document.body.appendChild(download_a);

		if (download_a.click) {
			download_a.click();
		} else {
			openUrl(domId, msg);
		}
		document.body.removeChild(download_a);
	}
	
	return {
		changeImage: changeImage,
		runScriptDirectly: runScriptDirectly,
		showMsgBox: showMsgBox,
		showErrorMsgBox: showErrorMsgBox,
		showOperInfo: showOperInfo,
		showWarnings: showWarnings,
		showFieldError: showFieldError,
		showYesNoDialog: showYesNoDialog,
		changeapp: changeapp,
		openrecord: openrecord,
		gotoapp: gotoapp,
		rtnapp: rtnapp,
		showTimeoutDialog: showTimeoutDialog,
		getTimeoutDialogContent: getTimeoutDialogContent,
		signOut: signOut,
		clearUF: clearUF,
		resetTimeOut: resetTimeOut,
		reloadPage: reloadPage,
		startcntr: startcntr,
		licenseError: licenseError,
		reloadwfcanvas: reloadwfcanvas,
		addWfNode: addWfNode,
		reloadNode: reloadNode,
		setWfActionId: setWfActionId,
		linkUrl: linkUrl,
		linkReport: linkReport,
		showOrHideCtrl: showOrHideCtrl,
		submiteReport: submiteReport,
		openUrl: openUrl,
		clickchart: clickchart,
		clickkpi: clickkpi,
		download: download,
		submitForm: submitForm
	}
}


