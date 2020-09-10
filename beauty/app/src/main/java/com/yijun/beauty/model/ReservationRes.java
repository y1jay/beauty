package com.yijun.beauty.model;

import com.google.gson.annotations.SerializedName;

public class ReservationRes {
    @SerializedName("success")
    private boolean success;

    @SerializedName("menu")
    private String menu;

    @SerializedName("price")
    private String price;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

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
}
