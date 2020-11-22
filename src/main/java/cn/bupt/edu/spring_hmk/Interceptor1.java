package cn.bupt.edu.spring_hmk;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class Interceptor1 extends HandlerInterceptorAdapter
{
    @Override
    public boolean preHandle(HttpServletRequest request,HttpServletResponse response,Object handler)
    {
        HttpSession session=request.getSession();
        /*String uri = request.getRequestURI();//从WebConfig配置跳过login.do一直失败。。。暂且在interceptor里跳过吧
        System.out.println(uri);
        if("/login".equals(uri))
        {
            return true;
        }*/
        if(session.getAttribute("login")==null|| session.getAttribute("login").equals(false))
        {
            //chain.doFilter(req, resp);
            System.out.println("检测到未登陆，正在跳转……");
            try
            {
                response.sendRedirect(request.getContextPath()+"/");
            } catch (IOException e)
            {
                e.printStackTrace();
            }
            return false;
        }
        else
            //chain.doFilter(req, resp);
            return true;
    }
}
