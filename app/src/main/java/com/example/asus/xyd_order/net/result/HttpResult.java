package com.example.asus.xyd_order.net.result;

/**
 * Created by Zheng on 2017/3/16.
 */
public class HttpResult<T> {
    private int code;
    private String message;
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setCode(int code) {
        this.code = code;
    }


    public void setMessage(String message) {
        this.message = message;
    }


    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
