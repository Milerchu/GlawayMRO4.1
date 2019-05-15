/*
 * gwaccordion导航控件
 * 基于jquery的显示控制插件，此外还需导入accordion.css样式文件
 * 通过赋予localMethod初始值，可接受来自本地的点击事件处理函数，且此函数的参数为当前点击的accordion的id属性值
 * 此标签的固定高度为30px
 * 对象方法：
 *     	chooseAccordion(accordionId)选择指定标识的accordion
 *	   	disableAccordion(accordionId)使某accordion为不可使用状态
 *		enableAccordion(accordionId)使能某accordion
 **/
;(function($){
	$.fn.accordion = function(options) {
		var time = null;
		var defaults = {
			currentSelect: ""  //当前选中项的标识
		}
		var options = $.extend(defaults, options);
		var $thisObj = this;
		
		/* 初始化选项选中事件 */
		function init() {
			$thisObj.find("a").each(function() {
				$(this).next().hide();
				regClickEvent($(this)); //注册标签的单击响应事件		
			});
			setTimeout(chooseDefaultAccordion, 31);
		}
		
		/* accordion单击响应事件的注册方法 */
		function regClickEvent($obj) {
			$obj.dblclick(function(event){
				clearTimeout(time);
				stopBubble(event.originalEvent);
			});
			$obj.click(function(){
				clearTimeout(time);
				if(isNavMenuLocked) {
					return;
				}
				time = setTimeout(function(){
					if($obj.hasClass("accordionDisabled")) {
						return;
					}
					if($obj.find("td.tdLeft").hasClass("leaf")) {
						$("#page_nav_header").focus();
						var ctrlId = $obj.attr("id");
						if(ctrlId == options.currentSelect) { 
							//重复选择了某应用导航
							parseClick(ctrlId, false);
						} else {
							//选择了新的导航bn  
							parseClick(ctrlId, true);
						}
						return;
					}
					$obj.toggleClass("selected");
					if($obj.hasClass("selected")) {
						$obj.next().show();
					} else {
						$obj.next().hide();
					}	
				},300);
			});
		}
		
		/* 选择指定名称的accordion */
		function chooseAccordion(accordionId) {
			var tarObj = $thisObj.find("#" + accordionId);
			if(tarObj.length <= 0 || tarObj == null) {
				return null;
			}
			tarObj.addClass("pageApp");
			
			//展开按钮及其父层
			var tempObj = tarObj;
			tempObj = tempObj.parent();
			while(tempObj && !(tempObj.hasClass("gw_accordion"))) {
				tempObj.prev().addClass("selected");
				tempObj.show();
				tempObj = tempObj.parent();
			}
			
			options.currentSelect = accordionId; //记录当前选中项的标识
			
			return tarObj;
		}	
		
		/* 初始化选中默认按钮 */
		function chooseDefaultAccordion() {
			if(undef(options.defaultAccordId)) {
				return;
			}
			var tarObj = chooseAccordion(options.defaultAccordId); //选中按钮
			if(undef(tarObj)) {
				return;
			}
			
			//调整滚动条位置
			var topOffset = 0;
			
			var tarOffset = tarObj.offset().top + tarObj.height();
			var accOffset = $thisObj.offset().top + $thisObj.height();
			if(accOffset > tarOffset) {
				return;
			}
			$thisObj.scrollTop(tarOffset - accOffset);
		}
		
		/* 使某accordion为不可使用状态 */
		function disableAccordion(accordionId) {
			var tarObj = $thisObj.find("#" + accordionId);
			tarObj.find("a").andSelf().each(function() {
				$(this).removeClass("selected");
				$(this).addClass("accordionDisabled");
				$(this).next().hide();
				
			});
		}
		
		/* 使能某accordion */
		function enableAccordion(accordionId) {
			$("#" + accordionId).removeClass("accordionDisabled");
		}
		
		/* 数据重载 */
		function loadData() {
			//目前导航菜单只一次加载到位，因此不需要此功能
		}
		
		/* 选择新导航 */
		function chooseAppNav(navId) {
			if(undef(navId)) {
				return;
			}
			var tarObj = chooseAccordion(navId); //选中按钮
			if(undef(tarObj)) {
				return;
			}
			
			//调整滚动条位置
			var topOffset = 0;
			
			var tarOffset = tarObj.offset().top + tarObj.height();
			var accOffset = $thisObj.offset().top + $thisObj.height();
			if(accOffset > tarOffset) {
				return;
			}
			$thisObj.scrollTop(tarOffset - accOffset);
		}
		
		function show() {
			$thisObj.show();
		}
		
		function hide() {
			$thisObj.hide();
		}
		
		init();
		return {
			loadData: loadData,//数据重载
			chooseAppNav: chooseAppNav, //选择新导航
			show: show,
			hide: hide
		};
	}
})(jQuery);

/*
 * 表格cell
 * 重要参数：
 * 对象方法：
 *		
 **/
;(function($){
	$.fn.tablecell = function(options) {
		var repeatClick = false;
		
		/* 
		* 设置点击是否叠加
		*/
		function setRepeatClick(isRepeatClick) {
			repeatClick = isRepeatClick;
		}
		
		/* 点击事件受理前 */
		function beforeClick() {
			parseClick(options.id, true, "selectrow");
		}
		
		return {
			setRepeatClick: setRepeatClick,
			beforeClick: beforeClick
		};
	}
})(jQuery);

/*
 * 表格cell的image文本控件
 * 重要参数：
 * 对象方法：
 *		
 **/
;(function($){
	$.fn.tableimage = function(options) {
		var $thisObj = this;
		
		function init() {
			
		}
		
		/* 
		* 加载新的文本内容
		*/
		function loadData(text) {
			//$thisObj.text(html2Str(text));
		}
		
		/* 
		 * 更新表格操作图标
		 * imgName: toggledetailstate_open.gif
		 * 事件图片完整地址：MRO_IMG_PTH + "event/" + imgName
		 * */
		function changeImage(imgName) {
			$thisObj.attr("src", MRO_IMG_PTH + "event/" + imgName);
		}
		
		/* 点击事件受理前 */
		function beforeClick(e) {
			stopBubble(e);
			parseClick(options.id, true, "callCenter", options.event);
		}
		
		return {
			loadData: loadData,
			beforeClick: beforeClick,
			changeImage: changeImage
		};
	}
})(jQuery);

/*
 * 表格cell的text文本控件
 * 重要参数：
 * 对象方法：
 *		
 **/
;(function($){
	$.fn.tabletext = function(options) {
		var $thisObj = this;
		var flag = true;
		
		/*
		 * 加载新的文本内容
		 */
		function loadData(dataStr) {
			var text = getTagInnerHtmlStr(dataStr, "text");
			var color = getTagInnerHtmlStr(dataStr, "color");
			if (undef(color)) {
				color = "#333333";
			}
			if (options.isSelRowEvent) {
				$thisObj.find("td").text(text);
				$thisObj.find("td").css("color", color);
			} else {
				$thisObj.find("a").text(text);
				$thisObj.find("a").css("color", color);
			}
		}

		/* 点击事件受理前 */
		function beforeClick(e) {
			if (flag) {
				flag = false;
				parseClick(options.id, true, "callCenter", options.event);
				setTimeout(function() {
					flag = true;
				}, 1000);
			}
			stopBubble(e);
		}

		/* 下载附件 */
		function download(url) {
			var downloadWindow = window.open(url, '', "width=200,height=100");
			if (downloadWindow) { // 浏览器可能会阻止打开窗口
				downloadWindow.focus();
			}
		}

		return {
			loadData : loadData,
			beforeClick : beforeClick,
			download : download
		};
	}
})(jQuery);


/*
 * table控件
 * 基于jquery的显示控制插件，此外还需导入pagegrid.css样式文件
 * 重要参数：
 * 对象方法：
 *		
 **/
;(function($){
	$.fn.table = function(options) {
		var defaults = {
			id: "",         //与后台交互的控件标识
			domId: "",
			height: "600px", //默认高度
			width: "100%",   //默认宽度为100%父层空间
			
			count: 0,    //总的结果集数量
			totalPage: 0,//总的分页数量
			currentPage: -1, //当前页码：从0开始
			currentRow: -1,     //当前选择首行
			rowsPerPage: 10,  //每页显示的数据量
			currentPageRowNum: 0 //当前页数据行数
		}
		options = $.extend(defaults, options);
		
		var $thisObj = this;
		options.domId = options.id + "_PG"; //初始化pagegrid的dom id
		var $filterLink = null; //过滤链接控件
		var $gotoInput = $thisObj.find("#" + options.id + "_PG_GOTO"); //跳转页面页码输入框
		
		init(); //初始化
		
		return {
			loadData: loadData, //加载json格式分页数据
			changeStyle: changeStyle,//重置表格控件显示样式
			selectRow: selectRow, //选择行
			beforeSelectRow: beforeSelectRow, //提交后台处理选择行以前
			beforeLoadPage: beforeLoadPage, //页面跳转前
			loadDetails: loadDetails, //加载并显示详细控件
			showDetails: showDetails, //关闭详细信息
			toggleDelRow: toggleDelRow, //标记/移除行删除状态
			loadRow: loadRow, //新增行后，加载新增行显示相关数据
			signDelRow: signDelRow, //标记行为删除状态
			unsignDelRow: unsignDelRow, //移除行的删除状态
			resize: resize, //重置显示区域空间
			hideCol: hideCol, //隐藏指定索引列
			showCol: showCol  //显示指定索引列
		};	
		
		/* 初始化控件，根据传入的数据拼装数据分页 */
		function init() {
			$thisObj.find("#" + options.id + "_PG_BODY").removeClass("hide");
			updateCurrentPageRowNum(); //初始化当前页需显示多少行记录		
			
			initMinWidth();//初始化最小宽度
			
			$gotoInput.keydown(function(event) {
				if(13 == event.keyCode) {
					parseClick(options.id, true, "gotopage", this.value);
					/*var gotoPageNum = parseInt(this.value, 0);
					if(options.count > 0 && gotoPageNum != Number.NaN) {
						if(gotoPageNum >= 0 && gotoPageNum <= options.totalPage && gotoPageNum != (options.currentPage + 1)) {
							parseClick(options.id, true, "gotopage", gotoPageNum);
						}
					}*/
				}
			});
		}
		
		/* 分页控制数据更新后，更新当前分页能够显示的行数量 */
		function updateCurrentPageRowNum() {
			if(options.count > 0) {
				if(options.currentPage < options.totalPage - 1) {
					options.currentPageRowNum = options.rowsPerPage;
				} else {
					options.currentPageRowNum = options.count - options.rowsPerPage * (options.totalPage - 1);
				} 
			} else {
				options.currentPageRowNum = 0;
			}
		}
		
		/* 初始化最小宽度 */
		function initMinWidth() {
			if(options.minWidth <= 0) {
				return;
			}
			ctrlMgrSys.addResize(options.id);
			setTimeout(resize, 50);
		}
		
		/* 表格显示空间大小调整（被动触发） */
		function resize() {
			if(options.minWidth > 0) {
				var $container = $thisObj.find("#" + options.id + "_PG_BDC");
				$container.children().width(1);
				$container.width("100%");
				setTimeout(innerResize, 50);
			}
		}
		
		function innerResize() {
			if(options.minWidth > 0) {
				var $container = $thisObj.find("#" + options.id + "_PG_BDC");
				var parWidth = $container.parent().width();
				$container.width(parWidth);
				if(parWidth > options.minWidth) {
					$container.children().width("100%");
				} else {
					$container.children().width(options.minWidth);
				}
			}
		}
		
		/* 设置表格label */
		function setLable(newLabel) {
			$thisObj.find("#" + options.id + "_LABEL").text(newLabel);
		}
		
		/* 
		* 加载json格式的分页数据 
		* 重组分页body中的内容 
		* @rtn <count></count><label></label>
		*/
		function loadData(rtnStr) {
			var lable = getTagInnerHtmlStr(rtnStr, "label");
			setLable(lable);
			var jsonDataObj = getTagInnerHtmlStr(rtnStr, "count");
			var jsonData;
			if((typeof jsonDataObj) == "string" || (typeof jsonDataObj) == "String") {
				jsonData = $.parseJSON(jsonDataObj);
			} else {
				jsonData = jsonDataObj;
			}
			options.count = jsonData.count;
			options.totalPage = jsonData.totalPage;
			options.currentPage = jsonData.currentPage;
			options.currentRow = jsonData.currentRow;  
			
			if(options.currentRow < 0) {
				//表格加载异常，此时默认显示为空
				options.count = 0;
				options.totalPage = 0;
				options.currentPage = -1;
			}
			
			if(options.count > 0) { //结果集不为空
				if(options.currentPage < options.totalPage - 1) {
					options.currentPageRowNum = options.rowsPerPage;
				} else {
					options.currentPageRowNum = options.count - options.rowsPerPage * (options.totalPage - 1);
				} 
				
				//更新内容行的显示与影藏
				for(var i = 0, SIZE = options.rowIds.length; i < SIZE; i++) {
					if(i < options.currentPageRowNum) {
						$thisObj.find("#" + options.rowIds[i]).show();
						
						if(i == options.currentRow) { //当前行
							highlightRow(options.rowIds[i], options.currentRow); //设置指定行为高亮状态
						}
					} else {
						$thisObj.find("#" + options.rowIds[i]).hide();
					}
				}
				
			} else { //此表格无数据
				options.currentPageRowNum = 0;
				
				//隐藏所有内容行
				for(var i = 0, SIZE = options.rowIds.length; i < SIZE; i++) {
					$thisObj.find("#" + options.rowIds[i]).hide();
				}
			}
			
			
			resetPageCount(); //重置分页相关数量信息
			resetButtomPageCount();//分页条在底部时，用于portlet控件
		}
		
		/* 重置分页相关数量信息 */
		function resetPageCount() {
			if(options.count <= 0) {
				$thisObj.find("#" + options.id + "_PG_FROM").text(0);
				$thisObj.find("#" + options.id + "_PG_TO").text(0);
				$thisObj.find("#" + options.id + "_PG_COUNT").text(0);
				$thisObj.find("#" + options.id + "_PG_CURRPG").text(0);
				$thisObj.find("#" + options.id + "_PG_PGTOTAL").text(0);
			} else {
				$thisObj.find("#" + options.id + "_PG_FROM").text((options.currentPage * options.rowsPerPage + 1));
				var to = (options.currentPage + 1) * options.rowsPerPage;
				$thisObj.find("#" + options.id + "_PG_TO").text((to > options.count? options.count: to));
				$thisObj.find("#" + options.id + "_PG_COUNT").text(options.count); 
				$thisObj.find("#" + options.id + "_PG_CURRPG").text((options.currentPage + 1));
				$thisObj.find("#" + options.id + "_PG_PGTOTAL").text(options.totalPage);
			}
			$gotoInput.val("");
		}	
		
		/* 重置分页相关数量信息  分页条在底部时，用于portlet控件*/
		function resetButtomPageCount() {
			var $head = $("#" + options.domId + "_BUTTOM");
			if(options.currentRow >= 0 && options.count > 0) {
				$head.find("span.pagemsg").html("第" + (options.currentRow + 1) + "/" + options.currentPageRowNum + "条");
			} else {
				$head.find("span.pagemsg").html("第/" + (options.currentPageRowNum>0?options.currentPageRowNum:"") + "条");
			}
			
			var $nowPageInput = $head.find("input.nowpage");
			if(options.count > 0) {
				$nowPageInput.val((options.currentPage + 1));
				$nowPageInput.show();
			} else {
				$nowPageInput.hide();
				$nowPageInput.val("");
			}
			$head.find("span.totalpage").html(((options.totalPage>0)? options.totalPage: ""));
		}	

		/* 
		 * 重置表格控件显示样式 
		 * dataStr: "closefilter" 
		 * dataStr: "openfilter" 
		 */
		function changeStyle(dataStr) {
			if(dataStr == "closefilter") {
				closefilter();
			} else if(dataStr == "openfilter") {
				openfilter();
			} 
		}
		
		/* 关闭表格过滤器 */
		function closefilter() {
			$thisObj.find("#" + options.domId + "_FILTERROW").hide();
			getFilterLink().find("div:first").removeClass("fltOnIcon").addClass("fltOffIcon");
			getFilterLink().find(".btn_label:first").children().text("显示过滤");
		}
		
		/* 打开表格过滤器 */
		function openfilter() {
			$thisObj.find("#" + options.domId + "_FILTERROW").show();
			getFilterLink().find("div:first").removeClass("fltOffIcon").addClass("fltOnIcon");
			getFilterLink().find(".btn_label:first").children().text("隐藏过滤");
		}
		
		/* 页面跳转前 */
		function beforeLoadPage(wh) {
			if(wh == "firstpage") {
				if(options.count > 0 && options.currentPage > 0) {
					parseClick(options.id, true, wh);
				}
			} else if(wh == "prepage") {
				if(options.count > 0 && options.currentPage > 0) {
					parseClick(options.id, true, wh);
				}
			} else if(wh == "gotopage") {
				parseClick(options.id, true, wh, $gotoInput.val());
				/*var gotoPageNum = parseInt($gotoInput.val(), 0);
				if(options.count > 0 && gotoPageNum != Number.NaN) {
					if(gotoPageNum >= 0 && gotoPageNum <= options.totalPage && gotoPageNum != (options.currentPage + 1)) {
						parseClick(options.id, true, wh, gotoPageNum);
					}
				}*/
			} else if(wh == "nextpage") {
				if(options.count > 0 && options.currentPage >= 0 && options.currentPage < options.totalPage - 1) {
					parseClick(options.id, true, wh);
				}
			} else if(wh == "lastpage") {
				if(options.count > 0 && options.currentPage >= 0 && options.currentPage < options.totalPage - 1) {
					parseClick(options.id, true, wh);
				}
			} else if(wh == "clearfilter") {
				parseClick(options.id, true, wh);
				
			} else if(wh == "resetAndReload") { //重载数据并刷新
				parseClick(options.id, true, wh);
			} else if(wh == "downloadExcel") { //重载数据并刷新
				parseClick(options.id, true, wh);
			}
		}
		
		/* 
		 * 设置指定行为高亮状态
		 * rowId: 行标识
		 * rowNum： 行索引
		 */
		function highlightRow(rowId, rowNum) {
			var rowObj = $thisObj.find("#" + rowId);
			rowObj.siblings().each(function(){
				var $obj = $(this);
				if($obj.hasClass("tablerow")) { 
					$obj.removeClass("trselect").addClass("trunselect");
					if($obj.hasClass("trseldelline")) {
						$obj.removeClass("trseldelline").addClass("delline");
					}
				}
			});
			rowObj.removeClass("trunselect").removeClass("hover");
			if(rowObj.hasClass("delline")) {
				rowObj.addClass("trseldelline")
			} else {
				rowObj.addClass("trselect");
			}
		}
		
		/* 
		 * 选择行
		 * dataStr: <rowid>GWC11</rowid><rownum>2</rownum>
		 */
		function selectRow(dataStr) {
			var rowNumStr = getTagInnerHtmlStr(dataStr, "rownum");
			var rowId = getTagInnerHtmlStr(dataStr, "rowid");
			if(undef(rowNumStr)) {
				return;
			}
			var rowNum = parseInt(rowNumStr, 0);
			highlightRow(rowId, rowNum); //设置指定行为高亮状态
		}
		
		/** 
		 * 点击行事件提交前处理
		 * ctrlId: 行标识 
		 * rowNum: 选择的行号 
		 * eventType: 源自tablecol中注册的事件
		 			  1.若tablecol中未注册事件，则此属性有值，且为selectrow
		 			  2.若tablecol中注册了事件，则此属性无值
		 */
		function beforeSelectRow(ctrlId, rowNum, eventType) {
			if(rowNum >= 0 && rowNum < options.currentPageRowNum && rowNum != options.currentRow) {//有效选择新行
				if(undef(eventType)) { //没有绑定事件，则无需传递事件
					parseClick(ctrlId, true);
				} else { //需要将绑定的事件一同传递
					parseClick(ctrlId, true, eventType);
				}
			} if(rowNum >= 0 && rowNum < options.currentPageRowNum && rowNum == options.currentRow) {//重复选择行
				if(undef(eventType)) {//只有当行有绑定事件时，才会有响应*****有问题*****
					parseClick(ctrlId, true);
				}
			} else { //无效选择
				
			}
		}
		
		/* 获取过滤链接控件jquery对象 */
		function getFilterLink() {
			if($filterLink == null) {
				$filterLink = $thisObj.find("#" + options.domId + "_FILTERLINK");
			}
			return $filterLink;
		}
		
		/** 
		 * 加载并显示详细控件
		 * @dataStr: 详细内容控件
		 */
		function loadDetails(dataStr) {
			if(options.hasDetail) {
				var $detailsRow = $thisObj.find("#" + options.id + "_PG_DETAILS");
				$detailsRow.find("#" + options.detailId).html(dataStr);
				$detailsRow.show();
				options.detailExpanded = true;
			}
		}
		
		/** 
		 * 关闭详细控件
		 * @isOpen: 是否显示详细信息
		 */
		function showDetails(isOpen) {
			if(options.hasDetail) {
				if(isOpen == "true") {
					$thisObj.find("#" + options.id + "_PG_DETAILS").show();
					options.detailExpanded = true;
				} else {
					$thisObj.find("#" + options.id + "_PG_DETAILS").hide();
					options.detailExpanded = false;
				}
			}
		}
		
		/** 
		 * 新增行后，加载新增行显示相关数据
		 * @rtn {}
		 */
		function loadRow(rtn) {
			var jsonData;
			if((typeof rtn) == "string" || (typeof rtn) == "String") {
				jsonData = $.parseJSON(rtn);
			} else {
				jsonData = rtn;
			}
			options.count = jsonData.count;
			options.totalPage = jsonData.totalPage;
			options.currentPage = jsonData.currentPage;
			options.currentRow = jsonData.currentRow;  
			
			if(options.count > 0) { //结构集不为空
				if(options.currentPage < options.totalPage - 1) {
					options.currentPageRowNum = options.rowsPerPage;
				} else {
					options.currentPageRowNum = options.count - options.rowsPerPage * (options.totalPage - 1);
				} 
				
				//显示新行
				if(options.currentRow < options.rowsPerPage && options.currentRow >= 0) {
					$thisObj.find("#" + options.rowIds[options.currentRow]).show(); //当前行
					highlightRow(options.rowIds[options.currentRow], options.currentRow); //设置指定行为高亮状态
				}
				
			} else { //此表格无数据
				options.currentPageRowNum = 0;
				
				//隐藏所有内容行
				for(var i = 0, SIZE = options.rowIds.length; i < SIZE; i++) {
					$thisObj.find("#" + options.rowIds[i]).hide();
				}
			}
			
			
			resetPageCount(); //重置分页相关数量信息
		}
		
		/** 
		 * 标记/移除行删除状态
		 * @rowId: 行标识
		 */
		function toggleDelRow(rowId) {
			var $rowObj = $thisObj.find("#" + rowId);
			$rowObj.toggleClass("delline");
		}
		
		/** 
		 * 标记行删除状态
		 * @rowId: 行标识
		 */
		function signDelRow(rowId) {
			var rowObj = $thisObj.find("#" + rowId);
			if(rowObj.hasClass("trselect")) {
				rowObj.removeClass("trselect").addClass("trseldelline");
			} else {
				rowObj.addClass("delline");
			}
		}
		
		/** 
		 * 移除行删除标记
		 * @rowId: 行标识
		 */
		function unsignDelRow(rowId) {
			var rowObj = $thisObj.find("#" + rowId);
			rowObj.removeClass("delline");
			if(rowObj.hasClass("trseldelline")) {
				rowObj.removeClass("trseldelline").addClass("trselect")
			} else if(rowObj.hasClass("trhovdelline")) {
				rowObj.removeClass("trhovdelline");
			}
		}
		
		/** 
		 * 隐藏指定索引列
		 * @colIndex: 列索引
		 */
		function hideCol(colIndex) {
			var $trs = $thisObj.find("#" + options.id + "_PG_BDC_TBL").children(); //行s
			if($trs.length <= 1) {
				$trs = $trs.children();
			}
			$trs.each(function(){
				$(this).children().eq(colIndex).addClass("hide");
			});
		}
		
		/** 
		 * 显示指定索引列
		 * @colIndex: 列索引
		 */
		function showCol(colIndex) {
			var $trs = $thisObj.find("#" + options.id + "_PG_BDC_TBL").children(); //行s
			if($trs.length <= 1) {
				$trs = $trs.children();
			}
			$trs.each(function(){
				$(this).children().eq(colIndex).removeClass("hide");
			});
		}
	}
})(jQuery);

