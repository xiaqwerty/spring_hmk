package cn.bupt.edu.spring_hmk.entity;

public class Person
{
    private String name;
    private String phoneNumber;
    private String email;
    private String address;
    private String QQ;
    public Person(String name,String phoneNumber,String email,String address,String QQ)
    {
        this.name=name;
        this.phoneNumber=phoneNumber;
        this.email=email;
        this.address=address;
        this.QQ=QQ;
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
}
