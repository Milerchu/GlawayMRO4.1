/**
 * 程序控制在地图上画点
 */
function markData(markerId, lon, lat, title, eqType, model, location, hearUrl, remark, tipCont) {	
	if(remark == "R"){
		var marker = new OpenLayers.Marker(new OpenLayers.LonLat(lon, lat).transform(geographic, mercator), markerIconR.clone()); //创建标记
		markerRArray.push(marker);
	}else if(remark == "B"){
		var marker = new OpenLayers.Marker(new OpenLayers.LonLat(lon, lat).transform(geographic, mercator), markerIconB.clone()); //创建标记
	}
	markerBArray.push(marker);
	regMarkerEvt(markerId, marker, title, eqType, model, location, hearUrl, tipCont); //注册标记事件
    markers.addMarker(marker); //将标记添加至标记集合内，以在前端显示
    
}

/* 注册标记事件 */
function regMarkerEvt(markerId, marker, title, eqType, model, location, hearUrl, tipCont) {
	if(marker != null) {
		/*marker.events.register('mousemove', marker, function(evt) { mousemoveEvt(evt.clientX, evt.clientY, markerId, marker, title, content); });
		marker.events.register('mousedown', marker, function(evt) { mousedownEvt(); });
		marker.events.register('mouseout', marker, function(evt) { mouseoutEvt(); });*/
		
		var $markerImg = $(marker.icon.imageDiv).find("img");
		if($markerImg) {
			$markerImg.bind("mouseenter", function() {
				var evtX = $(this).offset().left;
				var evtY = $(this).offset().top;
				//mousemoveEvt(evtX, evtY, markerId, title, content);
				evtX = evtX + 12.5;
				evtY = evtY + 40;
				showTip(evtX, evtY, title, tipCont);
			}).bind("mousedown", function() {
				//mousedownEvt();
//				holdTip();
				//alert(111);
				window.parent.$("#perLocationDiv").dialog('setTitle',title);
				initRYXX4DateGrid(eqType,model,location, hearUrl);
				initWCRW4DateGrid(eqType,model,location, hearUrl);
				initZBXX4DateGrid(eqType,model,location, hearUrl);
				window.parent.$('#tt').tabs('select',0);
				window.parent.$("#perLocationDiv").dialog('open');
			}).bind("mouseout", function() {
				//mouseoutEvt();
				hideTip();
			}).css({ cursor: "pointer" });
		}
	}
}
function initRYXX4DateGrid(eqType,model,location, hearUrl){
        var url = hearUrl + "gis?location=" + location + "&method=ryxx4DateGrid";
		$.ajax({
			type: "post",
			url: url,
			dataType: "jsonp",
			jsonp: "callback",
			success: function(json){
				window.parent.$("#ryxx").datagrid({
					fit:true,
					fitColumns:true,
					singleSelect:true,
					pagination:true,
					columns:[[
						{field:'personId',title:'人员ID',width:50,align:'center'},
						{field:'displayName',title:'名称',width:80,align:'center'},
						{field:'fieldtasknum',title:'外场任务编号',width:80,align:'center'},
						{field:'description',title:'任务内容',width:100,align:'center'}
					]]	
				}).datagrid('loadData',json[0]);
			}
		});
}
function initWCRW4DateGrid(eqType,model,location, hearUrl){
        var url = hearUrl + "gis?location=" + location + "&method=wcrw4DateGrid";
		$.ajax({
			type: "post",
			url: url,
			dataType: "jsonp",
			jsonp: "callback",
			success: function(json){
				window.parent.$("#wcrw").datagrid({
					fit:true,
					fitColumns:true,
					singleSelect:true,
					pagination:true,
					columns:[[
						{field:'fieldtasknum',title:'任务编号',width:50,align:'center'},
						{field:'description',title:'任务内容',width:80,align:'center'},
						{field:'personstr',title:'人员',width:100,align:'center'}
					]]	
				}).datagrid('loadData',json[0]);
			}
		});
}
function initZBXX4DateGrid(eqType,model,location, hearUrl){
        var url =  hearUrl + "gis?location=" + location + "&method=zbxx4DateGrid&eqType="+eqType;
		$.ajax({
			type: "post",
			url: url,
			dataType: "jsonp",
			jsonp: "callback",
			success: function(json){
				window.parent.$("#zbxx").datagrid({
					fit:true,
					fitColumns:true,
					singleSelect:true,
					pagination:true,
					onClickRow:function(index,data){
						//alert(data.status);
					},
					columns:[[
						{field:'assetnum',title:'装备序列号',width:80,align:'center'},
						{field:'description',title:'站点',width:150,align:'center'},
						{field:'productcode',title:'产品代号',width:80,align:'center'},
						{field:'productorderno',title:'生产令号',width:80,align:'center'},
						{field:'armytype',title:'军兵种',width:80,align:'center'},
						{field:'status',title:'装备状态',width:80,align:'center'}
					]]	
				}).datagrid('loadData',json[0]);
			}
		});
}

/**
 * 清除所有标记
 */
function clearMarkers() {
	if(markers != null) {
		//markers.markers为javascript的Array，最好使用此方法来移除标记
		while(markers.markers.length > 0) { 
			var marker = markers.markers[0];
			markers.removeMarker(marker);
			marker.destroy();
		}
	}
}

/* 标记的鼠标移动事件 */
function mousemoveEvt(evtX, evtY, markerId, title, content) {
	showTip(evtX, evtY, title, content);
}
/* 标记的鼠标点击事件 */
function mousedownEvt() {
	//holdTip();
	
}
/* 标记的鼠标移除事件 */
function mouseoutEvt() {
	hideTip();
}


/******* tip start ********/
var tipX = {
	isHold: false
};
function defaultTipX() {
	tipX.isHold = false;
}


/* 显示提示 */
function showTip(evtX, evtY, title, content) {
	var $map = $("#map");
	var cW = $map.width();
	var cT = $map.offset().top;
	                	
	var $tip = $("#tip_x");
	$tip.find("#tipTitle").html(title);//$tip.find("#tipTitle").html("南京军区");
	$tip.find("#tipContent").html(content);
	
	$tip.show();
	var tipW = $tip.find("table.content").width();
	var tipH = $tip.find("table.content").height();
	if(evtX <= (cW/2.0)) {
		$tip.offset({ top: (evtY - tipH/2), left: (evtX + 15) });
		$("#tipPointer").removeClass().addClass("pointer_w");
		$tip.find("#tipPointer").offset({ top: (evtY - 7.5), left: (evtX) });
	} else {
		$tip.offset({ top: (evtY - tipH/2), left: (evtX - tipW - 15) });
		$("#tipPointer").removeClass().addClass("pointer_e");
		$tip.find("#tipPointer").offset({ top: (evtY - 7.5), left: (evtX - 15) });
	}
}
/* 隐藏提示 */
function hideTip() {
	$("#tip_x").hide();
}
/* 固化提示 */
function holdTip() {
	tipX.isHold = true;
}
/* 最大化提示 */
function maxTip() {
	
}
/* 关闭提示 */
function closeTip() {
	$("#tip_x").hide();
}

function showBlueMark(){
	markers.clearMarkers();
	for(var i=0; i<markerBArray.length; i++){
    	markers.addMarker(markerBArray[i]); //将标记添加至标记集合内，以在前端显示
	}
}
function showRedMark(){
	markers.clearMarkers();
	for(var i=0; i<markerRArray.length; i++){
    	markers.addMarker(markerRArray[i]); //将标记添加至标记集合内，以在前端显示
	}
}

