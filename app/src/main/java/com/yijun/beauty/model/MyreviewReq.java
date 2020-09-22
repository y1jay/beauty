package com.yijun.beauty.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyreviewReq {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("nick_name")
    @Expose
    private String nick_name;

    @SerializedName("review")
    @Expose
    private String review;

    @SerializedName("rating")
    @Expose
    private Float rating;

    @SerializedName("created_at")
    @Expose
    private String created_at;

    public MyreviewReq(String review, Float rating) {
        this.review = review;
        this.rating = rating;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

}
