package com.yijun.beauty.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rows {
    @SerializedName("id")
    @Expose
    private Integer Id;
    @SerializedName("nick_name")
    @Expose
    private String nick_name;
    @SerializedName("review")
    @Expose
    private String review;
    @SerializedName("created_at")
    @Expose
    private String createdAt;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}