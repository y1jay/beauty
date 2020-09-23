package com.yijun.beauty.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ReservationRes {
    @SerializedName("success")
    private boolean success;

    @SerializedName("menu")
    private String menu;

    @SerializedName("price")
    private String price;

    @SerializedName("total")
    private String total;

    @SerializedName("rows")
    private ArrayList<Orders> rows;

    @SerializedName("menus")
    private ArrayList<Orders> menus;




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

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public ArrayList<Orders> getRows() {
        return rows;
    }

    public void setRows(ArrayList<Orders> rows) {
        this.rows = rows;
    }

    public ArrayList<Orders> getMenus() {
        return menus;
    }

    public void setMenus(ArrayList<Orders> menus) {
        this.rows = menus;
    }

}
