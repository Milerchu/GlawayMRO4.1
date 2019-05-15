var GW_PRECISION = 2; //默认精确度
/* 
 * 设置浮点数精度 
 * decimal precision
 */
function GW_DEC_PRC(fv, precision) {
	if(typeof(precision) != "undefined") {
		if(precision <= 20 && precision >= 0) {
			return fv.toFixed(precision);
		}
	} else {
		return fv.toFixed(GW_PRECISION);
	}
}

/* 获取百分比 */
function GW_PCT(count, total) {
	if(total == 0) {
		return 0;
	} else {
		return GW_DEC_PRC(count * 100.0 / total); //返回带精度浮点数
	}
}