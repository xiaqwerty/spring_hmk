package cn.bupt.edu.spring_hmk.dao;

import cn.bupt.edu.spring_hmk.entity.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Long> {
    //List<Blog> findBlogsByBlogId(long blogId);//主键查询
    Blog findBlogsByBlogId(long blogId);
    //List<Blog> findBlogsByUserIdOrderByPostTime(long userId);//个人主页显示
    Page<Blog> findBlogsByBlogId(long blogId, Pageable pageable);
    Page<Blog> findBlogsByUserId(long userId, Pageable pageable);
}
