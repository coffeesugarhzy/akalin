/**
 * 添加编辑器 content 为textarea 的名称
 */
function initEditor(content) {
	var contentK;
	KindEditor.ready(function(K) {
		contentK = K.create('textarea[name="' + content + '"]', {
			uploadJson : '../../dist/kindeditor/jsp/upload_json.jsp',
			fileManagerJson : 'dist/kindeditor/jsp/file_manager_json.jsp',
			allowFileManager : false,
			allowImageUpload : true,
			items : [ 'emoticons','image','table','link','unlink','fontname', 'fontsize', 'hr','forecolor',
			 'hilitecolor', 'bold', 'italic', 'underline', 'removeformat','justifyleft', 'justifycenter', 'justifyright',
			 'insertorderedlist', 'insertunorderedlist'],
			afterCreate : function() {
			},
			afterBlur : function() {
				this.sync();
			}
		});
	});
}

/**
 * 时间控件封装
 */
function calendarTime(id, formDate, showTime) {
	Calendar.setup({
		weekNumbers : true,
		inputField : id,
		trigger : id,
		dateFormat : formDate,
		showTime : showTime,
		minuteStep : 1,
		onSelect : function() {
			this.hide();
		}
	});
}

/**
 * 日历控件
 */
function calendar(id) {
	calendarTime(id, "%Y-%m-%d", true);
}

/**
 * 时间控件
 */
function caltime(id) {
	calendarTime(id, "%Y-%m-%d %H:%M:%S", true);
}

/**
 * 图片本地预览显示
 */
function showimageIE(fileuploadid, imgid) {
	if (document.getElementById(fileuploadid).value !== "") {
		document.getElementById(imgid).style.display = "block";
		document.getElementById(imgid).src = getpath(document
				.getElementById(fileuploadid));
	}
}

function getpath(obj) {
	if (obj) {
		return obj.value;
	}
}

function showimage(fileuploadid, imgid) {
	var file = document.getElementById(fileuploadid).files[0];
	var reader = new FileReader();
	reader.onload = function(evt) {
		document.getElementById(imgid).src = evt.target.result;
	}
	reader.readAsDataURL(file);
}

/**
 * 兼容ie8 图片预览
 * 
 * @param picId
 * @param fileId
 */
function change(picId, fileId) {
	var pic = document.getElementById(picId);
	var file = document.getElementById(fileId);
	if (window.FileReader) {
		oFReader = new FileReader();
		oFReader.readAsDataURL(file.files[0]);
		oFReader.onload = function(oFREvent) {
			pic.src = oFREvent.target.result;
		};
	} else if (document.all) {
		file.select();
		var reallocalpath = document.selection.createRange().text
		if (window.ie6)
			pic.src = reallocalpath;
		else {
			pic.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='image',src=\""
					+ reallocalpath + "\")";
			pic.src = 'data:image/gif;base64,R0lGODlhAQABAIAAAP///wAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==';
		}
	} else if (file.files) {
		if (file.files.item(0)) {
			url = file.files.item(0).getAsDataURL();
			alert("url = " + url);
			pic.src = url;
		}
	}
}

function changeImg(uploadId, headImageId) {
	if (!+[ 1, ]) {
		showimageIE(uploadId, headImageId);
	} else {
		showimage(uploadId, headImageId);
	}
}

function getWeekName(val){
	var valNum = parseInt(val);
	switch(valNum){
	case 0:
		return "星期日";
	case 1:
		return "星期一";
	case 2:
		return "星期二";
	case 3:
		return "星期三";
	case 4:
		return "星期四";
	case 5:
		return "星期五";
	case 6:
		return "星期六";
	default:
		return "";
	}
}
