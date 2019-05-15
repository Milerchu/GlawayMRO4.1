var hiddenTip=false;
var isRegist;
var valObj = {
	errorClass : "errormessage",
	errorClass : 'error',
	validClass : 'valid',
	validId : true,

	errorPlacement :function (error, element) {
		var elem = $(element);
		//判断初始化完成后能否显示提示-----Begin
		//解决校验不通过的情况下，鼠标点击画面其它地方校验提示会再闪烁一下
		var canShow=true;
		var pstip=elem.data("poshytip");
		if(pstip && pstip.$tip){
			canShow=pstip.$tip.data('active')
		}
		//判断初始化完成后能否显示提示-----End
		elem.poshytip('disable');
		elem.poshytip('destroy');

		if (!error.is(':empty')) {
			//右：x=right;y=center
			//左：x=left;y=center
			//上：x=inner-left
			//下：x=center;y=bottom
			var aX = "center";
			if (elem.attr("positionX") != null) {
				aX = elem.attr("positionX");
			}
			var aY = "bottom";
			if (elem.attr("positionY") != null) {
				aY = elem.attr("positionY");
			}
			
			elem.filter(':not(.valid)').poshytip({
				className: 'tip-yellowsimple',
				content : '<div style="white-space:nowrap;"><img src="'+_basePath+'/base/images/validate_warning.gif" align="top" style="padding:1px 5px 1px 0px;height:14px;"/><span style="line-height:16px;height:16px;">'+$(error).html() + "</span></div>",
				showOn:"all",
				alignTo: 'target',
				alignX: 'inner-left',
				alignY: 'bottom',
				offsetX: 0,
				offsetY: 7

			});
			if(!hiddenTip && canShow){
				elem.poshytip("show");
			}

			elem.css("border","#ff0000 solid 1px");
		} else {
			elem.poshytip('disable');
			elem.poshytip('destroy');

			elem.css("border","1px solid #7F9DB9");
		}
	},
	success : $.noop,
	submitHandler : function (form) {
		// form.submit();
	},
	invalidHandler : function (e, validator) {
		// validator.errorList contains an array of objects, where each object
		// has properties "element" and "message". element is the actual HTML
		// Input.
		for (var i = 0; i < validator.errorList.length; i++) {
			if (i == 0) {
				validator.errorList[i].element.focus();
			}
		}

		// validator.errorMap is an object mapping input names -> error messages
		for (var i in validator.errorMap) {}
	}
};

