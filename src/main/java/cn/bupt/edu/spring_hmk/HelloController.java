package cn.bupt.edu.spring_hmk;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController
{
    @RequestMapping("/hello")
    public String SayHello(String name)
    {
        return "Hello"+name;
    }
}
