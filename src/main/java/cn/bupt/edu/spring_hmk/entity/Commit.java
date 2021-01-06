package cn.bupt.edu.spring_hmk.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Commit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long commitId;//查询主键
    private long userId;//发表评论的用户id
    private long blogId;//具体归属的文章id
    private long replyId;//具体回复的id，如果没有回复则为-1
    private int likesNum=0;
    private Date time;//发布时间
    private String content;//内容

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

    public long getBlogId() {
        return blogId;
    }

    public void setBlogId(long blogId) {
        this.blogId = blogId;
    }

    public long getReplyId() {
        return replyId;
    }

    public void setReplyId(long replyId) {
        this.replyId = replyId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLikesNum() {
        return likesNum;
    }
    public void addLikesNum(){this.likesNum++;}
    public void subLikesNum(){this.likesNum--;}
    public void setLikesNum(int likeNum) {
        this.likesNum = likeNum;
    }
}