/* 具有排序功能的表格title */
;(function($){
	$.fn.tabletitle = function(options) {
		var $titleStatus = null;
		var $thisObj = this;
		var timer = -1;//处理双击加载字段提示事件
		
		/* 初始化控件 */
		function init() {
			var $titleContent = $thisObj.find("#" + options.id + "Content");
			if(options.isSortable) { //可排序
				$titleContent.bind("click", function(){
					clearTimeout(timer);
					timer = setTimeout(sortCol, 300);
				}).bind("dblclick", function(event){
					clearTimeout(timer);
					$titleContent.bind("mouseleave", function(event){
						leaveField(event);	
						$titleContent.unbind("mouseleave");						
					});	
					tipField(event);
				});
			} else { //不可排序
				$titleContent.bind("dblclick", function(event){
					$titleContent.bind("mouseleave", function(event){
						leaveField(event);	
						$titleContent.unbind("mouseleave");						
					});	
					tipField(event);
				});
			}
		}
		
		/* 排序列（发送事件） */
		function sortCol() {
			parseClick(options.id, true);
		}
		
		/* 字段提示 */
		function tipField(event) {
			TIP.hide();
			TIP.setBindCtrlId(options.id);
			TIP.hideResetBtn(); //隐藏重置值按钮;
			TIP.update(getFieldTipCont(options), false); //重置提示内容，更新提示控件绑定对象为当前控件
			TIP.mouseenter(event, $thisObj);
		} 
		
		/**
		 * 当鼠标移出字段时，隐藏提示信息
		 * */
		function leaveField(event) {
			TIP.mouseleave(event, $thisObj);
		} 
		
		
		/* 更新排序状态 */
		function updateOrderStatus(status) {
			if(options.isSortable) {
				if($titleStatus == null) {
					$titleStatus = $thisObj.find("#" + options.id + "Status");
				}
				$titleStatus.removeClass("asc").removeClass("desc");
				if(!undef(status)) {
					$titleStatus.addClass(status);
				}
			}
		}
		init(); //初始化控件
		return {
			updateOrderStatus: updateOrderStatus
		};
	}
})(jQuery);


/*
 * waitmark控件，用于异步过程的提交等待
 * 基于jqueryui，需导入waitmark样式文件
 * 对象方法：
 *     
 **/
;function waitMark() {
	var type = "waitmark";
	var zindex = 999999999999;
	var count = 0;
	var idPrefix = "WTMK";
	var waitmarks = new Array();
	
	this.addMark = addMark;
	this.removeMark = removeMark;
	
	/* 添加mark */
	function addMark() {
		var cur_height = $(window).height();
		var cur_width = $(window).width();
		var markStr = "<div class='waitmark' style='z-index:" + zindex + "'></div>";
		var $m = $(markStr);
		waitmarks.push($m);
		$m.appendTo("body");
		zindex = zindex + 1;
	}
	
	/* 移除mark */
	function removeMark() {
		var $m = waitmarks.pop();
		if(!undef($m)) {
			$m.remove();
			zindex = zindex - 1;
		}
	}
}




/**
 *toolbaraction
 */
(function($){
	$.fn.toolbaraction = function(options) {
		var $thisObj = this;
		function init() {
			$thisObj.click(function(){
				toolbarclick();
			});
			if(!options.disabled && ($thisObj.attr("actiontype")=="actionbutton"  || $thisObj.attr("actiontype")=="actionmenu")) {
				$thisObj.removeClass("toolbaraction_disable");
				$thisObj.find("img").each(function(){
					var imgobj = $(this);
					var imgsrc = imgobj.attr("src");
					var imgname = imgsrc.substring(0,imgsrc.lastIndexOf("."));
					var imgextname = imgsrc.substring(imgsrc.lastIndexOf(".")+1,imgsrc.lenght);
					$thisObj.mouseover(function(){
						imgobj.attr("src",imgname+"_hover."+imgextname);
					});
					$thisObj.mouseout(function(){
						imgobj.attr("src",imgname+"."+imgextname);
					});
				});
			}
		}
		function toolbarclick() {
			if(!options.disabled){
				if(options.value){
					parseClick(options.id,true,options.event,options.value);
				}else{
					parseClick(options.id,true);
				}
			}
		}
		function changeStyle(rtn) {
			var disabled = getTagInnerHtmlStr(rtn, "disabled");
			var icon = getTagInnerHtmlStr(rtn, "icon");
			var text = getTagInnerHtmlStr(rtn, "text");
			if($thisObj.attr("actiontype")=="actionbutton" || $thisObj.attr("actiontype")=="actionmenu"){//如果是工具栏按钮，不包含竖线(sep)
				$thisObj.children("img").attr("src",MRO_IMG_PTH+"toolbar/"+icon);
				$thisObj.attr("title", text);
				if(disabled=="true"){
					$thisObj.addClass("toolbaraction_disable");
					//$thisObj.unbind("click");
					$thisObj.unbind("mouseover");
					$thisObj.unbind("mouseout");
					options.disabled = true;
				}else{
					$thisObj.removeClass("toolbaraction_disable");
					/*$thisObj.click(function(){
						parseClick($thisObj.attr("id"),true);
					});*/
					$thisObj.find("img").each(function(){
						var imgobj = $(this);
						var imgsrc = imgobj.attr("src");
						var imgname = imgsrc.substring(0,imgsrc.lastIndexOf("."));
						var imgextname = imgsrc.substring(imgsrc.lastIndexOf(".")+1,imgsrc.lenght);
						$thisObj.mouseover(function(){
							imgobj.attr("src",imgname+"_hover."+imgextname);
						});
						$thisObj.mouseout(function(){
							imgobj.attr("src",imgname+"."+imgextname);
						});
					});
					options.disabled = false;
				}
			}
		}
		//标签页切换时重载更多操作菜单
		function reLoadMoreOpttionMenu(rtn){
			var aid = getTagInnerHtmlStr(rtn, "actionid");
			var menuhtml = getTagInnerHtmlStr(rtn, "menuhtml");
			//console.log(menuhtml);
			$("#"+aid+"_menu").html(menuhtml);
			$("#"+aid).menu({
				menu:$("#"+aid+"_menu"),
				type:"async"
			});
		}
		init();
		return {
			changeStyle: changeStyle,
			reLoadMoreOpttionMenu : reLoadMoreOpttionMenu
		};
	}
})( jQuery );



/*
 * textbox控件、multilineTextbox控件
 * 文本框显示控制、多行文本框显示控制
 * 对象方法：
 *     
 **/
;(function($){
	$.fn.textbox = function(options) {
		var defaults = {
			id: "",
			isDateLookup: false,
			hasAppLink: false,
			hasLookup: false,
			errorMsg: ""  //后台错误消息
		}
		options = $.extend(defaults, options);
		var $thisObj = this;
		var $input = $thisObj.find("#" + options.id + "input0");
		/**
		 * 初始化控件
		 * */
		function init() {
			if(options.isDateLookup) {
				//只处理日期控件
				$thisObj.find("#" + options.id + "img").bind("click", function(event){
					stopBubble(event.originalEvent); //阻止点击事件冒泡
					parseClick(options.id, false);
					if(!options.isReadonly) { //只注册非只读输入框的日期图表按钮点击事件
						WdatePicker({el: (options.id + "input0"), dateFmt: options.dateFmt});
					}
				});
			} else if(options.menuType) {
				//menu事件
				$thisObj.find("#" + options.id + "img").bind("click", function(){
					if(options.isInCell) {
						callCtrlMthd(options.cellId, 'setRepeatClick', [true]); //阻止tablecell事件响应
					}
					
				});
				$("#"+options.id+"imgtd").menu({
					menu: $("#"+options.id+"_menu"),
					owner: "textbox"
				});
				//console.log($("#"+options.id+"_menu").html());
			} else if(options.hasAppLink) {
				//applink事件
				$thisObj.find("#" + options.id + "img").bind("click", function(event){
					debugger;
					stopBubble(event.originalEvent); //阻止点击事件冒泡
					//判断
					if(options.zzsEvent=="applink"){
						parseClick(options.id, true, "textLink", options.appLink);
					}else{
					parseClick(options.id, true, "beforeGotoApp", options.appLink);
					}
				});
			} else if(options.hasLookup) {
				//lookup事件
				$thisObj.find("#" + options.id + "img").bind("click", function(event){
					stopBubble(event.originalEvent); //阻止点击事件冒泡
					parseClick(options.id, true, "lookup");
				});
			}
			
			if(options.isChkbox) { //绑定checkbox的特定事件
				$input.bind("click", function(event){
					if(options.id != getChangedCtrlId()) { //确认不要在同一个控件内操作
						if(options.isInCell) {
							stopBubble(event.originalEvent); //阻止点击事件冒泡
							if(options.isCellEvent) { //目前checkbox只能作为表格的多选事件操作对象
								parseClick(options.id, true, "multipleselectrow");
							} else { //表格的内容对象
								parseClick(options.id, true, "onclick");
							}
						} else {
							parseClick(options.id, true, "onclick");
						}
					}								
				});
				
			} else { //绑定textbox和multilinetextbox的特定事件
				$input.bind("change", function() {
					var valTmp = this.value;
					if(valTmp == null) {
						valTmp = "";
					}
					setChangedCtrlVal(options.id, valTmp);//缓存输入框变更的值 eventmgr.js
				}).bind("focus", function(event){
					stopBubble(event.originalEvent); //阻止点击事件冒泡
					if(!options.isReadonly) {
						if(options.isOk) {
							$input.parent().addClass("inputfocus");
						}
					}		
					if(options.id != getChangedCtrlId()) { //确认不要在同一个控件内操作
						if(options.isInCell) {
							parseClick(options.id, true, "onfocus");
						} else {
							parseClick(options.id, false);
						}
					}					
				}).bind("blur", function(event){
					if(!options.isReadonly) {
						$input.parent().removeClass("inputfocus");
					}							
				});
			}
			$input.bind("mouseenter", function(event){
				if(!options.isOk) {
					onErrInputMouseEnter(event);
				}							
			}).bind("mouseleave", function(event){
				if(!options.isOk) {
					onErrInputMouseLeave(event);
				}							
			});
			
			//针对表格过滤输入框，添加回车键监听事件
			if(options.isFilter) {
				$input.keydown(function(event) {
					if(13 == event.keyCode) {
						if($.browser.msie) {
							setChangedCtrlVal(options.id, this.value);//缓存输入框变更的值 eventmgr.js
							parseClick(options.id, true, "filtertable");
							
						} else {
							stopBubble(event);
							$input.blur();
							setChangedCtrlVal(options.id, this.value);
							$("body").click();
						}
						
					}
				});
			}
			
			if(!options.isFilter && !options.isInCell) { //注册字段提示事件
				var $titleContent = $thisObj.find(".txbxlabeltx:first");
				$titleContent.bind("dblclick", function(event){
					$titleContent.bind("mouseleave", function(event){
						leaveField(event);	
						$titleContent.unbind("mouseleave");						
					});	
					tipField(event, $titleContent);
				});
			}
		}
		
		/* 字段提示 */
		function tipField(event, jqTarget) {
			TIP.hide();
			TIP.setBindCtrlId(options.id);
			TIP.hideResetBtn(); //隐藏重置值按钮;
			TIP.update(getFieldTipCont(options), false); //重置提示内容，更新提示控件绑定对象为当前控件
			TIP.mouseenter(event, jqTarget);
		} 
		
		/**
		 * 当鼠标移出字段时，隐藏提示信息
		 * */
		function leaveField(event) {
			TIP.mouseleave(event, $thisObj);
		} 
		
		/**
		 * 重新加载控件数据
		 * 根据返回值中的类型属性，更新文本框状态
		 * @param rtn - <type>ok or error</type><val></val><msg></msg><required></required><readonly></readonly>
		 * */
		function loadData(rtn) {
			if("ok" == getTagInnerHtmlStr(rtn, "type")) {
				if(!options.isOk) {
					options.isOk = true;
					options.errorMsg = "";
					$input.parent().removeClass("inputerror");
				}
				
			} else {
				if(options.isOk) {
					options.isOk = false;
					$input.parent().addClass("inputerror");
				}
				options.errorMsg = getTagInnerHtmlStr(rtn, "msg");
				
			}
			
			var newVal = getTagInnerHtmlStr(rtn, "val");
			if(options.isChkbox) { //更新checkbox的值
				if("Y" == newVal || "1" == ("" + newVal)) {
					$input.removeClass("unchecked").addClass("checked");
				} else {
					$input.removeClass("checked").addClass("unchecked");
				}
			} else if(options.isListbox) { //更新listbox的值
				$input.html((undefStrictly(newVal)? "": newVal));
			} else { //更新textbox或multilinetextbox的值
				$input.val((undefStrictly(newVal)? "": newVal));
			}
			
			
			if("true" == getTagInnerHtmlStr(rtn, "required")) {
				$thisObj.find("#" + options.id + "required").show();
			} else {
				$thisObj.find("#" + options.id + "required").hide();
			}
			
			if("true" == getTagInnerHtmlStr(rtn, "readonly")) {
				options.isReadonly = true;
				$input.parent().addClass("inputreadonly").removeClass("inputfocus");
				$input.attr("readonly", "readonly");
			} else {
				options.isReadonly = false;
				$input.parent().removeClass("inputreadonly");
				$input.removeAttr("readonly");
			}
		}
		
		/**
		 * 更新标签内容
		 * @param newLabel - 新标签内容
		 */
		function changeLabel(newLabel) {
			$thisObj.find("#" + options.id + "labeltd").find("span.txbxlabeltx").html(html2Str(newLabel));
		}

		function setVisible() {
			
		}
		
		/**
		 * 当鼠标移动至错误信息图标时，显示错误提示信息
		 * */
		function onErrInputMouseEnter(event) {
			TIP.hide();
			TIP.setBindCtrlId(options.id);
			TIP.showResetBtn(); //显示重置值按钮
			TIP.update(html2Str(options.errorMsg), false); //重置提示内容，更新提示控件绑定对象为当前控件
			TIP.mouseenter(event, $input);
		} 
		
		/**
		 * 当鼠标移出错误信息图标时，隐藏错误提示信息
		 * */
		function onErrInputMouseLeave(event) {
			TIP.mouseleave(event, $input);
		} 
		
		/**
		 * 日期控件设置日期后提交前，值变更处理
		 * */
		function beforeDateChange(newDate) {
			setChangedCtrlVal(options.id, newDate);//缓存输入框变更的值 eventmgr.js
		}
		
		/**
		 * 回退文本框值
		 * */
		function resetVal() {
			if(options.isOk) { //无异常的不需回退值
				return;
			}
			parseClick(options.id, true, "rollback");
		}
		
		/* 返回控件类型（目前服务于请求后的控件聚焦处理） */
		function getType() {
			return "textbox";
		}
		
		/* 返回控件内部需要被聚焦的dom标识 */
		function getFocusDomId() {
			return options.id + "input0";
		}
		
		init();
		/* *****变更值后，后台需返回更新值方法，将新值缓存在oldValue中，并根据需要更新输入框的状态**** */
		return {
			resetVal: resetVal,
			loadData: loadData,
			beforeDateChange: beforeDateChange,
			onErrInputMouseEnter: onErrInputMouseEnter,
			onErrInputMouseLeave: onErrInputMouseLeave,
			getType: getType,
			getFocusDomId: getFocusDomId
		};
	}
})(jQuery);



/*
 * radiobutton控件
 * 对象方法：
 *     
 **/
