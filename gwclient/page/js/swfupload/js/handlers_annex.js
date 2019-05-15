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
function fileQueued(file) {
	try {
		var progress = new FileProgress(file, this.customSettings.progressTarget);
		//正在等待中
		progress.setStatus('<s:text name="public.js.swfupload.handler.beWaiting"/>' + '...');
		progress.toggleCancel(true, this);
		
		var data = [
			{id : file.id, fileName : file.name}
		];
		
		//在表格中新增数据
		annexTable.addData(data);		

	} catch (ex) {
		this.debug(ex);
	}

}

function fileQueueError(file, errorCode, message) {
	try {
		if (errorCode === SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED) {
			var messageInfo = "";
			//文件数量达到限制
			messageInfo = '<s:text name="public.js.swfupload.handler.fileNumberLimit"/>';
			if(message > 0)
			{
				//最多选择messge个文件
				messageInfo = "," + '<s:text name="public.js.swfupload.handler.chooseFileMost"/>' + message + '<s:text name="public.js.swfupload.handler.file"/>';
			}
			
			glaway_dialog.alert('<s:text name="system.commonFrame.comment.tip"/>',messageInfo,'warning');
			return;
		}

		var progress = new FileProgress(file, this.customSettings.progressTarget);
		progress.setError();
		progress.toggleCancel(false);

		switch (errorCode) {
		case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT:
			//文件尺寸过大
			progress.setStatus('<s:text name="public.js.swfupload.handler.fileIsTooLarge"/>');
			this.debug('<s:text name="public.js.swfupload.handler.errorCode"/>' + ':' + '<s:text name="public.js.swfupload.handler.fileIsTooLarge"/>' + "," + '<s:text name="public.js.swfupload.handler.fileName"/>' + ':' + file.name + ',' + '<s:text name="public.js.swfupload.handler.fileSize"/>' + ':' + file.size + ',' + '<s:text name="public.js.swfupload.handler.message"/>' + ':' + message);
			break;
		case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
			//无法上传零字节文件
			progress.setStatus('<s:text name="public.js.swfupload.handler.cannotUploadZeroByteFile"/>');
			this.debug('<s:text name="public.js.swfupload.handler.errorCode"/>' + ':' + '<s:text name="public.js.swfupload.handler.zeroByteFile"/>' + ',' + '<s:text name="public.js.swfupload.handler.fileName"/>' + ':' + file.name + ',' + '<s:text name="public.js.swfupload.handler.fileSize"/>' + ':' + file.size + ',' + '<s:text name="public.js.swfupload.handler.message"/>' + ':' + message);
			break;
		case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE:
			//不支持的文件类型
			progress.setStatus('<s:text name="public.js.swfupload.handler.unsupportedFileType"/>');
			this.debug('<s:text name="public.js.swfupload.handler.errorCode"/>' + ':' + '<s:text name="public.js.swfupload.handler.unsupportedFileType"/>' + ',' + '<s:text name="public.js.swfupload.handler.fileName"/>' + ':' + file.name + ',' + '<s:text name="public.js.swfupload.handler.fileSize"/>' + ':' + file.size + ',' + '<s:text name="public.js.swfupload.handler.message"/>' + ':' + message);
			break;
		default:
			if (file !== null) {
				//未处理的错误
				progress.setStatus('<s:text name="public.js.swfupload.handler.unhandledError"/>');
			}
			this.debug('<s:text name="public.js.swfupload.handler.errorCode"/>' + ':' + errorCode + ',' + '<s:text name="public.js.swfupload.handler.fileName"/>' + ':' + file.name + ',' + '<s:text name="public.js.swfupload.handler.fileSize"/>' + ':' + file.size + ',' + '<s:text name="public.js.swfupload.handler.message"/>' + ':' + message);
			break;
		}
	} catch (ex) {
        this.debug(ex);
    }
}

function fileDialogComplete(numFilesSelected, numFilesQueued) {
	try {
		if (numFilesSelected > 0) {
			document.getElementById(this.customSettings.cancelButtonId).disabled = false;
		}
		
		/* I want auto start the upload and I can do that here */
//		this.startUpload();
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
		//正在上传
		progress.setStatus('<s:text name="public.js.swfupload.handler.uploading"/>' + '...');
		progress.toggleCancel(true, this);
	}
	catch (ex) {}
	
	return true;
}

function uploadProgress(file, bytesLoaded, bytesTotal) {
	try {
		var percent = Math.ceil((bytesLoaded / bytesTotal) * 100);

		var progress = new FileProgress(file, this.customSettings.progressTarget);
		progress.setProgress(percent);
		//正在上传
		progress.setStatus('<s:text name="public.js.swfupload.handler.uploading"/>' + '...');
	} catch (ex) {
		this.debug(ex);
	}
}

