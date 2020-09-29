package com.yijun.beauty.model;

public class ReservationReq {
    private String menu;
    private String price;
    private String nick_name;
    private int people_number;
    private  String time;

    public ReservationReq(String menu, String price, String nick_name) {
        this.menu = menu;
        this.price = price;
        this.nick_name = nick_name;
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

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
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
