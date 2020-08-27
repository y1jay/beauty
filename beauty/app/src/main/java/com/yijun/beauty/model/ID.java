package com.yijun.beauty.model;

import com.google.gson.annotations.SerializedName;

public class ID {
        @SerializedName("success")
        private boolean success;

        @SerializedName("ID")
        private String ID;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

}
