package com.base.common.permission.interceptor;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.base.common.permission.annatation.Permission;
import com.base.common.permission.annatation.Permission.ReturnType;
import com.base.common.permission.constant.PermissionConstant;
import com.base.common.permission.vo.LoginInfo;

/**
 * 
 * 权限拦截
 * @author <a href="mailto:zhuzhoucheng@base.com.cn">朱周城</a>
 * @since 1.0.0
 * @version 版本：1.0.0
 * @from 2015年6月24日
 */
public class PermissionInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = Logger.getLogger(PermissionInterceptor.class);
    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {

    }

    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
            throws Exception {

    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
        LOGGER.info("权限拦截...");
        LoginInfo loginUser = (LoginInfo)request.getSession().getAttribute(PermissionConstant.LOGIN_INFO);
        if (loginUser == null) {
            response.sendRedirect(request.getContextPath()+PermissionConstant.LOGIN_URL);
        } else {
            HandlerMethod method = (HandlerMethod)obj;
            //权限注解
            Permission permission = method.getMethodAnnotation(Permission.class);
            if (permission == null || !permission.validate()) {
                return true;
            }
            String pkey = permission.pkey();
            if (StringUtils.isNotEmpty(pkey)) {
                List<String> permissions = loginUser.getPermissions();
                for (String key : permissions) {
                    if (pkey.equals(key)) {
                        return true;
                    }
                }
            }
            ReturnType returnType = permission.returnType();
            if (Permission.ReturnType.page == returnType) {
                //传统的登录页面              
                response.sendRedirect(request.getContextPath()+PermissionConstant.MESSAGE_URL);
            } else {
                //ajax类型的登录提示
                response.setCharacterEncoding("utf-8");
                response.setContentType("text/html;charset=UTF-8");
                PrintWriter writer = response.getWriter();
                StringBuffer sb = new StringBuffer();
                sb.append("{\"code\":-1,\"msg\":\"没有权限\"}");
                writer.println(sb.toString());
                writer.flush();
                writer.close();
            }
        }
        return false;
    }

}
