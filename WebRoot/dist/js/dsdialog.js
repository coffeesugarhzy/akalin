$(function() {
	var windowHeight = $(window).height() - 80;
	$("#homeDisplay").css({
		"height" : windowHeight
	});
});



/**
 * 警告弹出层
 * 
 * @param title
 *            标题
 * @param content
 *            内附
 * @author LuoAnDong
 */
function dsAlert(title, content) {
	ds.dialog.alert(title, function() {
		alert(content)
	}, "info.png");
}

function dsAlert(content) {
	ds.dialog.alert(content, "info.png");
}

/**
 * 内容提示弹出层
 * 
 * @param content
 * @author LuoAnDong
 */
function dsTip(content) {
	ds.dialog.tips(content, 2, true, true);
}

var dslog;

function dsIframe(_title, url, width, height) {
	dslog = ds.dialog({
		title : _title,
		content : '<iframe name="editPowerFrame" frameborder="no" src="' + url
				+ '" border="0" width="' + width + 'px" height="' + height
				+ 'px" style="border: 0px none; ">'
	});
}

/**
 * 提示弹出窗口
 * 
 * @param titles
 * @param contents
 * @param icon
 *            info , loading , question , success 图片路径位于 /dist/dsdialog/images
 *            下添加
 * @author LuoAnDong
 */
function tip(_title, _content, _icon) {
	if (dslog) {
		dslog.close();
	}
	ds.dialog({
		title : _title,
		content : _content,
		zIndex : 199055,
		yesText : '确定',
		onyes : function() {
			this.close(); 
			refresh();
			return false;
		},
		noText : '取消',
		onno : function() {
			this.close(); 
		},
		icon : _icon + ".png",
	});
}

/**
 * 通过id删除实体
 * 
 * @param _id
 */
function deleteById(_url, _id) {
	ds.dialog.confirm("确定要删除吗？", function() {
		$.post(_url, {
			id : _id
		}, function(result) {
			if (result === "1") {
				top.dsTip("删除成功", 2, true, true);
			} else if (result === "2") {
				top.dsTip("已经存在账户关联，不能删除", 2, true, true);
			} else if (result === "0") {
				top.dsTip("删除失败", 2, true, true);
			}
			refresh();
		});
	});
}

/**
 * 得到主导航
 * @returns
 */
function getTopNavTxt(){
	var val = (document.getElementById("headerDisplay").contentDocument).getElementById("header-title").value;
	if(val===""){
		return "欢迎页" ; 
	}else{return val;}
} 

/**
 * 刷新内置iframe
 */
function refresh() {
	var childPage = (document.getElementById("homeDisplay").contentWindow).document
			.getElementById("rightMain").contentWindow;
	childPage.location.reload();
}

/**
 * 权限编辑
 * 
 * @param roleId
 * @author LuoAnDong
 */
function editRolePower(roleId) {
	dslog = ds
			.dialog({
				title : '权限编辑',
				content : '<iframe name="editPowerFrame" frameborder="no" src="/manager/sys/editPower.do?roleId='
						+ roleId
						+ '" border="0" style="border: 0px none; width:400px; height: 500px;"">',
				yesText : '确定',
				onyes : function() {
					var win = window.frames['editPowerFrame'];
					win.getSelectValue();
					return false;
				},
				noText : '取消',
				onno : function() {
					this.close();
				},
			});
}
