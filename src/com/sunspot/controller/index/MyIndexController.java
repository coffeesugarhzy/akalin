package com.sunspot.controller.index;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import sms.Sms;

import com.sunspot.common.DateUtil;
import com.sunspot.common.MyConstants;
import com.sunspot.common.Utils;
import com.sunspot.pojo.Advertising;
import com.sunspot.pojo.Area;
import com.sunspot.pojo.Coupon;
import com.sunspot.pojo.CustomAddr;
import com.sunspot.pojo.CustomInfo;
import com.sunspot.pojo.Feedback;
import com.sunspot.pojo.FoodExp;
import com.sunspot.pojo.Shop;
import com.sunspot.pojo.Snack;
import com.sunspot.pojo.UserInfo;
import com.sunspot.pojo.UserRole;
import com.sunspot.pojoext.CustomerLikeExt;
import com.sunspot.pojoext.DataGridModel;
import com.sunspot.pojoext.OrdersIndexExt;
import com.sunspot.pojoext.ShopExt;
import com.sunspot.service.CustomInfoService;
import com.sunspot.service.CustomLikeService;
import com.sunspot.service.MyIndexService;
import com.sunspot.service.OrdersService;
import com.sunspot.service.ProviderService;
import com.sunspot.service.UserInfoService;
import com.sunspot.service.UserRoleService;

@Controller
@Scope("prototype")
@RequestMapping("index/index")
public class MyIndexController
{

    @Resource
    private OrdersService ordersService;

    /**
     * 用户收藏业务层
     */
    @Autowired
    private CustomLikeService customLikeService;

    /**
     * 用户业务层
     */
    @Autowired
    private CustomInfoService customInfoService;
    
    /**
     * 商家业务层
     */
    @Autowired
    private ProviderService providerService;
    
    /**
     * 商家业务层
     */
    @Autowired
    private UserRoleService userRoleService;
    
    @Autowired
    private UserInfoService userInfoService;
    
    @Resource
    MyIndexService service;

    /**
     * 验证手机是否已经注册
     * 
     * @param phone
     * @return
     * @author LuoAnDong
     */
    @ResponseBody
    @RequestMapping("sendCheckPhone")
    public boolean sendCheckPhone(String phone)
    {
        if (StringUtils.isBlank(phone))
            return false;
        else
        {
            return customInfoService.checkRepeatPhone(phone);
        }
    }

    @RequestMapping("toIndex")
    public void toIndex(Model model)
    {
        List<Advertising> adList = service.adList();
        if (adList.size() > 0)
        {
            model.addAttribute("adList", adList);
        }
        model.addAttribute("tip", "index");
       // return "forward:/index/dinner/dinner.do";  
    }

    @RequestMapping("position")
    public void position(Model model)
    {
        List<Area> areaList = service.areaList();
        if (areaList.size() > 0)
        {
            model.addAttribute("areaList", areaList);
        }
    }

    @RequestMapping("selectCity")
    public void selectCity(HttpServletResponse response, String cityName)
            throws UnsupportedEncodingException
    {
        Cookie cookie = new Cookie("cityName", URLEncoder.encode(cityName,
                "UTF-8"));
        cookie.setPath("/index/");
        cookie.setMaxAge(60 * 60 * 24 * 365);
        response.addCookie(cookie);
    }

    @RequestMapping("snack")
    public void snack()
    {
    }

    @ResponseBody
    @RequestMapping("snackList")
    public DataGridModel<Snack> snackList(DataGridModel<Snack> list,
            String keyword)
    {
        return service.snackList(list, keyword);
    }

    @RequestMapping("snackDetail")
    public void snackDetail(Model model, String id)
    {
        model.addAttribute("snack", service.getSnack(id));
    }

    @RequestMapping("shop")
    public void shop()
    {
    }

