package cn.bupt.edu.spring_hmk.dao;

import cn.bupt.edu.spring_hmk.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    List<Likes> findByBlogIdAndUserId(long blogId, long userId);
    Likes findByUserIdAndCommitId(long userId, long commitId);
    @Query(value = "select l from Likes l where l.blogId=?1 and l.userId=?2 and l.commitId=?3")
    Likes findByBlogIdAndUserIdAndCommitId(long blogId,long userId,long commitId);
    //Likes deleteByUserIdAndCommitId(long userId,long commitId);
}
