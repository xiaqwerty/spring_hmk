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
@SessionAttributes(value = {"people","whichEdit"})
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
    public String addPerson(Model model,@SessionAttribute List<Person> people,@SessionAttribute int whichEdit,
                            String name, String phoneNumber, String email, String address, String QQ)
    {
        model.addAttribute("people",people);
        if(!isEmail(email))
        {
            return "emailError";
        }
        else
        {
            if(whichEdit==-1)//whichEdit=-1时添加元素
            {
                Person person=new Person(name, phoneNumber, email, address, QQ);
                people.add(person);
                model.addAttribute("people",people);
            }
            else
            {
                Person person=people.get(whichEdit);
                person.setPerson(name, phoneNumber, email, address, QQ);
                people.set(whichEdit,person);
                whichEdit=-1;//edit完成后置于-1
                model.addAttribute("people",people);
                model.addAttribute("whichEdit",whichEdit);
            }
            return "myAddressBook";
        }
    }
    @GetMapping("/edit")
    public String editPerson(Model model,@SessionAttribute List<Person>people,@SessionAttribute int whichEdit,@RequestParam int id)
    {
        whichEdit=id;
        model.addAttribute("whichEdit",whichEdit);
        return "addPerson";
    }
    @GetMapping("/delete")
    public String deletePerson(Model model,@SessionAttribute List<Person> people,@RequestParam int id)
    {
        System.out.println(id);
        people.remove(id);
        model.addAttribute("people",people);
        return "myAddressBook";
    }
    private static boolean isEmail(String email)
    {
        String myReg="[a-zA-Z_]{1,}[0-9]{0,}@(([a-zA-z0-9]-*){1,}\\.){1,3}[a-zA-z\\-]{1,}";
        Pattern pattern=Pattern.compile(myReg);
        Matcher matcher= pattern.matcher(email);
        return matcher.matches();
    }

}
