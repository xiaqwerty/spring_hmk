package cn.bupt.edu.spring_hmk;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller

public class LoginController
{
    @PostMapping("/login")
    public String login(String Username, String Password,
                        HttpServletResponse response, HttpServletRequest request)throws Exception
    {
        System.out.println(Username+"    "+Password);
        HttpSession session=request.getSession();
        session.setAttribute("login",false);
        if(Username.equals("admin")&&Password.equals("123456"))
        {
            session.setAttribute("login",true);
            System.out.println("login success!");
            return "redirect:/myAddressBook";
        }
        else
        {
            System.out.println("wrong login request detected");
            System.out.println("Wrong user is: "+request.getParameter("user"));
            System.out.println("Wrong password is: "+request.getParameter("password"));
            return "redirect:/myFrontPage";
        }
    }
}
