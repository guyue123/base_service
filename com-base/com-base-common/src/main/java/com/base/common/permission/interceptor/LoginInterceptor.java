package com.base.common.permission.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.base.common.permission.constant.PermissionConstant;
import com.base.common.permission.vo.LoginInfo;


public class LoginInterceptor implements HandlerInterceptor {

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
        LoginInfo loginInfo = (LoginInfo)request.getSession().getAttribute(PermissionConstant.LOGIN_INFO);
        if (loginInfo != null) {
            return true;
        } else {
            if ((request.getHeader("X-Requested-With") != null && "XMLHttpRequest"
                    .equals(request.getHeader("X-Requested-With").toString()))) {
                response.setCharacterEncoding("utf-8");
                response.setContentType("text/html;charset=UTF-8");
                response.setStatus(200);
                PrintWriter writer = response.getWriter();
                StringBuffer sb = new StringBuffer();
                sb.append("{\"code\":-2,\"msg\":\"用户信息已过期,请重新登录\"}");
                writer.println(sb.toString());
                writer.flush();
                writer.close();
                return false;
            } else {
                response.sendRedirect(request.getContextPath()+PermissionConstant.LOGIN_URL);
                return false;
            }
        	
        }
    }

}