    @ResponseBody
    @RequestMapping("shopList")
    public DataGridModel<Shop> shopList(DataGridModel<Shop> list)
    {
        return service.shopList(list);
    }

    @RequestMapping("shopDetail")
    public void shopDetail(Model model, String id)
    {
        model.addAttribute("shop", service.getShop(id));
    }

    @ResponseBody
    @RequestMapping("customerLogin")
    public String customerLogin(String phone, String password,
            String fromWhere, HttpSession session, Model model)
    {
        boolean result = service.customerLogin(phone, password, session);
        if (false == result)
        {
            return "loginError";
        }
        else
        {
            return fromWhere;
        }
    }

    @RequestMapping("toRegister")
    public void toRegister()
    {

    }

    @ResponseBody
    @RequestMapping("register")
    public String register(String phone, String password, String checkCode,
            HttpSession session)
    {
        Object code = session.getAttribute("checkCode");
        if (null == code) { return "checkCodeIsNull"; }
        String sendCheckCode = (String) code;
        if (!sendCheckCode.equalsIgnoreCase(checkCode)) { return "error"; }
        //添加注册用户
        CustomInfo customer = new CustomInfo();
        customer.setAddDate(DateUtil.getCurrentDateTime());
        customer.setTelphone(phone);
        customer.setLoginPassword(password);
        service.add(customer);
        boolean result = service.customerLogin(phone, password, session);
        if (false == result)
        {
            return "loginError";
        }else
        {
        	 return "loginSuccess";
        }
       
    }

    @ResponseBody
    @RequestMapping("sendCheckCode")
    public String sendCheckCode(HttpServletRequest request, String phone)
    {
        String checkCode = Utils.smsCheckCode();
        try
        {
           // SMSService.sendSms(phone, checkCode);
        	Sms.sendSms(phone, checkCode) ; 
        }
        catch (Exception e)
        {
            e.printStackTrace();
            checkCode = "0";
        }
        request.getSession(true).setAttribute("checkCode", checkCode);
        return checkCode;
    }

    @RequestMapping("login")
    public void login(HttpServletRequest request)
    {
    }
    
    @RequestMapping("toOrdersShop")
    public String toOrdersShop(String shopId, HttpSession session, Model model,
            String type)
    {
        Object loginCustomer = session.getAttribute("loginCustomer");
        if (null == loginCustomer)
        {
            if (type.equals("food"))
            {
                model.addAttribute("fromWhere", "/index/index/food.do");
            }
            else if (type.equals("table"))
            {
                model.addAttribute("fromWhere", "/index/index/table.do");
            }
            else
            {
                model.addAttribute("fromWhere", "/index/index/shop.do");
            }
            return MyConstants.LOGIN;
        }
        CustomInfo customer = (CustomInfo) loginCustomer;
        service.toOrdersShop(shopId, model, customer);
        return "/index/index/toOrdersShop";
    }

    @RequestMapping("orderShopSuccess")
    public String orderShopSuccess(String shopId, String bookDate,
            HttpSession session, Model model, String ofTableId)
    {
        Object loginCustomer = session.getAttribute("loginCustomer");
        if (null == loginCustomer)
        {
            model.addAttribute("fromWhere", "/index/index/shop.do");
            return MyConstants.LOGIN;
        }
        CustomInfo customer = (CustomInfo) loginCustomer;
        service.orderShopSuccess(shopId, bookDate, customer, ofTableId, model);
        return "/index/index/orderShopSuccess";
    }

    @RequestMapping("my")
    public String my(HttpSession session, Model model)
    {
        Object loginCustomer = session.getAttribute("loginCustomer");
        if (null == loginCustomer) { return MyConstants.LOGIN; }
        return "/index/index/my";
    }
    
    
    /**
     * 验证商家电话
     * @param phone
     * @return
     */
    @ResponseBody
    @RequestMapping("sendCheckTelPhone")
    public boolean sendCheckTelPhone(String telPhone)
    {
        if (StringUtils.isBlank(telPhone))
            return false;
        else
        {
            return providerService.checkRepeatTelPhone(telPhone);
        }
    }
    
