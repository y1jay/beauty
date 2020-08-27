package com.yijun.beauty.model;

import com.google.gson.annotations.SerializedName;

public class UserCheck {

    @SerializedName("success")
    private boolean success;



    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }


}
