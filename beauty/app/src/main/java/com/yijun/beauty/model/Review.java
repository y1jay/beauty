package com.yijun.beauty.model;

import java.io.Serializable;

public class Review implements Serializable {

    int id;
    String nick_name;
    String review;
    Float rating;
    String created_at;

    public Review() {
    }

    public Review(int id, String nick_name, String review, Float rating, String created_at) {
        this.id = id;
        this.nick_name = nick_name;
        this.review = review;
        this.rating = rating;
        this.created_at = created_at;
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