    /***
     * 发送验证码
     * @param request
     * @param phone
     * @return
     */
    @ResponseBody
    @RequestMapping("sendCheckShopCode")
    public String sendCheckShopCode(HttpServletRequest request, String telPhone)
    {
        String checkCode = Utils.smsCheckCode();
        try
        {
        	Sms.sendSms(telPhone, checkCode) ; 
        }
        catch (Exception e)
        {
            e.printStackTrace();
            checkCode = "0";
        }
        request.getSession(true).setAttribute("checkShopCode", checkCode);
        return checkCode;
    }
    
    
    @ResponseBody
    @RequestMapping("uploadShopLogo")
    public Map<String,String> uploadShopLogo(HttpServletRequest request){
    	Map<String,String> result=new HashMap<String,String>();
    	
    	try{
    		String url=Utils.uploadValidImages(request, "photo", null, null);
    		result.put("url", url);
    		result.put("code", "1");
    	}catch(Exception e){
    		e.printStackTrace();
    		result.put("code", "0");
    		result.put("msg", "上传文件时出现异常！");
    	}
    	
    	return result;
    }
    
    /**
     * 验证登录名是否存在
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "checkRepeatName", method = RequestMethod.POST)
    public int checkRepeatName(String loginName)
    {
        return providerService.checkRepeatName(loginName);
    }
    
    
    /**
     * 注册成为商家 注册页面
     */
    @RequestMapping("toRegShop")
    public void toRegShop(HttpSession session, Model model)
    {
    }
    
    /**
     * 注册成为商家
     */
    @ResponseBody
    @RequestMapping("regShop")
    public String regShop(UserInfo userInfo,String checkCode, HttpSession session)       
    {
        Object code = session.getAttribute("checkShopCode");
        if (null == code) { return "checkCodeIsNull"; }
        String sendCheckCode = (String) code;
        if (!sendCheckCode.equalsIgnoreCase(checkCode)) { return "error"; }
        if (providerService.checkRepeatName(userInfo.getLoginName()) == 1)
        {
        	return "userNameIsExits";
        }

        List<UserRole> list=userRoleService.queryUserRoleByRoleName("未认证商家");
        if(list.size()==1){
            userInfo.setUserRole(list.get(0));
            userInfo.setAddDate(Utils.getNowDate());
            userInfo.setIsSysuser(1);
            userInfo.setUserType(1);
            int result= providerService.add(userInfo);
            if(result==1){
           	 session.setAttribute("checkShopCode", null);
           }
        }else{
        	return "sysNullRoles";
        }

        return null;
    }
    
    /**
     * 注册成为商家 注册页面
     */
    @RequestMapping("toShopLogin")
    public void toShopLogin()
    {
    }
    
    @ResponseBody
    @RequestMapping("shopLogin")
    public Map<String,String> shopLogin(String userName, String password,HttpServletRequest request){
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("loginUser");   
        Map<String,String> result=new HashMap<String,String>();
        if (null != userInfo) {
        	result.put("success", "1");
        }
        if (StringUtils.isBlank(userName) || StringUtils.isBlank(password))
        {
        	result.put("msg", "账号或密码不能为空！");
        	result.put("success", "0");
        }
        userInfo = userInfoService.checkUserLogin(userName.trim(),password.trim(), "1");
        if (userInfo != null)
        {
            // 查询用户角色　
            UserRole userRole = userRoleService.queryRoleByUserInfo(userInfo);
            userInfo.setUserRole(userRole);

            // 如果合法将用户信息存入SESSION
            request.getSession().setAttribute("loginUser", userInfo);
            result.put("success", "1");
        }
        else
        { 
            // 登陆失败
        	result.put("msg","用户登陆失败，用户名或者账号不正确");
        	result.put("success", "0");
        }   
    	return result;
    }
    
