package com.yijun.beauty.model;

import com.google.gson.annotations.SerializedName;

public class Rows {
    @SerializedName("success")
    private boolean success;

    @SerializedName("name")
    private String name;

    @SerializedName("nick_name")
    private String nick_name;

    @SerializedName("phone")
    private String phone;

    @SerializedName("created_at")
    private String created_at;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
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
