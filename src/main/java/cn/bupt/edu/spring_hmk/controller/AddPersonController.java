package cn.bupt.edu.spring_hmk.controller;

import cn.bupt.edu.spring_hmk.dao.PersonRepository;
import cn.bupt.edu.spring_hmk.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.expression.Maps;

import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/addPerson")
@SessionAttributes("whichEdit")
public class AddPersonController
{
    private PersonRepository personRepository;
    @Autowired
    public void setPersonRepository(PersonRepository personRepository)
    {
        this.personRepository=personRepository;
    }
    @GetMapping("/addList")
    /*public String addPerson(Model model, @SessionAttribute(required = false) List<Person> people)
    {
        if (people == null)
        {
            people = new ArrayList<Person>();
        }
        model.addAttribute("people",people);
        model.addAttribute("whichEdit",-1);//点击add时whichEdit置-1
        return "addPerson";
    }*/
    public String addPerson(Model model,@SessionAttribute int whichEdit)
    {
        List<Person> people=  personRepository.findAll();
        model.addAttribute("people",people);
        whichEdit=-1;
        model.addAttribute("whichEdit",-1);
        return "addPerson";
    }

    @PostMapping("/add")
    public String addPerson(Model model,@SessionAttribute int whichEdit,
                            String name, String phoneNumber, String email, String address, String QQ)
    {
        List<Person>people=personRepository.findAll();
        model.addAttribute("people", people);
        if (!isEmail(email))
        {
            return "emailError";
        } else
        {
            if (whichEdit == -1)//whichEdit=-1时添加元素 {
            {
                Person person = new Person(name, phoneNumber, email, address, QQ);
                //people.add(person);
                personRepository.save(person);
                //model.addAttribute("people", people);
            }
            else                    //bug--当编辑未完成取消时，whichEdit不会置-1
            {
                //Person person = people.get(whichEdit);
                Person person=personRepository.findById(whichEdit).get();
                person.setPerson(name, phoneNumber, email, address, QQ);
                person.setId(whichEdit);
                //people.set(whichEdit, person);
                whichEdit = -1;//edit完成后置于-1
                //model.addAttribute("people", people);
                model.addAttribute("whichEdit", whichEdit);
                personRepository.saveAndFlush(person);
            }
            people=personRepository.findAll();
            model.addAttribute("people",people);
            return "myAddressBook";
        }
    }
    @RequestMapping("/cancel")//点击取消的情况
    public String cancelConfig(Model model)
    {
        List<Person>people=personRepository.findAll();
        model.addAttribute("people", people);
        return "myAddressBook";
    }
    @GetMapping("/edit")
    public String editPerson(Model model,@SessionAttribute int whichEdit,@RequestParam int id)
    {
        whichEdit=id;
        model.addAttribute("whichEdit",whichEdit);
        return "addPerson";
    }
    @GetMapping("/delete")
    public String deletePerson(Model model,@RequestParam int id)
    {
        System.out.println("delete by id："+id);
        personRepository.deleteById(id);
        List<Person>people=personRepository.findAll();
        model.addAttribute("people",people);
        return "myAddressBook";
    }
    @GetMapping("/ifDuplicate")
    @ResponseBody
    //还是用thymeleaf+map实现的
    /*public Map<String,Boolean> ifDuplicate(@SessionAttribute List<Person> people,@RequestParam String str)
    {
        Map<String, Boolean> map = new HashMap<String, Boolean>();
        map.put("ifDuplicate",false);
        Iterator<Person> iterator=people.iterator();
        while(iterator.hasNext())
        {
            if(iterator.next().getPhoneNumber().equals(str))
                //model.addAttribute("ifDuplicate",true);
                map.put("ifDuplicate",true);
        }
        return map;
    }*/
    //ajax配合实现
    public String ifDuplicate(@RequestParam String num)
    {
        //if (people == null)
       // {
       //     people = new ArrayList<Person>();
       //  }
        List<Person>people=personRepository.findAll();
        Iterator<Person> iterator=people.iterator();
        while(iterator.hasNext())
        {
            if(iterator.next().getPhoneNumber().equals(num))
               return "该电话号码已存在！";
        }
        return "";
    }
    private static boolean isEmail(String email)
    {
        String myReg="[a-zA-Z_]{1,}[0-9]{0,}@(([a-zA-z0-9]-*){1,}\\.){1,3}[a-zA-z\\-]{1,}";
        Pattern pattern=Pattern.compile(myReg);
        Matcher matcher= pattern.matcher(email);
        return matcher.matches();
    }

}
