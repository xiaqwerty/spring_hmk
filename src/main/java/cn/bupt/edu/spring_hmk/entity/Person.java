package cn.bupt.edu.spring_hmk.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Person
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name="这是个初始值";
    private String phoneNumber="这是个初始值";
    private String email="这是个初始值";
    private String address="这是个初始值";
    private String QQ="这是个初始值";
    public Person(String name,String phoneNumber,String email,String address,String QQ)
    {
        this.name=name;
        this.phoneNumber=phoneNumber;
        this.email=email;
        this.address=address;
        this.QQ=QQ;
    }

    public Person()
    {
    }

    //为什么？？？
    //为什么在调用getId()的时候会报错id为null
    //但是加上if（id==null）return -1之后，反而返回正常id了？？？
    //不返回-1吗？？？那我写他只是为了抹除本就不存在的错误情况？？？
    public int getId()
    {
        if(id==null)
            return -1;
        else
            return (int)id;
    }

    public String getName()
    {
        return name;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public String getEmail()
    {
        return email;
    }

    public String getAddress()
    {
        return address;
    }

    public String getQQ()
    {
        return QQ;
    }
    public void setPerson(String name,String phoneNumber,String email,String address,String QQ)
    {
        this.name=name;
        this.phoneNumber=phoneNumber;
        this.email=email;
        this.address=address;
        this.QQ=QQ;
    }

    public void setId(Integer id)
    {
        this.id=id;
    }
}