;(function($){
	$.fn.radiobutton = function(options) {
		var $thisObj = this;
		var $inputTd = $thisObj.find("#" + options.id + "inputtd");
		var $input = $inputTd.find("input[name=" + options.id + "]");
		/**
		 * 初始化控件
		 * */
		function init() {
			if(!undef(options.radiosInfo)) {
				$input.each(function(){
					var $that = $(this);
					$that.click(function(event) {
						stopBubble(event.originalEvent); //阻止点击事件冒泡
						if($that.hasClass("radiochecked") || options.isXmlReadonly || options.isReadonly) { //不可重复点击radio
							parseClick($that.attr("id"), false);
							
						} else { //点击新的radio
							parseClick($that.attr("id"), true, "onclick");
						}
					}).bind("mouseenter", function(event){
						if(!options.isOk && $that.hasClass("radiochecked")) {
							onErrInputMouseEnter(event, $that);
						}							
					}).bind("mouseleave", function(event){
						if(!options.isOk && $that.hasClass("radiochecked")) {
							onErrInputMouseLeave(event);
						}							
					});
					
				});
			}
		
			//if(!options.isFilter && !options.isInCell) { //注册字段提示事件
				
				
				var $titleContent = $thisObj.find(".txbxlabeltx:first");
				$titleContent.bind("dblclick", function(event){
					$titleContent.bind("mouseleave", function(event){
						leaveField(event);	
						$titleContent.unbind("mouseleave");						
					});	
					tipField(event, $titleContent);
				});
			
			
			
		//	}
			
		}
		
		
		/* 字段提示 */
		function tipField(event, jqTarget) {
			TIP.hide();
			TIP.setBindCtrlId(options.id);
			TIP.hideResetBtn(); //隐藏重置值按钮;
			TIP.update(getFieldTipCont(options), false); //重置提示内容，更新提示控件绑定对象为当前控件
			TIP.mouseenter(event, jqTarget);
		} 
		
		/**
		 * 当鼠标移出字段时，隐藏提示信息
		 * */
		function leaveField(event) {
			TIP.mouseleave(event, $thisObj);
		} 
		
		/**
		 * 设置控件值
		 * @param newVal - 新值
		 * */
		function val(newVal) {
			$input.each(function() {
				var $that = $(this);
				if($that.attr("ctrlvalue") == newVal) {
					$that.removeClass("radiounchecked").addClass("radiochecked");
					if(!options.isOk) {
						$that.parent().addClass("inputerror");
					}
				} else {
					$that.removeClass("radiochecked").addClass("radiounchecked");
					$that.parent().removeClass("inputerror");
				}
			});
		}
		
		/**
		 * 重新加载控件数据
		 * 根据返回值中的类型属性，更新文本框状态
		 * @param rtn - <type>ok or error</type><val></val><msg></msg><required></required><readonly></readonly>
		 * */
		function loadData(rtn) {
			if("ok" == getTagInnerHtmlStr(rtn, "type")) {
				if(!options.isOk) {
					options.isOk = true;
					options.errorMsg = "";
				}
				
			} else {
				if(options.isOk) {
					options.isOk = false;
				}
				options.errorMsg = getTagInnerHtmlStr(rtn, "msg");
				
			}
			
			var newVal = getTagInnerHtmlStr(rtn, "val");
			val((undef(newVal)? "": newVal));
			
			if("true" == getTagInnerHtmlStr(rtn, "required")) {
				$thisObj.find(".txbxlabelrq:first").show();
			} else {
				$thisObj.find(".txbxlabelrq:first").hide();
			}
			
			if(!options.isXmlReadonly) {
				if("true" == getTagInnerHtmlStr(rtn, "readonly")) {
					options.isReadonly = true;
					$inputTd.addClass("inputreadonly");
					$input.each(function(){
						$(this).attr("readonly", "readonly");
					});
					
				} else {
					options.isReadonly = false;
					$inputTd.removeClass("inputreadonly");
					$input.each(function(){
						$(this).removeAttr("readonly");
					});
				}
			}
		}
		
		/**
		 * 更新标签内容
		 * @param newLabel - 新标签内容
		 */
		function changeLabel(newLabel) {
			$thisObj.find("#" + options.id + "labeltd").find("span.txbxlabeltx").html(html2Str(newLabel));
		}

		function setVisible() {
			
		}

		/**
		 * 显示/隐藏异常标记
		 * */
		function showOrHideErrorTag() {
			if(isOk) { //必须是在当前值正常的情况下才能隐藏异常标记
				$thisObj.find("#" + options.id + "errortag").hide();//隐藏异常标记	
					
			} else {//必须是在当前值不正常的情况下才能显示异常标记
				$thisObj.find("#" + options.id + "errortag").show();//显示异常标记	
			}
		}
		
		/**
		 * 当鼠标移动至错误信息图标时，显示错误提示信息
		 * */
		function onErrInputMouseEnter(event, jqObj) {
			TIP.hide();
			TIP.setBindCtrlId(options.id);
			TIP.showResetBtn(); //显示重置值按钮
			TIP.update(html2Str(options.errorMsg), false); //重置提示内容，更新提示控件绑定对象为当前控件
			TIP.mouseenter(event, jqObj);
		} 
		
		/**
		 * 当鼠标移出错误信息图标时，隐藏错误提示信息
		 * */
		function onErrInputMouseLeave(event) {
			TIP.mouseleave(event);
		}
		
		/**
		 * 回退文本框值
		 * */
		function resetVal() {
			if(isOk) { //无异常的不需回退值
				return;
			}
			parseClick(options.id, true, "rollback");
		}
		
		init();
		/* *****变更值后，后台需返回更新值方法，将新值缓存在oldValue中，并根据需要更新输入框的状态**** */
		return {
			resetVal: resetVal,
			loadData: loadData,
			onErrInputMouseEnter: onErrInputMouseEnter,
			onErrInputMouseLeave: onErrInputMouseLeave
		};
	}
})(jQuery);

/*
 * combobox控件
 * 下拉框显示控制
 * 对象方法：
 *     
 **/
;(function($){

	$.fn.combobox = function(options) {
		var combobox = $("#combobox");
		var $thisObj = this;
		var $input = $thisObj.find("#" + options.id + "combo");
		//var $options = $thisObj.find("#" + options.id + "options");
		var $options = $("#combobox");
		var	$select = $thisObj.find("#" + options.id + "_select");
		var isOpen = false;
		var timer = -1;
		var isTimerRunning = false; //是否已启动关闭下拉的定时
		//var isFixed = false; //如果当前鼠标聚焦在输入框内，下拉内容将会被固定
		function init() {
			var width = $input.width();
			width = width + 1;
			$input.width(width);
			width = width + $select.width();
			$options.width(width + 4);
			
			//绑定事件
			$input.bind("change", function() {
				setChangedCtrlVal(options.id, this.value);//缓存输入框变更的值 eventmgr.js
			}).bind("focus", function(event) {
				if(options.id != getChangedCtrlId()) { //确认不要在同一个控件内操作
					if(options.isInCell) {
						if(options.isReadonly){
							stopBubble(event.originalEvent); //阻止点击事件冒泡，当作为表格元素时，必须有此控制
						}
					}
				}
				setTimeout(onfocusInput, 10);
				
			}).bind("mouseenter", function(event){
				if(!options.isOk) {
					onErrInputMouseEnter(event);
				}							
			}).bind("mouseleave", function(event){
				if(!options.isOk) {
					onErrInputMouseLeave(event);
				}							
			});	
			
			$options.bind("blur", function(event) {	
				clearTimeout(timer);
				isTimerRunning = true;
				//isFixed = false;
				timer = setTimeout(comboboxHide, 200);
			});
			
			$select.bind("click", function(event) {
				if(options.id != getChangedCtrlId()) { //确认不要在同一个控件内操作
					if(options.isInCell) {
						if(options.isReadonly){
							stopBubble(event.originalEvent); //阻止点击事件冒泡，当作为表格元素时，必须有此控制
						}
					}
				}
				setTimeout(onclickSelect, 100);
			});
			
			if(!options.isFilter && !options.isInCell && !options.isToolbarComb) { //注册字段提示事件
				var $titleContent = $thisObj.find(".txbxlabeltx:first");
				$titleContent.bind("dblclick", function(event){
					$titleContent.bind("mouseleave", function(event){
						leaveField(event);	
						$titleContent.unbind("mouseleave");						
					});	
					tipField(event, $titleContent);
				});
			}
		}
		
		/* 字段提示 */
		function tipField(event, jqTarget) {
			TIP.hide();
			TIP.setBindCtrlId(options.id);
			TIP.hideResetBtn(); //隐藏重置值按钮;
			TIP.update(getFieldTipCont(options), false); //重置提示内容，更新提示控件绑定对象为当前控件
			TIP.mouseenter(event, jqTarget);
		} 
		
		/**
		 * 当鼠标移出字段时，隐藏提示信息
		 * */
		function leaveField(event) {
			TIP.mouseleave(event, $thisObj);
		} 
		
		/* 重新加载下拉 */
		function reloadOptions() {
			if(!options.isReadonly) {
				if(options.isOk) {
					$input.parent().addClass("combfocus");
					$select.parent().addClass("combfocus");
				}
			}
			if(options.id != getChangedCtrlId()) { //确认不要在同一个控件内操作
				if(options.isInCell) {
					if(options.isReadonly){
						parseClick(options.id, true, 'loadOptions');
						isOpen = true;
					}
				} else {
					if(options.isReadonly){
						parseClick(options.id, false);
					} else {
						parseClick(options.id, true, 'loadOptions');
						isOpen = true;
					}
				}
			}
		}
		
		/* 聚焦输入框后触发此方法 */
		function onfocusInput() {
			clearTimeout(timer);
			isTimerRunning = false;
			//isFixed = true;
			if(!isOpen) {
				reloadOptions(); //重新加载下拉
			}
		}
		
		/* 点击下拉按钮后触发此方法 */
		function onclickSelect() {
			clearTimeout(timer);
			isTimerRunning = false;
			if(!isOpen) {
				reloadOptions(); //重新加载下拉
			}else{
				comboboxHide();
			}
		}

		/**
		 * 重新加载控件数据
		 * 根据返回值中的类型属性，更新文本框状态
		 * @param rtn - <type>ok or error</type><val></val><msg></msg><required></required><readonly></readonly>
		 * */
		function loadData(rtn) {
			if("ok" == getTagInnerHtmlStr(rtn, "type")) {
				if(!options.isOk) {
					options.isOk = true;
					options.errorMsg = "";
					$input.parent().removeClass("inputerror");
					$select.parent().removeClass("inputerror");
				}
				
			} else {
				if(options.isOk) {
					options.isOk = false;
					$input.parent().addClass("inputerror");
					$select.parent().addClass("inputerror");
				}
				options.errorMsg = getTagInnerHtmlStr(rtn, "msg");
				
			}
			
			var newVal = getTagInnerHtmlStr(rtn, "val");
			$input.val((undef(newVal)? "": newVal));
			if("true" == getTagInnerHtmlStr(rtn, "required")) {
				$thisObj.find("#" + options.id + "required").show();
			} else {
				$thisObj.find("#" + options.id + "required").hide();
			}
			
			if("true" == getTagInnerHtmlStr(rtn, "readonly")) {
				options.isReadonly = true;
				$input.parent().addClass("inputreadonly");
				$input.attr("readonly", "readonly");
			} else {
				options.isReadonly = false;
				$input.parent().removeClass("inputreadonly");
				//$input.removeAttr("readonly");
			}
		}
		
		/**
		 * 当鼠标移动至错误信息图标时，显示错误提示信息
		 * */
		function onErrInputMouseEnter(event) {
			TIP.hide();
			TIP.setBindCtrlId(options.id);
			TIP.showResetBtn(); //显示重置值按钮
			TIP.update(html2Str(options.errorMsg), false); //重置提示内容，更新提示控件绑定对象为当前控件
			TIP.mouseenter(event, $input);
		} 
		
		/**
		 * 当鼠标移出错误信息图标时，隐藏错误提示信息
		 * */
		function onErrInputMouseLeave(event) {
			TIP.mouseleave(event, $input);
		} 
		
		/* 加载下拉选项 */
		function loadOptions(val) {
			var list = eval('(' + val + ')');
			var ops = "";
			if (!options.isRequired && !options.isToolbarComb) {
				ops += "<div onclick=\"callCtrlMthd('"
						+ options.id
						+ "', 'selectOption', ['setValue', ''])\" onmousemove='hoverDom(this)' onmouseout='unhoverDom(this)'>&nbsp;</div>";
			}
			if (list.length > 0) {
				for ( var i = 0; i < list.length; i++) {
					var item = list[i];
					ops += "<div title=\""
							+ html2Str(item['TEXT'])
							+ "\" onclick=\"callCtrlMthd('"
							+ options.id
							+ "', 'selectOption', ['"
							+ item["EVENT"]
							+ "', '"
							+ item['EVENTVALUE'].replace(/'/g, "\\'").replace(
									/[&"\<\>]/g, function(c) {
										switch (c) {
										case "&":
											return "&amp;";
										case '"':
											return "&quot;";
										case "<":
											return "&lt;";
										case ">":
											return "&gt;";
										}
									})
							+ "'])\" "
							+ " onmousemove='hoverDom(this)' onmouseout='unhoverDom(this)'>"
							+ html2Str(item['TEXT']) + "</div>";
				}
			}
			$input.focus();

			if (!options.isOk) {
				$input.parent().removeClass("combfocus");
				$select.parent().removeClass("combfocus");
			}

			if (ops != "") {
				$options.html(ops);
				if (ctrlMgrSys.getDialogCount() > 0) {
					$options.hide();
					$options.css('zIndex', artDialog.defaults.zIndex + 1);
				}

				showAndPosOpts(); // 显示并定位下拉选项

				$(document).bind("mouseup", function(event) {
					/*if (!isTimerRunning && !isFixed) {
						isTimerRunning = true;
						timer = setTimeout(comboboxHide, 200);
					}*/
					if($(event.target).is('input')){
						return;
					}
					
					if(!isTimerRunning ) {
						isTimerRunning = true;
						timer = setTimeout(comboboxHide, 200);
					}
				});
			}
		}
		
		/* 显示并定位下拉选项 */
		function showAndPosOpts() {
			$options.show();
			$options.focus();
			var inputOffset = $input.offset();
			var inputSpaceHeight = inputOffset.top + $input.height();
			var optsHeight = $options.height();
			var winHeight = $(window).height();
			
			if((optsHeight + 1) > (winHeight - inputSpaceHeight) && inputOffset.top > (winHeight - inputSpaceHeight)) {
				$options.offset({top: (inputOffset.top - optsHeight - 3), left: inputOffset.left});
			} else {
				$options.offset({top: (inputSpaceHeight + 3), left: inputOffset.left});
			}
		}
		
		/* 选中下拉 */
		function selectOption(event, eventValue) {
			if(!options.isToolbarComb) {
				$input.val(eventValue);
			}
			parseClick(options.id, true, event, eventValue);
			comboboxHide();
		}
		
		/* 关闭并隐藏下拉，移除当前聚焦状态 */
		function comboboxHide() {
			$input.parent().removeClass("combfocus");
			$select.parent().removeClass("combfocus");
			$options.hide();
			/*$(document).unbind("mouseup");*/
			$(document).not(".gw_designer_subctrl").unbind("mouseup");
			isOpen = false;
			isTimerRunning = false;
		}
		
		/**
		 * 更新标签内容
		 * @param newLabel - 新标签内容
		 */
		function changeLabel(newLabel) {
			$thisObj.find("#" + options.id + "labeltd").find("span.txbxlabeltx").html(html2Str(newLabel));
		}
		
		/**
		 * 回退文本框值
		 * */
		function resetVal() {
			if(options.isOk) { //无异常的不需回退值
				return;
			}
			parseClick(options.id, true, "rollback");
		}
		
		init();
		
		return {
			loadData: loadData,
			loadOptions: loadOptions,
			onErrInputMouseEnter: onErrInputMouseEnter,
			onErrInputMouseLeave: onErrInputMouseLeave,
			resetVal: resetVal,
			selectOption: selectOption
		};
	};
})(jQuery);

/*
 * menu控件
 * 菜单显示控制
 * 对象方法：
 *     
 **/
;(function($){



	$.fn.menu = function(options){
		var topMenu;
		var subMenus = [];
		var previtemid;
		var caller = this;
		var options = options;
		topMenu = new Menu(caller, options);
		if(options.type=="async"){
			//topMenu = new Menu(caller, options);
			topMenu.refresh(caller,options);
			topMenu.showMenu(caller,options);
			$(this).click(function(){
				hideAllMenu();
			});
		}else{
			$(this).click(function(){
				// btn_label 如果置灰不触发显示或隐藏下拉菜单事件
				if(!$(this).find(".btn_label").hasClass('btngray')){
					hideAllMenu();
					if (topMenu.menuOpen == false) {
						topMenu.refresh(caller,options);
						topMenu.showMenu();
					}else {
						topMenu.hideMenu();
					}
					return false;
				}
			});	
		}
		
		function hideAllMenu(){
			if (topMenu.menuOpen){
				topMenu.hideMenu();
				hideSubMenu();
				topMenu.menuOpen = false;
			}
		}
		function hideSubMenu(){
			$.each(subMenus, function(i){
				if (subMenus[i].menuOpen) {
					subMenus[i].hideMenu();
				}
			});
		}
		function Menu(caller, options){
			this.menuOpen = false;
			this.menuExists = false;
			this.caller = caller;
			var menuobj = this;
			var menu = options.menu;
			var menuzIndex = 0;
			//var zindex = parseInt(menu.css("z-index"));
			this.showMenu = function (){
			    if(options.menu.html()==""){
			    	return;
			    }
				this.menuOpen = true;
				if(menu.hasClass("gw_menu-submenu")){
					//hideSubMenu();
					var menuLeft = caller.position().left+caller.width();
					var menuTop  = caller.position().top;
					var domwidth = $(document).width();
					var menumaxleft = (parseInt(caller.offset().left)+parseInt(caller.width())+150);
					if(menumaxleft>=domwidth){
						menuLeft = 0-(caller.position().left+caller.width());
						menuTop = caller.position().top;
					}
				}else{
					//hideAllMenu();
					var menuLeft = caller.offset().left;
					var menuTop  = caller.offset().top+caller.height();
					var domwidth = $(document).width();
					var menumaxleft = menuLeft+150;
					var isOverflow = false;
					if(menumaxleft>=domwidth){
						menuLeft = menuLeft+caller.width()-150;
						isOverflow = true;
					}
					//console.log("menuLeft="+menuLeft+",domwidth="+domwidth+",menumaxleft="+menumaxleft);
					$(document).unbind("click", hideAllMenu); 
					$(document).unbind("keydown", hideAllMenu); 
					$(document).click(hideAllMenu);
					$(document).keydown(hideAllMenu);
					
					if(ctrlMgrSys.getDialogCount()>0){
						menuzIndex = artDialog.defaults.zIndex+1;
					}
					menu.css("zIndex",menuzIndex+100);
					if(options.owner){
						if(options.owner==="textbox"){
							//menuTop = menuTop + 5;
							if(isOverflow) {
								menuLeft = menuLeft - 5;
							} else {
								menuLeft = menuLeft + 5;
							}
						}
					}
				}
				menu.css({"left":menuLeft,"top":menuTop});
				/*var mileft = menuLeft;
				var mitop = 5;
				menu.children(".gw_menu-itembody").each(function(){
					$(this).css({"top":mitop});
					mitop = mitop + $(this).height();
				});
				menu.height(mitop+5);
				menu.width(155);*/
				menu.show();
				menu.children().children(".gw_menu-item").each(function(){
					if($(this).children(".gw_menu-rightarrow").length){
						var submenu = $(this).parent().children(".gw_menu-submenu");
						var subm = new Menu($(this), {"menu":submenu});	
						subMenus.push(subm);
						$(this).hover(function(){
							$(this).addClass("gw_menu-active");
							submenu.parent().css("zIndex",menuzIndex+200);
							submenu.css("zIndex",menuzIndex+210);
							previtemid = $(this).attr("id");
							hideSubMenu();
							if(subm.menuOpen == false){
								subm.showMenu();
							}
						},function(e){
							$(this).removeClass("gw_menu-active");
							$(this).parent().css("zIndex",menuzIndex+100);
							submenu.css("zIndex",menuzIndex+110);
							/*if(submenu.offset()){
								if (!((e.pageX >= parseInt(submenu.offset().left)) && submenu.attr("pid")==previtemid)) {
									subm.hideMenu();
								}
							}*/
						});
					}else{
						$(this).click(function(){
							parseClick($(this).parent().attr("id"),true);
						});
						$(this).hover(function(){
							$(this).addClass("gw_menu-active");
							if(menu.hasClass("gw_menu-submenu")){//如果是子菜单
								var submenupid = $(this).parent().parent().attr("pid");
								if(submenupid!=previtemid){
									hideSubMenu();
								}else{
									caller.addClass("gw_menu-active");
									menu.parent().css("zIndex",menuzIndex+200);
									menu.css("zIndex",menuzIndex+210);
								}
							}else{
								hideSubMenu();

							}
						},function(e){
							$(this).removeClass("gw_menu-active");
						});
					}
				});
			};
			this.hideMenu = function(){
				if(!menu.hasClass("gw_menu-submenu")){
					//$(document).unbind('click');
					//$(document).unbind('keydown');	
					//@swyang menu按钮菜单清除附件按钮事件问题
					$(document).unbind("click", hideAllMenu); 
					$(document).unbind("keydown", hideAllMenu); 
				}
				menu.hide();
				this.menuOpen = false;
			};
			this.refresh = function(cl,options){
				$("#pagemenu").empty();
				$("#pagemenu").html(options.menu.html());
				menu = $("#pagemenu");
				//zindex = parseInt(menu.css("z-index"));
				caller = cl;
				menu.hover(function(e){
				},function(){
					if(menu.hasClass("gw_menu-submenu")){
						menuobj.hideMenu();
					}else{
						hideAllMenu();
					}
				});
			};
			menu.hover(function(e){
			},function(){
				if(!menu.hasClass("gw_menu-submenu")){


					hideAllMenu();
				}
			});
		}
	};
	





})(jQuery);


