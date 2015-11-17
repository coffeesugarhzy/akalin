$(document).ready(function() {
    initEditor("remark");
    initSet();

    tt.vf.req.add("shopName", "linkMan", "telphone", "openTime", "cost", "address", "ofAreaId");
    tt.vf.num.add("cost");

    $("#btn").click(function() {
        var point = new BMap.Point(parseFloat($("#text1").val()), parseFloat($("#text2").val()));
        addMarker(point);
    });
});

var submitnum = 0;
function validate() {
    if (0 != submitnum) {
        return;
    }

    var tip = tt.validate();
    if (tip == true) {
        if ("" == $("#shopId").val()) {
            var file = $('#file').val();
            if (null === file || "" === file) {
                top.dsAlert('请上传LOGO');
                return false;
            }
        }
        submitnum++;
        document.getElementById("myform").submit();
    }
}

function setEditView() {
    var shopDetailObj = document.getElementById('shopDetail');
    var formObj = document.getElementById('myform');
    var saleA = document.getElementById('saleA');
    var fullA = document.getElementById('fullA');
    if ('none' == shopDetailObj.style.display) {
        shopDetailObj.style.display = '';
        formObj.style.display = 'none'
        saleA.style.display = '';
        fullA.style.display = ''
        document.getElementById("editA").innerHTML = "编辑";
    } else {
        shopDetailObj.style.display = 'none';
        formObj.style.display = ''
        saleA.style.display = 'none';
        fullA.style.display = 'none'
        document.getElementById("editA").innerHTML = "取消编辑";
    }
}

// 编写自定义函数，创建标注
function addMarker(point) {
    // 创建图标对象
    var myIcon = new BMap.Icon("http://api.map.baidu.com/img/markers.png", new BMap.Size(23, 25), {
        offset : new BMap.Size(10, 25),
        imageOffset : new BMap.Size(0, 0 - 1 * 25)
    // 设置图片偏移
    });
    // 创建标注对象并添加到地图
    var marker = new BMap.Marker(point);
    map.addOverlay(marker);
    // 移除标注
    marker.addEventListener("click", function() {
        var opts = {
            width : 250, // 信息窗口宽度
            height : 100, // 信息窗口高度
            title : "lng:" + point.lng + "lat:" + point.lat // 信息窗口标题
        }
        var infoWindow = new BMap.InfoWindow("", opts); // 创建信息窗口对象
        marker.openInfoWindow(infoWindow, this.point); // 打开信息窗口
    });
}

function addtr() {
    // 增加表格
    var t = document.getElementById("tab");
    var row = t.insertRow(t.rows.length);
    var cell1 = row.insertCell(0);
    var cell2 = row.insertCell(1);
    var cell3 = row.insertCell(2);
    var cell4 = row.insertCell(3);
    cell1.innerHTML = "<input type='text' class='input-text' name='tableName' id='tableName' maxlength='5' style='width:80px'/>";
    cell2.innerHTML = "<input type='text' class='input-text' name='tableVolume' id='tableVolume' value='0' maxlength='4' style='width:80px' />";
    cell3.innerHTML = "<input type='text' class='input-text' name='tableNum' id='tableNum' value='0' maxlength='4' style='width:80px'/>";
    cell4.innerHTML = "<a href='#' onclick='javascript:delRow(this)'>删除</a>";
}

function delRow(obj) {
    var tr = obj.parentNode.parentNode;
    if (tr != null) {
        tr.parentNode.removeChild(tr);
    }
}
