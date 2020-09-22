package com.yijun.beauty.model;

import java.io.Serializable;

public class Review implements Serializable {

    String nick_name;
    String review;
    Float rating;


    public Review(String review, String rating, String nick_name) {
    }

    public Review(String nick_name, String review, Float rating) {
        this.nick_name = nick_name;
        this.review = review;
        this.rating = rating;
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
}
