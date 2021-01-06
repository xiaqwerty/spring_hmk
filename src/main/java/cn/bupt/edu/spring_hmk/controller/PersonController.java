package cn.bupt.edu.spring_hmk.controller;

import cn.bupt.edu.spring_hmk.dao.BlogRepository;
import cn.bupt.edu.spring_hmk.dao.MentionRepository;
import cn.bupt.edu.spring_hmk.dao.UserRepository;
import cn.bupt.edu.spring_hmk.entity.Blog;
import cn.bupt.edu.spring_hmk.entity.Mention;
import cn.bupt.edu.spring_hmk.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class PersonController {
    private UserRepository userRepository;
    private BlogRepository blogRepository;
    private MentionRepository mentionRepository;
    @Autowired
    public void setUserRepository(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Autowired
    public void setBlogRepository(BlogRepository blogRepository){
        this.blogRepository= blogRepository;
    }
    @Autowired
    public void setMentionRepository(MentionRepository mentionRepository){
        this.mentionRepository= mentionRepository;
    }

    @GetMapping("/personalpage")
    public String getPersonlaPage(HttpServletRequest req,
                                  Model model,
                                  @RequestParam("personId") long personId,
                                  @RequestParam("start") int start,
                                  @RequestParam("size") int size){
        Long userId = null;
        model.addAttribute("result", "关注");
        if(req.getSession().getAttribute("userId")!=null){
            userId = (Long)req.getSession().getAttribute("userId");
            List<Long> Bs = mentionRepository.findBByA(userId);
            if(userId == personId){
                model.addAttribute("isMyself", true);
            }
            else if(Bs.contains(personId)){
                model.addAttribute("ifNoticed", true);
                model.addAttribute("result", "取消关注");
            }
        }
        start = start < 0 ? 0:start;
        Sort s = Sort.by(Sort.Direction.DESC, "postTime");
        Pageable pageable = PageRequest.of(start, size, s);
        Page<Blog> blogs = blogRepository.findBlogsByUserId(personId, pageable);
        User person = userRepository.findByUserId(personId).get(0);
        model.addAttribute("person", person);
        model.addAttribute("list", blogs);
        model.addAttribute("size", size);
        model.addAttribute("personId", personId);
        return "personalpage";
    }
    @PostMapping("/notice")
    @ResponseBody
    public String notice(HttpServletRequest req,
                         @RequestParam("personId") long personId){
        long a = (long)req.getSession().getAttribute("userId");
        List<Mention> mentions = mentionRepository.findByAAndB(a, personId);
        User A = userRepository.findByUserId(a).get(0);
        User B = userRepository.findByUserId(personId).get(0);
        if(mentions != null && mentions.size() > 0){
            mentionRepository.delete(mentions.get(0));
            A.setNoticeNum(A.getNoticeNum()-1);
            B.setFansNum(B.getFansNum()-1);
            userRepository.save(A);
            userRepository.save(B);
            return "关注," + B.getNoticeNum() + "," + B.getFansNum();
        }
        else{
            Mention mention = new Mention((long)req.getSession().getAttribute("userId"), personId);
            mentionRepository.save(mention);
            A.setNoticeNum(A.getNoticeNum()+1);
            B.setFansNum(B.getFansNum()+1);
            userRepository.save(A);
            userRepository.save(B);
            return "取消关注," + B.getNoticeNum() + "," + B.getFansNum();
        }
    }

    @GetMapping("/friends")
    public String getFriends(HttpServletRequest req,
                             Model model,
                             @RequestParam("userId") long userId,
                             @RequestParam("mention") int mention)
    {
        List<Long> usersId;
//        List<User> list;
        if(mention==1)
        {
            usersId = mentionRepository.findUsersByB(userId);
//            list = userRepository.findAllByUserId(usersId);
            model.addAttribute("result",  "用户no"+userId+ "的粉丝");
        }else
            {
            usersId = mentionRepository.findUsersByA(userId);
//            list = userRepository.findAllByUserId(usersId);
            model.addAttribute("result",  "用户no"+userId+ "的关注");
        }
        model.addAttribute("list", usersId);
        return "friendspage";
    }
}
