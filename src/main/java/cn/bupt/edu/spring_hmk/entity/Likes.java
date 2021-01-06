package cn.bupt.edu.spring_hmk.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Likes
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long likeId;//查询主键

    private long blogId;//归属的文章id
    private long commitId;//归属的回复id，不是评论/回复的点赞则为-1
    private long userId;//点赞用户的id
  //  private Date time;//发表的时间

    public long getLikeId() {
        return likeId;
    }

    //public void setLikeId(long likeId) {
   //     this.likeId = likeId;
   // }

    public long getBlogId() {
        return blogId;
    }

    public void setBlogId(long blogId) {
        this.blogId = blogId;
    }

    public long getCommitId() {
        return commitId;
    }

    public void setCommitId(long commitId) {
        this.commitId = commitId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

   // public Date getTime() {
   //     return time;
   // }

    //public void setTime(Date time) {
    //    this.time = time;
   // }
}
