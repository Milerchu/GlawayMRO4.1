/*
 * jQuery Select Plugins v1.3.6.1
 */
 /*
 *模拟Select方法
 		全部模拟：$("select").sSelect();
		分开模拟:$('#ID').sSelect({panelWidth:250});
*模拟Select取值
		用模拟前的ID对于的JQUERY方法:$('#ID').val()
*模拟Select赋值
		$("#ID").sCombobox("setValue","test");
		或
		$("#ID").setValue("test");
* 禁用Select方法
		$('#ID').disable(true);
* 模拟的Select支持onchange事件（SELECT联动）
		$('#ID1').change(function(){
			var getVal = $('#ID1 option:selected').val();
				$('#ID2').empty().append('<option val="cy">朝阳区</option><option val="hd">海淀区</option><option val="ft">丰台区</option>');
			
			$('#ID2').sSelect({panelWidth:150});
		});
* options中增加参数：redStar:true|false  决定是否显示红色的*号，默认不显示
 	
 */

(function($){
	$.fn.sSelectvol = function (options) {
		var defaults = {tCode:options.tcode, tval:"", dataresult:"select", panelWidth:"150", panelHeight:"250",volchecked:true,redStar:false};
		options = $.extend(defaults, options);
		dataresult = options.dataresult;
		volchecked=options.volchecked;
		tval = options.tval;
		
		var panelHeight = options.panelHeight;
		var panelWidth = options.panelWidth;
		
		var codeValCn = "";
		var thisSelect = $(this);
		$.ajax({type:"GET", async:false, data:"D_CODE=" + options.tcode, cache:false, url:options.basepath + "tipCode/getCodeValByCode.action", dataType:"json", success:function (dataResponse) {
			var option = "";
			if(volchecked){
			
			option="<option  value=''>" + "-- 请选择 --" +"</option>";}
			for (var j = 0; j < dataResponse.tvol.length; j++) {
				option += "<option  value=\"" + dataResponse.tvol[j].vol + "\">" + dataResponse.tvol[j].vol + "&nbsp;-&nbsp;" + dataResponse.tvol[j].loven + "</option>";
			}
			if (dataresult != "text") {
				$(thisSelect).html(option);
			}
			
		}});
		var optionsel=thisSelect.find("option");
		var dataOptions=new Array();
		if(optionsel){
			for(var i=0;i<optionsel.length;i++){
				dataOptions[i]=new Object();
				dataOptions[i].id=$(optionsel[i]).val();
				dataOptions[i].text=$(optionsel[i]).html();
				if(i==0){
					dataOptions[i].selected=true;
				}
			}
		}
		$(thisSelect).sSelect({
			panelWidth:panelWidth, 
			panelHeight:panelHeight,
			redStar:options.redStar
		});
		if(tval){
		  $(thisSelect).sCombobox("setValue",tval);
		}
		return codeValCn;
	};
$.fn.extend({
	sSelect: function(options) {
		//panelWidth:"150", panelHeight:"250"
		var myDate1=new Date();
		var defaults = {};
		options = $.extend(defaults, options);

		return this.each(function(i,obj){
		$(this).css("border","0");
		var selectId = (this.id || this.name)+'__jQSelect'+i||'__jQSelect'+i;
		if(obj.style.display != 'none' && $(this).parents()[0].id.indexOf('__jQSelect')<0){
		var tabindex = this.tabIndex||0;
		$(this).before("<div class='dropdown' id="+selectId+" tabIndex="+tabindex+"></div>").prependTo($("[id='"+selectId+"']"));
		
		var curSelectDom=$("[id='"+selectId+"']");
		var selectZindex = $(this).css('z-index'),selectIndex =curSelectDom.find('option').index(curSelectDom.find('option:selected')[0]);
		curSelectDom.append('<div class="dropselectbox"><h4></h4><ul></ul></div>');
		curSelectDom.find('h4').empty().append("<span class=\"dropText\">"+curSelectDom.find('option:selected').text()+"</span>"+"<h5></h5>");
		var tmpTitle=curSelectDom.find('.dropText').html()==null?"":(curSelectDom.find('.dropText').html().replace(new RegExp("(([ ]{2,})|[\t]|[\r\n]|[\r])+","gmi"),"").replace(new RegExp("(&nbsp;)+","gmi")," "));
		curSelectDom.find('.dropText').attr("title",tmpTitle.replace(new RegExp("(([ ]{2,})|[\t]|[\r\n]|[\r])+","gmi"),""));
		var selectWidth=curSelectDom.find('select').width();
		if(selectWidth<=0 && curSelectDom.find('select').css("width")!=undefined){
			var test=curSelectDom.find('select').css("width").split("px");
			
			if(test.length==2){
				selectWidth=test[0];
			}else{
				selectWidth=curSelectDom.find('select').actual("width");
			}
		}
		if($.browser.safari){
			//selectWidth = parseInt(selectWidth)+15
		}
		curSelectDom.find('h4').css({width:selectWidth});
		
		curSelectDom.find('h4 span').css("width",(selectWidth-17));
		curSelectDom.find('h4 h5').css("width",12);

		var selectUlwidth;
		if(options.panelWidth){
			selectUlwidth=options.panelWidth;
		}else{
			selectUlwidth= selectWidth + parseInt(curSelectDom.find('h4').css("padding-left")) + parseInt(curSelectDom.find('h4').css("padding-right"));
		}
		var selectUlheight;
		if(options.panelHeight){
			selectUlheight=options.panelHeight;
		}
		var myDate2=new Date();
		curSelectDom.find('ul').css({width:selectUlwidth+'px'});
		if(selectUlheight){
			curSelectDom.find('ul').css("height",selectUlheight+"px");	
		}
		var myDate3=new Date();
		curSelectDom.find('select').hide();
		curSelectDom.find('div').hover(function(){
			//如果disable的话就不做后续处理
			if(curSelectDom.find('h4.disable').length>0){
				return false;
			}
			curSelectDom.find('h4').css("border-color","#369");
			curSelectDom.find('h5').addClass("over");
		},function(){
			//如果disable的话就不做后续处理
			if(curSelectDom.find('h4.disable').length>0){
				return false;
			}
			curSelectDom.find('h4').css("border-color","#AAA");
			curSelectDom.find('h5').removeClass("over");
		});
		if(options.redStar){
			curSelectDom.wrap("<div style=\"float:left\"></div>")
			curSelectDom.parent().after("<span style=\"color:red;float:left;line-height:22px;height:22px;padding-left:1px;\">*</span>");
		}
		
		curSelectDom
		.bind("focus",function(){
			//$.fn.clearSelectMenu(selectId,selectZindex);
			$(this).find('h4').addClass("over");
		})
		.bind("blur",function(){
			$.fn.clearSelectMenu(selectId,selectZindex);
			return false;
		})
		.bind("click",function(e){ 	
			var thisRef=this;
			//如果disable的话就不做后续处理
			if($(this).find('h4.disable').length>0){
				return false;
			}
		
			if($(this).find('ul').css("display") == 'block'){
				$.fn.clearSelectMenu(selectId,selectZindex);
				return false;
			}else{
				$(this).focus();
				
				$(this).find('h4').addClass("current");
				$(this).find('ul').show();
				var selectZindex = $(this).css('z-index');
				if ($.browser.msie || $.browser.opera){$('.dropdown').css({'position':'relative','z-index':'0'});}
				$(this).css({'position':'relative','z-index':9010});
				$.fn.setSelectValue(selectId);
				//selectIndex = $(this).find('li').index($('.selectedli')[0]);

				var windowspace = ($(window).scrollTop() + document.documentElement.clientHeight) - $(this).offset().top;
				var ulspace = $(this).find('ul').outerHeight(true);
				var windowspace2 = $(this).offset().top - $(window).scrollTop() - ulspace;
				
				$(this).find('ul').css("top","20px");
				//windowspace < ulspace && windowspace2 > 0?$(this).find('ul').css({top:-ulspace}):$(this).find('ul').css({top:$('#'+selectId+' h4').outerHeight(true)});
				//$(window).scroll(function(){
				//	windowspace = ($(window).scrollTop() + document.documentElement.clientHeight) - $('#'+selectId).offset().top;
				//	windowspace < ulspace?$(this).find('ul').css({top:-ulspace}):$(this).find('ul').css({top:$('#'+selectId+' h4').outerHeight(true)});
				//});	
		
				$(this).find('li').click(function(e){
						selectIndex = $(thisRef).find('li').index(this);
						$.fn.keyDown(selectId,selectIndex);
						
						$(thisRef).find('.dropText').empty().append($(thisRef).find('option:selected').text());
						var tmpTitle=$(thisRef).find('.dropText').html()==null?"":($(thisRef).find('.dropText').html().replace(new RegExp("(([ ]{2,})|[\t]|[\r\n]|[\r])+","gmi"),"").replace(new RegExp("(&nbsp;)+","gmi")," "));
						$(thisRef).find('.dropText').attr("title",tmpTitle);
						$.fn.clearSelectMenu(selectId,selectZindex);
						
						e.stopPropagation();
						e.cancelbubble = true;
				});
				
				
				$(this).find('li').hover(
					   function(){
							//$(this).addClass("over").addClass("selectedli");
							$(this).addClass("over");
							selectIndex = $(thisRef).find('li').index(this);
						},
						function(){
							$(this).removeClass("over");
						}
				);
			};
			e.stopPropagation();
		})
		 .bind('mousewheel', function(e,delta) {
		 		//如果disable的话就不做后续处理
				if($(this).find('h4.disable').length>0){
					return false;
				}
				//e.preventDefault();
				var mousewheel = {
					$obj : $(this).find('li.over'),
					$slength : $(this).find('option').length,
					mup:function(){
						this.$obj.removeClass("over");
						selectIndex == 0?selectIndex = 0:selectIndex--;
						$.fn.keyDown(selectId,selectIndex);
					},
					mdown:function(){
						this.$obj.removeClass("over");
						selectIndex == (this.$slength - 1)?selectIndex = this.$slength - 1:selectIndex ++;
						$.fn.keyDown(selectId,selectIndex);
					}
				}
				delta>0?mousewheel.mup():mousewheel.mdown();
		 })
		.bind("dblclick", function(){
			$.fn.clearSelectMenu(selectId,selectZindex);
			return false;
		})
		.bind("keydown",function(e){
			//如果disable的话就不做后续处理
			if($(this).find('h4.disable').length>0){
				return false;
			}
			$(this).bind('keydown',function(e){
				if (e.keyCode == 40 || e.keyCode == 38 || e.keyCode == 35 || e.keyCode == 36){
					return false;
				}
			});
			var $obj = $(this).find('li.over'),$slength = $(this).find('option').length;
			switch(e.keyCode){
				case 9:
					return true;
					break;
				case 13:
					//enter
					$.fn.clearSelectMenu(selectId,selectZindex);
					break;
				case 27:
					//esc
					$.fn.clearSelectMenu(selectId,selectZindex);
					break;
				case 33:
					$obj.removeClass("over");
					selectIndex = 0;
					$.fn.keyDown(selectId,selectIndex);
					break;
				case 34:
					$obj.removeClass("over");
					selectIndex = ($slength - 1);
					$.fn.keyDown(selectId,selectIndex);
					break;
				case 35:
					$obj.removeClass("over");
					selectIndex = ($slength - 1);
					$.fn.keyDown(selectId,selectIndex);
					break;
				case 36:
					$obj.removeClass("over");
					selectIndex = 0;
					$.fn.keyDown(selectId,selectIndex);
					break;
				case 38:
					//up
					e.preventDefault();
					$obj.removeClass("over");
					selectIndex == 0?selectIndex = 0:selectIndex--;
					$.fn.keyDown(selectId,selectIndex);
					break;
				case 40:
					//down
					e.preventDefault();
					$obj.removeClass("over");
					selectIndex == ($slength - 1)?selectIndex = $slength - 1:selectIndex ++;
					$.fn.keyDown(selectId,selectIndex);
					break;
				default:
					e.preventDefault();
					break;
			};
		})
		.bind("selectstart",function(){
				return false;
		});
	}else if($(this).parents()[0].id.indexOf('__jQSelect')>0){
		$.fn.setSelectValue(selectId);
		var selectWidth=$("[id='"+selectId+"']"+' select').width();
		if($.browser.safari){selectWidth = selectWidth+15}
		$("[id='"+selectId+"']"+' h4').css({width:selectWidth});
		
		
		//var selectUlwidth = selectWidth + parseInt($("[id='"+selectId+"']"+' h4').css("padding-left")) + parseInt($("[id='"+selectId+"']"+' h4').css("padding-right"));
		//$("[id='"+selectId+"']"+' ul').css({width:selectUlwidth+'px'});
		var selectUlwidth;
		if(options.panelWidth){
			selectUlwidth=options.panelWidth;
		}else{
			selectUlwidth= selectWidth + parseInt($("[id='"+selectId+"']"+' h4').css("padding-left")) + parseInt($("[id='"+selectId+"']"+' h4').css("padding-right"));
		}
		var selectUlheight;
		if(options.panelHeight){
			selectUlheight=options.panelHeight;
		}
		$("[id='"+selectId+"']"+' ul').css({width:selectUlwidth+'px'});
		if(selectUlheight){
			$("[id='"+selectId+"']"+' ul').css("height",selectUlheight+"px");	
		}
		
		if(this.style.display != 'none'){$(this).hide();}
	}
	var myDate4=new Date();
	//alert("Break1  "+myDate1.getSeconds()+":"+myDate1.getMilliseconds()+
	//				",Break2    "+myDate2.getSeconds()+":"+myDate2.getMilliseconds()+
	//				",Break3     "+myDate3.getSeconds()+":"+myDate3.getMilliseconds()+
	//				",Break4     "+myDate4.getSeconds()+":"+myDate4.getMilliseconds());
					
	})},
	clearSelectMenu:function(selectId,selectZindex){
		if(selectId != undefined && selectId!=""){
			var Zindex=0;
			if(selectZindex){
				Zindex = selectZindex
			}
			var curOperSelect=$("div[id='"+selectId+"']");
			curOperSelect.find("ul").unbind();
			curOperSelect.find("ul").find("*").unbind();
			curOperSelect.find("ul").empty().hide();
			curOperSelect.find("h4").removeClass("over").removeClass("current");
			curOperSelect.css({'z-index':Zindex});
		}
	},
	setSelectValue:function(sID){
		if(sID==""){
			return;
		}
		var curOperSelect=$("div[id='"+sID+"']");
		var content = [];
		$.each(curOperSelect.find("option"), function(i){
			content.push("<li class='FixSelectBrowser'>"+$(this).text()+"</li>");
		});
		content = content.join('');
		curOperSelect.find("ul").html(content);
		curOperSelect.find(".dropText").html(curOperSelect.find('option:selected').text());
		var tmpTitle=curOperSelect.find('.dropText').html()==null?"":(curOperSelect.find('.dropText').html().replace(new RegExp("(([ ]{2,})|[\t]|[\r\n]|[\r])+","gmi"),"").replace(new RegExp("(&nbsp;)+","gmi")," "));
		curOperSelect.find('.dropText').attr("title",tmpTitle);
		if(curOperSelect.find('select').length>0){
			curOperSelect.find("li").eq(curOperSelect.find("select")[0].selectedIndex).addClass("over").addClass("selectedli");
		}
	},
	setValue:function(value){
		$(this).val(value);
		$(this).trigger("change");
		$.fn.setSelectValue($(this).parent().attr("id"));
	},
	sCombobox:function(oper,parm){
		if(oper=="setValue"){
			$(this).setValue(parm);
		}else if(oper=="getValue"){
			return $(this).val(); 
		}
	},
	disable:function(b){
		var operId=$(this).parent().attr("id");
		if(b){
			$("[id='"+operId+"']"+' h4').removeClass();
			$("[id='"+operId+"']"+' h4').addClass("disable");
		}else{
			$("[id='"+operId+"']"+' h4').removeClass("disable");
		}
	},
	keyDown:function(sID,selectIndex){
		var $obj = $("[id='"+sID+"']"+' select');
		$obj[0].selectedIndex = selectIndex;
		$obj.change();
		$("[id='"+sID+"']"+' li:eq('+selectIndex+')').toggleClass("over");
		$("[id='"+sID+"']"+' .dropText').html($("[id='"+sID+"']"+' option:selected').text());
		var tmpTitle=$("[id='"+sID+"']"+' .dropText').html()==null?"":($("[id='"+sID+"']"+' .dropText').html().replace(new RegExp("(([ ]{2,})|[\t]|[\r\n]|[\r])+","gmi"),"").replace(new RegExp("(&nbsp;)+","gmi")," "));
		$("[id='"+sID+"']"+' .dropText').attr("title",tmpTitle);
	}
});
var types = ['DOMMouseScroll', 'mousewheel'];
$.event.special.mousewheel = {
	setup: function() {
		if ( this.addEventListener )
			for ( var i=types.length; i; )
				this.addEventListener( types[--i], handler, false );
		else
			this.onmousewheel = handler;
	},	
	teardown: function() {
		if ( this.removeEventListener )
			for ( var i=types.length; i; )
				this.removeEventListener( types[--i], handler, false );
		else
			this.onmousewheel = null;
	}
};
$.fn.extend({
	mousewheel: function(fn) {
		return fn ? this.bind("mousewheel", fn) : this.trigger("mousewheel");
	},
	
	unmousewheel: function(fn) {
		return this.unbind("mousewheel", fn);
	}
});
function handler(event) {
	var args = [].slice.call( arguments, 1 ), delta = 0, returnValue = true;
	event = $.event.fix(event || window.event);
	event.type = "mousewheel";	
	if ( event.wheelDelta ) delta = event.wheelDelta/120;
	if ( event.detail     ) delta = -event.detail/3;
	args.unshift(event, delta);
	return $.event.handle.apply(this, args);
}
})(jQuery);

