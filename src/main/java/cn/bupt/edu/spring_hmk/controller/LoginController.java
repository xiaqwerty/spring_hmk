package cn.bupt.edu.spring_hmk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller

public class LoginController
{
    @PostMapping("/login")
    public String login(String Username, String Password, HttpServletRequest request)throws Exception
    {
        System.out.println("当前用户名："+Username+"    密码："+Password);
        HttpSession session=request.getSession();
        session.setAttribute("login",false);
        session.setAttribute("whichEdit",-1);//AddPersonController用
        if(Username.equals("admin")&&Password.equals("123456"))
        {
            session.setAttribute("login",true);
            System.out.println("login success!");
            return "redirect:/myAddressBook";
        }
        else
        {
            System.out.println("wrong login request detected");
            System.out.println("Wrong user is: "+Username);
            System.out.println("Wrong password is: "+Password);
            return "redirect:/myFrontPage";
        }
    }
}
