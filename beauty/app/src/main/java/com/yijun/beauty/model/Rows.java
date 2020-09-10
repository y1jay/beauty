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

    @SerializedName("rating")
    @Expose
    private Float rating;

    @SerializedName("created_at")
    @Expose
    private String created_at;

    public Rows(String nick_name, String review, Float rating) {
        this.nick_name = nick_name;
        this.review = review;
        this.rating = rating;
    }

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