package com.sunspot.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.sunspot.pojo.CustomInfo;
import com.sunspot.pojo.UserInfo;

/**
 * 安全验证
 * 
 * @author LuoAnDong
 * 
 */
public class SecurityFilter extends HttpServlet implements Filter
{

    /**
     * 序列号
     */
    private static final long serialVersionUID = -7761091428133060818L;

    /**
     * 请求对象
     */
    private HttpServletRequest request = null;

    /**
     * 响应对象
     */
    private HttpServletResponse response = null;

    /**
     * 重载初始化
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {

    }

    @Override
    public void doFilter(ServletRequest servletRequest,
            ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException
    {

        request = (HttpServletRequest) servletRequest;
        response = (HttpServletResponse) servletResponse;

        // begin:判断是否需要检查权限
        String url = request.getRequestURI();

        url = StringUtils.isNotBlank(url) ? url.toLowerCase() : "";

        if (!url.contains("tohome.do") && !url.contains("/manager/") && !url.contains("sec.do"))
        {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        HttpSession session = request.getSession(true);

        // 权限过滤器测试代码
        UserInfo loginUser = (UserInfo) session.getAttribute("loginUser");// 登录人角色
        CustomInfo customInfo = (CustomInfo) session
                .getAttribute("loginCustomer");// 客户登录角色

        // 判断获取的路径不为空且不是访问登录页面或执行登录操作时跳转
        if (null == loginUser && StringUtils.isNoneBlank(url)
                && (url.contains("/manager/") || url.contains("tohome.do")))
        {
            response.getWriter().write(
                    "<script>window.top.location='/toLogin.do'</script>");
            return;
        }

        // 判断获取的路径不为空且不是访问登录页面或执行登录操作时跳转
        if (null == customInfo && StringUtils.isNoneBlank(url)
                && url.contains("sec.do"))
        {

            if (url.contains("/dinner/"))
            {
                request.getSession().setAttribute("fromWhere",
                        "/index/dinner/ordersec.do");
            }
            else if (url.contains("/orders/"))
            {
                request.getSession().setAttribute("fromWhere","/index/orders/ordershopsec.do");
            }
            else
            {
                request.getSession().setAttribute("fromWhere", "/index/index/toIndex.do");
            }

            response.getWriter()
                    .write("<script>window.top.location='/index/index/login.do'</script>");
            return;
        }
        
        
        filterChain.doFilter(servletRequest, servletResponse);
    }

}
