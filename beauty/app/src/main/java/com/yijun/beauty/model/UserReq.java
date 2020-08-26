package com.yijun.beauty.model;

public class UserReq {
    private String name;
    private String nick_name;
    private String passwd;
    private String phone;

    public UserReq() {
    }



    public UserReq(String name, String nick_name, String passwd, String phone) {
        this.name = name;
        this.nick_name = nick_name;
        this.passwd = passwd;
        this.phone = phone;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
