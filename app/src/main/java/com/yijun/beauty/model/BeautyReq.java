package com.yijun.beauty.model;

public class BeautyReq {
    private String nick_name;
    private String phone_number;
    private Boolean info_agree;

    public BeautyReq() {
    }

    public BeautyReq(String nick_name, String phone_number, Boolean info_agree) {
        this.nick_name = nick_name;
        this.phone_number = phone_number;
        this.info_agree = info_agree;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public Boolean getInfo_agree() {
        return info_agree;
    }

    public void setInfo_agree(Boolean info_agree) {
        this.info_agree = info_agree;
    }
}
