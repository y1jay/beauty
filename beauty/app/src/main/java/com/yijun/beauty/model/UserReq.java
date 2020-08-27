package com.yijun.beauty.model;

public class UserReq {
    private String name;
    private String nick_name;
    private String passwd;
    private String phone;
    private String created_at;

    public UserReq() {
    }

    public UserReq(String nick_name, String name, String phone, String created_at){
        this.name = name;
        this.nick_name = nick_name;
        this.phone = phone;
        this.created_at = created_at;
    }

    public UserReq(String nick_name, String passwd) {
        this.nick_name = nick_name;
        this.passwd = passwd;
    }

    public UserReq(String nick_name) {
        this.nick_name = nick_name;
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

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
