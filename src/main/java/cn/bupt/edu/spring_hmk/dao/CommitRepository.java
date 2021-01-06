package cn.bupt.edu.spring_hmk.dao;

import cn.bupt.edu.spring_hmk.entity.Commit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommitRepository extends JpaRepository<Commit, Long> {
    Commit findCommitsByCommitId(long commitId);//主键查询
    List<Commit> findCommitsByBlogIdOrderByTime(long blogId);//通过文章查询所有评论按时间排序
    List<Commit> findCommitsByBlogIdOrderByLikesNum(long blogId);//通过文章查询所有评论按照点赞数查询
}
