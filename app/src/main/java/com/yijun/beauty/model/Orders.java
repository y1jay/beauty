package com.yijun.beauty.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Orders {
    @SerializedName("menu")
    @Expose
    private String menu;

    @SerializedName("price")
    @Expose
    private String price;

    @SerializedName("created_at")
    @Expose
    private String created_at;

    @SerializedName("time")
    @Expose
    private String time;

    @SerializedName("take_out")
    @Expose
    private int take_out;

    @SerializedName("people_number")
    @Expose
    private int people_number;

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int isTake_out() {
        return take_out;
    }

    public void setTake_out(int take_out) {
        this.take_out = take_out;
    }

    public int getPeople_number() {
        return people_number;
    }

    public void setPeople_number(int peple_number) {
        this.people_number = people_number;
    }
}
