相关说明：
    1、开发工具为:myeclipse11或以上,Spring,hibernate,springmvc,前端使用freemarker 
    2、数据库：mysql5.x，数据库文件于db文件目录下面
    3、服务器：tomcat7.x
    4、数据库配置于applicationContext.xml文件中
    5、后台登陆地址：http://xxxx:8080/login.do
                    用户名：admin 
                    密码：123456 
                    （默认商户与管理员都是)
    6、网站必须放置于服务器根目录下面，
       myeclipse配置方法http://jingyan.baidu.com/article/eae07827a945b61fed548544.html
    
目录说明：
    1、WebRoot/file:
            数据库设计概要：数据库设计概要（为上一个电商平台，做为参考)
            limmai.cdm: PowerDesign 设计（做为参考）
            wxpay: 微信支付小项目源码，可直接使用
    2、src/sms:
            短信第三方接口; 
    3、WebRoot/dist:
            css/js/js插件
    4、webRoot/WEB-INF/page:
            manager:后台管理员
            index:前台页面
            rsp:仿ajax返回



韦英飘：任务日记
	/manager/orders/listtakeoutProv.html
		110行   '提交时间','预定时间' 位置调换
		149-159行    位置调换
		
	/index/orders/ordertakeoutsec.html
		164-229行 
				
	/index/comments	
		添加toAdd.html
		添加toAppend.html
		
	src/sms/Sms.java 27行 变量 p2值为3 
	
	src/com/sunspot/service/CommentsService.java 添加 42行-93行
	
	src/com/sunspot/service/CommentsServiceImpl.java	添加 170-323行	

	/src/com/sunspot/service/OrdersServiceImpl.java 添加 第342行 ，434行
	
	/src/com/sunspot/controller/index/CommentsIndexController.java 是新添加的类
	
	黄祖源
	
	黄祖源 任务日志：
	1、com.sunspot.controller.manager.OrdersController类里面send方法里面添加
		ordersService.autoReceiveGoods(orderId, shop.getShopId());
	2、com.sunspot.service.impl.OrdersServiceImpl
			添加自动收货方法autoReceiveGoods(String, String);