<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
 <head>
  <title>成功页面</title>
    <base href="<%=basePath%>">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="./static/assets/css/dpl-min.css" rel="stylesheet" type="text/css" />
    <link href="./static/assets/css/bui-min.css" rel="stylesheet" type="text/css" />
    <link href="./static/assets/css/page-min.css" rel="stylesheet" type="text/css" /> 
 </head>
 <body>
  
  <div class="container">
    <div class="row">
      <div class="span10">
        <div class="tips tips-large tips-success">
          <span class="x-icon x-icon-success"><i class="icon icon-ok icon-white"></i></span>
          <div class="tips-content">
            <h2>${result}</h2>
            <p class="auxiliary-text">
                                     你可以继续操作以下内容：
            </p>
            <p>
              <a class="page-action" data-type="close" href="#">关闭本页</a>
              <a class="page-action" data-type="setTitle" title="继续操作" href="${link}">继续操作</a>
            </p>
          </div>
        </div>
      </div> 
    </div>
  </div>
  <script type="text/javascript" src="./static/assets/js/jquery-1.8.1.min.js"></script>
  <script type="text/javascript" src="./static/assets/js/bui-min.js"></script>

  <script type="text/javascript" src="./static/js/config-min.js"></script>
  <script type="text/javascript">
    BUI.use('common/page');
  </script>


<body>
</html>  