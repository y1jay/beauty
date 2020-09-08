package com.yijun.beauty.model;

public class UserReq {
    private String email;
    private String nick_name;


    public UserReq() {
    }


    public UserReq(String email, String nick_name) {
        this.email = email;
        this.nick_name = nick_name;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

}
