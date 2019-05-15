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
			window.parent.ctrlMgrSys.update(ctrlId, ctrlOper, getTagInnerHtmlStr(ctrlElemStr, "ctrldata"));
			dataResp = dataResp.substring(end + 11);
			start = dataResp.indexOf("<ctrlelem>");
			end = dataResp.indexOf("</ctrlelem>");
		}
	} else {
		$("#DIRECT_JS_CONTAINER").html(dataResp);	
	}
   	
   	return true;
}
