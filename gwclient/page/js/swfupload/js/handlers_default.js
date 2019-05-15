/*
		[Leo.C, Studio] (C)2004 - 2008
		
   		$Hanization: LeoChung $
   		$E-Mail: who@imll.net $
   		$HomePage: http://imll.net $
   		$Date: 2008/11/8 18:02 $
*/
/* Demo Note:  This demo uses a FileProgress class that handles the UI for displaying the file name and percent complete.
The FileProgress class is not part of SWFUpload.
*/


/* **********************
   Event Handlers
   These are my custom event handlers to make my
   web application behave the way I went when SWFUpload
   completes different tasks.  These aren't part of the SWFUpload
   package.  They are part of my application.  Without these none
   of the actions SWFUpload makes will show up in my application.
   ********************** */
function startUploadFile(){
	if(swfu.customSettings.upLoadFileQueue && swfu.customSettings.upLoadFileQueue.length>0){
		swfu.startUpload(swfu.customSettings.upLoadFileQueue.pop().id);
	}
}

function clearUploadFile(){
	swfu.customSettings.upLoadFileQueue = null;
	swfu.customSettings.fileQueue = null;
	$("#fileButtonPlaceHolder_tbody").empty();
	parseClick(swfu.customSettings.ctrlid,true,"clearqueue");
}

function cancelUploadFile(){
	swfu.stopUpload();
	$.each(swfu.customSettings.upLoadFileQueue,function (i,item){
		swfu.cancelUpload(item.id,false);
		$("#fileButtonPlaceHolder_tbody").find("#"+item.id+"_status").text(COMM_CONSTANT.SWFU.CANCELUP);
	});
}

function stopUploadFile(fileId){
	swfu.stopUpload();
	$("#fileButtonPlaceHolder_tbody").find("#"+fileId+"_status").text(COMM_CONSTANT.SWFU.STOPUP);
}

function fileQueued(file) {
	try {
		var samefile = false;
		swfu.customSettings.fileQueue = swfu.customSettings.fileQueue || new Array();
		swfu.customSettings.upLoadFileQueue = swfu.customSettings.upLoadFileQueue || new Array();
		$.each(swfu.customSettings.fileQueue,function (i,item){
			if(item.name == file.name){
				samefile = true;
				return false;
			}
		});
		if(!samefile){
			swfu.customSettings.fileQueue.push(file);
			swfu.customSettings.upLoadFileQueue.push(file);
			var createDate = file.creationdate;
			var cdate = createDate.getYear() + "-" + createDate.getMonth() + "-" +  createDate.getDate() + " ";
			cdate = cdate + createDate.getHours() + ":" + createDate.getMinutes() + ":" + createDate.getSeconds();
			var str = "<tr id='"+file.id+"_tr'>";
			str = str + "<td id='"+file.id+"_filename'>"+file.name+"</td>";
			str = str + "<td id='"+file.id+"_progress'>0%</td>";
			str = str + "<td id='"+file.id+"_status'>"+COMM_CONSTANT.SWFU.WAIT+"</td>";
			str = str + "<td id='"+file.id+"_createdate'>"+cdate+"</td>";
			str = str + "<td><a id='"+file.id+"_upbtn' href='javascript:stopUploadFile(\""+file.id+"\")'>"+COMM_CONSTANT.SWFU.STOP+"</a>&nbsp;&nbsp;&nbsp;";
			str = str + "<a id='"+file.id+"_delbtn' href='javascript:delFile(\""+file.id+"\",\""+file.name+"\")'>"+COMM_CONSTANT.SWFU.DEL+"</a></tr>";
			$("#fileButtonPlaceHolder_tbody").append(str);
		}
		//var progress = new FileProgress(file, this.customSettings.progressTarget);
		//progress.setStatus("正在等待...");
		//progress.toggleCancel(true, this);

	} catch (ex) {
		this.debug(ex);
	}

}


function fileQueueError(file, errorCode, message) {
	try {
		if (errorCode === SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED) {
			alert("您正在上传的文件队列过多.\n" + (message === 0 ? "您已达到上传限制" : "您最多能选择 " + (message > 1 ? "上传 " + message + " 文件." : "一个文件.")));
			return;
		}

		var progress = new FileProgress(file, this.customSettings.progressTarget);
		progress.setError();
		progress.toggleCancel(false);

		switch (errorCode) {
		case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT:
			progress.setStatus("文件尺寸过大.");
			this.debug("错误代码: 文件尺寸过大, 文件名: " + file.name + ", 文件尺寸: " + file.size + ", 信息: " + message);
			break;
		case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
			progress.setStatus("无法上传零字节文件.");
			this.debug("错误代码: 零字节文件, 文件名: " + file.name + ", 文件尺寸: " + file.size + ", 信息: " + message);
			break;
		case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE:
			progress.setStatus("不支持的文件类型.");
			this.debug("错误代码: 不支持的文件类型, 文件名: " + file.name + ", 文件尺寸: " + file.size + ", 信息: " + message);
			break;
		default:
			if (file !== null) {
				progress.setStatus("未处理的错误");
			}
			this.debug("错误代码: " + errorCode + ", 文件名: " + file.name + ", 文件尺寸: " + file.size + ", 信息: " + message);
			break;
		}
	} catch (ex) {
        this.debug(ex);
    }
}

