/* 显示提示 */
var TIP_X_HOLD = false;
function showTip($target, $tip) {
	var cW = $target.parent().width();
	var cH = $target.parent().height();
	var cT = $target.parent().offset().top;
	var cL = $target.parent().offset().left;
	
	var tT = $target.position().top;
	var tL = $target.position().left;
	var tW = $target.width();
	var tH = $target.height();
	
	var tipW = $tip.find("table.content").width();
	var tipH = $tip.find("table.content").height();
	if((tL + tW/2.0) <= (cW/2.0)) {
		$tip.offset({ top: (cT + tT + tH/2.0 - tipH/2.0), left: (cL + tL + tW + 15) });
		$("#tipPointer").removeClass().addClass("pointer_w");
		$tip.find("#tipPointer").offset({ top: (cT + tT + tH/2.0 - 6.5), left: (cL + tL + tW) });
	} else {
		$tip.offset({ top: (cT + tT + tH/2.0 - tipH/2.0), left: (cL + tL - 315) });
		$("#tipPointer").removeClass().addClass("pointer_e");
		$tip.find("#tipPointer").offset({ top: (cT + tT + tH/2.0 - 6), left: (cL + tL -15) });
	}
	$tip.show();
}

function hideTip() {
	if(!TIP_X_HOLD) {
		$("#tip_x").hide();
	}
}