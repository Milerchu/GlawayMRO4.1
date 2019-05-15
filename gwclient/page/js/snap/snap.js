//snap类：缩略图
function glaway_snap(elementid)
{  
	var obj;
	try
	{
		obj = document.getElementById(elementid);
	}
	catch (err)
	{
		alert(elementid+" NOT Found!");
	}
	return obj;
}

function setElementsDisplay(es,s){
	for(var n=0;n<es.length;n++){
		if(glaway_snap(es[n])){
			glaway_snap(es[n]).style.display = ((s[n])?"":"none");
		}
	}
}

function SNAP(){
	var ctrlId = "";
	var _t=this;
	_t.hasInit=false;
	_t.ID="snapdiv";
	_t.X=0;
	_t.Y=0;
	_t.W = 300 ;	_t.H = 200 ;	//Default Size
	_t.fixX = -4;	_t.fixY = 10;	//Fix position
	_t.mX = 0;		_t.mY = 0;		//Mouse Position
	_t.eX = 0;		_t.eY = 0;		//Event Position
	_t.maxW = 800;	_t.maxH = 500;	_t.minW =300; _t.minH =200;		//Width & Height Range
	_t.title ;
	_t.url ;
	_t.html ;
	_t.isShow = false;
	_t.showNum = 0;
	_t.showDelayMouseOver = 10;	//Show Delay. Default : 0.5s
	_t.hideDelayMouseOver = 800;	//Hide Delay. Default : 0.8s
	_t.showDelayClick = 0;	//Show Delay. Default : 0.5s
	_t.hideDelayClick = 800;	//Hide Delay. Default : 0.8s
	
	_t.showDelay = _t.showDelayMouseOver;
	_t.hideDelay = _t.hideDelayMouseOver;	
	
	_t.eSrc;
	_t.st;
	_t.ht;
	_t.ifrBlank = "about:blank";
	_t.setSize = function(w,h,v){
		//v==true:change the window's size and show immediately.
		if(v==false)
		{
			setElementsDisplay([_t.ID,_t.ID+"_pointer",_t.ID+"_html",_t.ID+"_loading",_t.ID+"_iframe"],[false,false,false,true,false]);
		}
		_t.W =w;
		_t.H =h;
		var elemSize = [
			{ID:_t.ID,w:_t.W,h:_t.H},
			{ID:_t.ID+"_main",w:(_t.W-20),h:20},
			{ID:_t.ID+"_title",w:(_t.W-76),h:20},
			{ID:_t.ID+"_content",w:(_t.W-20),h:(_t.H-46)},
			{ID:_t.ID+"_html",w:(_t.W-20),h:(_t.H-46)},
			{ID:_t.ID+"_loading",w:(_t.W-20),h:(_t.H-46)},
			{ID:_t.ID+"_iframe",w:(_t.W-20),h:(_t.H-46)},
			{ID:_t.ID+"_ifr",w:(_t.W-20),h:(_t.H-46)},

			{ID:_t.ID+"_123",w:_t.W,h:27},
			{ID:_t.ID+"_1",w:38,h:27},
			{ID:_t.ID+"_2",w:(_t.W-76),h:27},
			{ID:_t.ID+"_3",w:38,h:27},
			
			{ID:_t.ID+"_456",h:(_t.H-46)},
			{ID:_t.ID+"_4",w:38,h:(_t.H-46)},
			{ID:_t.ID+"_6",w:38,h:(_t.H-46)},

			{ID:_t.ID+"_789",h:19},
			{ID:_t.ID+"_7",w:38,h:19},
			{ID:_t.ID+"_8",w:(_t.W-76),h:19},
			{ID:_t.ID+"_9",w:38,h:19}
		]
		var curE;
		for(var n=0;n<elemSize.length;n++){
			curE=elemSize[n];
			if(glaway_snap(curE.ID)){
				if(curE.w !=undefined)glaway_snap(curE.ID).style.width = curE.w +"px";
				if(curE.h !=undefined)glaway_snap(curE.ID).style.height = curE.h +"px";
			}
		}
		
	}

	_t.larger = function(){
		var tw = _t.W + 80;
		var th = _t.H + 80;
		if(tw>_t.maxW||th>_t.maxH)return;
		_t.setSize(tw,th);
		_t.showDiv();
	}

	_t.smaller = function(){
		var tw = _t.W - 50;
		var th = _t.H - 50;
		if(tw<_t.minW||th<_t.minH)return;
		_t.setSize(tw,th);
		_t.showDiv();
	}

	_t.preInit = function(){
		if(!glaway_snap(_t.ID+"_out")){
			//这里为了全局考虑，不然页面上方会空出一些，加了margin:-10 0 0 -10;
			document.write("<div id=\""+_t.ID+"_out\" style=\"width:0px;height:0px;margin:0;padding:0px\">"+_t.ID+"_out</div>");
		}
		else{
			glaway_snap(_t.ID+"_out").innerHTML = "";
		}
		document.getElementById(_t.ID+"_out").style.display="none";
	}
	_t.Init = function(){
		if(_t.hasInit){
			return;
		}
		_t.createDiv();
		_t.setSize(_t.W,_t.H);
		_t.hasInit=true;
		document.getElementById(_t.ID+"_out").style.display="block";
	}

	_t.createDiv = function(){
		var s="";
			s+=("<div style=\"position:absolute;left:0px;top:0px;z-index:9999999;display:none\" id=\""+_t.ID+"\" onmouseout=\"snap.hide(event)\" onmouseover=\"snap.enter(event)\">");
			s+=("	<div style=\"position:absolute;left: 8px; top: 5px;\" id=\""+_t.ID+"_main\"><div style=\"float:left;\" id=\""+_t.ID+"_title\" class=\"snapdiv_title\">&nbsp;<\/div><div class=\"snapdiv_button_close\" onclick=\"snap.close()\"></div><div class=\"snapdiv_button_smaller\" onclick=\"snap.smaller()\"></div><div class=\"snapdiv_button_larger\" onclick=\"snap.larger()\"></div><div title=\"回退\" class=\"snapdiv_button_reset\" onclick=\"snap.resetVal()\"></div><\/div>");
				s+=("	<div style=\"position:absolute;left: 8px; top: 27px;z-index:100;\" id=\""+_t.ID+"_content\" class=\"snapdiv_content\">");
				s+=("		<div id=\""+_t.ID+"_loading\" class=\"snapdiv_loading\"><\/div>");
				s+=("		<div id=\""+_t.ID+"_html\" style=\"display:none;\"><\/div>");
				s+=("		<div id=\""+_t.ID+"_iframe\" style=\"display:none;\"><iframe src=\""+_t.ifrBlank+"\" onreadystatechange=\"snap.IFrameStateChangeIE(this)\" onload=\"snap.IFrameStateChangeFF(this)\" style=\"border:0px;\" marginwidth=\"1\" marginheight=\"1\" name=\""+_t.ID+"_ifr\" id=\""+_t.ID+"_ifr\"  frameborder=\"0\" scrolling=\"auto\"><\/iframe></div>");
				s+=("	<\/div>");			
				s+=("	<div style=\"clear:both;\" id=\""+_t.ID+"_123\">");
				s+=("		<div style=\"float:left;\" id=\""+_t.ID+"_1\" class=\"snapdiv_1\"><\/div>");
				s+=("		<div style=\"float:left;\" id=\""+_t.ID+"_2\" class=\"snapdiv_2\"><\/div>");
				s+=("		<div style=\"float:right;\" id=\""+_t.ID+"_3\" class=\"snapdiv_3\"><\/div>");
				s+=("	<\/div>");
				s+=("	<div style=\"clear:both;\" id=\""+_t.ID+"_456\">");
				s+=("		<div style=\"float:left;\" class=\"snapdiv_4\" id=\""+_t.ID+"_4\"><\/div>");
				s+=("		<div style=\"float:right;\" class=\"snapdiv_6\" id=\""+_t.ID+"_6\"><\/div>");
				s+=("	<\/div>");
				s+=("	<div style=\"clear:both;\" id=\""+_t.ID+"_789\">");
				s+=("		<div style=\"float:left;\" id=\""+_t.ID+"_7\" class=\"snapdiv_7\"><\/div>");
				s+=("		<div style=\"float:left;\" id=\""+_t.ID+"_8\" class=\"snapdiv_8\"><\/div>");
				s+=("		<div style=\"float:right;\" id=\""+_t.ID+"_9\" class=\"snapdiv_9\"><\/div>");
				s+=("	<\/div>");
			s+=("<\/div>");
			s+=("<div style=\"position:absolute;width:38px;height:25px;display:none;\" id=\""+_t.ID+"_pointer\" onmouseover=\"snap.enter(event)\">");
			s+=("<\/div>");
			glaway_snap(_t.ID+"_out").innerHTML =s;
	}


	_t.showURL	= function(title,url,evt,w,h){
		if(evt.type=="click"){
			_t.showDelay = _t.showDelayClick;
			_t.hideDelay = _t.hideDelayClick;

		}else{
			_t.showDelay = _t.showDelayMouseOver;
			_t.hideDelay = _t.hideDelayMouseOver;
		}
		_t.show(title,url,null,evt,w,h);
	}
	_t.showHTML	= function(title,html,evt,w,h){
		_t.show(title,null,html,evt,w,h);
	}

	_t.enter = function(evt){
		var e=evt||window.event;
		_t.mX = e.clientX + document.documentElement.scrollLeft;
		_t.mY = e.clientY + document.documentElement.scrollTop;
		clearTimeout(_t.ht);
	}

	_t.show = function(title,url,html,evt,w,h){
		snap.Init();
		clearTimeout(_t.ht);
		clearTimeout(_t.st);
		var eSrc= evt.target||evt.srcElement;  	
		if(_t.isShow&&_t.eSrc== eSrc)return;
		if(w&&h){
			_t.setSize(w,h,false);
		}
		eSrc.onmouseout =function(event){snap.hide(event);}
		_t.eSrc	= eSrc;
		_t.showNum ++;
		_t.title = title;
		_t.url = url;
		_t.html = html;
		 var e=evt||window.event;
		 var pSrc= e.target||e.srcElement;  	
		 
		 //定位snap弹出窗口的位置到event的源---------Begin
		 /*
		 _t.eX = e.clientX ;
		 _t.eY = e.clientY ;
		 _t.X = _t.eX + document.documentElement.scrollLeft ;
		 _t.Y = _t.eY + document.documentElement.scrollTop ;
		*/
		_t.eX = $(eSrc).offset().left+$(eSrc).width()/2;
		 _t.eY =$(eSrc).offset().top+$(eSrc).height()/2;
		 _t.X = _t.eX + document.documentElement.scrollLeft ;
		 _t.Y = _t.eY + document.documentElement.scrollTop ;
		//定位snap弹出窗口的位置到event的源---------End
		
		 _t.st = self.setTimeout("snap.showDiv();",_t.showDelay);
	}

/**
*	_t.X = the distance from left to snap point. 		
*	_t.Y = the distance from top to snap point. 
*	_t.W = the snap window's width.
*	_t.H = the snap window's height.
*	winW = the browser's width.
*	winH = the browser's height.
*/

	_t.showDiv = function(){
		_t.isShow = true;

		_t.X = _t.eX + document.documentElement.scrollLeft;			
		_t.Y = _t.eY + document.documentElement.scrollTop;		
		//_t.X = _t.eX;			
		//_t.Y = _t.eY;		
			
		
		var winW = document.documentElement.clientWidth;
		var winH = document.documentElement.clientHeight;
		
		var sp=1;
		//if(leftW<_t.eX){
		if(_t.X > (_t.W + 15)){
			_t.X -= (_t.W+_t.fixX);					//Show At Left;
			sp+=0;
		}else{
			_t.X +=_t.fixX;							//Show At Right;
			sp+=2;
		}
		//if(topH < _t.eY && topH > _t.H){
		if(_t.eY > (_t.H + 20)){
			sp+=0;
			_t.Y -= (_t.H+_t.fixY);					//Show At Top;
		}
		else if(( winH-_t.eY ) > _t.H){
			_t.Y += _t.fixY;							//Show At Bottom;
			sp+=6;			
		}
		else if(_t.Y > (_t.H + 20)){
			_t.Y -= (_t.H+_t.fixY);					//Show At Top;
			sp+=0;
		}else{
			_t.Y += _t.fixY;							//Show At Bottom;
			sp+=6;
		}

		//alert(leftW+","+topH+" | "+_t.eX+","+_t.eY+ "|"+ _t.X+","+_t.Y);

		glaway_snap(_t.ID).style.width = _t.W+"px";
		glaway_snap(_t.ID).style.height = _t.H+"px";
		glaway_snap(_t.ID).style.left = _t.X+"px";
		glaway_snap(_t.ID).style.top = _t.Y+"px";
		glaway_snap(_t.ID).style.display = "";
		_t.showPic(sp);
		
		glaway_snap(_t.ID+"_title").innerHTML = _t.title;
		if(_t.html != null){
			setElementsDisplay([_t.ID+"_loading",_t.ID+"_iframe",_t.ID+"_ifr",_t.ID+"_html"],[false,false,false,true]);
			glaway_snap(_t.ID+"_ifr").src =_t.ifrBlank;
			glaway_snap(_t.ID+"_html").innerHTML = _t.html;
		}
		else if(_t.url != null){
			setElementsDisplay([_t.ID+"_loading",_t.ID+"_iframe",_t.ID+"_ifr",_t.ID+"_html"],[true,false,true,false]);
			glaway_snap(_t.ID+"_html").innerHTML = "";
			glaway_snap(_t.ID+"_ifr").src =_t.url;
		}
		clearTimeout(_t.st);
	}

	_t.IFrameStateChangeIE = function(obj){
		if (obj.readyState=="interactive")		//state: loading ,interactive,   complete
		{
			setElementsDisplay([_t.ID+"_loading",_t.ID+"_iframe"],[false,true]);
			if(_t.html != null){
				setElementsDisplay([_t.ID+"_loading",_t.ID+"_iframe",_t.ID+"_ifr",_t.ID+"_html"],[false,false,false,true]);
			}
		}   
	}

	_t.IFrameStateChangeFF = function(obj){
		setElementsDisplay([_t.ID+"_loading",_t.ID+"_iframe"],[false,true]);
		if(_t.html != null){
			setElementsDisplay([_t.ID+"_loading",_t.ID+"_iframe",_t.ID+"_ifr",_t.ID+"_html"],[false,false,false,true]);
		}
	}



	_t.hide= function(evt){
		var e=evt||window.event;
		_t.mX = e.clientX + document.documentElement.scrollLeft;
		_t.mY = e.clientY + document.documentElement.scrollTop;
		_t.isShow = false;
		//if(_t.isShow){
			_t.ht = self.setTimeout("snap.hideDiv();",_t.hideDelay);
		//}
		
		clearTimeout(_t.st);
	}

	_t.hideDiv = function(){
		if ((_t.mX< parseInt(glaway_snap(_t.ID).style.left)) || (_t.mX> parseInt(glaway_snap(_t.ID).style.left)+glaway_snap(_t.ID).offsetWidth) || (_t.mY < parseInt(glaway_snap(_t.ID).style.top)) || (_t.mY > parseInt(glaway_snap(_t.ID).style.top)+glaway_snap(_t.ID).offsetHeight)){
			_t.close();
			clearTimeout(_t.ht);
		}
	}

	_t.close = function(){
		setElementsDisplay([_t.ID,_t.ID+"_pointer",_t.ID+"_html",_t.ID+"_loading",_t.ID+"_iframe"],[false,false,false,true,false]);
		glaway_snap(_t.ID+"_html").innerHTML = "";
		glaway_snap(_t.ID+"_title").innerHTML = "";
		glaway_snap(_t.ID+"_ifr").src = _t.ifrBlank;
		_t.isShow = false;

	}
	
	_t.showPic = function(pid){
		snap.Init();
		pid = 10-pid;
		glaway_snap(_t.ID+"_pointer").style.display = "";
		glaway_snap(_t.ID+"_pointer").className = "snapdiv_"+pid+"_s";
		
		var pLeft = 0;
		var pTop = 0;
		
		switch(pid){
			case 1:
				pLeft = _t.X;	
				pTop = _t.Y - 21;	
			break;
			
			case 3:
				pLeft = _t.X + _t.W - 38;	
				pTop = _t.Y - 21;	
			break;
			
			case 7:
				pLeft = _t.X ;	
				pTop = _t.Y + _t.H - 6;	
			break;
			
			case 9:
				pLeft = _t.X + _t.W - 38;	
				pTop = _t.Y + _t.H - 6;					
			break;
		
		}
		glaway_snap(_t.ID+"_pointer").style.left = pLeft+"px";	
		glaway_snap(_t.ID+"_pointer").style.top = pTop+"px";
		
	}
	
	_t.setCtrlId = function(tagId) {
		ctrlId = tagId;
	}
	
	_t.clearCtrlId = function() {
		ctrlId = "";
	}
	
	_t.resetVal = function() {
		if(!undef(ctrlId)) {
			callCtrlMthd(ctrlId, 'resetVal', []); //回退控件值
			ctrlId = "";
			_t.close();
		}
	}
}


var snap=new SNAP();
//延迟初始化的时间点
//snap.Init();
snap.preInit();
