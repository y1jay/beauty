package com.yijun.beauty.model;

public class UserReq {
    private String name;
    private String nick_name;
    private String passwd;
    private String phone;

    public UserReq() {
    }


    public UserReq(String nick_name) {
        this.nick_name = nick_name;
    }


    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

}
