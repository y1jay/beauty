package com.yijun.beauty.model;

import com.google.gson.annotations.SerializedName;

public class UserCheck {

    @SerializedName("success")
    private boolean success;

    @SerializedName("nick_name")
    private String nick_name;

    @SerializedName("email")
    private String email;

    @SerializedName("created_at")
    private String created_at;

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

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
