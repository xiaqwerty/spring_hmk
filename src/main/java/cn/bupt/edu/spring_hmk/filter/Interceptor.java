package cn.bupt.edu.spring_hmk.filter;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Interceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest req,
                             HttpServletResponse res,
                             Object o){
        Object obj = req.getSession().getAttribute("userId");
        if (obj != null)
            return true;
        try
        {
            res.sendRedirect("/login");
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return false;
    }
}