function registValidate() {
    isRegist=true;
	$.validator.addMethod("cRequired", $.validator.methods.required, "Input name is required");

	$.validator.addMethod("Fomat2B",
		function (value, element, param) {
			var flag=true;
			if (value != null && value != "") {
				$(element).data("errorMsg", "");
				// value:输入的字段，param：
	
				/*
				 * 检查CODE 参数： value： 待检查的输入值（data） dataLen： 最大长度（dataLen） dataFomat：
				 * 数据格式（dataFomat） isLenfixed： 长度是否固定（isLenfixed） decimalLen： 小数位长度（decimalLen）
				 * startPos：起始位置（startPos）
				 */
				// 先去掉-，不考虑
				var str = param[0].replace('-', '');
				// console.log(str+"str");
				// ffgg段的起始（截了前面的数字ll的位置）
				var ffggStart;
				// ffgg段的起始（截了后面的数字decimalLen的位置）
				var ffggEnd;
				// ffgg字符串
				var ffggStr;
				// 位数，格式，是否固定长度，小数位
				var dataLen,
				dataFomat,
				isLenfixed,
				decimalLen;
				var pp1 = value;
				var compareId = param[2];
				var compare = param[1];
				// 决定数字类型是否允许负数		
				var _allowNegative = param[5];
	
				// 获得长度
				for (var i = 0; i <= str.length - 1; i++) {
					if (isNaN(parseInt(str.charAt(i)))) {
						dataLen = str.substr(0, i);
						ffggStart = i;
						break;
					}
				}
				// 获得允许的小数位
				for (var i = str.length - 1; i >= 0; i--) {
					if (isNaN(parseInt(str.charAt(i)))) {
						decimalLen = str.substr(i + 1);
						ffggEnd = i;
						break;
					}
				}
				// 获得ffgg字符串
				ffggStr = str.substr(ffggStart, ffggEnd - 1);
				// 开始循环ffgg，获得ff和gg
	
				var startPos = "";
				for (var i = 0; i <= str.length - 1; i++) {
					if (str.charAt(i) == 'A' || str.charAt(i) == 'N' || str.charAt(i) == 'X' || str.charAt(i) == 'D' || str.charAt(i) == 'B' || str.charAt(i) == 'E') {
						dataFomat = str.charAt(i);
					} else if (str.charAt(i) == 'F') {
						isLenfixed = str.charAt(i);
					} else if (str.charAt(i) == 'R' || str.charAt(i) == 'L') {
						startPos = str.charAt(i);
					}
				}
				var ll_Source = dataLen;
			
				if (null == isLenfixed || '' == isLenfixed) {
					if (dataFomat == "N" && value.indexOf(".") != -1 && decimalLen != 0) {
						ll_Source = parseInt(ll_Source) + 1;
					}
					// 实数类型的小数点占用总长度，但是负号不占用
					if (dataFomat == "D" && (_allowNegative == "true") && value.indexOf("-") != -1) {
						ll_Source = parseInt(ll_Source) + 1;
					}
					if (value.length > ll_Source) {
						$(element).data("errorMsg", "数据长度不超过" + ll_Source);
						return false;
					}
				}else if(isLenfixed=='F'){
					if(value.length != ll_Source){
				   		$(element).data("errorMsg", "数据长度必须为" + ll_Source);
						return false;
					}
				}
				switch (dataFomat) {
					// --如果是A的话，代表字母，我们只要判断 字母 即可
					case 'A':
						if(/^[a-zA-Z]+$/.test(value)){
			
						}else{
				   			$(element).data("errorMsg", "只能输入字母");
							return false;
						}
			    		break;
			    		
					// ---如果是D的话，代表数字，只要是数字就OK
					case 'D':
						var patten;
						// 负数情况
						if(_allowNegative=="true"){
							patten=/^-?\d+\.?\d*$/;
						}else{
							patten=/^\d+\.?\d*$/;
						}
						if(patten.test(value)){
			 			}else{
			 	   			$(element).data("errorMsg", "只能输入数字");
			 				return false;
			 			}
			    		break;
			
					// 如果是N的话，代表整数和小数，最麻烦的，判断 格式 + 小数位数
					case 'N':
						
						if((decimalLen==0 || isLenfixed=='F') && value.indexOf(".")!=-1){
						   $(element).data("errorMsg", "不允许有小数点");
			    			return false;
			    		}
			    		if(/^-?\d+\.?\d*$/.test(value)) {
							if(value.indexOf(".") != -1 && value.split(".")[1].length>decimalLen){
								 $(element).data("errorMsg", "超出数据精确范围");
								return false;
							}
			   			} else {
			   				 $(element).data("errorMsg", "只能输入数字");
							return false;
			   			}
			   			
			    		if(startPos == "R"||startPos == "L") { // 右计数小数
			    			
			    			if(0 <= value && value <= (Math.pow(10, dataLen) - 1) * Math.pow(10, -1 * decimalLen)) {
			    		
			    			} else {
			    				var mesTmp = "";
			    				for(var i = 0; i < dataLen; i++) {
			    					mesTmp += "9"
			    					if(i == dataLen - decimalLen-1) {
			    						mesTmp += ".";
			    					}
			    					
			    				}
			    				$(element).data("errorMsg", "数据不大于" +　mesTmp);
			    				return false;
			    			}
			    		}
			    		
			   			break;
			   		// ---如果是B的话，代表数字和大写字母
					case 'B':
						if(/^[A-Z0-9]+$/.test(value)){
			 	
			 			}else{
			 	   			$(element).data("errorMsg","只能输入大写字母和数字");
			 				return false;
			 			}
			    		break;			
			    	// ---如果是E的话，代表数字、大写字母和‘-’
					case 'E':
						if(/^[A-Z0-9\-]+$/.test(value)){
			 	
			 			}else{
			 	   			$(element).data("errorMsg","只能输入大写字母、数字或‘-’");
			 				return false;
			 			}
			    		break;	
					default :
				}
				if(compareId != null && compare != null && compareId != 'undefined' && compare != 'undefined') {
					if(document.getElementById(compareId)!=null){
					
						var aa = document.getElementById(compareId).value;
			    		if(dataFomat == 'D' || dataFomat == 'N') { // 数字型对比，需将输入数据转换为数值
			    			if(param[3]&&param[4])
			    			{
			    				flag =multipleCompareValues(parseFloat(pp1), compare, parseFloat(aa),param[3],parseFloat($("#" + param[4]).val()),element)
			    			}else{
			    				flag = comparevalues(parseFloat(pp1), compare, parseFloat(aa),element); 
			    			}
			    		} else {
			    			flag = comparevalues(pp1, compare, aa , element); 
			    		}	
					}
					
		    	}
				return flag;
			} else {
				//$(element).qtip('destroy');
				return true;
			}
		}, 
		function (params, element) {
			return $(element).data("errorMsg")
		}
	);

}


