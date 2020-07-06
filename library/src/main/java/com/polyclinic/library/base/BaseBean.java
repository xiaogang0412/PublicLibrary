package com.polyclinic.library.base;

import java.io.Serializable;

/**
 * @author Lxg
 * @create 2020/3/3
 * @Describe
 */
public class BaseBean implements Serializable {
    private String code;
    private String message;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
