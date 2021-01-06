package cn.bupt.edu.spring_hmk.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
import java.util.Random;

@Entity
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long blogId;//查询主键
    private long userId;//发帖人id
    private Date postTime;//发帖时间
    private String title;
    private String content;//内容
    private int likesNum;
    private int commitNum;
    private String pic1, pic2, pic3, pic4, pic5, pic6;//图片

    public Blog(){}
    public Blog(long userid, String title, String content){
        this.userId = userid;
        this.title = title;
        this.content = content;
        this.postTime = new Date();
        this.commitNum = 0;
        this.likesNum = 0;
    }
    public long getBlogId() {
        return blogId;
    }

    public void setBlogId(long blogId) {
        this.blogId = blogId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getPic6() {
        return pic6;
    }

    public void setPic6(String pic6) {
        this.pic6 = pic6;
    }

    public String getPic5() {
        return pic5;
    }

    public void setPic5(String pic5) {
        this.pic5 = pic5;
    }

    public String getPic4() {
        return pic4;
    }

    public void setPic4(String pic4) {
        this.pic4 = pic4;
    }

    public String getPic3() {
        return pic3;
    }

    public void setPic3(String pic3) {
        this.pic3 = pic3;
    }

    public String getPic2() {
        return pic2;
    }

    public void setPic2(String pic2) {
        this.pic2 = pic2;
    }

    public String getPic1() {
        return pic1;
    }

    public void setPic1(String pic1) {
        this.pic1 = pic1;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    public int getLikesNum() {
        return likesNum;
    }

    public void setLikesNum(int likesNum) {
        this.likesNum = likesNum;
    }
    public void addLikesNum(){this.likesNum++;}
    public void subLikesNum(){this.likesNum--;}

    public int getCommitNum() {
        return commitNum;
    }
    public void addCommitNum(){this.commitNum++;}
    public void subCommitNum(){this.commitNum--;}
    public void setCommitNum(int commitNum) {
        this.commitNum = commitNum;
    }
}
