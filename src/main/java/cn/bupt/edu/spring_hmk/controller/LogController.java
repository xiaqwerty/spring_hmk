package cn.bupt.edu.spring_hmk.controller;


import cn.bupt.edu.spring_hmk.dao.CheckCodeRepository;
import cn.bupt.edu.spring_hmk.dao.UserRepository;
import cn.bupt.edu.spring_hmk.entity.CheckCode;
import cn.bupt.edu.spring_hmk.entity.User;
import cn.bupt.edu.spring_hmk.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Random;

@Controller
public class LogController {
    private UserRepository userRepository;
    private CheckCodeRepository checkCodeRepository;
    private MailService mailService;
    @Autowired
    public void setUserRepository(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Autowired
    public void setCheckCodeRepository(CheckCodeRepository checkCodeRepository){
        this.checkCodeRepository=checkCodeRepository;
    }
    @Autowired
    public void setMailService(MailService mailService){
        this.mailService=mailService;
    }

    @GetMapping("/")
    public String log(){
        return "login";
    }

    @RequestMapping("/login")
    public String getLogin(){
        return "login";
    }
    @PostMapping("/login")
    public String postLogin(HttpServletRequest req,
                            Model model,
                            @RequestParam("email") String email,
                            @RequestParam("password") String password){
        if (email.equals("") || password.equals("")){
            model.addAttribute("result", "不能为空");
            return "login";
        }
        List<User> users = userRepository.findByEmail(email);
        if(users!=null && users.size() > 0){
            if(users.get(0).getPassword().equals(password)){
                //暂时不设计失败三次的登陆限制
                req.getSession().setAttribute("userId", users.get(0).getUserId());
                model.addAttribute("size", 2);
                return "redirect:frontpage?sort=time&start=0&size=2";
            }
//            users.get(0).setErrorNum(users.get(0).getErrorNum()+1);
            model.addAttribute("result", "密码错误，累计三次则一小时内无法登录");
            return "login";
        }
        model.addAttribute("result", "用户不存在");
        return "login";
    }

    @GetMapping("/sign1")
    public String getSign1(){
        return "sign1";
    }


    @PostMapping("/sign1")
    public String postSign1(HttpServletRequest req,
                            Model model,
                            @RequestParam("email") String email){
        if(email.equals("")){
            model.addAttribute("result", "邮箱不能为空");
            return "sign1";
        }
        if(userRepository.existsByEmail(email)){
            model.addAttribute("result", "邮箱已经注册");
            return "sign1";
        }
        model.addAttribute("email", "email: " + email);
        req.getSession().setAttribute("email", email);
        String chechCode = String.valueOf(new Random().nextInt(899999)+100000);
        String message = "您的验证码为: "+chechCode;
        checkCodeRepository.save(new CheckCode(email, chechCode));
        mailService.sendSimpleMail(email, "验证码", message);
        return "sign2";
    }
    @GetMapping("/sign2")//本GetMapping Controller仅供测试用，直接进入/sign2注册，邮箱1@qq.com 验证码1111。绕过获取验证码环节
    public String postSign2(HttpServletRequest req)
    {
        req.getSession().setAttribute("email","xiaqwerty@yahoo.com");
        CheckCode check=new CheckCode();
        check.setEmail("1@qq.com");
        check.setCheckCode("1111");
        checkCodeRepository.save(check);
        return "sign2";
    }
    @PostMapping("/sign2")
    public String postSign2(HttpServletRequest req,
                            Model model,
                            @RequestParam("checkCode") String verification,
                            @RequestParam("name") String name,
                            @RequestParam("password1") String password1,
                            @RequestParam("password2") String password2){
        if (verification.equals("") || name.equals("") || password1.equals("") || password2.equals("")){
            model.addAttribute("result", "不能为空");
            return "sign2";
        }
        String email = req.getSession().getAttribute("email").toString();
        CheckCode check = checkCodeRepository.findByEmail(email).get(0);
        model.addAttribute("email", "email: " + req.getSession().getAttribute("email"));
        String checkCode = check.getCheckCode();
        if(verification.equals("1111") || verification.equals(checkCode)){
            if(password1.equals(password2)){
                User u = new User((String)req.getSession().getAttribute("email"), name, password1, "");
                req.getSession().removeAttribute("email");
                req.getSession().invalidate();
                userRepository.save(u);
                checkCodeRepository.delete(check);
                return "login";
            }
            model.addAttribute("check", checkCode);
            model.addAttribute("name", name);
            model.addAttribute("result", "密码不一致，重新输入密码");
            return "sign2";
        }
        model.addAttribute("result", "验证码错误，重新输入邮箱");
        checkCodeRepository.delete(check);
        return "sign1";
    }


    /*@PostMapping("/sign1")
    public String postSign1(HttpServletRequest req,
                            Model model,
                            @RequestParam("email") String email){
        if(email.equals("")){
            model.addAttribute("result", "邮箱不能为空");
            return "sign1";
        }
        if(userRepository.existsByEmail(email)){
            model.addAttribute("result", "邮箱已经注册");
            return "sign1";
        }
        model.addAttribute("email", "email: " + email);
        req.getSession().setAttribute("email", email);
        //String chechCode = String.valueOf(new Random().nextInt(899999)+100000);
        //String message = "您的验证码为: "+chechCode;
        //checkCodeRepository.save(new CheckCode(email, chechCode));
        //mailService.sendSimpleMail(email, "验证码", message);
        return "sign2";
    }

    @PostMapping("/sign2")
    public String postSign2(HttpServletRequest req,
                            Model model,
                            @RequestParam("checkCode") String verification,
                            @RequestParam("name") String name,
                            @RequestParam("password1") String password1,
                            @RequestParam("password2") String password2){
        if (verification.equals("") || name.equals("") || password1.equals("") || password2.equals("")){
            model.addAttribute("result", "不能为空");
            return "sign2";
        }
        String email = req.getSession().getAttribute("email").toString();
        //CheckCode check = checkCodeRepository.findByEmail(email).get(0);
        model.addAttribute("email", "email: " + req.getSession().getAttribute("email"));
       // String checkCode = check.getCheckCode();
        if(verification.equals("1111") ){
            if(password1.equals(password2)){
                User u = new User((String)req.getSession().getAttribute("email"), name, password1, "");
                req.getSession().removeAttribute("email");
                req.getSession().invalidate();
                userRepository.save(u);
                //checkCodeRepository.delete(check);
                return "login";
            }
            //model.addAttribute("check", checkCode);

            model.addAttribute("name", name);
            model.addAttribute("result", "密码不一致，重新输入密码");
            return "sign2";
        }
        model.addAttribute("result", "验证码错误，重新输入邮箱");
        //checkCodeRepository.delete(check);
        return "sign1";
    }
*/
    @GetMapping("/logout")
    public String getExit(HttpServletRequest req){
        req.getSession().removeAttribute("userId");
        req.getSession().invalidate();
        return "login";
    }

    @GetMapping("/tourist")
    public String getTourist(){
        return "redirect:frontpage?sort=time&start=0&size=2";
    }
}

