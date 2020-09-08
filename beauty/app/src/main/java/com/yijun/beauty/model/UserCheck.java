package com.yijun.beauty.model;

import com.google.gson.annotations.SerializedName;

public class UserCheck {

    @SerializedName("success")
    private boolean success;

    @SerializedName("nick_name")
    private String nick_name;

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }


}
