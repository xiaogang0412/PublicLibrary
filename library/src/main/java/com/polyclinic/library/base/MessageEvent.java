package com.polyclinic.library.base;

/**
 * @author Lxg
 * @create 2020/3/6
 * @Describe
 */
public class MessageEvent {
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private int type;
    private String code;
    private String specId;
    private boolean isShowBadge;

    public boolean isShowBadge() {
        return isShowBadge;
    }

    public void setShowBadge(boolean showBadge) {
        isShowBadge = showBadge;
    }

    public String getSpecId() {
        return specId;
    }

    public void setSpecId(String specId) {
        this.specId = specId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    private String message;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