    @RequestMapping("intro")
    public void intro(){
	   
    }
   
    @RequestMapping("myData")
    public String myData(HttpSession session, Model model)
    {
        Object loginCustomer = session.getAttribute("loginCustomer");
        if (null == loginCustomer)
        {
            model.addAttribute("fromWhere", "/index/index/toIndex.do");
            return MyConstants.LOGIN;
        }
        return "/index/index/myData";
    }

    @RequestMapping("updateHead")
    public String updateHead(HttpSession session, Model model)
    {
        Object loginCustomer = session.getAttribute("loginCustomer");
        if (null == loginCustomer)
        {
            model.addAttribute("fromWhere", "/index/index/toIndex.do");
            return MyConstants.LOGIN;
        }
        return "/index/index/updateHead";
    }

    @RequestMapping("updatePhone")
    public String updatePhone(HttpSession session, Model model)
    {
        Object loginCustomer = session.getAttribute("loginCustomer");
        if (null == loginCustomer)
        {
            model.addAttribute("fromWhere", "/index/index/toIndex.do");
            return MyConstants.LOGIN;
        }
        return "/index/index/updatePhone";
    }

    @RequestMapping("updateName")
    public String updateName(HttpSession session, Model model)
    {
        Object loginCustomer = session.getAttribute("loginCustomer");
        if (null == loginCustomer)
        {
            model.addAttribute("fromWhere", "/index/index/toIndex.do");
            return MyConstants.LOGIN;
        }
        return "/index/index/updateName";
    }

    @ResponseBody
    @RequestMapping("doUpdateName")
    public String doUpdateName(HttpSession session, Model model,
            String customerName)
    {
        Object loginCustomer = session.getAttribute("loginCustomer");
        if (null == loginCustomer)
        {
            model.addAttribute("fromWhere", "/index/index/toIndex.do");
            return MyConstants.LOGIN;
        }
        if (StringUtils.isNotBlank(customerName))
        {
            CustomInfo customer = (CustomInfo) loginCustomer;
            customer.setCustomName(customerName);
            service.update(customer);
        }
        return MyConstants.SUCCESS;
    }

    @RequestMapping("updateSex")
    public String updateSex(HttpSession session, Model model)
    {
        Object loginCustomer = session.getAttribute("loginCustomer");
        if (null == loginCustomer)
        {
            model.addAttribute("fromWhere", "/index/index/toIndex.do");
            return MyConstants.LOGIN;
        }
        return "/index/index/updateSex";
    }

    @ResponseBody
    @RequestMapping("doUpdateSex")
    public String doUpdateSex(HttpSession session, Model model, int sex)
    {
        Object loginCustomer = session.getAttribute("loginCustomer");
        if (null == loginCustomer)
        {
            model.addAttribute("fromWhere", "/index/index/toIndex.do");
            return MyConstants.LOGIN;
        }
        CustomInfo customer = (CustomInfo) loginCustomer;
        customer.setCustomSex(sex);
        service.update(customer);
        return MyConstants.SUCCESS;
    }

    @RequestMapping("updatePwd")
    public String updatePwd(HttpSession session, Model model)
    {
        Object loginCustomer = session.getAttribute("loginCustomer");
        if (null == loginCustomer)
        {
            model.addAttribute("fromWhere", "/index/index/toIndex.do");
            return MyConstants.LOGIN;
        }
        return "/index/index/updatePwd";
    }

    @ResponseBody
    @RequestMapping("doUpdatePwd")
    public String doUpdatePwd(HttpSession session, Model model, String newPasswd)
    {
        Object loginCustomer = session.getAttribute("loginCustomer");
        if (null == loginCustomer)
        {
            model.addAttribute("fromWhere", "/index/index/toIndex.do");
            return MyConstants.LOGIN;
        }
        CustomInfo customer = (CustomInfo) loginCustomer;
        customer.setLoginPassword(newPasswd);
        service.update(customer);
        return MyConstants.SUCCESS;
    }

