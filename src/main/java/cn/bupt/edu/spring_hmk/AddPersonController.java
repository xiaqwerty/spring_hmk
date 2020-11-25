package cn.bupt.edu.spring_hmk;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/addPerson")
public class AddPersonController
{
    int i=0;
    @RequestMapping("/list")
    public String addPerson(Model model, @SessionAttribute(required = false) List<Person> people)
    {
        if (people == null)
        {
            people = new ArrayList<Person>();
        }
        model.addAttribute("people",people);
        return "addPerson";
    }

    @PostMapping("/add")
    public String addPerson(Model model,@SessionAttribute List<Person> people,
                            String name, String phoneNumber, String email, String address, String QQ, HttpServletResponse res)
    {
        if(!isEmail(email))
        {
            res.setContentType("text/html; charset=UTF-8");
            PrintWriter out = null;
            try
            {
                out = res.getWriter();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
            out.flush();
            out.println("<script>");
            out.println("alert('邮箱格式错误！');");
            out.println("history.back();");
            out.println("</script>");
            return "redirect:/addPerson";
        }
        else
        {
            Person person=new Person(name, phoneNumber, email, address, QQ);
            people.add(person);
            model.addAttribute("people",people);
            return "redirect:/myAddressBook";
        }
    }
    private static boolean isEmail(String email)
    {
        String myReg="[a-zA-Z_]{1,}[0-9]{0,}@(([a-zA-z0-9]-*){1,}\\.){1,3}[a-zA-z\\-]{1,}";
        Pattern pattern=Pattern.compile(myReg);
        Matcher matcher= pattern.matcher(email);
        return matcher.matches();
    }

}
