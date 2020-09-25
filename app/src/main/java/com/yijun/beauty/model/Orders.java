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
    private boolean take_out;

    @SerializedName("peple_number")
    @Expose
    private int peple_number;

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

    public boolean isTake_out() {
        return take_out;
    }

    public void setTake_out(boolean take_out) {
        this.take_out = take_out;
    }

    public int getPeple_number() {
        return peple_number;
    }

    public void setPeple_number(int peple_number) {
        this.peple_number = peple_number;
    }
}