/*
 * menu控件
 * 右键菜单显示控制
 * 对象方法：
 *     
 **/
;(function($){
	var topMenu;
	var subMenus = [];
	$.fn.rightmenu = function(options){
		var caller = this;
		var options = options;
		topMenu = new Menu(caller, options);
		topMenu.refresh(caller,options);
		topMenu.updateMenu(caller,options);
		topMenu.showRightMenu(caller,options);
		$(this).click(function(){
			hideAllMenu();
		});
	};
	
	function hideAllMenu(){
		topMenu.hideMenu();
		hideSubMenu();
	}
	function hideSubMenu(){
		$.each(subMenus, function(i){
			subMenus[i].hideMenu();
		});
	}
	function checkDisable(options,menuitem){
		var disable = false;
		if(options.mjson){
			$.each($.parseJSON(options.mjson),function(idx,item){
				if(menuitem.parent().attr("id")==item.id && item.disable=="true"){
					disable = true;
				}
			});
		}
		return disable;
	}
	
	function Menu(caller, options){
		var menuobj = this;
		var menu = options.menu;
		var menuzIndex = 0;
		this.showRightMenu = function (){
			if(menu.hasClass("gw_menu-submenu")){
				//hideSubMenu();
				var menuLeft = caller.position().left+caller.width();
				var menuTop  = caller.position().top;
				var domwidth = $(document).width();
				var domheight = $(document).height();
				var menumaxleft = (parseInt(caller.offset().left)+parseInt(caller.width())+150);
				var menumaxtop = (parseInt(caller.offset().top)+parseInt(menu.height()));
				if(menumaxleft>=domwidth){
					menuLeft = 0-(caller.position().left+caller.width());
					menuTop = caller.position().top;
				}
				if(menumaxtop>domheight){
					menuTop = caller.position().top - (menumaxtop-domheight) - 50;
				}
				//alert("-1-"+menu.height()+"-"+menuTop+":"+menumaxtop+":"+domheight);
				menu.css({"left":menuLeft,"top":menuTop});
			}else{
				//hideAllMenu();
				var menuLeft = parseInt(options.X);
				var menuTop  = parseInt(options.Y);
				var domwidth = $(document).width();
				var domheight = $(document).height();
				var menumaxleft = menuLeft+150;
				var menumaxtop = (parseInt(caller.offset().top)+parseInt(menu.height()));
				if(menumaxleft>=domwidth){
					menuLeft = menuLeft-150;
				}
				if(menumaxtop>domheight){
					menuTop = caller.position().top - (menumaxtop-domheight) - 50;
				}
				//alert("-2-"+menu.height()+"-"+menuTop+":"+menumaxtop+":"+domheight);
				menu.css({"left":menuLeft,"top":menuTop});
				$(document).click(hideAllMenu);
				$(document).keydown(hideAllMenu);
				if(ctrlMgrSys.getDialogCount()>0){
					menuzIndex = artDialog.defaults.zIndex+1;
				}
				menu.css("zIndex",menuzIndex+300);
			}
			menu.children().children(".gw_menu-item").each(function(){
				if(checkDisable(options,$(this))){
					$(this).addClass("gw_menu-item-disabled");
				}
				if($(this).children(".gw_menu-rightarrow").length){
					var submenu = $(this).parent().children(".gw_menu-submenu");
					var subm = new Menu($(this), {"menu":submenu});	
					subMenus.push(subm);
					$(this).hover(function(){
						if(!checkDisable(options,$(this))){
							$(this).addClass("gw_menu-active");
						}
						submenu.parent().css("zIndex",menuzIndex+200);
						submenu.css("zIndex",menuzIndex+210);
						previtemid = $(this).attr("id");
						subm.showRightMenu();
					},function(e){
						if(!checkDisable(options,$(this))){
							$(this).removeClass("gw_menu-active");
						}
						$(this).parent().css("zIndex",menuzIndex+100);
						submenu.css("zIndex",menuzIndex+110);
						/*if(submenu.offset()){
							if (!((e.pageX >= parseInt(submenu.offset().left)) && submenu.attr("pid")==previtemid)) {
								subm.hideMenu();
							}
						}*/
					});
				}else{
					if(!checkDisable(options,$(this))){
						$(this).click(function(){
							if(options.preventDefault!="true"){//阻止默认的请求，为自己添加请求留处理接口
								parseClick($(this).parent().attr("id"),true);
							}
						});
					}
					$(this).hover(function(){
						if(!checkDisable(options,$(this))){
							$(this).addClass("gw_menu-active");
						}
						if(menu.hasClass("gw_menu-submenu")){
							var submenupid = $(this).parent().parent().attr("pid");
							if(submenupid!=previtemid){
								hideSubMenu();
							}else{
								menu.parent().css("zIndex",menuzIndex+200);
								menu.css("zIndex",menuzIndex+210);
							}
						}else{
							hideSubMenu();
						}
					},function(e){
						if(!checkDisable(options,$(this))){
							$(this).removeClass("gw_menu-active");
						}
					});
				}
			});
			menu.show();
		};
		this.hideMenu = function(){
			if(!menu.hasClass("gw_menu-submenu")){
				$(document).unbind('click');
				$(document).unbind('keydown');	
			}
			menu.hide();
		};
		this.refresh = function(cl,options){
			$("#pagemenu").empty();
			$("#pagemenu").html(options.menu.html());
			menu = $("#pagemenu");
		};
		this.updateMenu = function(cl,options){
			if(options.needupdate == "true"){
				$("#pagemenu").empty();
				var menujs = eval("("+options.mjson+")");
				var menuhtml = "";
				for(var i=0;i<menujs.length;i++){
					var cmenu = menujs[i];
					menuhtml+="<DIV id=\""+cmenu.id+"\" class=gw_menu-itembody>";
					menuhtml+="<DIV id=\""+cmenu.id+"_menuitem\" class=gw_menu-item>";
					menuhtml+="<DIV class=gw_menu-icon><IMG src=\""+cmenu.image+"\"> </DIV>";
					menuhtml+="<DIV class=gw_menu-text>&nbsp;"+cmenu.label+"</DIV>"
					menuhtml+="<DIV></DIV></DIV></DIV>";
				}
				$("#pagemenu").html(menuhtml);
				menu = $("#pagemenu");
			}
			
		};
		menu.click(function(){
			hideAllMenu();
		});
		menu.hover(function(e){
		},function(){
			if(menu.hasClass("gw_menu-submenu")){
				hideSubMenu();
			}
		});
	}
})(jQuery);

/*
 * recordimage控件
 * 记录图片显示控制
 * 对象方法：
 *     
 **/
;(function($){
	$.fn.recordimage = function(options) {
		var $thisObj = this;
		var $img = null;
		var $imgoper = null;
		var $imgContainer = null;
		var isUrlAddStr = true;
		function init() {
			$imgContainer = $thisObj.find("#" + options.id + "imgContainer");
			$img = $thisObj.find("#" + options.id + "img1");
			$imgoper = $thisObj.find('#' + options.id + "img_modify_div");
			if(options.isShowModify) {
				$img.bind("click", function() {
					//点击事件触发
					parseClick(options.id, true, "viewimage");
					$imgoper.hide();
				});
			}
			
			$thisObj.bind("mouseover", function() {
				if(options.isShowModify) {
					//显示图片变更按钮
					$imgoper.show().position({
						of: $img,
						my: "left top",
						at: "right top"
					});
				}
			});
			$thisObj.bind("mouseout", function() {
				//点击事件触发
				$imgoper.hide();
			});
			$imgoper.bind("click", function() {
				if(options.isShowModify) {
					//点击事件触发
					$imgoper.hide();
					parseClick(options.id, true, "itemimage");
				}
			});
		}
		
		/**
		 * 重新加载控件数据
		 * 根据返回值中的类型属性，更新文本框状态
		 * @param rtn - <showmodify>true or false</showmodify><label></label><cachedimagesrc></cachedimagesrc>
		 * */
		function loadData(rtn) {
			var newImgSrc = getTagInnerHtmlStr(rtn, "cachedimagesrc")
			reloadImg(newImgSrc); //更新图片
			
			options.isShowModify = ("true" == getTagInnerHtmlStr(rtn, "showmodify"));
			$thisObj.find("#" + options.id + "label").html(html2Str(getTagInnerHtmlStr(rtn, "label")));
		}
		
		/* 更新图片 */
		function reloadImg(newImgSrc) {
			if(undef(newImgSrc)) {
				newImgSrc = options.defaultImg; 
			} else {
				var end = "";
				if(isUrlAddStr) {
					end = end + ".jpg";
				} 
				isUrlAddStr = !isUrlAddStr;
				//newImgSrc = options.imagePath + newImgSrc + end;
				newImgSrc = options.imagePath + newImgSrc;
			}
			$img.remove();
			$imgContainer.html("");
			$img = $("<img />");
			$img.attr("src", newImgSrc+"?loginstamp="+LOGINSTAMP);
			$img.width(options.width);
			$img.height(options.height);
			if(options.isShowModify) {
				$img.bind("click", function() {
					//点击事件触发
					parseClick(options.id, true, "viewimage");
					$imgoper.hide();
				});
			}
			$imgContainer.append($img);
		}
		
		init();
		return {
			loadData: loadData
		};
	}
})(jQuery);

/*
 * button控件
 * 对象方法：
 *     
 **/
;
(function($) {
	$.fn.pushbutton = function(options) {
		var $thisObj = this;
		var $thisObjSel = $thisObj.find("#" + options.id + "_select");

		function init() {
			if (options.buttontype == "button") {
				$thisObj.find(".btn_label:first").click(function() {
					if (options.disabled) {
						parseClick(options.id, false);
					} else {
						parseClick(options.id, true);
					}
				});
				$thisObj.find(".btn_img:first").click(function() {
					if (options.disabled) {
						parseClick(options.id, false);
					} else {
						parseClick(options.id, true);
					}
				});
			} else {
				if (options.buttontype == "menu") {
					$thisObj.menu({
						menu : $("#" + options.id + "_menu")
					});
				} else {
					$thisObjSel.menu({
						menu : $("#" + options.id + "_menu")
					});
				}
				// 此处代码可能有问题
				if (!options.disabled) {
					$thisObj.find(".btn_label:first").click(function() {
						if (options.disabled) {
							parseClick(options.id, false);
						} else {
							parseClick(options.id, true);
						}
					});
					$thisObj.find(".btn_img:first").click(function() {
						if (options.disabled) {
							parseClick(options.id, false);
						} else {
							parseClick(options.id, true);
						}
					});
				}

			}
		}

		function refreshButton(rtn) {
			var disabled = getTagInnerHtmlStr(rtn, "disable");
			if (disabled == "true") {
				options.disabled = true;
				$thisObj.find(".btn_label:first").addClass("btngray");
			} else {
				options.disabled = false;
				$thisObj.find(".btn_label:first").removeClass("btngray");
			}
		}

		init();
		return {
			refreshButton : refreshButton
		}
	}
})(jQuery);

/**
*	startCenter
*/
;(function($){
	$.fn.startCenter = function(options) {
		var defaults = {
			id: "",
			subid:""
		}
		
		options = $.extend(defaults, options);
		var $thisObj = this;
		function init() {
			
			
		}
		
		function loadData(val) {
			
		}
		
		/**
		 *	删除对应的protal
		 */
		function deletePortal(id) {
			$portal =$thisObj.find("#" + options.id + "_" + id);
			ctrlMgrSys.remove(id); //移除缓存控件
			$portal.next(".blankline").remove();//移除后面的blankline
			$portal.remove(); //移除页面控件
		}
		/**
		 * 更新控件
		 * 此方法充当更新调度中心，由此方法去执行具体的更新内容
		 * */
		function update() {
			
		}
		
		init();
		this.update = update;
		this.loadData = loadData;
		this.deletePortal = deletePortal;
		/* *****变更值后，后台需返回更新值方法，将新值缓存在oldValue中，并根据需要更新输入框的状态**** */
		return this;
	}
})(jQuery);

/**
*	designer-canvas
*/
;(function($){
	$.fn.designerCanvas = function(options) {
		var defaults = {
			id: ""
		}
		
		options = $.extend(defaults, options);
		var $thisObj = this;
		var $treeobj;
		function init() {
			parseClick(options.id, true, "openXml");
			$treeobj = $thisObj.find("#" + options.id + "_contree");
			$treeobj.tree({
				onContextMenu:function(e, node){
					e.preventDefault();
					$treeobj.tree("select",node.target);
					
					
				},
				"onBeforeDrop":function(target, source, point){
	                  var tnode =$treeobj.tree("getNode",target);
	                  var roots = $treeobj.tree("getRoots");
	                  var tpnode = $treeobj.tree("getParent",target);
	                     if($.inArray(tnode, roots)>-1){
	                     	return false;
	                     }
	                    var flag = true;
	                     var pdata = {type:point,tid:tnode.id,tname:tnode.name,sid:source.id,sname:source.name,tpid:tpnode.id,tpname:tpnode.name};
	                     $.ajax({
	                     	url:'<%=basePath%>servlet/RequestMgr?method=movenode',
	                     	async:false,//发同步请求！
	                     	dataType:"json",
	                     	data:pdata,
	                     	success: function(data){
	                     		flag = data;
	                     	}
	                     });
	                     if(flag){
	                     	showMSG("xml结构变化成功！");
	                     }
	                    return flag; 
	            }
			});
		}
		
		function loadData(val) {
			$treeobj.tree("loadData",val);
		}
		
		function appendNode(){
			
		}
		function getChildren(){
			parseClick(options.id, true, "getChildren");
		}
		function createMenuItem(data){
			
		
		}
	
		/**
		 * 更新控件
		 * 此方法充当更新调度中心，由此方法去执行具体的更新内容
		 * */
		function update() {
			
		}
		
		init();
		this.update = update;
		this.loadData = loadData;
		this.appendNode = appendNode;
		/* *****变更值后，后台需返回更新值方法，将新值缓存在oldValue中，并根据需要更新输入框的状态**** */
		return this;
	}
})(jQuery);


/**
 *	基础数据控件（输入框之类）错误提示
 * */
