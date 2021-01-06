package cn.bupt.edu.spring_hmk.controller;


import cn.bupt.edu.spring_hmk.dao.*;
import cn.bupt.edu.spring_hmk.entity.Blog;
import cn.bupt.edu.spring_hmk.entity.Commit;
import cn.bupt.edu.spring_hmk.entity.Likes;
import cn.bupt.edu.spring_hmk.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.h2.H2ConsoleAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
public class FrontPageController {
    @Value("${user.file.path}")
    private String filePath;
    private UserRepository userRepository;
    private BlogRepository blogRepository;
    private CommitRepository commitRepository;
    private LikesRepository likesRepository;
    private MentionRepository mentionRepository;
    @Autowired
    public void setUserRepository(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Autowired
    public void setBlogRepository(BlogRepository blogRepository){
        this.blogRepository = blogRepository;
    }
    @Autowired
    public void setCommitRepository(CommitRepository commitRepository){
        this.commitRepository = commitRepository;
    }
    @Autowired
    public void setLikeRepository(LikesRepository likesRepository){
        this.likesRepository = likesRepository;
    }
    @Autowired
    public void setMentionRepository(MentionRepository mentionRepository){ this.mentionRepository = mentionRepository; }

    @GetMapping("/frontpage")
    public String getFrontPage(HttpServletRequest req,
                               Model model,
                               @RequestParam("sort") String sort,
                               @RequestParam("start") int start,
                               @RequestParam("size") int size){
        start = start < 0 ? 0:start;
        Sort s = Sort.by(Sort.Direction.DESC, "postTime");
        Pageable pageable = PageRequest.of(start, size, s);
        Page<Blog> blogs = blogRepository.findAll(pageable);
        List<Commit> commit=commitRepository.findAll();
        model.addAttribute("sort", "time");
        model.addAttribute("size", size);
        if(sort != null && sort.equals("like")){
            s = Sort.by(Sort.Direction.DESC, "likesNum");
            pageable = PageRequest.of(start, size, s);
            blogs = blogRepository.findAll(pageable);
            model.addAttribute("sort", "like");
        }
        if(sort != null && sort.equals("commit")){
            s = Sort.by(Sort.Direction.DESC, "commitNum");
            pageable = PageRequest.of(start, size, s);
            blogs = blogRepository.findAll(pageable);
            model.addAttribute("sort", "commit");
        }
        if(req.getSession().getAttribute("userId")!=null){
            model.addAttribute("iflog", req.getSession().getAttribute("userId"));
        }
        model.addAttribute("list", blogs);
        model.addAttribute("result", sort);
        model.addAttribute("commitList",commit);
        return "frontpage";
    }

    @GetMapping("/postblog")
    public String getblog(HttpServletRequest req,
                          Model model){
        return "postblog";
    }
    @PostMapping("/postblog")
    public String postblog(HttpServletRequest req,
                           Model model,
                           @RequestParam("title") String title,
                           @RequestParam("content") String content,
                           @RequestParam("pic1") MultipartFile pic1,
                           @RequestParam("pic2") MultipartFile pic2,
                           @RequestParam("pic3") MultipartFile pic3,
                           @RequestParam("pic4") MultipartFile pic4,
                           @RequestParam("pic5") MultipartFile pic5,
                           @RequestParam("pic6") MultipartFile pic6){
        if(content.equals("") || content.length()<20){
            model.addAttribute("result", "文章内容太短");
            return "postblog";
        }
        if(title.equals("")){
            title = content.substring(0, 10);
        }
        try{
            String fileName = pic1.getOriginalFilename();
            File file = new File(filePath + fileName);
            pic1.transferTo(file);
            fileName = pic2.getOriginalFilename();
            file = new File(filePath + fileName);
            pic2.transferTo(file);
            fileName = pic3.getOriginalFilename();
            file = new File(filePath + fileName);
            pic3.transferTo(file);
            fileName = pic4.getOriginalFilename();
            file = new File(filePath + fileName);
            pic4.transferTo(file);
            fileName = pic5.getOriginalFilename();
            file = new File(filePath + fileName);
            pic5.transferTo(file);
            fileName = pic6.getOriginalFilename();
            file = new File(filePath + fileName);
            pic6.transferTo(file);
        }catch (IOException e){
            e.printStackTrace();
        }
        long userId = (long)req.getSession().getAttribute("userId");
        Blog blog = new Blog(userId, title, content);
        blog.setPic1(pic1.getOriginalFilename());
        blog.setPic2(pic2.getOriginalFilename());
        blog.setPic3(pic3.getOriginalFilename());
        blog.setPic4(pic4.getOriginalFilename());
        blog.setPic5(pic5.getOriginalFilename());
        blog.setPic6(pic6.getOriginalFilename());

        blogRepository.save(blog);
        User user = userRepository.findByUserId(userId).get(0);
        user.setBlogNum(user.getBlogNum()+1);
        userRepository.save(user);
        return "redirect:frontpage?sort=time&start=0&size=2";
    }
    @GetMapping("/postCommit")
    public String personalPage(Model model,
                               @RequestParam Long blogId,
                               @RequestParam(required = false)Long replyId)
    {
        model.addAttribute("blogId",blogId);
        if(replyId!=null)
            model.addAttribute("replyId",replyId);
        else model.addAttribute("replyId",-1);
        return "postcommit";
    }
    @PostMapping("/postCommit")
    public String postCommit(HttpServletRequest req,
                             Model model,
                             @RequestParam Long blogId,
                             @RequestParam(required = false)Long replyId,
                             @RequestParam String content)
    {
        if(content.equals(""))
        {
            model.addAttribute("result","评论不能为空！");
            return "postcommit";
        }
        Commit commit=new Commit();
        commit.setBlogId(blogId);
        commit.setUserId((Long) req.getSession().getAttribute("userId"));
        commit.setContent(content);
        commit.setReplyId(replyId==null?-1:replyId);
        commit.setTime(new Date());
        Blog blog=blogRepository.findBlogsByBlogId(blogId);
        blog.addCommitNum();
        blogRepository.saveAndFlush(blog);
        commitRepository.save(commit);
        return "redirect:blog?blogId="+blogId;
    }
    @GetMapping("/likeBlog")
    @ResponseBody
    public String likeBlog(HttpServletRequest req,
                           @RequestParam Long blogId,
                           @RequestParam Boolean ifInit)
    {
        Blog blog = blogRepository.findBlogsByBlogId(blogId);
        Likes likes = new Likes();
        if(ifInit)
        return Long.toString(blog.getLikesNum());
        else if (req.getSession().getAttribute("userId") == null)//应该不会出现这种情况，会被interceptor拦截
            return Long.toString(-100);
        else
        {
            Likes findLikes = likesRepository.findByBlogIdAndUserIdAndCommitId(blogId, (Long) req.getSession().getAttribute("userId"),-1);
            /*if (findLikes != null&&!findLikes.isEmpty())
                for(Iterator<Likes> iterator = findLikes.iterator(); iterator.hasNext();)
                //取消点赞
                {
                    Likes findThis= iterator.next();
                    if(findThis.getCommitId()==-1)
                    {
                        blog.subLikesNum();
                        likesRepository.delete(findThis);

                        break;
                    }
                }
            else
            {
                blog.addLikesNum();
                likes.setUserId((Long)req.getSession().getAttribute("userId"));
                likes.setBlogId(blogId);
                likes.setCommitId(-1);
                likesRepository.saveAndFlush(likes);
            }*/
            if (findLikes != null)
            {
                blog.subLikesNum();
                likesRepository.deleteById(findLikes.getLikeId());
            }
            else
            {
                blog.addLikesNum();
                likes.setUserId((Long)req.getSession().getAttribute("userId"));
                likes.setBlogId(blogId);
                likes.setCommitId(-1);
                likesRepository.saveAndFlush(likes);
            }

            blogRepository.saveAndFlush(blog);
            return Long.toString(blog.getLikesNum());
        }
    }
    @GetMapping("/likeCommit")
    @ResponseBody
    public String likeCommit(HttpServletRequest req,
                           @RequestParam Long commitId,
                             @RequestParam Boolean ifInit)
    {
        Blog blog = new Blog();
        Commit commit=commitRepository.findCommitsByCommitId(commitId);
        Likes likes = new Likes();
        if(ifInit)
            return Long.toString(commit.getLikesNum());
        else if (req.getSession().getAttribute("userId") == null)//应该不会出现这种情况，会被interceptor拦截
            return Long.toString(-100);
        else
        {
            Likes findLikes = likesRepository.findByUserIdAndCommitId((Long) req.getSession().getAttribute("userId"),commitId);
            if (findLikes != null)
            {
                commit.subLikesNum();
                likesRepository.deleteById(findLikes.getLikeId());
            }
            else
            {
                commit.addLikesNum();
                likes.setUserId((Long) req.getSession().getAttribute("userId"));
                likes.setBlogId(commit.getBlogId());
                likes.setCommitId(commitId);
                likesRepository.saveAndFlush(likes);
            }

            commitRepository.saveAndFlush(commit);
            return Long.toString(commit.getLikesNum());
        }
    }
    @RequestMapping("/blog")
    public String viewBlog(Model model, @RequestParam Long blogId, HttpServletRequest req)
    {
        Blog blog=blogRepository.findBlogsByBlogId(blogId);
        User user = new User("", "", "", "");
        if(req.getSession().getAttribute("userId")!=null)
        {
            user = userRepository.findByUserId((Long)req.getSession().getAttribute("userId")).get(0);
        }
        List<Commit> commits=commitRepository.findCommitsByBlogIdOrderByLikesNum(blogId);
        Iterator<Commit> iterator=commits.iterator();
        Map<Long,Integer> replyMap = new HashMap<>();
        while(iterator.hasNext())
        {
            Commit replyCommit=iterator.next();
            if(replyCommit.getReplyId()!=-1)
            {
                Commit repliedCommit=commitRepository.findCommitsByCommitId(replyCommit.getReplyId());
                replyMap.put(replyCommit.getReplyId(), commits.indexOf(repliedCommit));
            }
        }
        model.addAttribute("commits",commits);
        model.addAttribute("blog",blog);
        model.addAttribute("user",user);
        model.addAttribute("replyMap",replyMap);
        return "blog";
    }

}
