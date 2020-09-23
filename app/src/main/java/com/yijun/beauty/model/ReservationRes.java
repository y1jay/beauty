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

    @SerializedName("take_out")
    private Boolean take_out;

    @SerializedName("people_number")
    private int people_number;

    @SerializedName("time")
    private String time;

    @SerializedName("total")
    private String total;

    @SerializedName("rows")
    private ArrayList<Orders> rows;

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

    public Boolean getTake_out() {
        return take_out;
    }

    public void setTake_out(Boolean take_out) {
        this.take_out = take_out;
    }

    public int getPeople_number() {
        return people_number;
    }

    public void setPeople_number(int people_number) {
        this.people_number = people_number;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