function GWTip(options) {
	var defaults = {
		content: 		'[title]',	// content to display ('[title]', 'string', element, function(updateCallback){...}, jQuery)
		className:		'tip-yellow',	// class for the tips
		bgImageFrameSize:	10,		// size in pixels for the background-image (if set in CSS) frame around the inner content of the tip
		showTimeout:		500,		// timeout before showing the tip (in milliseconds 1000 == 1 second)
		hideTimeout:		100,		// timeout before hiding the tip
		timeOnScreen:		0,		// timeout before automatically hiding the tip after showing it (set to > 0 in order to activate)
		showOn:			'hover',	// handler for showing the tip ('hover', 'focus', 'none') - use 'none' to trigger it manually
		liveEvents:		false,		// use live events
		alignTo:		'cursor',	// align/position the tip relative to ('cursor', 'target')
		alignX:			'right',	// horizontal alignment for the tip relative to the mouse cursor or the target element
							// ('right', 'center', 'left', 'inner-left', 'inner-right') - 'inner-*' matter if alignTo:'target'
		alignY:			'bottom',		// vertical alignment for the tip relative to the mouse cursor or the target element
							// ('bottom', 'center', 'top', 'inner-bottom', 'inner-top') - 'inner-*' matter if alignTo:'target'
		offsetX:		-22,		// offset X pixels from the default position - doesn't matter if alignX:'center'
		offsetY:		18,		// offset Y pixels from the default position - doesn't matter if alignY:'center'
		keepInViewport:		true,		// reposition the tooltip if needed to make sure it always appears inside the viewport
		allowTipHover:		true,		// allow hovering the tip without hiding it onmouseout of the target - matters only if showOn:'hover'
		followCursor:		false,		// if the tip should follow the cursor - matters only if showOn:'hover' and alignTo:'cursor'
		fade: 			true,		// use fade animation
		slide: 			true,		// use slide animation
		slideOffset: 		8,		// slide animation offset
		showAniDuration: 	300,		// show animation duration - set to 0 if you don't want show animation
		hideAniDuration: 	300,		// hide animation duration - set to 0 if you don't want hide animation
		refreshAniDuration:	200		// refresh animation duration - set to 0 if you don't want animation when updating the tooltip asynchronously
	};

	var opts = $.extend({}, defaults, options);

	var self = this;

	var winTip = null,  //用于窗口大小变更后的尺寸调整
		reBgImage = /^url\(["']?([^"'\)]*)["']?\);?$/i,
		rePNG = /\.png$/i,
		ie6 = !!window.createPopup && document.documentElement.currentStyle.minWidth == 'undefined';

	var $tip = $(['<div class="',opts.className,'">',
			'<div class="tip-inner tip-bg-image"><div style="width:100%;height:16px;"><div class="tip_button_close" onclick="TIP.hide();"></div><div class="tip_button_reset" onclick="TIP.resetVal();"></div></div><div class="tipContent"></div><div style="width:100%;height:16px;"></div></div>',
			'<div class="tip-arrow tip-arrow-top tip-arrow-right tip-arrow-bottom tip-arrow-left"></div>',
		'</div>'].join('')).appendTo(document.body);
	var $arrow = $tip.find('div.tip-arrow');
	var $inner = $tip.find('div.tip-inner');
	var disabled = false;
	var tipContent = opts.content;
	var eventX;
	var eventY;
	var asyncAnimating;
	var opacity;
	var pos = {l: 0, t: 0, arrow: ''}
	var tipOuterW;
	var tipOuterH;
	var showTimeout;
	var hideTimeout;
	var bindCtrlId = ""; //当前消息绑定的控件的标识，服务于控件值回退
	
	init();
	
	function init() {
		if (opts.showOn != 'none') {
			if(opts.showOn == 'hover') {
				if (opts.allowTipHover) {
					$tip.hover($.proxy(clearTimeouts, self), $.proxy(mouseleave, self));
				}
			}
		}
	}

	function mouseenter(e, jqTarget) {
		if (disabled)
			return true;
		if(jqTarget && jqTarget.length > 0) {
			eventX = e.pageX;//
			eventY = jqTarget.offset().top + jqTarget.height() - 5;//eventY = e.pageY;//
		} else { //候补方案，容错处理
			eventX = e.pageX;
			eventY = e.pageY;
		}
		if (opts.showOn == 'focus')
			return true;

		showDelayed();
	}
	function mouseleave(e, jqTarget) {
		if (disabled || asyncAnimating && ($tip[0] === e.relatedTarget || jQuery.contains($tip[0], e.relatedTarget)))
			return true;

		if (opts.showOn == 'focus')
			return true;

		hideDelayed();
	}
	function mousemove(e) {
		if (disabled)
			return true;

		eventX = e.pageX;
		eventY = e.pageY;
		if (opts.followCursor && $tip.data('active')) {
			calcPos();
			$tip.css({left: pos.l, top: pos.t});
			if (pos.arrow)
				$arrow[0].className = 'tip-arrow tip-arrow-' + pos.arrow;
		}
	}
	function show() {
		if (disabled || $tip.data('active'))
			return;

		reset();
		update();

		// don't proceed if we didn't get any content in update() (e.g. the element has an empty title attribute)
		if (!tipContent)
			return;

		display();
		if (opts.timeOnScreen)
			hideDelayed(opts.timeOnScreen);
	}
	function showDelayed(timeout) {
		clearTimeouts();
		showTimeout = setTimeout($.proxy(show, self), typeof timeout == 'number' ? timeout : opts.showTimeout);
	}
	function hide() {
		if (disabled || !$tip.data('active'))
			return;

		display(true);
	}
	function hideDelayed(timeout) {
		clearTimeouts();
		hideTimeout = setTimeout($.proxy(hide, self), typeof timeout == 'number' ? timeout : opts.hideTimeout);
	}
	function reset() {
		$tip.queue([]).detach().css('visibility', 'hidden').data('active', false);
		hide();
		if (opts.fade)
			$tip.css('opacity', opacity);
		$arrow[0].className = 'tip-arrow tip-arrow-top tip-arrow-right tip-arrow-bottom tip-arrow-left';
		asyncAnimating = false;
	}
	function update(content, dontOverwriteOption) {
		if (disabled)
			return;

		var async = content !== undefined;
		if (async) {
			if (!dontOverwriteOption) {
				opts.content = content;
			}
			
			if (!$tip.data('active'))
				return;
		} else {
			content = opts.content;
		}

		$inner.find(".tipContent").empty().append(content);//$inner.empty().append(content);
		tipContent = content;

		refresh(async);
	}
	function refresh(async) {
		if (disabled)
			return;

		if (async) {
			if (!$tip.data('active'))
				return;
			// save current position as we will need to animate
			var currPos = {left: $tip.css('left'), top: $tip.css('top')};
		}

		// reset position to avoid text wrapping, etc.
		$tip.css({left: 0, top: 0}).appendTo(document.body);

		// save default opacity
		if (opacity === undefined)
			opacity = $tip.css('opacity');

		// check for images - this code is here (i.e. executed each time we show the tip and not on init) due to some browser inconsistencies
		var bgImage = $tip.css('background-image').match(reBgImage),
			arrow = $arrow.css('background-image').match(reBgImage);

		if (bgImage) {
			var bgImagePNG = rePNG.test(bgImage[1]);
			// fallback to background-color/padding/border in IE6 if a PNG is used
			if (ie6 && bgImagePNG) {
				$tip.css('background-image', 'none');
				$inner.css({margin: 0, border: 0, padding: 0});
				bgImage = bgImagePNG = false;
			} else {
				$tip.prepend('<table class="tip-table" border="0" cellpadding="0" cellspacing="0"><tr><td class="tip-top tip-bg-image" colspan="2"><span></span></td><td class="tip-right tip-bg-image" rowspan="2"><span></span></td></tr><tr><td class="tip-left tip-bg-image" rowspan="2"><span></span></td><td></td></tr><tr><td class="tip-bottom tip-bg-image" colspan="2"><span></span></td></tr></table>')
					.css({border: 0, padding: 0, 'background-image': 'none', 'background-color': 'transparent'})
					.find('.tip-bg-image').css('background-image', 'url("' + bgImage[1] +'")').end()
					.find('td').eq(3).append($inner);
			}
			// disable fade effect in IE due to Alpha filter + translucent PNG issue
			if (bgImagePNG && !$.support.opacity)
				opts.fade = false;
		}
		// IE arrow fixes
		if (arrow && !$.support.opacity) {
			// disable arrow in IE6 if using a PNG
			if (ie6 && rePNG.test(arrow[1])) {
				arrow = false;
				$arrow.css('background-image', 'none');
			}
			// disable fade effect in IE due to Alpha filter + translucent PNG issue
			opts.fade = false;
		}

		var $table = $tip.find('> table.tip-table');
		if (ie6) {
			// fix min/max-width in IE6
			$tip[0].style.width = '';
			$table.width('auto').find('td').eq(3).width('auto');
			var tipW = $tip.width(),
				minW = parseInt($tip.css('min-width')),
				maxW = parseInt($tip.css('max-width'));
			if (!isNaN(minW) && tipW < minW)
				tipW = minW;
			else if (!isNaN(maxW) && tipW > maxW)
				tipW = maxW;
			$tip.add($table).width(tipW).eq(0).find('td').eq(3).width('100%');
		} else if ($table[0]) {
			// fix the table width if we are using a background image
			// IE9, FF4 use float numbers for width/height so use getComputedStyle for them to avoid text wrapping
			// for details look at: http://vadikom.com/dailies/offsetwidth-offsetheight-useless-in-ie9-firefox4/
			$table.width('auto').find('td').eq(3).width('auto').end().end().width(document.defaultView && document.defaultView.getComputedStyle && parseFloat(document.defaultView.getComputedStyle($tip[0], null).width) || $tip.width()).find('td').eq(3).width('100%');
		}
		tipOuterW = $tip.outerWidth();
		tipOuterH = $tip.outerHeight();

		calcPos();

		// position and show the arrow image
		if (arrow && pos.arrow) {
			$arrow[0].className = 'tip-arrow tip-arrow-' + pos.arrow;
			$arrow.css('visibility', 'inherit');
		}

		if (async && opts.refreshAniDuration) {
			asyncAnimating = true;
			$tip.css(currPos).animate({left: pos.l, top: pos.t}, opts.refreshAniDuration, function() { asyncAnimating = false; });
		} else {
			$tip.css({left: pos.l, top: pos.t});
		}
		$arrow.css('margin-left', eventX-pos.l);
	}
	function display(hide) {
		var active = $tip.data('active');
		if (active && !hide || !active && hide)
			return;

		$tip.stop();
		if ((opts.slide && pos.arrow || opts.fade) && (hide && opts.hideAniDuration || !hide && opts.showAniDuration)) {
			var from = {}, to = {};
			// this.pos.arrow is only undefined when alignX == alignY == 'center' and we don't need to slide in that rare case
			if (opts.slide && pos.arrow) {
				var prop, arr;
				if (pos.arrow == 'bottom' || pos.arrow == 'top') {
					prop = 'top';
					arr = 'bottom';
				} else {
					prop = 'left';
					arr = 'right';
				}
				var val = parseInt($tip.css(prop));
				from[prop] = val + (hide ? 0 : (pos.arrow == arr ? -opts.slideOffset : opts.slideOffset));
				to[prop] = val + (hide ? (pos.arrow == arr ? opts.slideOffset : -opts.slideOffset) : 0) + 'px';
			}
			if (opts.fade) {
				from.opacity = hide ? $tip.css('opacity') : 0;
				to.opacity = hide ? 0 : opacity;
			}
			$tip.css(from).animate(to, opts[hide ? 'hideAniDuration' : 'showAniDuration']);
		}
		hide ? $tip.queue($.proxy(reset, self)) : $tip.css('visibility', 'inherit');
		$tip.data('active', !active);
	}
	function disable() {
		reset();
		disabled = true;
	}
	function enable() {
		disabled = false;
	}
	function clearTimeouts() {
		if (showTimeout) {
			clearTimeout(showTimeout);
			showTimeout = 0;
		}
		if (hideTimeout) {
			clearTimeout(hideTimeout);
			hideTimeout = 0;
		}
	}
	function calcPos() {
		pos = {l: 0, t: 0, arrow: ''}
		var $win = $(window),
			win = {
				l: $win.scrollLeft(),
				t: $win.scrollTop(),
				w: $win.width(),
				h: $win.height()
			}, xL, xC, xR, yT, yC, yB;
		xL = xC = xR = eventX;
		yT = yC = yB = eventY;

		// keep in viewport and calc arrow position
		switch (opts.alignX) {
			case 'right':
			case 'inner-left':
				pos.l = xR + opts.offsetX;
				if (opts.keepInViewport && pos.l + tipOuterW > win.l + win.w)
					pos.l = win.l + win.w - tipOuterW;
				if (opts.alignX == 'right' || opts.alignY == 'center')
					pos.arrow = 'left';
				break;
			case 'center':
				pos.l = xC - Math.floor(tipOuterW / 2);
				if (opts.keepInViewport) {
					if (pos.l + tipOuterW > win.l + win.w)
						pos.l = win.l + win.w - tipOuterW;
					else if (pos.l < win.l)
						pos.l = win.l;
				}
				break;
			default: // 'left' || 'inner-right'
				pos.l = xL - tipOuterW - opts.offsetX;
				if (opts.keepInViewport && pos.l < win.l)
					pos.l = win.l;
				if (opts.alignX == 'left' || opts.alignY == 'center')
					pos.arrow = 'right';
		}
		switch (opts.alignY) {
			case 'bottom':
			case 'inner-top':
				pos.t = yB + opts.offsetY;
				// 'left' and 'right' need priority for 'target'
				if (!pos.arrow || opts.alignTo == 'cursor')
					pos.arrow = 'top';
				if (opts.keepInViewport && pos.t + tipOuterH > win.t + win.h) {
					pos.t = yT - tipOuterH - opts.offsetY;
					if (pos.arrow == 'top')
						pos.arrow = 'bottom';
				}
				break;
			case 'center':
				pos.t = yC - Math.floor(tipOuterH / 2);
				if (opts.keepInViewport) {
					if (pos.t + tipOuterH > win.t + win.h)
						pos.t = win.t + win.h - tipOuterH;
					else if (pos.t < win.t)
						pos.t = win.t;
				}
				break;
			default: // 'top' || 'inner-bottom'
				pos.t = yT - tipOuterH - opts.offsetY;
				// 'left' and 'right' need priority for 'target'
				if (!pos.arrow || opts.alignTo == 'cursor')
					pos.arrow = 'bottom';
				if (opts.keepInViewport && pos.t < win.t) {
					pos.t = yB + opts.offsetY;
					if (pos.arrow == 'bottom')
						pos.arrow = 'top';
				}
		}
	}
	
	/*
	 * 更新绑定控件标识
	 * */
	function setBindCtrlId(ctrlId) {
		bindCtrlId = ctrlId;
	}
	
	/*
	 * 重置当前控件值
	 * */
	function resetVal() {
		if(!undef(bindCtrlId)) { //当前消息绑定在具体的控件上
			hide();//隐藏当前提示信息
			callCtrlMthd(bindCtrlId, "resetVal", []); //调用控件重置值方法，“resetVal”为基础数据控件固定重置值方法
		}
	}
	
	/* 隐藏重置值按钮（显示字段提示时调用） */
	function hideResetBtn() {
		$tip.find(".tip_button_reset:first").hide();
	}
	
	/* 显示重置值按钮（显示错误提示时调用） */
	function showResetBtn() {
		$tip.find(".tip_button_reset:first").show();
	}

	// make sure the tips' position is updated on resize
	function handleWindowResize() {
		refresh(true);
	}
	$(window).resize(handleWindowResize);

	var rtnObj = {
		mouseenter: mouseenter,
		mouseleave: mouseleave,
		refresh: refresh,
		show: show,
		hide: hide,
		reset: reset,
		update: update,
		resetVal: resetVal,
		setBindCtrlId: setBindCtrlId,
		hideResetBtn: hideResetBtn,
		showResetBtn: showResetBtn
	};
	winTip = rtnObj; //全局缓存，用于窗口大小变更后的尺寸调整
	return rtnObj;
}

/**
*	container
*   容器类控件  如 propertiesContainer   designer_canvas  previewContainer
*/
;(function($){
	$.fn.container = function(options) {
		var defaults = {
			id: ""
		}
		
		options = $.extend(defaults, options);
		var $thisObj = this;
		
		function init() {
			var cid = "#"+options.id+"_container";
			$(cid).closest(".tabcenter.ctnscroll").scroll(function(){
				var stop = $(this).offset().top+100;
				if($(this).scrollTop()>40){
					$(cid).offset({top:stop-80});
				}else{
					$(cid).offset({top:stop});
				}
				
			});
		}
		
		function loadData(val) {
			$thisObj.html(val);
			var cid = "#"+options.id+"_container";
			var tab = $(cid).closest(".tabcenter.ctnscroll");
			if(!undef(tab.offset())){
				var stop = tab.offset().top+100;
				if($(tab).scrollTop()>10){
					stop = tab.offset().top+20
				}
				//console.log("--------"+$(tab).scrollTop());
				$(cid).offset({top:stop});
			}
		}
		
		function clearContainer(){
			$thisObj.html("");
			$('#'+options.id+"_subctrlsCont").html("");
		}
		
		function loadSubCtrls(data){
			if(data != "" && jQuery.type(data) === "string"){
				data = eval("("+data+")");
			}
			var ctrlhtml = "";
			if(data.length>0){
				for(var i=0;i<data.length;i++){
					var cmenu = data[i];
					ctrlhtml+="<div class=\"gwsubctrl gwdrag gwdesignersub\" eid='' etype='"+cmenu.ctrl+"' onmousemove=\"hoverDom(this)\" onmouseout=\"unhoverDom(this)\"  title=\""+cmenu.label+"\">";
					ctrlhtml+="<img src=\""+cmenu.image+"\" />";
					ctrlhtml+="</div>";
				}
			}
			$('#'+options.id+"_subctrlsCont").html(ctrlhtml);	
		}
		
		/**
		 * 更新控件
		 * 此方法充当更新调度中心，由此方法去执行具体的更新内容
		 * */
		function update() {
		}
		
		function deleteControl(data){
			var id = getTagInnerHtmlStr(data, "id");
			var dobj = $("[exid='"+id+"']");
			if(dobj){
				dobj.remove();
			}
		}
		//用于更新局部控件信息，如需更新全局  将调用loaddata
		function updateControl(data){
			var id = getTagInnerHtmlStr(data, "id");
			var newhtml = getTagInnerHtmlStr(data, "newhtml");
			var dobj = $("[exid='"+id+"']");
			if(dobj){
				if(dobj.attr("etype") == "sectioncol"){ //部分列稍有特殊，请关注ftl
					dobj.html(newhtml);
				} else {
					dobj.replaceWith(newhtml);
				}
			}
		}
		
		init();
		this.update = update;
		this.loadData = loadData;
		this.deleteControl = deleteControl;
		this.clearContainer = clearContainer;
		this.loadSubCtrls = loadSubCtrls;
		this.updateControl = updateControl;
		return this;
	}
})(jQuery);

/**
*	container
*   容器类控件  如 subCtrlToolbar-->propertiesview 
*/
;(function($){
	$.fn.subCtrlToolbar = function(options) {
		var SCROLL_WIDTH = 0; // 滚动条宽度。在resize后初始化
		var srcollWidth = 0;
		var $thisObj = this;
		var timeoutId = -1;
		
		function init() {
			$("#" + options.id + "_moveLeft").click(scrollTabLeft);
			$("#" + options.id + "_moveRight").click(scrollTabRight);
			
			resize();
			$(window).resize(resize);
		}
		
		function resize() {
			if(timeoutId) {
				clearTimeout(timeoutId);			
			}
			timeoutId = setTimeout(delayResize, 300);
		}
		
		/* 
		 * 窗口大小变化后，用于刷新tabgroup的尺寸 
		 * 此操作只限制于顶层标签页
		 * */
		function delayResize() {
			var toobarWidth = $('#'+options.id+"_subCtrlToolbar").width();
			var $imgs = $('#'+options.id+"_ctrlImgs");
			var imgsWidth = $imgs.find("table").width();
			SCROLL_WIDTH = imgsWidth - toobarWidth;
			if(SCROLL_WIDTH <= 0) {
				$("#" + options.id + "_moveLeft").hide();
				$("#" + options.id + "_moveRight").hide();
				$imgs.removeClass("scrolling");
				$imgs.css({"width": "100%"});
				$imgs.css({"margin-left": "0px"});
			} else {
				SCROLL_WIDTH = imgsWidth - toobarWidth + 2 * 20;
				$("#" + options.id + "_moveLeft").show();
				$("#" + options.id + "_moveRight").show();
				$imgs.css({"width": (toobarWidth - 2 * 20) + "px"});
				$imgs.css({"margin-left": "20px"});
			}
		}
		
		function clearContainer(){
			$('#'+options.id+"_ctrlImgs").html("");
		}
		
		function loadSubCtrls(data){
			if(data != "" && jQuery.type(data) === "string"){
				data = eval("("+data+")");
			}
			var ctrlhtml = "";
			if(data.length>0){
				ctrlhtml = "<table cellspacing=\"0\" cellpadding=\"0\"><tr>";
				for(var i=0;i<data.length;i++){
					var cmenu = data[i];
					ctrlhtml+="<td><div class=\"gwsubctrl gwdrag gwdesignersub\" eid='' etype='"+cmenu.ctrl+"' onmousemove=\"hoverDom(this)\" onmouseout=\"unhoverDom(this)\"  title=\""+cmenu.label+"\">";
					ctrlhtml+="<img src=\""+cmenu.image+"\" />";
					ctrlhtml+="</div></td>";
				}
				ctrlhtml += "</tr></table>";
			}
			$('#'+options.id+"_ctrlImgs").html(ctrlhtml);	
			delayResize()
		}
		
		
		/* 后移 */
		function scrollTabLeft() {
			srcollWidth = srcollWidth - 80;
			if(srcollWidth < 0) {
				srcollWidth = 0;
			}
			if(srcollWidth > SCROLL_WIDTH) {
				srcollWidth = SCROLL_WIDTH;
			}
			$('#'+options.id+"_ctrlImgs").scrollLeft(srcollWidth);
			
		}

		/* 前移 */
		function scrollTabRight() {
			srcollWidth = srcollWidth + 80;
			if(srcollWidth < 0) {
				srcollWidth = 0;
			}
			if(srcollWidth > SCROLL_WIDTH) {
				srcollWidth = SCROLL_WIDTH;
			}
			$('#'+options.id+"_ctrlImgs").scrollLeft(srcollWidth);
		}
		
		init();
		this.clearContainer = clearContainer;
		this.loadSubCtrls = loadSubCtrls;
		return this;
	}
})(jQuery);

/**
*	container
*   容器类控件  如 propertiesContainer
*/
;(function($){
	$.fn.propertiesContainer = function(options) {
		var defaults = {
			id: ""
		}
		
		options = $.extend(defaults, options);
		var $thisObj = this;
		
		function init() {
		}
		
		function loadData(val) {
			$thisObj.html(val);
		}
		
		function clearContainer(){
			$thisObj.html("");
		}
		
		init();
		this.loadData = loadData;
		this.clearContainer = clearContainer;
		return this;
	}
})(jQuery);

/**
 *	应用程序界面预览容器previewContainer
 * */
