package cn.bupt.edu.spring_hmk.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Mention {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long mentionId;//查询主键
    private long A;//关注人
    private long B;//被关注人

    public Mention(){}
    public Mention(long A, long B){
        this.A = A;
        this.B = B;
    }

    public long getMentionId() {
        return mentionId;
    }

    public void setMentionId(long mentionId) {
        this.mentionId = mentionId;
    }

    public long getA() {
        return A;
    }

    public void setA(long a) {
        A = a;
    }

    public long getB() {
        return B;
    }

    public void setB(long b) {
        B = b;
    }
}