function validate(_id, _required, _format) {

	if(!isRegist){
		registValidate();
	}
	var isString=_id.split(".");
	if(isString[1]!=undefined&&isString[0].indexOf("\\")==-1) {
		_id=isString[0]+"\\."+isString[1];
	}
		
	if ($("#" + _id).length > 0) {
		
		var form = $("#" + _id).closest('form');
		if (form.length == 1) {
			if(!(form.data("hasValidate")=="hasV")){
				form.validate(valObj);
				form.data("hasValidate","hasV");
			}
		}else{
			alert(_id+"没有form!");
		}
	}else
	{
	
	alert(_id+"不要验证");
	}
	
	$("#" + _id).rules("add", {
		required : _required,
		messages : {
			required : '必填项！'
		}
	});
	
	$("#" + _id).rules("add", {
		Fomat2B :[ _format]
	});
}

$.glawayValidator = function (options, invalidObj) {
	var requered = "false";
	var ispass = true;
	var len = options.validatordata.length;
	var forms=[];
	var fields=[];
	for (var i = 0, a = len; i < a; i++) {
		var selectid = options.validatordata[i][0];
		requered = options.validatordata[i][1];
		
		var isString=selectid.split(".");
		if(isString[1]!=undefined)
		{
			selectid=isString[0]+"\\."+isString[1];
		}
		
		//var $tmp = (selectid.indexOf('.') >= 0) ? $('[id="' + selectid + '"]') : $('#' + selectid);
		var $tmp=$('#' + selectid);
		if (requered) {
			$tmp.val($.trim($tmp.val()));
		}
		var format = options.validatordata[i][2];
		if (!options.validatordata[i][3] && !options.validatordata[i][4]) {
			if (options.validatordata[i][5]) {
				validate(selectid, requered, format, null, null, options.validatordata[i][5]);
			} else {
		
				validate(selectid, requered, format);
				
			
			}
		}
		if (options.validatordata[i][3] && options.validatordata[i][4]) {
			validate(selectid, requered, format, options.validatordata[i][3], options.validatordata[i][4]);
		}
		
		if(options.validatordata[i][5] && options.validatordata[i][6])
		{
		validate(selectid, requered, format, options.validatordata[i][3], options.validatordata[i][4], options.validatordata[i][5],options.validatordata[i][6]);
		}
		
		var tmpFd = $tmp;
		if(tmpFd.length==1){
			var hasExist=false;
			for(var j=0;j<fields.length;j++){
				if(tmpFd[0]==fields[j][0]){
					hasExist=true;
					break;
				}
			}
			if(!hasExist){
				fields.push(tmpFd);
			}
		}			
	}
	
	for(var i=0;i<fields.length;i++){
		var tmpPass=true;
		hiddenTip=true;
		tmpPass=fields[i].valid();
		hiddenTip=false;
		if(!tmpPass){
			ispass=false;
			continue;
		}
	}
	fields=null;
	return ispass;
	
};