;(function($){
	$.fn.previewContainer = function(options) {		
		var $thisObj = this;
		/* 重新加载整个预览内容 */
		function loadData(htmlStr) {
			$thisObj.html(htmlStr);
		}
		
		function clearContainer(){
			$thisObj.html("");
		}
		
		function loadSubCtrls(data){
			if(data != "" && jQuery.type(data) === "string"){
				data = eval("("+data+")");
			}
			var ctrlhtml = "";
			if(data.length>0){
				for(var i=0;i<data.length;i++){
					var cmenu = data[i];
					ctrlhtml+="<li class=\"gwsubctrl gwdrag gwdesignersub\" eid='' etype='"+cmenu.ctrl+"'>";
					ctrlhtml+="<table id=\"\" class=\"pushbtn pushbtn_table\" cellspacing=\"0\" cellpadding=\"0\">";
					ctrlhtml+="<tbody><tr><td class=\"btn_left\"></td><td class=\"btn_img\">";
					ctrlhtml+="<img src=\""+cmenu.image+"\"></td>";
					ctrlhtml+="<td class=\"btn_label \">"+cmenu.label+"</td>"
					ctrlhtml+="<td class=\"btn_right\"></td></tr></tbody></table></li>";
				}
			}
			$('#'+options.id+"_subctrlsCont").html(ctrlhtml);	
		}
		
		function deleteControl(data){
			var id = getTagInnerHtmlStr(data, "id");
			var dobj = $("[exid='"+id+"']");
			if(dobj){
				dobj.remove();
			}
		}
		//用于更新局部控件信息，如需更新全局  将调用loaddata
		function updateControl(data){
			var id = getTagInnerHtmlStr(data, "id");
			var newhtml = getTagInnerHtmlStr(data, "newhtml");
			var dobj = $("[exid='"+id+"']");
			if(dobj){
				if(dobj.attr("etype") == "tab_title") {
					$("#" + dobj.attr("eid") + "_paneltd").html(newhtml);
				} else if(dobj.attr("etype") == "sectioncol") { //部分列稍有特殊，请关注ftl
					dobj.html(newhtml);
				} else if(dobj.attr("etype") == "tabgroup") { //标签页组稍有特殊，请关注ftl
					dobj.parent().replaceWith(newhtml);
				} else {
					dobj.replaceWith(newhtml);
				}
			}
		}
		
		//用于更新局部控件信息，如需更新全局  将调用loaddata
		function updateTabLabel(data){
			var id = getTagInnerHtmlStr(data, "id");
			var label = getTagInnerHtmlStr(data, "label");
			var dobj = $("#"+id);
			if(dobj){
				dobj.find(".tabtitcenter:first").html("<div>"+label+"</div>");
			}
		}
		
		return {
			loadData: loadData,
			deleteControl: deleteControl,
			clearContainer: clearContainer,
			loadSubCtrls: loadSubCtrls,
			updateControl: updateControl,
			updateTabLabel: updateTabLabel
		};
	}
})(jQuery);


/**
 *  navbar
 *  系统主导航
 *  对象方法：
 *		  
 * */
function NavMenuJsonCache(navs) {
	var currModuleJson = navs; //当前模块下的应用导航json
	var commonAppJson = new Array(); //
	
	/*  
	 * 获取子层menuunit的json数据
	 * @param parId 父级标识
	 * @param isAppLink 父级是否为应用链接
	 * */
	function getSubUnitJson(parId, isAppLink) {
		if(isAppLink) { //获取当前应用链接的子层menuunit的json数据
			if(commonAppJson && commonAppJson.length > 0) {
				for(var i = 0, SIZE = commonAppJson.length; i < SIZE; i++) {
					var appJson = commonAppJson[i];
					if(appJson.id == parId) {
						return appJson.subs;
					}
				}
			}
			
			if(currModuleJson && currModuleJson.length > 0) {
				for(var i = 0, SIZE = currModuleJson.length; i < SIZE; i++) {
					var appJson = currModuleJson[i];
					if(appJson.id == parId) {
						return appJson.subs;
					}
				}
			}
		} else {
			if(commonAppJson && commonAppJson.length > 0) {
				for(var i = 0, SIZE = commonAppJson.length; i < SIZE; i++) {
					var appJson = commonAppJson[i];
					if(appJson.id == parId) {
						return appJson.subs;
					} else {
						var result = recureSearchJson(appJson.subs, parId);
						if(result && result.length > 0) {
							return result;
						}
					}
				}
			}
			
			if(currModuleJson && currModuleJson.length > 0) {
				for(var i = 0, SIZE = currModuleJson.length; i < SIZE; i++) {
					var appJson = currModuleJson[i];
					if(appJson.id == parId) {
						return appJson.subs;
					} else {
						var result = recureSearchJson(appJson.subs, parId);
						if(result && result.length > 0) {
							return result;
						}
					}
				}
			}
		}
		return [];
	}
	
	function recureSearchJson(links, parId) {
		if(links) {
			for(var i = 0, SIZE = links.length; i < SIZE; i++) {
				var app = links[i];
				if(app.id == parId) {
					return app.subs;
				} else {
					var result = recureSearchJson(app.subs, parId);
					if(result && result.length > 0) {
						return result;
					}
				}
			}
		}
		return null;
	}
	
	/* 添加应用链接子层json（动态加载的menuunits） */
	function addCommAppJson(appJson) {
		commonAppJson.push(appJson);
	}
	
	return {
		getSubUnitJson: getSubUnitJson,
		addCommAppJson: addCommAppJson	
	}
}

/*
 * 顶部应用导航控件
 * 内部控件
 * 对象方法：
 *     
 **/
function NavLinkMenu() {
	var $appMenuContainer = null; //menuunit的dom缓存区
	var menus = {}; //menuunit缓存队列
	/* 向menuunit缓存队列中添加新的menuunit */
	function put(id, obj) {
		menus[id] = obj;
	}
	/* 从menuunit缓存队列中移除指定标识的menuunit */
	function remove(id) {
		delete menus[id];
	}
	/* 从menuunit缓存队列中获取指定标识的menuunit */
	function get(id) {
		return menus[id];
	}
	
	/* 
	 * 加载应用链接子层menuunit的json数据 
	 * dataStr: <applinkid>GWC26</applinkid><applinkjson>{id:"ss",subs:[]}</applinkjson>
	 * */
	function loadSubs(dataStr) {
		var appLinkId = getTagInnerHtmlStr(dataStr, "applinkid");
		var appLinkJsons = getTagInnerHtmlStr(dataStr, "applinkjson");

		NAV_MENU_CACHE.addCommAppJson(eval("(" + appLinkJsons + ")"));
		dynamicActiveTarget(appLinkId, $("#" + appLinkId));
	}
	
	/** 
	 * 鼠标移入applink后，高亮目标（只受理applink hover） 
	 * @param id 目标标识
	 * @param domObj 目标dom对象
	 * */
	function hoverTarget(id, domObj) {
		var appLink = get(id);
		if(appLink) {
			appLink.active();

		} else {
			appLink = createMenuUnit(id, $(domObj), null, null, null);
			put(id, appLink);
			appLink.active();
		}
	}
	
	/** 
	 * 鼠标点击后，激活menu 
	 * @param id 目标标识
	 * @param domObj 目标dom对象
	 * @param type 目标类型是applink、menu、menuline
	 * @param unitId 目标是menuline时，为line所属单元id
	 * */
	function activeTarget(id, domObj, type, unitId) {
		if($appMenuContainer == null) {
			$appMenuContainer = $("#appmenu");
		}
		
		var $this = $(domObj);
		if(type == "applink") { //当前聚焦新的applink
			var appLink = get(id);
			if(appLink) {
				appLink.active();

			} else {
				appLink = createMenuUnit(id, $this, null, null, null);
				put(id, appLink);
				appLink.active();
			}
			
			if($this.hasClass("hassubs")) { //目标存在子层
				if(!get(id + "_mu")) {
					var $newMenuObj = buildMenuUnit(id, true);
					if($newMenuObj) { //前端已缓存子层menu的json
						var menuUnit = createMenuUnit(id + "_mu", $newMenuObj, null, id, $this);
						put(id + "_mu", menuUnit);
						menuUnit.show();
					} else { //前端尚无此应用链接的子层menu的json
						parseClick(id, true, "", "", "", true);
					}
				}
			} else { //无子层时，此applink为应用链接
				parseClick(id, true);
			}
			
		} else if(type == "menu") { //当前聚焦新的菜单单元
			var menu = get(id);
			if(menu) {
				menu.active();
			}

		} else if(type == "menuline") { //当前聚焦新的菜单行
			var menu = get(unitId);
			if(menu) {
				menu.active();
			}
			if($this.hasClass("hassubs")) { //目标必存在子层
				var subMenuUnit = get(id + "_mu");
				if(subMenuUnit) {
					subMenuUnit.activeByParent();
				} else {
					var $subMenuObj = buildMenuUnit(id, false);
					subMenuUnit = createMenuUnit(id + "_mu", $subMenuObj, unitId, id, $this);
		
					put(id + "_mu", subMenuUnit);
					subMenuUnit.show();
				}
				
			}
		}
	}
	
	/** 
	 * 加载applink的子层menuunit数据后，激活menu 
	 * @param id 目标标识
	 * @param domObj 目标dom对象
	 * @param type 目标类型是applink、menu、menuline
	 * @param unitId 目标是menuline时，为line所属单元id
	 * */
	function dynamicActiveTarget(id, jqObj) {
		if($appMenuContainer == null) {
			$appMenuContainer = $("#appmenu");
		}
		
		var appLink = get(id);
		if(appLink) {
			appLink.active();

		} else {
			appLink = createMenuUnit(id, jqObj, null, null, null);
			put(id, appLink);
			appLink.active();
		}
		
		if(!get(id + "_mu")) {
			var $newMenuObj = buildMenuUnit(id, true);
			if($newMenuObj) { //前端已缓存子层menu的json
				var menuUnit = createMenuUnit(id + "_mu", $newMenuObj, null, id, jqObj);
				put(id + "_mu", menuUnit);
				menuUnit.show();
			}
		}
		appLink.startTimer(1500); //处理未加载完成，鼠标移走的情况
	}
	
	/** 
	 * 鼠标移出后，释放menu的锁住状态 
	 * @param id 目标标识
	 * @param domObj 目标dom对象
	 * @param type 目标类型是applink、menu、menuline
	 * */
	function restTarget(id, domObj, type) {
		var menuTmp = null;
		if(type == "applink" || type == "menu") { //鼠标离开applink或menuunit
			menuTmp = get(id);
			if(menuTmp) {
				menuTmp.startTimer();
			}

		} else if(type == "menuline") { //鼠标离开menuline
			menuTmp = get(id + "_mu");
			if(menuTmp) {
				menuTmp.startTimer(50);
			}
		}
	}
	
	/* 
	 * 获取并创建新的menuunit，
	 * 1. 注册menuunit和其内部menuline事件 
	 * 2. 如果是通用applink，缓存中未存在其子层json时，还需调用后台请求，以查找到获取其子层menuunit数据
	 * @param parId 父级标识
	 * @param isAppLink 当前要查找的是否是applink的直接子层
	 * */ 
	function buildMenuUnit(parId, isAppLink) {
		var subLinkJson = NAV_MENU_CACHE.getSubUnitJson(parId, isAppLink);
		if(subLinkJson && subLinkJson.length > 0) {
			var meuuUnitStr = '<div class="menuunit ctnscroll" id="' + parId + '_mu">';
			for(var i = 0, SIZE = subLinkJson.length; i < SIZE; i++) {
				meuuUnitStr += '<div class="menuline ' + (isArrayNotEmpty(subLinkJson[i].subs)?'menuarrow hassubs':'')
									+ ((i==SIZE-1)?' lastmenuline':'') + '" id="' + subLinkJson[i].id + '"' 
									+ ' key="' + subLinkJson[i].key + '">';
				meuuUnitStr += '<table><tr><td class="menulinetext">' + subLinkJson[i].title + '</td><td class="menulineright"></td></tr></table>';
				meuuUnitStr += '</div>';
			}
			meuuUnitStr += '</div>'
			$appMenuContainer.append(meuuUnitStr);
			
			var $newMenuObj = $appMenuContainer.children("#" + parId + "_mu");
			$newMenuObj.bind("mouseenter", function(){
				activeTarget(parId + '_mu', this, 'menu');
			}).bind("mouseleave", function(){
				restTarget(parId + '_mu', this, 'menu');
			});
			$newMenuObj.children("div").each(function(){
				var that = this;
				var $that = $(this);
				$that.bind("mouseenter", function(){
					activeTarget(that.id, that, 'menuline', parId + "_mu");
					$that.addClass("menulinehover");
					
				}).bind("mouseleave", function(){
					restTarget(that.id, that, 'menuline');
					$that.removeClass("menulinehover");
					
				}).bind("click", function(){
					beforeMenuLineClick(that.id, $that, parId + "_mu");					
				});
			});
			return $newMenuObj;
		}
		return null;
	}
	
	/* menuline点击事件前 */
	function beforeMenuLineClick(lineId, jqObj, parMenuId) {
		if(jqObj.hasClass("hassubs")) {
			parseClick(lineId, false);
		} else {
			if(jqObj.attr("key") == APP_NAME) { //与当前应用重复
				parseClick(lineId, false);
			} else {
				parseClick(lineId, true);
			}
			var menuTmp = get(parMenuId);
			if(menuTmp) {
				menuTmp.startTimer(1);
			}
		}
	}
	
	/**
	 * 	menu单元
	 *	1. 包含applink和menu
	 *	@param paramId 目标标识：applink or menu
	 *	@param paramThis 目标jquery对象 
	 *	@param paramParUnitId 目标的父级所在menu单元的标识
	 *	@param paramParId 目标父级标识
	 *	@param paramPar 目标父级jquery对象
	 * */
	function createMenuUnit(paramId, paramThis, paramParUnitId, paramParId, paramPar) {
		var id;
		var $this = null;
		var parUnitId = null; 
		var parId;       // lineid or applinkid
		var $par = null; // line or applink
		var isHold = false;
		var isSubHold = false;
		var isParLine;   // 父元素是否为menuline （or applink） 
		var isMenuUnit; // 当前元素是否为menuunit （or applink） 
		var timer;
		
		function init() {
			id = paramId;
			$this = paramThis;
			parUnitId = paramParUnitId;
			parId = paramParId;
			$par = paramPar;

			if(undef(parUnitId)) {
				if(undef(parId)) {
					isMenuUnit = false;
				} else {
					isMenuUnit = true;
				}

			} else {
				isMenuUnit = true;
			}
			
			if(isMenuUnit) { //menuunit
				if(undef(parUnitId)) {
					isParLine = false;
				} else {
					isParLine = true;
				}
				
			} else { //applink
				isParLine = false;
			}
			
			
		}
		
		/* 启动unit当前的定时 */
		function startTimer(t) {
			isHold = false;
			isSubHold = false;
			if(t) {
				timer = setTimeout(releaseSelf, t);
			} else {
				timer = setTimeout(releaseSelf, 200);
			}
		}
		
		/* 清除unit当前的定时 */
		function clearTimer() {
			isHold = true;
			clearTimeout(timer);
		}
		
		/* 鼠标移出unit后，释放自身 */
		function releaseSelf() {
			if(isHold || isSubHold) {
				return;
			}
			if(isMenuUnit) {
				removeSelf();
				
				if(isParLine) {
					var parUnit = get(parUnitId);
					if(parUnit) {
						$par.removeClass("menulinehover");
						var $subHover = parUnit.getThisJqObj().children("div.menulinehover");
						if(!($subHover && $subHover.length > 0)) {
							parUnit.releaseBySub();
							parUnit.startTimer();
						}
					}
				} else {
					var parLink = get(parId);
					if(parLink) {
						parLink.removeSelf();
					} 
				}

			} else {
				var menuUnit = get(id + "_mu");
				if(menuUnit) {
					menuUnit.releaseSelf();

				} else {
					removeSelf();
				}
			}
			
		}
		
		/* 鼠标移动至menuunit或applink上 */
		function active() {
			clearTimer();
			isHold = true;
			isSubHold = false;
			if(isMenuUnit) { // 在menuunit上
				var parUnit = get(parUnitId);
				if(parUnit) {
					if(parUnit.isMenuUnit) {
						$par.addClass("menulinehover");
					}
					parUnit.activeBySub();
				}
				
			} else { // 在applink上
				var subMenuUnit = get(id + "_mu");
				if(subMenuUnit) {
					subMenuUnit.activeByParent();
				}
				$this.addClass("appunit_hover"); //修改其状态为聚焦
			}
			
		}
		/* 被子层menuunit hold住了 */
		function activeBySub() {
			clearTimer();
			isHold = false;
			isSubHold = true;
			if(isMenuUnit) {
				
			} else {
				$this.addClass("appunit_hover"); //修改其状态为聚焦
			}
		}
		/* 被父层hold住 */
		function activeByParent() {
			clearTimer();
			isHold = false;
			isSubHold = false;
		}
		
		/* 释放子层menuunit的hold状态 */
		function releaseBySub() {
			isSubHold = false;
		}

		/* 获取当前unit的子unit的相对左偏移（考虑自身的宽度） */
		function getSubMenuOffsetLeft() {
			return getThisOffsetLeft() + $this.width() + 0;
		}
		
		/* 获取当前unit的左偏移 */
		function getThisOffsetLeft() {
			return $this.offset().left;
		}

		/* 显示并定位menuunit */
		function show() {
			if(isParLine) { //由menuline产生的
				var parUnit = get(parUnitId);
				if(parUnit) {
					var top = $par.offset().top;
					var left = parUnit.getSubMenuOffsetLeft();
					
					//如果层次过多，应避免其在body外显示（overflow:hidden）
					var winWidth = $(window).width();
					var thisWidth = $this.width();
					if(left + thisWidth > winWidth) { // menuunit将会溢出至屏幕外
						left = parUnit.getThisOffsetLeft() - thisWidth;
					}
					
					var winHeight = $(window).height();
					var thisHeight = $this.height();
					if(top + thisHeight >= winHeight) {
						if(thisHeight > winHeight) {
							top = 20;
							$this.height(winHeight - 40);
						} else {
							top = winHeight - thisHeight;
							if(top >= 20) {
								top = top - 20;
							} else if(top >= 10) { 
								top = top - 10;
							}
						}
					}
					
					$this.offset({top: top, left: left}).removeClass("hide");
				}

			} else { //由applink产生的
				var top = $par.offset().top + $par.height();
				var left = $par.offset().left;
				
				//如果层次过多，应避免其在body外显示（overflow:hidden）
				var winWidth = $(window).width();
				var thisWidth = $this.width();
				if(left + thisWidth > winWidth) { // menuunit将会溢出至屏幕外
					left = left + $par.width() - thisWidth;
				}
				
				var winHeight = $(window).height();
				var thisHeight = $this.height();
				if(top + thisHeight > winHeight) {
					$this.height(winHeight - top - 20);
				}
				$this.offset({top: top, left: left}).removeClass("hide");
			}
		}
		
		/* 
		 * 移除unit 
		 * 1. 移除unit在队列中的缓存
		 * 2. 移除unit在div.appmenu中的dom
		 * */
		function removeSelf() {
			remove(id);
			if(isMenuUnit) {
				if($this) {
					$this.remove();
				}
			} else {
				$this.removeClass("appunit_hover");
			}
		}
		function getThisJqObj() {
			return $this;
		}
		init();
		return {
			startTimer: startTimer,
			active: active,
			activeBySub: activeBySub,
			activeByParent: activeByParent,
			releaseBySub: releaseBySub,
			getSubMenuOffsetLeft: getSubMenuOffsetLeft,
			getThisOffsetLeft: getThisOffsetLeft,
			show: show,
			removeSelf: removeSelf,
			releaseSelf: releaseSelf,
			isMenuUnit: isMenuUnit,
			getThisJqObj: getThisJqObj
		}
	}

	return {
		hoverTarget: hoverTarget,
		activeTarget: activeTarget,
		dynamicActiveTarget: dynamicActiveTarget,
		restTarget: restTarget,
		loadSubs: loadSubs
	}
}


/*
 * gwtab标签控件
 * 内部控件
 * 对象方法：
 *     
 **/
