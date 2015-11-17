package com.sunspot.controller.manager;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sunspot.common.Constants;
import com.sunspot.pojo.UserMenus;
import com.sunspot.pojo.UserInfo;
import com.sunspot.pojo.UserRole;
import com.sunspot.pojoext.UserSessionUtil;
import com.sunspot.service.MenusService;
import com.sunspot.service.UserInfoService;
import com.sunspot.service.UserRoleService;

/**
 * 后台登录
 * 
 * @author LuoAnDong
 * 
 */
@Controller
public class LoginControl
{

    private static final String PERFIX = "manager/login/";

    /**
     * 登录成功
     */
    private static final String LOGIN_SUCCESS = PERFIX + "main";

    /**
     * 登录页面
     */
    private static final String LOGIN_PAGE = PERFIX + "login";

    /**
     * 重定向登录页面
     */
    private static final String RE_LOGIN_PAGE = "redirect:/toLogin.do";

    /**
     * 框架页头
     */
    private static final String LOGIN_HEADER = PERFIX + "header";

    /**
     * 框架页主页
     */
    private static final String LOGIN_HOME = PERFIX + "home";

    /**
     * 运行日志
     */
    private final Log runLog = LogFactory.getLog(LoginControl.class);

    /**
     * 用户管理业务层
     */
    @Autowired
    private UserInfoService userInfoService;

    /**
     * 角色业务层
     */
    @Autowired
    private UserRoleService userRoleService;

    /**
     * 菜单业务层
     */
    @Autowired
    private MenusService menusService;

    /**
     * 登录
     * 
     * @param username
     *            用户名
     * @param password
     *            密码
     * @return LOGIN_PAGE
     */
    @RequestMapping("/toLogin")
    public String toLogin(ModelMap modelMap)
    {
        return LOGIN_PAGE;
    }

    /**
     * 框架页头
     * 
     * @param username
     *            用户名
     * @param password
     *            密码
     * @return LOGIN_HEADER
     */
    @RequestMapping("/toHeader")
    public String toHeader(ModelMap modelMap, HttpServletRequest request)
    {
        dealUserRole(modelMap, request, null);
        return LOGIN_HEADER;
    }

    private void dealUserRole(ModelMap modelMap, HttpServletRequest request,
            String menuId)
    {
        // 得到登陆用户信息
        UserInfo userInfo = UserSessionUtil.getUserInfo(request);

        // 得到用户角色
        UserRole userRole = userInfo.getUserRole();

        // 子类菜单　
        if (menuId != null)
        {

            // 查询出菜单的模块类型
            UserMenus parentMenus = menusService.queryMenuUrlById(menuId);

            // 根据用户权限去查询厅级菜单下的子菜单信息
            List<UserMenus> subMenus = menusService.querySubMenuByParantId(
                    userRole, menuId.trim(), userInfo.getUserType());

            modelMap.addAttribute("subMenus", subMenus);
            modelMap.addAttribute("parentMenus", parentMenus);

        }
        else
        {

            // 得到主菜单信息
            List<UserMenus> mainMenus = menusService.queryMainMenuByRole(
                    userRole, userInfo.getUserType());
            modelMap.addAttribute("mainMenus", mainMenus);
            modelMap.addAttribute("userRole", userRole);

        }
    }

    /**
     * 框架主页
     * 
     * @param username
     *            用户名
     * @param password
     *            密码
     * @return LOGIN_HOME
     */
    @RequestMapping("/toHome")
    public String toHOME(ModelMap modelMap, String menuId,
            HttpServletRequest request)
    {

        dealUserRole(modelMap, request, menuId);

        return LOGIN_HOME;
    }

    /**
     * 登录
     * 
     * @param username
     *            用户名
     * @param password
     *            密码
     * @param loginType
     *            登陆类型 0为业务后 1为商家
     * @return LOGIN_PAGE
     */
    @RequestMapping("/login")
    public String login(String userName, String password, String loginType,
            HttpServletRequest request, ModelMap modelMap)
    {

        UserInfo userInfo = (UserInfo) request.getSession().getAttribute(
                "loginUser");

        if (null != userInfo) { return LOGIN_SUCCESS; }

        // 如果登录用户名或密码为空则直接返回
        if (StringUtils.isBlank(userName) || StringUtils.isBlank(password))
        {
            modelMap.addAttribute("errorCode", Constants.USER_OR_PASS_NULL);
            runLog.info("login failed,ERRORCODE:" + Constants.USER_OR_PASS_NULL);
            return RE_LOGIN_PAGE;
        }

        // 验证用户信息
        userInfo = userInfoService.checkUserLogin(userName.trim(),
                password.trim(), loginType);

        // 登陆成功
        if (userInfo != null)
        {
            // 查询用户角色　
            UserRole userRole = userRoleService.queryRoleByUserInfo(userInfo);
            userInfo.setUserRole(userRole);

            // 如果合法将用户信息存入SESSION
            request.getSession().setAttribute("loginUser", userInfo);
            runLog.info("login success,userName=" + userName);
            return LOGIN_SUCCESS; 
        }
        else
        { 
            // 登陆失败
            runLog.info("用户登陆失败，用户名或者账号不正确");
            return RE_LOGIN_PAGE;
        }
    }

    /**
     * 退出登录
     * 
     * @return LOGIN_PAGE
     */
    @RequestMapping("/logOut")
    public String logOut(HttpServletRequest request)
    {
        request.getSession().removeAttribute("loginUser");
        return RE_LOGIN_PAGE;
    }

    /**
     * 退出登录
     * 
     * @return LOGIN_PAGE
     */
    @RequestMapping("/logOutShop")
    public String logOutShop(HttpServletRequest request)
    {
        request.getSession().removeAttribute("loginUser");
        return "redirect:/";
    }
}
