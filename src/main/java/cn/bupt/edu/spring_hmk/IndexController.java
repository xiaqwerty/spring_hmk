package cn.bupt.edu.spring_hmk;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController
{
    @RequestMapping("/")
    public String index()
    {
        return "myFrontPage";
    }
    @RequestMapping("/myAddressBook")
    public String myAddressBook()
    {
        return "myAddressBook";
    }
    @RequestMapping("/myFrontPage")
    public String myFrontPage()
    {
        return "myFrontPage";
    }
    @RequestMapping("/addPerson")
    public String addPerson()
    {
        return "addPerson";
    }
}