;(function($){
	$.fn.gwtab = function(options) {
		var defaults = {
			isTopTabGrp: false
		};
		var $thisObj = this;
		options = $.extend(defaults, options);
		//var $tabNavContainer = $thisObj.find(".tabnavcontainer:first");
		var $panelContainer = $thisObj.children(".tabcenter:first");
		var $tabTitUp = $thisObj.find(".tabtitleup:first");
		var $tabTitDown = $thisObj.find(".tabtitledown:first");
		var SCROLL_WIDTH = 0; //滚动条宽度。在resize后初始化
		var srcollWidth = 0; //当前滚动条在前后移动后的宽度
		var timeoutId;
		
		/* 初始化控件 */
		function init() {
			$tabTitUp.click(scrollTabDown);
			$tabTitDown.click(scrollTabUp);
			
			resize();
			$(window).resize(resize);
		}
		
		function resize() {
			if(timeoutId) {
				clearTimeout(timeoutId);			
			}
			timeoutId = setTimeout(delayResize, 100);
		}
		
		/* 
		 * 窗口大小变化后，用于刷新tabgroup的尺寸 
		 * 此操作只限制于顶层标签页
		 * */
		function delayResize() {
			if(!options.isTabCenterFull) {
				if(options.isTopTabGrp && $.browser.msie && $.browser.version <= 6) {
					var tabNorthHeight = $("#" + options.id + "_tabnorth").height();
					if(undef(tabNorthHeight)) {
						tabNorthHeight = 0;
					}
					tabCenter.height($thisObj.height() - tabNorthHeight);
				}
				return;
			}
			var tabCenter = $("#" + options.id + "_tabcenter");
			
			var $thisObj = $("#" + options.id);
			var position = $thisObj.position();
			tabCenter.css({"top": (position.top + 41)});
			tabCenter.css({"left": position.left});
			tabCenter.addClass("tabcenter_full");
			
			tabCenter.find(">.tabpanel").addClass("ctnscroll");
			
			if(options.isTopTabGrp && $.browser.msie && $.browser.version <= 6) {
				var tabNorthHeight = $("#" + options.id + "_tabnorth").height();
				if(undef(tabNorthHeight)) {
					tabNorthHeight = 0;
				}
				tabCenter.height($thisObj.height() - tabNorthHeight);
			}
		}
		
		/* 
		 * 加载tab内容数据 
		 * dataStr: <tabid>GWC26</tabid><tabcontent>...</tabcontent>
		 * */
		function loadData(dataStr) {
			var tabId = getTagInnerHtmlStr(dataStr, "tabid");
			var $tabNavContainer = $("#"+options.id).find(".tabnavcontainer:first");
			$tabNavContainer.find("table.tabselected:first").removeClass("tabselected");
			$tabNavContainer.find("#" + tabId).children().addClass("tabselected");

			var panelId = tabId + "_panel";
			$panelContainer.children().each(function() {
				if(this.id == panelId) {
					$(this).removeClass("hide").html(getTagInnerHtmlStr(dataStr, "tabcontent"));
				} else {
					$(this).addClass("hide");
				}
			})
			$tabNavContainer = null;
		}
		
		/* 
		 * 改变tab显示样式 
		 * dataStr: GWC26
		 * */
		function changeStyle(tabId) {
			showTab(tabId);
		}

		/* 显示指定标识的标签 */
		function showTab(tabId) {
			var $tabNavContainer = $("#"+options.id).find(".tabnavcontainer:first")
			$tabNavContainer.find("table.tabselected:first").removeClass("tabselected");
			$tabNavContainer.find("#" + tabId).children().addClass("tabselected");

			var panelId = tabId + "_panel";
			$panelContainer = $("#"+options.id).find(".tabcenter:first");
			$panelContainer.children().each(function() {
				if(this.id == panelId) {
					$(this).removeClass("hide");
				} else {
					$(this).addClass("hide");
				}
			});
			$tabNavContainer = null;
		}

		/* 后移 */
		function scrollTabDown() {
			var $tabNavContainer = $("#"+options.id).find(".tabnavcontainer:first");
			srcollWidth = srcollWidth + 75;
			if(srcollWidth < 0) {
				srcollWidth = 0;
			}
			if(srcollWidth > SCROLL_WIDTH) {
				srcollWidth = SCROLL_WIDTH;
			}
			$tabNavContainer.scrollLeft(srcollWidth);
			$tabNavContainer = null;
		}

		/* 前移 */
		function scrollTabUp() {
			var $tabNavContainer = $("#"+options.id).find(".tabnavcontainer:first");
			srcollWidth = srcollWidth - 75;
			if(srcollWidth < 0) {
				srcollWidth = 0;
			}
			if(srcollWidth > SCROLL_WIDTH) {
				srcollWidth = SCROLL_WIDTH;
			}
			$tabNavContainer.scrollLeft(srcollWidth);
			$tabNavContainer = null;
		}
		
		/* 
		 * 发送切换标签页请求前 
		 * 避免重复选择标签页
		 * */
		function beforeClickTab(tabId) {
			var $tabNavContainer = $("#"+options.id).find(".tabnavcontainer:first");
			if($tabNavContainer.find("#" + tabId + ":first").children().hasClass("tabselected")) {
				parseClick(tabId, false);
			} else {
				parseClick(tabId, true);
			}
			$tabNavContainer = null;
		}
		
		/* 移除标签页 */
		function removeTab(tabId) {
			var $tabNavContainer = $("#"+options.id).find(".tabnavcontainer:first");
			$tabNavContainer.find("#" + tabId).remove();
			$panelContainer.children("#" + tabId + "_panel").remove();
			resize();
			$tabNavContainer = null;
		}
		
		/* 移除标签页组合 */
		function remove(p) {
			$thisObj.remove();
			ctrlMgrSys.remove(options.id);
		}
		
		/* 新增标签页 */
		function addTab(tabId, tabTit, tabContent) {
			
		}

		init();

		return {
			resize: resize,
			loadData: loadData,
			changeStyle: changeStyle,
			showTab: showTab,
			beforeClickTab: beforeClickTab,
			removeTab: removeTab,
			remove: remove,
			addTab: addTab
		};
	}
})(jQuery);



/*
 * attachment控件
 * 内部控件
 * 对象方法：
 *     
 **/
;(function($){
	$.fn.attachment = function(options) {
		var $thisObj = this;
		function init() {
			if(options.eventname){
				$thisObj.live("click",function(event){
						parseClick(options.id, true, options.eventname);
				});
			}else{
				$thisObj.find("#" + options.id + "Atta").menu({
					menu: $thisObj.find("#" + options.id + "_menu")
				});
			}
		}
		init();

		return {
		};
	}
})(jQuery);

/*
 * calendar控件
 * 内部控件
 * 对象方法：
 *     
 **/
;(function($){
	$.fn.wallcalendar = function(options) {
		var $thisObj = this;
		function init() {
			if(options.events){
				for(var g=0;g<options.events.length;g++){
					var start = options.events[g].start;
					var startT = start.split("T");
					if($.isArray(startT) && startT.length ==2 ){
						options.events[g].allDay = false;
					}else{
						options.events[g].allDay = true;
					}
				}
			}
			$('#'+options.id).fullCalendar({
				theme: true,
				header: {
					left: 'prev,next today',
					center: 'title',
					right: 'month,agendaWeek,agendaDay'
				},
				aspectRatio: 2.8,//单元格宽与高度的比值
				editable: false,
				//disableDragging:true,
				//disableResizing:false,
				events: options.events,
			/*	events:function(start,end, callback) {
			       var data = sendSyncEvent("getEventData", "",options.id,"","");
					if(data){
						callback(data);
					}
			    },*/
				dayClick: function(date, allDay, jsEvent, view) {
					$('#'+options.id).fullCalendar('changeView', "agendaDay");
					$('#'+options.id).fullCalendar('gotoDate', date);
					if(options.eventdayoper){
						parseClick(options.id, true,options.eventdayoper,date);
					}	
				},
				eventClick:function( event, jsEvent, view ) { 
					if(options.eventsoper){
						parseClick(options.id, true,"",event.id);
					}
				}
			});
		}
		
		function loadData(event){
			if(event){
				if($.type(event) == "string" ){
					event = eval("("+event+")");
				}
				for(var g=0;g<event.length;g++){
					var start = event[g].start;
					var startT = start.split("T");
					if($.isArray(startT) && startT.length ==2 ){
						event[g].allDay = false;
					}else{
						event[g].allDay = true;
					}
				}
			}
			// $('#'+options.id).fullCalendar('renderEvent', event);
			$('#'+options.id).fullCalendar("removeEvents")
			$('#'+options.id).fullCalendar('addEventSource', event);
		}
		init();

		return {
			loadData: loadData
		};
	}
})(jQuery);


/*
 * 控件拖拽
 * 内部控件
 * 对象方法：
 *    实现原理：
 *			1、	
 *	 
 **/
;
(function($) {
	$.fn.designerDrag = function(options) {
		var defaults = {
			id : "", // 与后台交互的控件标识
			sigleEle : [ "tab_title", "wallcalendar", "blankline", "helpgrid",
					"tablecol", "textbox", "combobox", "multiparttextbox",
					"multilinetextbox", "tabletitle", "control",
					"defaultvalue", "image", "checkbox", "attachments",
					"pushbutton", "radiobutton", "uploadfile" ],
			curinfo : {
				"direction" : "up",
				"left" : 0,
				"top" : 0,
				"curEleId" : ""
			},
			dragflag : false,
			asyncCtrls : [ "tab", "tabgroup", "tab_title" ], // 遇到tab时需要将tab和其子控件一同进行移动
			asyncAreaCtrls : [ "tablecol", "tablebody", "tabledetails" ]
		}
		options = $.extend(defaults, options);
		var $thisObj = this;
		var parseId = null;
		function init() {
			$(".gwdesigner,.gwdesignersub").live("mousedown", function() {
				var curCtrl = getCurCtrl($(this));
				if (candrag(curCtrl)) {
					$('#tempcontainer').html(curCtrl.clone());
					$('#tempcontainer').data("curdom", curCtrl);

					$('#tempcontainer').data("candrag", true);
//						$('#curdomshow').html(curCtrl.clone());
					options.dragflag = true;
				} else {
					$('#tempcontainer').data("candrag", false);
				}
				return false;
			}).live("mousemove", function(event) {
				var curCtrl = getCurCtrl($(this));
//				console.log($('#tempcontainer').data("curdom"));
				if (!options.dragflag) {
					$('#curdomshow').html(curCtrl.attr("etype"));
				}
				$('#curdomshow').css({
					"left" : event.clientX + 10,
					"top" : event.clientY + 10,
					"z-index":9999
				}).show();
				changeCurInfo(event, $(this));
				var curdom = $('#tempcontainer').data("curdom");
				/*
				 * if(curdom){ $('#'+options.id+"_debug").html("<b>调试信息：</b></br>被移动控件id:"+curdom.attr("exid")+";被移动控件类型："+curdom.attr("etype") +"</br>&nbsp;&nbsp;&nbsp;当前id:"+curCtrl.attr("eid")+";当前控件类型："+curCtrl.attr("etype") +"</br>移动类型："+getMoveType(event,$(this))).show(); }
				 */
				return false;
			}).live(
					"mouseup",
					function(event) {
						options.dragflag = false;
						var curCtrl = getCurCtrl($(this));// 鼠标松开后的控件，即目标位置
						if (!$('#tempcontainer').data("candrag"))
							return false;
						var curdom = $('#tempcontainer').data("curdom"); // 缓存待移动控件信息
						var etype = getMoveType(event, $(this));
						var targetCtrl = getTargetCtrl($(this));
						var sid = curdom.attr("exid");
						var tid = curCtrl.attr("exid");
						// 针对表格中的元素做特别处理，操作表格中的元素时将刷新整个表格
						if (candrop(curCtrl) && sid != tid) {
							if (curdom.hasClass("gwdesignersub")) {
								parseClick(options.id, true, 'addControl',
										curdom.attr("etype") + "_&" + tid
												+ "_&" + etype + "_&"
												+ curCtrl.attr("efreshid"));
							} else {
								parseClick(options.id, true, 'moveControl', sid
										+ "_&" + tid + "_&" + etype + "_&"
										+ curCtrl.attr("efreshid"));
							}
						}
						$('#tempcontainer,#curdomshow').html("");
						$('#tempcontainer').data("curdom", "");
						$('#tempcontainer').data("candrag", false);
						$('#curdomshow').hide();
						$('#' + options.id + "_debug").html("").hide();// 清空debug信息
						return false;
					}).live("mouseout", function(event) {
				$('#curdomshow').html("").hide();// 清空提示信息
				$('#' + options.id + "_debug").html("").hide();
			})
			$(".gw_designer_subctrl .subctrl_toolbar .ctrlImgs .gwsubctrl img").live('dragstart',function(event){
				event.preventDefault();
			});
			function aaaaa($that) {
				var oldId;
				if(document.activeElement){
					oldId = document.activeElement.id;
				}
				var curCtrl = getCurCtrl($that);
				$('.gwdesigner').removeClass("gwdesigner_select");
				$that.addClass("gwdesigner_select");
				var etype = curCtrl.attr("etype");
				if (etype == "tab_title") {
					etype = "tab";
				}
				parseClick(options.id, true, 'clickControl', curCtrl
						.attr("exid")
						+ "_&" + etype);
				if(oldId){
					document.getElementById(oldId).blur();
				}
				// document.body.foucs();
				return false;
			}

			$(".gwdesigner").live("dblclick", function() {
				return aaaaa($(this));
			}).live(
					"contextmenu",
					function(e) {
						aaaaa($(this));
						$('.designer_menuitem').die("click");
						var curCtrl = getCurCtrl($(this));
						if (parseId != null) {
							$("#" + options.id + "_debugmenu").find(
									"[oper=pasteNode]").show();
						} else {
							$("#" + options.id + "_debugmenu").find(
									"[oper=pasteNode]").hide();
						}
						$(this).rightmenu({
							menu : $("#" + options.id + "_debugmenu"),
							X : e.clientX,
							Y : e.clientY,
							preventDefault : "true"// 阻止默认的请求处理
						});

						$('.designer_menuitem').live(
								"click",
								function(event) {
									var operTmp = $(this).attr("oper");
									if (operTmp != null
											&& operTmp.indexOf("Node")) {
										operTmp = operTmp.replace(/Node/,
												"Control");
									}
									if (operTmp == 'copyControl') {
										parseId = curCtrl.attr("exid");
									}
									parseClick(options.id, true, operTmp,
											curCtrl.attr("exid"));
								})
						e.preventDefault();
						return false;
					})

		}
		init();
		// 前置校验 是否可以接受放置元素
		function candrop(obj) {
			return obj.hasClass("gwdrop");
		}
		// 根据鼠标事件判断 移动类型：在边框上时判断为next，在元素内部时判断为inner
		function getMoveType(event, obj) {
			// obj.removeClass("movenext");
			if ($.inArray(obj.attr("etype"), options.sigleEle) != -1) {
				return "next";
			}
			var pos = obj.offset();
			var omx = pos.left + obj.innerWidth();// 防止刚刚创建的元素 无法计算补白空间
			var omy = pos.top + obj.innerHeight();

			var ex = event.clientX;
			var ey = event.clientY;
			if (ex == pos.left || ex == omx || ey == pos.top || ey == omy) {
				// obj.addClass("movenext");
				return "next";
			}
			if (ex > pos.left && ex < omx && ey > pos.top && ey < omy) {
				return "inner";
			}
			return "";
		}
		// 获取当前操作的元素，使用情况：当鼠标定位的元素非真实需要操作的元素时，使用eresourceid指定到真实操作的元素
		function getCurCtrl(obj) {
			var esid = obj.attr("eresourceid");
			if (esid) {
				return $("[eid='" + esid + "']");
			} else {
				return obj;
			}
		}
		// 获取目标元素，使用情况：etargetid指定真正需要变化的元素
		function getTargetCtrl(obj) {
			var etid = obj.attr("etargetid");
			if (etid) {
				return $("[eid='" + etid + "']");
			} else {
				return obj;
			}
		}
		// 前置校验 是否可以拖拽
		function candrag(obj) {
			return obj.hasClass("gwdrag");
		}

		function changeCurInfo(event, obj) {
			if (event.clientX > options.curinfo.left) {

			}
			options.curinfo.left = event.clientX;
			options.curinfo.top = event.clientY;
		}

		return {

		};
	}
})(jQuery);

/*
 * kpi控件
 *	 
 **/
;(function($){
	$.fn.kpigraph = function(options) {
		var flag = true;
		var defaults = {
			currentSelect: ""  //当前选中项的标识
		}
		var options = $.extend(defaults, options);
		var $thisObj = this;
		/* 初始化选项选中事件 */
		function init() {
		    if(options.isShowKPI){
		        $thisObj.highcharts(options,function (chart) {});
		    }else{
		        $("#" + options.id).html(options.errorMsg);
		        $("#" + options.id).css("height","15px");
		    }
		}
		
		/* 
		 * 加载kpigraph内容数据 
		 * dataStr: <tabid>GWC26</tabid><tabcontent>...</tabcontent>
		 * */
		function loadData(dataStr){
		    var isUnconfig = getTagInnerHtmlStr(dataStr, "isUnconfig");
		    var label = getTagInnerHtmlStr(dataStr, "label");
			var start = parseFloat(getTagInnerHtmlStr(dataStr, "min"));
			var warn = parseFloat(getTagInnerHtmlStr(dataStr, "max"));
			var valuesuffix = getTagInnerHtmlStr(dataStr, "valuesuffix");
			var part1color = getTagInnerHtmlStr(dataStr, "part1color");
			var part2color = getTagInnerHtmlStr(dataStr, "part2color");
			var part3color = getTagInnerHtmlStr(dataStr, "part3color");
			var tooltip = getTagInnerHtmlStr(dataStr, "tooltip");
			var target = parseFloat(getTagInnerHtmlStr(dataStr, "target"));
			var nowvalue = parseFloat(getTagInnerHtmlStr(dataStr, "nowvalue"));
			var normal = parseFloat(getTagInnerHtmlStr(dataStr, "normal"));
			var care = parseFloat(getTagInnerHtmlStr(dataStr, "care"));
			var id = getTagInnerHtmlStr(dataStr, "id");
			var nowcolor = getTagInnerHtmlStr(dataStr, "nowcolor");
			var nowDiffValue = nowvalue - target;
		   if(isUnconfig == "true"){
		        $("#" + options.id).html(options.errorMsg);
		        $("#" + options.id).css("height","15px");
		        $("#" + options.id + "_hd_title").html("");
		        $("#" + options.id + "_status").html("");
		        $("#" + options.id + "_currval").html("");
				$("#" + options.id + "_targetval").html("");
				$("#" + options.id + "_nodediffval").html("");
		   }else{
				$("#" + options.id + "_hd_title").html(label);
				$("#" + options.id + "_currval").html("<div>" + nowvalue + "</div>");
				$("#" + options.id + "_targetval").html("<div>" + target + "</div>");
				$("#" + options.id + "_nodediffval").html("<div>" + nowDiffValue + "</div>");
				$("#" + options.id + "_status").html('<div style="width: 15px;height: 5px;background-color: '+nowcolor+';"></div>');
				$("#" + options.id).css("height",options.height+"px");
				var jsonobj = {
				    chart: {
				        type: 'gauge'
				    },
				    
				    title: {
				        text: label
				    },
				    
				    pane: {
			        	startAngle: -150,
			        	endAngle: 150,
			        	background: [{
				        	backgroundColor: {
				                linearGradient: {
				                    x1: 0,
				                    y1: 0,
				                    x2: 0,
				                    y2: 1
				                },
				                stops: [
				                    [0, '#FFF'],
				                    [1, '#333']
				                ]
				        	},
				        	borderWidth:0,
				        	outerRadius:'109%'
				    	},{
				        	backgroundColor: {
				                linearGradient: {
				                    x1: 0,
				                    y1: 0,
				                    x2: 0,
				                    y2: 1
				                },
				                stops: [
				                    [0, '#333'],
				                    [1, '#FFF']
				                ]
				        	},
				        	borderWidth:2,
				       	 	outerRadius:'107%'
				   	 	},{ },{
				   	 		backgroundColor: {
				                linearGradient: {
				                    x1: 0,
				                    y1: 0,
				                    x2: 0,
				                    y2: 1
				                },
				                stops: [
				                    [0, '#FFF'],
				                    [1, '#DDD']
				                ]
				        	},
				        	borderWidth:0,
				        	outerRadius:'105%',
				        	innerRadius:'103%'
				    	}]
			    	},
				    yAxis: {
				        min: start,
				        max: warn,
				        
				        minorTickInterval: 'auto',
				        minorTickWidth: 1,
				        minorTickLength: 8,
				        minorTickPosition: 'inside',
				        minorTickColor: '#333',
			        
				        tickPixelInterval: 30,
				        tickWidth: 1,
				        tickPosition: 'inside',
				        tickLength: 15,
				        tickColor: '#333',
				        labels: {
				            step: 2,
				            rotation: 'auto',
				            style: {
				            	color: '#333',
				            	font: '13px 宋体'
				            }
				        },
				        title: {
				            text: valuesuffix
				        },
				        plotBands: [{
				            from: start,
				            to: normal,
				            color: part1color // green '#55BF3B'
				        }, {
				            from: normal,
				            to: care,
				            color: part2color // yellow '#DDDF0D'
				        }, {
				            from: care,
				            to: warn,
				            color: part3color // red '#DF5353'
				        }]        
				    },
				    tooltip: {
				        	enabled : Boolean(tooltip)
				    },
				    series: [{
				        name: '',
				        data: [{id:'nowvalue',y:nowvalue},{id:'targetvalue',y:target,dial:{backgroundColor:'red'}}],
				        dataLabels: {
				       		enabled: true,
				            formatter: function () {
				                var str = '<span style="color:#555">当前：'+ nowvalue + ' ' + valuesuffix + '</span>';
				                if(target!=""){
				                	str += '<br/>';
				                	str += '<span style="color:#555">目标：'+ target + ' ' + valuesuffix +'</span>';
				                }
				                return str;
				            },
				            backgroundColor: {
				                linearGradient: {
				                    x1: 0,
				                    y1: 0,
				                    x2: 0,
				                    y2: 1
				                },
				                stops: [
				                    [0, '#DDD'],
				                    [1, '#FF0']
				                ]
				            }
				        },
				        tooltip: {
							pointFormat: '{point.y}',
				            valueSuffix: '  ' + valuesuffix
				        }
				    }]
				};
				//console.log(jsonobj.series[0].data);
				$thisObj.highcharts(jsonobj,function (chart) {});
		   }
		}
		
		init();
		
		return {
			loadData: loadData
		};
	}
})(jQuery);



