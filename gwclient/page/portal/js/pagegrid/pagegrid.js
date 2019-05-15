/*
 * 自定义分页标签
 * 基于jquery的显示控制插件，此外还需导入pagegrid.css样式文件
 * 重要参数：
 * 对象方法：
 *     	chooseRow()选择指定标识的分页行
 *	   	reloadData()
 **/
;(function($){
	$.fn.pagegrid = function(options) {
		var defaults = {
			title:"",
			height: "600px", //默认高度
			width: "100%",   //默认宽度为100%父层空间
			dataUrl: '',        //数据源地址
			additionParams: '', //额外的数据源检索条件
			isFirstRowSel: false, //是否初始化选择首行
			isFiltering: false, //是否开启基本属性过滤功能
			
			total: 0,    //总的结果集数量
			totalPage: 0,//总的分页数量
			pageNumber: 0, //当前页码：从0开始
			pageSize: 10,  //每页显示的数据量
			rows: [],    //当前分页下的行数据
			columns: [], //属性列
			keyFields: [],    //行关键属性集
			domId: '',
			rowStyler: function(rowIndex, rowData) {
				return "";
			},  //行样式
			onRowSelect: null, //选中行事件,
			fieldVals: {}
		}
		var options = $.extend(defaults, options);
		
		return this.each(function(){
			options.domId = $(this).attr('id') + "_PG"; //初始化pagegrid的dom id
			initParam(); //初始化各参数
			createGrid($(this));            //根据传入的数据拼装数据分页\
			this.chooseRow = chooseRow; //选中某行 rowIndex, rowKeyData
			this.reloadData = reloadData;   //重载数据
			this.setAdditionParams = setAdditionParams; //重置附加查询条件
		})[0];
		
		/* 初始化各参数 */
		function initParam() {
			//初始化过滤参数
			if(options.isFiltering == true) {
				for(var i = 0, LENGTH = options.columns.length; i < LENGTH; i++) { //columns: [{}, {}];
					options.columns[i].field
					eval("options.fieldVals." + options.columns[i].field + "=''");
				}
				//alert(eval("options.fieldVals." + options.columns[0].field))	
			}
			
			//初始化
		}
		
		/* 根据传入的数据拼装数据分页 */
		function createGrid($Obj) {
			var tableStr = "";
			tableStr += createTableTarget();
			tableStr += createTbodyTarget();
			tableStr += createTfootTarget();
			tableStr += "</table>";
			
			$Obj.find("table:first").remove();
			$Obj.html(tableStr);
			
			bindPageFootEvent(); //绑定分页底部导向按钮操作事件
			
			if(options.dataUrl != null && options.dataUrl != "" && options.dataUrl != "null") {
				loadUrlData(); //加载数据
			}
		}
		
		/* 绑定分页底部导向按钮操作事件 */
		function bindPageFootEvent() {
			if(options.dataUrl != null && options.dataUrl != "" && options.dataUrl != "null") {
				var $foot = $("#" + options.domId + "_FOOT");
				$foot.find("input.firstpage").bind('click', function(){
					if (options.pageNumber == 0 || options.totalPage <= 0) {
						return;
					} else {
						options.pageNumber = 0;
						loadUrlData();
					}
				});
				$foot.find("input.touppage").bind('click', function(){
					if (options.pageNumber <= 0 || options.totalPage <= 0) {
						return;
					} else {
						options.pageNumber = options.pageNumber -1;
						loadUrlData();
					}
				});
				$foot.find("input.todownpage").bind('click', function(){
					if (options.pageNumber >= options.totalPage - 1 || options.totalPage <= 0) {
						return;
					} else {
						options.pageNumber = options.pageNumber + 1;
						loadUrlData();
					}
				});
				$foot.find("input.lastpage").bind('click', function(){
					if (options.pageNumber == options.totalPage - 1 || options.totalPage <= 0) {
						return;
					} else {
						options.pageNumber = options.totalPage - 1;
						if(options.totalPage - 1 >= 0) {
							options.pageNumber = options.totalPage - 1;
							loadUrlData();
						}
					}
				});
			}
		}
		
		/* 根据分页参数及数据源地址加载分页数据 */
		function loadUrlData() {
			var condStr = "";
			if(options.isFiltering == true) { //必须是过滤条件开启的情况下
				condStr = getFilterData();
			}
			//缺少查询出错处理
			$.ajax({ 
				type: "GET",
				url: options.dataUrl + "?pageNumber=" + options.pageNumber + "&pageSize=" + options.pageSize + "&" + options.additionParams + condStr, 
				cache: false,
				dataType: "json",
				success: function(dataResp){
			    	showNewPageData(dataResp);
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					alert(textStatus)
				}
			});
		}		
		/* 根据查询所得数据，重组分页body中的内容 */
		function showNewPageData(dataResp) {
			options.total = dataResp.total;
			options.totalPage = ((options.total%options.pageSize==0)? (options.total/options.pageSize): (parseInt(options.total/options.pageSize) + 1));
			options.rows = dataResp.rows;	
			
			$body = $("#" + options.domId + "_BODY");
			clearPageRowEvent($body);
			$body.remove();
			$("#" + options.domId + "_HEAD").after(createTbodyTarget());
			
			if(options.isFiltering == true) {
				bindFilterEvent(); //绑定过滤事件对象
			}
			resetPageFoot(); //重置分页底部导向信息
			bindPageRowEvent(); //绑定分页数据行单击操作事件
			
			if(options.isFirstRowSel) { //是否初始化选择首行
				chooseRow(0); //默认选中第一行
			}
		}
		/* 重置分页底部导向信息 */
		function resetPageFoot() {
			var $foot = $("#" + options.domId + "_FOOT");
			$foot.find("span.pagemsg").html("第0/" + options.rows.length + "条");
			$foot.find("input.nowpage").val((options.pageNumber + 1));
			$foot.find("span.totalpage").html(options.totalPage);
		}
		/* 清楚分页数据行单击操作事件 */
		function clearPageRowEvent($body) {
			$body.find("tr.trhover").each(function(){
				$(this).unbind();
			});
		}
		
		/* 绑定过滤事件对象 */
		function bindFilterEvent() {
			if(options.isFiltering == true) { //必须是过滤条件开启的情况下
				
				$("#" + options.domId).find("input.i_filter").each(function(index){
					$(this).bind("keyup", function(event){
						if((event.keyCode + "") == "13") {
							options.pageNumber = 0; //从过滤器中走，将页面初始从首页开始搜索
							loadUrlData()//过滤页面数据
						}
					});
				});
			}
			
		}	
		
		/* 过滤页面数据 */	
		function getFilterData() {
			var queryStr = "";
			if(options.isFiltering == true) { //必须是过滤条件开启的情况下
				
				$("#" + options.domId).find("input.i_filter").each(function(index){
					queryStr += "&" + $(this).attr("prop") + "=" + encodeURIComponent($(this).val()); 
					
					eval("options.fieldVals." + $(this).attr("prop") + "='" + $(this).val() + "'");
				});
			}
			return queryStr;
		} 	
		
		/* 绑定分页数据行单击操作事件 */
		function bindPageRowEvent() {
			$("#" + options.domId + "_BODY").find("tr.trhover").each(function(index){
				$(this).bind("click", function(){
					$(this).siblings(".trhover").each(function(){
						$(this).removeClass("trselect");
					})
					$(this).addClass("trselect");
					if(options.onRowSelect != null && typeof(options.onRowSelect) != "undefine") {
						options.onRowSelect(index, options.rows[index]);
					}
					$("#" + options.domId + "_FOOT").find("span.pagemsg").html("第" + (index + 1) + "/" + options.rows.length + "条");
				})
			});
		}
	
		/* 创建分页顶层标签及其标题 */
		function createTableTarget() {
			var tableStr = "";
			tableStr += "<table id='" + options.domId + "' class='pagegrid' style='width: " + options.width + "; '>";
			tableStr += "<tr id='" + options.domId + "_HEAD'><td class='hbouter '>" 
							+ "<table style='width: 100%; margin:0;' cellSpacing='0' cellPadding='0'><tr>" 
									+ "<td class='h_l'></td>"
									+ "<td class='h_c thb hbsh'>" 
										+ "<span class='text ttit'>" 
											+ options.title 
										+ "</span>" 
									+ "</td>"; 
			/*
			 * 因设计多条件过滤复杂，此处待以后时间允许再实现
			if(options.isFiltering) {
				tableStr += 		  "<td class='h_c thb hbsh'>" 
										+ "<div class='h_divid'></div>" 
									+ "</td>"
									+ "<td class='h_c thb hbsh' width='100%'></td>";
			}	
			*/									
			tableStr += 			 "<td class='h_r'></td>"
							+ "</tr></table>" 
						+ "</td></tr>"; 
			return tableStr;
		}
		
		/* 创建分页内容标题及相关数据内容 */
		function createTbodyTarget() {
			var tableStr = "";
			tableStr += "<tr id='" + options.domId + "_BODY'><td class='tbod'><table cellSpacing='0' cellPadding='0' class='tbod_tb'>"; 
			
			//创建分页内容标题
			tableStr += "<tr class='ttitr treven'>";
			for(var i = 0, LENGTH = options.columns.length; i < LENGTH; i++) { //columns: [{}, {}];
				if(typeof(options.columns[i].width) != "undefine") {
					tableStr += "<td class='tc' style='width:" + options.columns[i].width + ";'>" 
									+ html2Str(options.columns[i].title) 
								+ "</td>";
				} else {
					tableStr += "<td class='tc'>" 
									+ html2Str(options.columns[i].title) 
								+ "</td>";
				}
			}	
			tableStr += "</tr>";
			
			//是否开启基本属性过滤功能
			if(options.isFiltering) { 
				tableStr += "<tr class='ttitr filter'>";
				for(var i = 0, LENGTH = options.columns.length; i < LENGTH; i++) { //columns: [{}, {}];
					var val = eval("options.fieldVals." + options.columns[i].field);
					if(val != null && val != "null" && val != "NULL") {
						
					} else {
						val = "";
					}
					if(typeof(options.columns[i].width) != "undefine") {
						tableStr += "<td class='tc' style='width:" + options.columns[i].width + ";'>" 
										+ "<input class='i_filter' type='text' prop='" + options.columns[i].field + "' value='" + val + "'/>" 
									+ "</td>";
					} else {
						tableStr += "<td class='tc'>" 
										+ "<input class='i_filter' type='text' prop='" + options.columns[i].field + "' value='" + val + "'/>" 
									+ "</td>";
					}
				}	
				tableStr += "</tr>";
			}
			
			//创建分页内容
			for(var i = 0, LENGTH = options.rows.length; i < LENGTH; i++) { //rows: [{}, {}];
				if(i % 2 == 0) { //偶数加奇数=奇数
					tableStr += "<tr class='tablerow trodd trhover'>";
				} else { //奇数加奇数=偶数
					tableStr += "<tr class='tablerow treven trhover'>";
				}
				for(var j = 0, COL_LENGTH = options.columns.length; j < COL_LENGTH; j++) { //columns: [{}, {}];
					tableStr += "<td class='tc'>" + html2Str(options.rows[i][options.columns[j].field]) + "</td>";
				}	
				tableStr += "</tr>";
				
			}
			tableStr += "</table></td></tr>"; 
			return tableStr;
		}
		
		/* 创建分页操作按钮 */
		function createTfootTarget() {
			var tableStr = "";
			tableStr += "<tr id='" + options.domId + "_FOOT'><td class='tbod fbouter'><table cellSpacing='0' cellPadding='0' class='tbod'>"; 
				tableStr += "<tr><td class='tbod tftd tsrs'>"
									+ "<table align='right'><tr>"
										+ "<td><span class='pagemsg'>第/" + options.rows.length + "条</span></td>"
										+ "<td><input class='firstpage' onfocus='this.blur()' type='button'></td>"
										+ "<td><input class='touppage' onfocus='this.blur()' type='button'></td>"
										+ "<td>第<input type='text' style='border:#bdbdbd 1px solid; background-color: #fff; width: 35px;' class='nowpage' ></td>" 
										+ "<td>/</td>"
										+ "<td><span class='totalpage'>0</span></td>"
										+ "<td>页</td>"
										+ "<td><input class='todownpage' onfocus='this.blur()' type='button'></td>"
										+ "<td><input class='lastpage' onfocus='this.blur()' type='button'></td>"
									+ "</tr></table>";
			tableStr += "</td></tr>";
			tableStr += "</table></td></tr>"; 
			return tableStr;
		}
		
		/* 字符串解析 */
		function html2Str(str) {
			if(str == "null" || str == "NULL") {
				return "";
			}
			str = ((str == null) ? "" : str);
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
			})
		}		
		
		/* 
		 * 选中行 
		 * rowIndex为选中的行号，从0起，
		 * 当rowIndex未指明号时（rowIndex<0 || rowIndex==undefine），
		 * 		应用rowKeyData中的键值获取行号，此值与keyFields需对应
		 **/
		function chooseRow(rowIndex, rowKeyData) {
			if(rowIndex < 0 || typeof(rowIndex) == "undefine") {
				var rowIndex = -1;
				for(var i = 0, LENGTH = options.rows.length; i < LENGTH; i++) { //rows: [{}, {}];
					var isFound = true;
					for(var j = 0, COL_LENGTH = options.keyFields.length; j < COL_LENGTH; j++) { //columns: [{}, {}];
						if(rowKeyData[j] != options.rows[i][options.keyFields[j]]) {
							isFound = false;
							break;
						}
					}	
					if(isFound) {
						rowIndex = i;
						break;
					}
				}
				if(rowIndex >= 0) {
					$("#" + options.domId + "_BODY").find("tr.trhover").eq(rowIndex).click();				
				}
			} else {
				if(rowIndex < options.rows.length) {
					$("#" + options.domId + "_BODY").find("tr.trhover").eq(rowIndex).click();
				}
			}
		}
		
		/* 重载数据 */
		function reloadData() {
			options.pageNumber = 0;//重新加载前需将页码指针初始化
			loadUrlData(); //加载分页数据
		}
		
		/* 重置附加查询条件 */
		function setAdditionParams(additionParams) {
			options.additionParams = additionParams;
		}
	}
})(jQuery);