function uploadSuccess(file, serverData) {
	try {
		var progress = new FileProgress(file, this.customSettings.progressTarget);
		progress.setComplete();
		//上传成功
		progress.setStatus('<s:text name="public.js.swfupload.handler.uploadSuccessFully"/>');
		//progress.toggleCancel(false);

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
			//上传错误
			progress.setStatus('<s:text name="public.js.swfupload.handler.uploadError"/>' + ':' + message);
			this.debug('<s:text name="public.js.swfupload.handler.errorCode"/>' + ':' + '<s:text name="public.js.swfupload.handler.HTTPError"/>' + ',' + '<s:text name="public.js.swfupload.handler.fileName"/>' + ':' + file.name + ',' + '<s:text name="public.js.swfupload.handler.message"/>' + ':' + message);
			break;
		case SWFUpload.UPLOAD_ERROR.UPLOAD_FAILED:
			//上传失败
			progress.setStatus('<s:text name="public.js.swfupload.handler.uploadFailure"/>');
			this.debug('<s:text name="public.js.swfupload.handler.errorCode"/>' + ':' + '<s:text name="public.js.swfupload.handler.uploadFailure"/>' + ',' + '<s:text name="public.js.swfupload.handler.fileName"/>' + ':' + file.name + ',' + '<s:text name="public.js.swfupload.handler.fileSize"/>' + ':' + file.size + ',' + '<s:text name="public.js.swfupload.handler.message"/>' + ':' + message);
			break;
		case SWFUpload.UPLOAD_ERROR.IO_ERROR:
			//IO错误
			progress.setStatus('<s:text name="public.js.swfupload.handler.IOError"/>');
			this.debug('<s:text name="public.js.swfupload.handler.errorCode"/>' + ':' + '<s:text name="public.js.swfupload.handler.IOError"/>' + ',' + '<s:text name="public.js.swfupload.handler.fileName"/>' + ':' + file.name + ',' + '<s:text name="public.js.swfupload.handler.message"/>' + ':' + message);
			break;
		case SWFUpload.UPLOAD_ERROR.SECURITY_ERROR:
			progress.setStatus('<s:text name="public.js.swfupload.handler.securityError"/>');
			this.debug('<s:text name="public.js.swfupload.handler.errorCode"/>' + ':' + '<s:text name="public.js.swfupload.handler.securityError"/>' + ',' + '<s:text name="public.js.swfupload.handler.fileName"/>' + ':' + file.name + ',' + '<s:text name="public.js.swfupload.handler.message"/>' + ':' + message);
			break;
		case SWFUpload.UPLOAD_ERROR.UPLOAD_LIMIT_EXCEEDED:
			progress.setStatus('<s:text name="public.js.swfupload.handler.beyondUploadLimit"/>');
			this.debug('<s:text name="public.js.swfupload.handler.errorCode"/>' + ':' + '<s:text name="public.js.swfupload.handler.beyondUploadLimit"/>' + ',' + '<s:text name="public.js.swfupload.handler.fileName"/>' + ':' + file.name + ',' + '<s:text name="public.js.swfupload.handler.fileSize"/>' + ':' + file.size + ',' + '<s:text name="public.js.swfupload.handler.message"/>' + ':' + message);
			break;
		case SWFUpload.UPLOAD_ERROR.FILE_VALIDATION_FAILED:
			progress.setStatus('<s:text name="public.js.swfupload.handler.fileValidationFailure"/>' + ',' + '<s:text name="public.js.swfupload.handler.skipUpload"/>');
			this.debug('<s:text name="public.js.swfupload.handler.errorCode"/>' + ':' + '<s:text name="public.js.swfupload.handler.fileValidationFailure"/>' + ',' + '<s:text name="public.js.swfupload.handler.fileName"/>' + ':' + file.name + ',' + '<s:text name="public.js.swfupload.handler.fileSize"/>' + ':' + file.size + ',' + '<s:text name="public.js.swfupload.handler.message"/>' + ':' + message);
			break;
		case SWFUpload.UPLOAD_ERROR.FILE_CANCELLED:
			// If there aren't any files left (they were all cancelled) disable the cancel button
			if (this.getStats().files_queued === 0) {
				document.getElementById(this.customSettings.cancelButtonId).disabled = true;
			}
			progress.setStatus('<s:text name="public.js.swfupload.handler.cancel"/>');
			progress.setCancelled();
			break;
		case SWFUpload.UPLOAD_ERROR.UPLOAD_STOPPED:
			progress.setStatus('<s:text name="public.js.swfupload.handler.stop"/>');
			break;
		default:
			progress.setStatus('<s:text name="public.js.swfupload.handler.unhandledError"/>' + '' + errorCode);
			this.debug('<s:text name="public.js.swfupload.handler.errorCode"/>' + ':' + errorCode + ',' + '<s:text name="public.js.swfupload.handler.fileName"/>' + ':' + file.name + ',' + '<s:text name="public.js.swfupload.handler.fileSize"/>' + ':' + file.size + ',' + '<s:text name="public.js.swfupload.handler.message"/>' + ':' + message);
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
	//console.log("队列中的数量=" + this.getStats().files_queued + "    " + "上传成功的数量=" + this.getStats().successful_uploads);
	//获取已上传成功数
	var uploadSuccessCount = this.getStats().successful_uploads;
	//判断新增的附件是否已全部上传到action中
	if(uploadSuccessCount == newFileCount)
	{
		//开始操作（添加、修改）
		startOperate();
	}
	this.startUpload();
}

// This event comes from the Queue Plugin
function queueComplete(numFilesUploaded) {
	var status = document.getElementById("divStatus");
	status.innerHTML = numFilesUploaded + '<s:text name="public.js.swfupload.handler.a"/>' + '<s:text name="public.js.swfupload.handler.file"/>' + (numFilesUploaded === 1 ? "" : "s") + '<s:text name="public.js.swfupload.handler.uploaded"/>';
}
