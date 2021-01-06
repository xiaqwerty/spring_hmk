package cn.bupt.edu.spring_hmk.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userId;//查询主键

//    @Column(nullable = false)
    private String name;//用户名

//    @Column(nullable = false)
    private String password;//密码

//    @Column(unique = true, nullable = false)
    private String email;//邮箱

    private String briefIntro;//简介
    private String photo;//头像
    private int blogNum;//博客数
    private int fansNum;//粉丝数
    private int noticeNum;//关注数量
    private int errorNum;//错误的次数
    private Date errorTime;//错误的时间

    public User(){}

    public User(String email, String name, String password,
                String briefIntro){
        this.email = email;
        this.name = name;
        this.password = password;
        this.briefIntro = briefIntro;
        this.photo = "";
        this.fansNum = 0;
        this.noticeNum = 0;
        this.blogNum = 0;
        this.errorNum = 0;
        this.errorTime = new Date();
    }

    public Date getErrorTime() {
        return errorTime;
    }

    public void setErrorTime(Date errorTime) {
        this.errorTime = errorTime;
    }

    public int getErrorNum() {
        return errorNum;
    }

    public void setErrorNum(int errorNum) {
        this.errorNum = errorNum;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNoticeNum() {
        return noticeNum;
    }

    public void setNoticeNum(int noticeNum) {
        this.noticeNum = noticeNum;
    }

    public int getFansNum() {
        return fansNum;
    }

    public void setFansNum(int fansNum) {
        this.fansNum = fansNum;
    }

    public int getBlogNum() {
        return blogNum;
    }

    public void setBlogNum(int blogNum) {
        this.blogNum = blogNum;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getBriefIntro() {
        return briefIntro;
    }

    public void setBriefIntro(String briefIntro) {
        this.briefIntro = briefIntro;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