/*
 * bulletinboardlink控件
 *	 
 **/
;(function($){
	$.fn.bulletinboardlink = function(options) {
		/* 更新值 */
		function loadData(count) {
			$("#" + options.id + "_count").text(count);
		}
		return {
			loadData: loadData
		};
	}
})(jQuery);


/*
 * iframe控件
 **/
;(function($){
	$.fn.iframe = function(options) {
		function init() {
			if(!options.isHeight100P) {
				return;
			}
			var tab = $("#" + options.id + "Contain").parents(".tabcenter").eq(0);
			if(tab == null) {
				return;
			}
			$(window).resize(handleResizeEvent);
			handleResizeEvent();
		}
		
		function handleResizeEvent() {
			setTimeout(resize, 50);
		}
		
		function resize() {
			var $this = $("#" + options.id + "Contain");
			var tab = $this.parents(".tabcenter").eq(0);
			if(tab == null) {
				return;
			}
			$this.height(tab.height());
		}
		
		init();
		
		return {};
	}
})(jQuery);

/*
 * chartportlet
 * */
;(function($){
	$.fn.chartportlet = function(options) {
		var thisChartObj = null;
		var id = options.id;
		var chartType = options.chartType;
		var maxwidth = options.maxwidth;
		var maxheight = options.maxheight;
		
		/**
		 * 获取列图表的百分比
		 * @param name: 类型名称
		 */
		function getColPercentage(name) {
			if(options.dataObj == null) {
				return 0;
			}
			var sum = 0.0;
			var nameVal = 0.0;
			for(var i = 0; i < options.dataObj.length; i++) {
				sum += options.dataObj[i]["data"][0];
				if(name == options.dataObj[i]["name"]) {
					nameVal = options.dataObj[i]["data"][0];
				}
			}
			if(sum == 0) {
				return 0;
			}
			return (nameVal*100 / sum).toFixed(2);
		}
		
		/**
		 * 绘制列图
		 * [{name:'草稿', data:[12.8], color:'#CCFF00'}]
		 */
		function drawColumn() {
			thisChartObj = echarts.init(document.getElementById(id));
			var names=[];
			var nums=[];
			
			var colors=[];
			for (var i = 0; i < options.dataObj.length; i++){
				names.push(options.dataObj[i]["name"]);
				colors.push(options.dataObj[i]["color"]);
				var numsitem=[];
				for(var j=0;j<options.dataObj[i]["data"].length;j++)
				{
					numsitem.push(options.dataObj[i]["data"][j]);
				}
				nums.push(numsitem);
				
			}
			thisChartObj.on('click', handleClick);
			option = {
    			title : {
        			text: null
    				},
    			tooltip : {
					trigger: 'item',
        			axisPointer : {            // 坐标轴指示器，坐标轴触发有效
            		type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
        				}
					},
   				legend: {
   					show:true,
   					x:'center',
   					y:'bottom',
        			data:[]
    			},
    			toolbox: {
        			show : true,
        			feature : {
            			mark : {show: true},
            			dataView : {show: true, readOnly: true},
            			magicType : {show: true, type: ['line', 'bar']},
            			restore : {show: true},
            			saveAsImage : {show: true}
        				}
    			},
    			calculable : true,
    			xAxis : [
        		{
            			type : 'category',
            			data : []
        		}
    				],
    			yAxis : [
        		{
            			type : 'value',
            			splitArea:{show:true}
        		}
    				],
    			series : []  
    			};
    		var serData=[];
    		for(var i = 0; i < names.length; i++){
    			{
    				serData.push({
    					name:names[i],
    					type:'bar',
            			data:nums[i],
            			itemStyle:{
            				normal:{
            					color:colors[i],
            					label:{
            						show:true,
            						position:'top',
            						formatter:'{b}\n{c}'
            					}
            				}
            			},
            			barWidth:70
    				})
	    			}
    		}
    		option.legend.data=names;
    		option.xAxis.data=names;
    		option.series=serData;
			thisChartObj.setOption(option);
			
		}
		
		/**
		 * 绘制饼图
		 * [{name:'草稿',y: 12.8, color:'#CCFF00'},{name:'进行中',y: 12.8, color:'#00FF00'}]
		 * */
		function drawPie() {
			thisChartObj=echarts.init(document.getElementById(id));
			option = {
				title: {
					text: null
				},
				tooltip:{
					trigger:'item',
					formatter: "{a}<br/>{b}:{c}({d}%)"
    				
				},
				legend:{
					data:[],
					y:"bottom"
					},
				calculable:true,
				toolbox:{
					show:true,
					feature:{
						mark:{show:true},
						dataView:{show:true,readOnly: true},
						restore:{show:true}
						}
					},
				series:[
   				{
   					name:'数量',                
   					type:'pie',
   					radius:'55%',
   					center:['50%','60%'],
   					data:[]
   					}
   				]
			};

			var nameData=[];//属性值数据
			var res=[];
			var len=options.dataObj.length;
			if(len>0){
				for(var i = 0; i < len; i++) {
					nameData.push(options.dataObj[i]["name"]);
					res.push({
						name:options.dataObj[i]["name"],
						value:options.dataObj[i]["y"]
					});
					}
				}
					
				
				
			
			thisChartObj.on('click', handleClick);
			option.legend.data=nameData;
			option.series[0].data=res;
			thisChartObj.setOption(option);
			
			
		}
		
		
		/**
		 * 处理图形点击事件
		 * @param name: 类型名称
		 */
		function handleClick(param) {
			if(param.seriesType=="pie"){
				parseClick(id, true, "", param.name);
			}else{
				parseClick(id, true, "", param.seriesName);
			}
			
		}
		
		/**
		 * 绘制图形
		 */
		function drawChart() {
			var $this = $("#" + id);
			var outerWidth = $this.parent().width()-2; //td
			var width = outerWidth;
			if(width > maxwidth && maxwidth > 0) {
				width = maxwidth;
			}
			
			$this.width(width);
			
			if(chartType =="column" || chartType=="spline") {
				var height = outerWidth * 0.66; // 9/14=0.64
				if(height > maxheight && maxheight > 0) {
					height = maxheight;
				}
				$this.height(height);
				
				drawColumn(chartType);
			} else if(chartType == "pie") {
				var height = outerWidth; // 方形图
				if(height > maxheight && maxheight > 0) {
					height = maxheight;
				}
				$this.height(height);
				
				drawPie();
			}
			$this.show();
		}
		
		/**
		 * window窗体缩放时重新绘制图形
		 */
		function handleWindowResize() {
//			var chartObj = $("#" + id);
//			chartObj.clear();
//			chartObj.dispose();
//			setTimeout(drawChart, 100);	
		}
		
		/**
		 * 初始化图形
		 */
		function init() {
			setTimeout(drawChart, 100);
			$(window).resize(handleWindowResize);
		}
		
		init();
		
		/**
		 * 重新加载方法
		 */
		function reload() {
		}
		
		return {
			reload: reload
		};
	}
})(jQuery);
/**
 * chart
 */
;(function($){
	$.fn.chart = function(options) {
	
	
	var myTool={};
	myTool.show="true";
	myTool.title="刷新";
	myTool.icon="M22,1.4L9.9,13.5l12.3,12.3 M10.3,13.5H54.9v44.6 H10.3v-26";
	myTool.onclick=function(){
		parseClick(options.id,true,"loadData");	
	};
		
		function loadData(rtn){
			$("#" + options.id).html("");
			var thisChartObj=echarts.init(document.getElementById(options.id));
			var newoption=getTagInnerHtmlStr(rtn, "optiondata");
			var optionobj=eval('('+newoption+')');
			optionobj.toolbox.feature.mytool=myTool;
			thisChartObj.setOption(optionobj,true);
			thisChartObj.on('click', handleClick);
		}
		
		function handleClick(param) {
			if(param.seriesType=="pie"){
				parseClick(options.id, true, "", param.name);
			}else{
				if(undef(param.seriesName)){
					parseClick(options.id, true, "", param.name);
				}else{
					parseClick(options.id, true, "", param.name+','+param.seriesName);					
				}
			}
			
		}
		
		/**
		 * 绘制图形
		 */
		function drawChart() {
			var thisChartObj=echarts.init(document.getElementById(options.id));
			if(options.dataObj == null || thisChartObj==null) {
				return 0;
			}
			options.dataObj.toolbox.feature.mytool=myTool;
			thisChartObj.setOption(options.dataObj,true);
			var app=options.linkedapp;
			if(app !=''){
			thisChartObj.on('click', handleClick);
			}
			
		}
		
		
		
		/**
		 * 初始化图形
		 */
		function init() {
			setTimeout(drawChart, 100);
		}
		
		init();
		
		/**
		 * 重新加载方法
		 */
		function reload() {
		}
		
		return {
			loadData: loadData,
			reload: reload
		};
	}
})(jQuery);
/*
 * kpi
 * */
;(function($){
	$.fn.kpi = function(options) {		
		var myTool={};
		myTool.show="true";
		myTool.title="刷新数据";
		myTool.icon="M3.8,33.4 M47,18.9h9.8V8.7 M56.3,20.1 C52.1,9,40.5,0.6,26.8,2.1C12.6,3.7,1.6,16.2,2.1,30.6 M13,41.1H3.1v10.2 M3.7,39.9c4.2,11.1,15.8,19.5,29.5,18 c14.2-1.6,25.2-14.1,24.7-28.5";
		myTool.onclick=function(){
			parseClick(options.id,true,"loadData");	
		};
		
		function loadData(rtn){			
			$("#" + options.id).html("");	
			document.getElementById(options.id+"_chartContainer").style.display="none";
			document.getElementById(options.id+"_kpiContainer").style.display="";
			var thisChartObj=echarts.init(document.getElementById(options.id));
			//thisChartObj.off('click');
			var newoption=getTagInnerHtmlStr(rtn, "optiondata");
			var optionobj=eval('('+newoption+')');
			optionobj.toolbox.feature.mytool=myTool;
			thisChartObj.setOption(optionobj,true);
			thisChartObj.on('click', handleClick);
			
		}
				
		function loadChart(rtn){
			var newoption=getTagInnerHtmlStr(rtn, "optiondata");
			$("#" + options.id+"_chartContainer").html(newoption);
			document.getElementById(options.id+"_kpiContainer").style.display="none";
			document.getElementById(options.id+"_chartContainer").style.display="";
			var div=document.getElementById(options.id+"__chartContainer");
			//text-align:center; margin: 0 auto; width:450px;height:300px; margin-top:20px
			div.style.margin="20px auto 70px auto";
			//alert(kpiWidth+" "+kpiHeight);
			//div.style.width=kpiWidth;
			//div.style.height="300px";
			/*var thisChartObj=echarts.init(div);
			//thisChartObj.off('click');
			var optionobj=eval('('+newoption+')');
			optionobj.toolbox.feature.mytool=myTool;
			
			thisChartObj.setOption(optionobj,true);
			thisChartObj.on('click', handleClick);*/
			
		}
		
		function handleClick(param) {
			parseClick(options.id, true, "", param.seriesName);
			
		}
		
		/**
		 * 绘制图形
		 */
		function drawChart() {
			if(document.getElementById(options.id)==null)
			{
				return;
			}
			var thisChartObj=echarts.init(document.getElementById(options.id));
			if(options.dataObj == null) {
				return 0;
			}
			thisChartObj.off('click');
			options.dataObj.toolbox.feature.mytool=myTool;
			
			thisChartObj.setOption(options.dataObj,true);
			thisChartObj.on('click', handleClick);
			
			
		}
		
		
		
		
		/**
		 * 初始化图形
		 */
		function init() {
			setTimeout(drawChart, 100);
		}
		
		init();
		
		/**
		 * 重新加载方法
		 */
		function reload() {
		}
		
		return {
			loadData: loadData,
			reload: reload,
			loadChart: loadChart
		};
	}

})(jQuery);

/**
 * listbox
 */
;(function($){
	$.fn.listbox = function(options) {
		var defaults = {
			id: "",
			
			errorMsg: ""  //后台错误消息
		}
		options = $.extend(defaults, options);
		var $thisObj = this;
		var $input = $thisObj.find("#" + options.id + "input0");
		/**
		 * 初始化控件
		 * */
		function init() {
			
			
			if(options.isChkbox) { //绑定checkbox的特定事件
				$input.bind("click", function(event){
					if(options.id != getChangedCtrlId()) { //确认不要在同一个控件内操作
						if(options.isInCell) {
							stopBubble(event.originalEvent); //阻止点击事件冒泡
							if(options.isCellEvent) { //目前checkbox只能作为表格的多选事件操作对象
								parseClick(options.id, true, "multipleselectrow");
							} else { //表格的内容对象
								parseClick(options.id, true, "onclick");
							}
						} else {
							parseClick(options.id, true, "onclick");
						}
					}								
				});
				
			} else { //绑定textbox和multilinetextbox的特定事件
				$input.bind("change", function() {
					var valTmp = this.value;
					if(valTmp == null) {
						valTmp = "";
					}
					setChangedCtrlVal(options.id, valTmp);//缓存输入框变更的值 eventmgr.js
				}).bind("focus", function(event){
					stopBubble(event.originalEvent); //阻止点击事件冒泡
					if(!options.isReadonly) {
						if(options.isOk) {
							$input.parent().addClass("inputfocus");
						}
					}		
					if(options.id != getChangedCtrlId()) { //确认不要在同一个控件内操作
						if(options.isInCell) {
							parseClick(options.id, true, "onfocus");
						} else {
							parseClick(options.id, false);
						}
					}					
				}).bind("blur", function(event){
					if(!options.isReadonly) {
						$input.parent().removeClass("inputfocus");
					}							
				});
			}
			$input.bind("mouseenter", function(event){
				if(!options.isOk) {
					onErrInputMouseEnter(event);
				}							
			}).bind("mouseleave", function(event){
				if(!options.isOk) {
					onErrInputMouseLeave(event);
				}							
			});
			
		
			
			if(!options.isFilter && !options.isInCell) { //注册字段提示事件
				var $titleContent = $thisObj.find(".txbxlabeltx:first");
				$titleContent.bind("dblclick", function(event){
					$titleContent.bind("mouseleave", function(event){
						leaveField(event);	
						$titleContent.unbind("mouseleave");						
					});	
					tipField(event, $titleContent);
				});
			}
		}
		
		/* 字段提示 */
		function tipField(event, jqTarget) {
			TIP.hide();
			TIP.setBindCtrlId(options.id);
			TIP.hideResetBtn(); //隐藏重置值按钮;
			TIP.update(getFieldTipCont(options), false); //重置提示内容，更新提示控件绑定对象为当前控件
			TIP.mouseenter(event, jqTarget);
		} 
		
		/**
		 * 当鼠标移出字段时，隐藏提示信息
		 * */
		function leaveField(event) {
			TIP.mouseleave(event, $thisObj);
		} 
		
		/**
		 * 重新加载控件数据
		 * 根据返回值中的类型属性，更新文本框状态
		 * @param rtn - <type>ok or error</type><val></val><msg></msg><required></required><readonly></readonly>
		 * */
		function loadData(rtn) {
			if("ok" == getTagInnerHtmlStr(rtn, "type")) {
				if(!options.isOk) {
					options.isOk = true;
					options.errorMsg = "";
					$input.parent().removeClass("inputerror");
				}
				
			} else {
				if(options.isOk) {
					options.isOk = false;
					$input.parent().addClass("inputerror");
				}
				options.errorMsg = getTagInnerHtmlStr(rtn, "msg");
				
			}
			
			var newVal = getTagInnerHtmlStr(rtn, "val");
			if(options.isChkbox) { //更新checkbox的值
				if("Y" == newVal || "1" == ("" + newVal)) {
					$input.removeClass("unchecked").addClass("checked");
				} else {
					$input.removeClass("checked").addClass("unchecked");
				}
			} else if(options.isListbox) { //更新listbox的值
				$input.html((undefStrictly(newVal)? "": newVal));
			} else { //更新textbox或multilinetextbox的值
				$input.val((undefStrictly(newVal)? "": newVal));
			}
			
			
			if("true" == getTagInnerHtmlStr(rtn, "required")) {
				$thisObj.find("#" + options.id + "required").show();
			} else {
				$thisObj.find("#" + options.id + "required").hide();
			}
			
			if("true" == getTagInnerHtmlStr(rtn, "readonly")) {
				options.isReadonly = true;
				$input.parent().addClass("inputreadonly").removeClass("inputfocus");
				$input.attr("readonly", "readonly");
			} else {
				options.isReadonly = false;
				$input.parent().removeClass("inputreadonly");
				$input.removeAttr("readonly");
			}
		}
		
		/**
		 * 更新标签内容
		 * @param newLabel - 新标签内容
		 */
		function changeLabel(newLabel) {
			$thisObj.find("#" + options.id + "labeltd").find("span.txbxlabeltx").html(html2Str(newLabel));
		}

		function setVisible() {
			
		}
		
		/**
		 * 当鼠标移动至错误信息图标时，显示错误提示信息
		 * */
		function onErrInputMouseEnter(event) {
			TIP.hide();
			TIP.setBindCtrlId(options.id);
			TIP.showResetBtn(); //显示重置值按钮
			TIP.update(html2Str(options.errorMsg), false); //重置提示内容，更新提示控件绑定对象为当前控件
			TIP.mouseenter(event, $input);
		} 
		
		/**
		 * 当鼠标移出错误信息图标时，隐藏错误提示信息
		 * */
		function onErrInputMouseLeave(event) {
			TIP.mouseleave(event, $input);
		} 
		
		/**
		 * 日期控件设置日期后提交前，值变更处理
		 * */
		function beforeDateChange(newDate) {
			setChangedCtrlVal(options.id, newDate);//缓存输入框变更的值 eventmgr.js
		}
		
		/**
		 * 回退文本框值
		 * */
		function resetVal() {
			if(options.isOk) { //无异常的不需回退值
				return;
			}
			parseClick(options.id, true, "rollback");
		}
		
		/* 返回控件类型（目前服务于请求后的控件聚焦处理） */
		function getType() {
			return "listbox";
		}
		
		/* 返回控件内部需要被聚焦的dom标识 */
		function getFocusDomId() {
			return options.id + "input0";
		}
		
		init();
		/* *****变更值后，后台需返回更新值方法，将新值缓存在oldValue中，并根据需要更新输入框的状态**** */
		return {
			resetVal: resetVal,
			loadData: loadData,
			beforeDateChange: beforeDateChange,
			onErrInputMouseEnter: onErrInputMouseEnter,
			onErrInputMouseLeave: onErrInputMouseLeave,
			getType: getType,
			getFocusDomId: getFocusDomId
		};
	}
})(jQuery);