    @ResponseBody
    @RequestMapping("doUpdatePhone")
    public String doUpdatePhone(HttpSession session, String phone,
            String checkCode)
    {
        Object loginCustomer = session.getAttribute("loginCustomer");
        if (null == loginCustomer) { return "login"; }
        CustomInfo customer = (CustomInfo) loginCustomer;
        if (StringUtils.isNotBlank(phone))
        {
            Object code = session.getAttribute("checkCode");
            if (null == code) { return "checkCodeIsNull"; }
            String sendCode = (String) code;
            if (!sendCode.equalsIgnoreCase(checkCode)) { return "checkCodeIsError"; }
            customer.setTelphone(phone);
            service.update(customer);
        }
        return MyConstants.SUCCESS;
    }

    @ResponseBody
    @RequestMapping("doUpdateHead")
    public String doUpdateHead(HttpSession session, Model model,
            MultipartHttpServletRequest request)
    {
        Object loginCustomer = session.getAttribute("loginCustomer");
        if (null == loginCustomer) { return "login"; }
        CustomInfo customer = (CustomInfo) loginCustomer;
        service.doUpdateHead(session, model, request, customer);
        return MyConstants.SUCCESS;
    }

    @RequestMapping("updateAddress")
    public String updateAddress(HttpSession session, Model model)
    {
        Object loginCustomer = session.getAttribute("loginCustomer");
        if (null == loginCustomer)
        {
            model.addAttribute("fromWhere", "/index/index/toIndex.do");
            return MyConstants.LOGIN;
        }
        CustomInfo customer = (CustomInfo) loginCustomer;
        List<CustomAddr> list = service.getAddressByCustomerId(customer
                .getCustomId());
        if (list.size() > 0)
        {
            model.addAttribute("list", list);
        }
        return "/index/index/updateAddress";
    }

    @ResponseBody
    @RequestMapping("addAddress")
    public String addAddress(HttpSession session, Model model, String addrName)
    {
        Object loginCustomer = session.getAttribute("loginCustomer");
        if (null == loginCustomer)
        {
            model.addAttribute("fromWhere", "/index/index/toIndex.do");
            return MyConstants.LOGIN;
        }
        CustomInfo customer = (CustomInfo) loginCustomer;
        CustomAddr customAddr = new CustomAddr();
        customAddr.setAddress(addrName);
        customAddr.setCustomInfo(customer);
        customAddr.setAddDate(DateUtil.getCurrentDateTime());
        customAddr.setIsDefault(0);
        service.add(customAddr);
        return MyConstants.SUCCESS;
    }

    @ResponseBody
    @RequestMapping("setDefaultAddress")
    public String setDefaultAddress(HttpSession session, Model model, String id)
    {
        Object loginCustomer = session.getAttribute("loginCustomer");
        if (null == loginCustomer)
        {
            model.addAttribute("fromWhere", "/index/index/toIndex.do");
            return MyConstants.LOGIN;
        }
        CustomInfo customer = (CustomInfo) loginCustomer;
        List<CustomAddr> list = service.getAddressByCustomerId(customer
                .getCustomId());
        if (list.size() > 0)
        {
            for (CustomAddr customAddr : list)
            {
                if (customAddr.getAddrId().equalsIgnoreCase(id))
                {
                    customAddr.setIsDefault(1);
                }
                else
                {
                    customAddr.setIsDefault(0);
                }
                customAddr.setCustomInfo(customer);
                service.update(customAddr);
            }
        }
        return MyConstants.SUCCESS;
    }

