package cn.bupt.edu.spring_hmk.controller;

import cn.bupt.edu.spring_hmk.entity.Person;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/addPerson")
@SessionAttributes("people")
public class AddPersonController
{
    @GetMapping("/addList")
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
        model.addAttribute("people",people);
        if(!isEmail(email))
        {
            return "emailError";
        }
        else
        {
            Person person=new Person(name, phoneNumber, email, address, QQ);
            people.add(person);
            model.addAttribute("people",people);
            return "myAddressBook";
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
