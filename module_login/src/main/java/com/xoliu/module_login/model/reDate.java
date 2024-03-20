package com.xoliu.module_login.model;

import com.google.gson.annotations.SerializedName;

public class reDate {
    @SerializedName("Code")
    int code;
    @SerializedName("Message")
    String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "reDate{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
