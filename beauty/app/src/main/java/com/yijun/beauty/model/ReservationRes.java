package com.yijun.beauty.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ReservationRes {
    @SerializedName("success")
    private boolean success;

    @SerializedName("menu")
    private ArrayList menu;

    @SerializedName("price")
    private ArrayList price;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList getMenu() {
        return menu;
    }

    public void setMenu(ArrayList menu) {
        this.menu = menu;
    }

    public ArrayList getPrice() {
        return price;
    }

    public void setPrice(ArrayList price) {
        this.price = price;
    }
}