    @ResponseBody
    @RequestMapping("delAddress")
    public String delAddress(HttpSession session, Model model, String id)
    {
        Object loginCustomer = session.getAttribute("loginCustomer");
        if (null == loginCustomer)
        {
            model.addAttribute("fromWhere", "/index/index/toIndex.do");
            return MyConstants.LOGIN;
        }
        CustomAddr address = service.getByHibernate(CustomAddr.class, id);
        service.del(address);
        return MyConstants.SUCCESS;
    }

    @RequestMapping("coupon")
    public String coupon(HttpSession session, Model model)
    {
        Object loginCustomer = session.getAttribute("loginCustomer");
        if (null == loginCustomer)
        {
            model.addAttribute("fromWhere", "/index/index/toIndex.do");
            return MyConstants.LOGIN;
        }
        CustomInfo customer = (CustomInfo) loginCustomer;
        List<Coupon> list = service.getCustomCoupon(customer.getCustomId());
        if (list.size() > 0)
        {
            model.addAttribute("list", list);
        }
        return "/index/index/coupon";
    }

    /***
     * 显示收藏页面信息
     * @param session
     * @param model
     * @return
     */
    @RequestMapping("myLike")
    public String myLike(HttpSession session, Model model)
    {
        Object loginCustomer = session.getAttribute("loginCustomer");
        if (null == loginCustomer)
        {
            model.addAttribute("fromWhere", "/index/index/toIndex.do");
            return MyConstants.LOGIN;
        }

        return "/index/index/myLike";
    }
    
    @ResponseBody
    @RequestMapping("queryMyLike")
    public List<CustomerLikeExt> queryMyLike(HttpSession session,String type,int curSize){
    	Object loginCustomer = session.getAttribute("loginCustomer");
        if (null != loginCustomer)
        {
            CustomInfo customer = (CustomInfo) loginCustomer;
            return service.myLike(customer.getCustomId(), type, curSize, 10);
        }
       return null;
    }

    @ResponseBody
    @RequestMapping("delShopLike")
    public String delShopLike(HttpSession session,String id)
    {
        Object loginCustomer = session.getAttribute("loginCustomer");
        if (null != loginCustomer) {
        	service.update("delete from custom_like where like_id=?", new Object[] { id });
        	return "success";
        }
        return "error";
    }

    @RequestMapping("delCookbookLike")
    public String delCookbookLike(HttpSession session, Model model, String id)
    {
        Object loginCustomer = session.getAttribute("loginCustomer");
        if (null == loginCustomer)
        {
            model.addAttribute("fromWhere", "/index/index/toIndex.do");
            return MyConstants.LOGIN;
        }
        service.update("delete from custom_like where GOODS_ID=?", new Object[]
        { id });
        return "redirect:/index/index/myLike.do";
    }

    @RequestMapping("delMyLike")
    public String delMyLike(HttpSession session, Model model, String id)
    {
        Object loginCustomer = session.getAttribute("loginCustomer");
        if (null == loginCustomer)
        {
            model.addAttribute("fromWhere", "/index/index/toIndex.do");
            return MyConstants.LOGIN;
        }
        service.update("delete from custom_like where GOODS_ID=?", new Object[]
        { id });
        return "redirect:/index/index/myLike.do";
    }

    @RequestMapping("couponDetail")
    public String couponDetail(HttpSession session, Model model, String id)
    {
        Object loginCustomer = session.getAttribute("loginCustomer");
        if (null == loginCustomer)
        {
            model.addAttribute("fromWhere", "/index/index/toIndex.do");
            return MyConstants.LOGIN;
        }
        model.addAttribute("customCoupon", service.couponDetail(id));
        return "/index/index/couponDetail";
    }

    @RequestMapping("systemInfo")
    public String systemInfo(HttpSession session, Model model)
    {
        Object loginCustomer = session.getAttribute("loginCustomer");
        if (null == loginCustomer)
        {
            model.addAttribute("fromWhere", "/index/index/toIndex.do");
            return MyConstants.LOGIN;
        }
        return "/index/index/systemInfo";
    }

