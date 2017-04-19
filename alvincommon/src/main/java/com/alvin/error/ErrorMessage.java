package com.alvin.error;

/**
 * Created by Administrator on 2017/4/19.

/**
 * Created by Administrator on 2017/4/19.
 */
public class ErrorMessage {
    private int code;
    private String message;


    public ErrorMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }

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
}