function fileDialogComplete(numFilesSelected, numFilesQueued) {
	try {
		this.startUpload();
	} catch (ex)  {
        this.debug(ex);
	}
}

function uploadStart(file) {
	try {
		/* I don't want to do any file validation or anything,  I'll just update the UI and
		return true to indicate that the upload should start.
		It's important to update the UI here because in Linux no uploadProgress events are called. The best
		we can do is say we are uploading.
		 */
		var progress = new FileProgress(file, this.customSettings.progressTarget);
		progress.setStatus("正在上传...");
		progress.toggleCancel(true, this);
	}
	catch (ex) {}
	
	return true;
}

function uploadProgress(file, bytesLoaded, bytesTotal) {
	try {
		var percent = Math.ceil((bytesLoaded / bytesTotal) * 100);

		//var progress = new FileProgress(file, this.customSettings.progressTarget);
		//progress.setProgress(percent);
		//progress.setStatus("正在上传...");
		$("#fileButtonPlaceHolder_tbody").find("#"+file.id+"_progress").text(percent + "% ");
		$("#fileButtonPlaceHolder_tbody").find("#"+file.id+"_status").text(COMM_CONSTANT.SWFU.UPLOADING);
	} catch (ex) {
		this.debug(ex);
	}
}

function uploadSuccess(file, serverData) {
	try {
		//var progress = new FileProgress(file, this.customSettings.progressTarget);
		//progress.setComplete();
		var fileId = file.id;
		//var fileName=file.name;
		//var html="上传成功<div style='margin-left: 305px;margin-top: -20px;cursor:pointer;'><a onclick=\"del('"+fileId+"','"+fileName+"');\">删除</a><div>";
		//progress.setStatus(html);
		
		$("#fileButtonPlaceHolder_tbody").find("#"+fileId+"_status").text(COMM_CONSTANT.SWFU.SUCCESS);
	} catch (ex) {
		this.debug(ex);
	}
}

function uploadError(file, errorCode, message) {
	try {
		var progress = new FileProgress(file, this.customSettings.progressTarget);
		progress.setError();
		progress.toggleCancel(false);

		switch (errorCode) {
		case SWFUpload.UPLOAD_ERROR.HTTP_ERROR:
			progress.setStatus("上传错误: " + message);
			this.debug("错误代码: HTTP错误, 文件名: " + file.name + ", 信息: " + message);
			break;
		case SWFUpload.UPLOAD_ERROR.UPLOAD_FAILED:
			progress.setStatus("上传失败");
			this.debug("错误代码: 上传失败, 文件名: " + file.name + ", 文件尺寸: " + file.size + ", 信息: " + message);
			break;
		case SWFUpload.UPLOAD_ERROR.IO_ERROR:
			progress.setStatus("服务器 (IO) 错误");
			this.debug("错误代码: IO 错误, 文件名: " + file.name + ", 信息: " + message);
			break;
		case SWFUpload.UPLOAD_ERROR.SECURITY_ERROR:
			progress.setStatus("安全错误");
			this.debug("错误代码: 安全错误, 文件名: " + file.name + ", 信息: " + message);
			break;
		case SWFUpload.UPLOAD_ERROR.UPLOAD_LIMIT_EXCEEDED:
			progress.setStatus("超出上传限制.");
			this.debug("错误代码: 超出上传限制, 文件名: " + file.name + ", 文件尺寸: " + file.size + ", 信息: " + message);
			break;
		case SWFUpload.UPLOAD_ERROR.FILE_VALIDATION_FAILED:
			progress.setStatus("无法验证.  跳过上传.");
			this.debug("错误代码: 文件验证失败, 文件名: " + file.name + ", 文件尺寸: " + file.size + ", 信息: " + message);
			break;
		case SWFUpload.UPLOAD_ERROR.FILE_CANCELLED:
			// If there aren't any files left (they were all cancelled) disable the cancel button
			if (this.getStats().files_queued === 0) {
				document.getElementById(this.customSettings.cancelButtonId).disabled = true;
			}
			progress.setStatus("取消");
			progress.setCancelled();
			break;
		case SWFUpload.UPLOAD_ERROR.UPLOAD_STOPPED:
			progress.setStatus("停止");
			break;
		default:
			progress.setStatus("未处理的错误: " + errorCode);
			this.debug("错误代码: " + errorCode + ", 文件名: " + file.name + ", 文件尺寸: " + file.size + ", 信息: " + message);
			break;
		}
	} catch (ex) {
        this.debug(ex);
    }
}

function uploadComplete(file) {
//	if (this.getStats().files_queued === 0) {
//		document.getElementById(this.customSettings.cancelButtonId).disabled = true;
//	}
	if(swfu.customSettings.upLoadFileQueue && swfu.customSettings.upLoadFileQueue.length>0){
		swfu.startUpload(swfu.customSettings.upLoadFileQueue.pop().id);
	}
}

// This event comes from the Queue Plugin
function queueComplete(numFilesUploaded) {
	var status = document.getElementById("divStatus");
	status.innerHTML = numFilesUploaded + " 个文件" + (numFilesUploaded === 1 ? "" : "s") + "已上传.";
}