    @RequestMapping("exit")
    public String exit(HttpSession session, Model model)
    {
        Object loginCustomer = session.getAttribute("loginCustomer");
        if (null == loginCustomer)
        {
            model.addAttribute("fromWhere", "/index/index/toIndex.do");
            return MyConstants.LOGIN;
        }
        session.removeAttribute("loginCustomer");
        session.invalidate();
        model.addAttribute("fromWhere", "/index/index/toIndex.do");
        return MyConstants.LOGIN;
    }

    @RequestMapping("msg")
    public String msg(HttpSession session, Model model)
    {
        Object loginCustomer = session.getAttribute("loginCustomer");
        if (null == loginCustomer)
        {
            model.addAttribute("fromWhere", "/index/index/toIndex.do");
            return MyConstants.LOGIN;
        }
        return "/index/index/msg";
    }

    @ResponseBody
    @RequestMapping("doMsg")
    public String doMsg(HttpSession session, Model model, String content,
            String contact)
    {
        Object loginCustomer = session.getAttribute("loginCustomer");
        if (null == loginCustomer)
        {
            model.addAttribute("fromWhere", "/index/index/toIndex.do");
            return MyConstants.LOGIN;
        }
        Feedback feedback = new Feedback();
        feedback.setAddDate(DateUtil.getCurrentDateTime());
        feedback.setContent(content);
        feedback.setContact(contact);
        service.add(feedback);
        return MyConstants.SUCCESS;
    }

    @RequestMapping("food")
    public void food()
    {
    }

    @ResponseBody
    @RequestMapping("foodList")
    public DataGridModel<FoodExp> foodList(DataGridModel<FoodExp> list)
    {
        return service.foodList(list);
    }

    @RequestMapping("foodDetail")
    public void foodDetail(Model model, String id)
    {
        model.addAttribute("food", service.getFoodExp(id));
    }

    @RequestMapping("table")
    public void table(Model model)
    {
        model.addAttribute("tip", "dingzhuo");
    }

    @RequestMapping("queryTableByType")
    public void queryTableByType(String type, Model model)
    {
        service.queryTableByType(type, model);
    }

    @ResponseBody
    @RequestMapping("/showByType")
    public List<ShopExt> showByType(HttpSession session, int filterType,
            String filter, int currentSize, String typeName)
    {
        String longitude = (String) session.getAttribute("longitude");
        String latitude = (String) session.getAttribute("latitude");
        return service.showByType(currentSize, MyConstants.PAGE_SIZE,
                longitude, latitude, filterType, filter, typeName);
    }

    @RequestMapping("tableDetail")
    public void tableDetail(Model model, String id, HttpSession session)
    {
        service.tableDetail(model, (String) session.getAttribute("longitude"),
                (String) session.getAttribute("latitude"), id, session,
                customLikeService);
    }

    @RequestMapping("popularity")
    public void popularity()
    {

    }

    @RequestMapping("hot")
    public void hot()
    {

    }

    /**
     * 火爆小吃json
     * @param list
     * @return
     */
    @ResponseBody
    @RequestMapping("hotList")
    public DataGridModel<Shop> hotList(DataGridModel<Shop> list)
    {
        return service.hotList(list);
    }

    /**
     * 人气餐厅json 
     * @param list
     * @return
     */
    @ResponseBody
    @RequestMapping("popularityList")
    public DataGridModel<Shop> popularityList(DataGridModel<Shop> list)
    {
        return service.popularityList(list);
    }

    @RequestMapping("popularityDetail")
    public void popularityDetail(Model model, String id)
    {
        service.popularityDetail(model, id);
    }

    @RequestMapping("myConsume")
    public void myConsume(HttpServletRequest request, Model model)
    {
        List<OrdersIndexExt> list = ordersService.queryMyOrder(request);
        if (list.size() > 0)
        {
            model.addAttribute("list", list);
        }
    }
}
