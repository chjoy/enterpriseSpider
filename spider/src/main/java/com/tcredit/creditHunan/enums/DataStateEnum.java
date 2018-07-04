package com.tcredit.creditHunan.enums;

/**
 * Created by yp-tc-m-7179 on 2018/7/4.
 */
public enum  DataStateEnum {
    ACTIVE(1,"有效"),
    UNACTIVE(0,"无效");
    private Integer value;
    private String msg;

    private DataStateEnum(Integer value,String msg){
        this.value= value;
        this.msg = msg;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
