package com.kotlin.loyal.beans;

public class ResultBean {
    private int resultCode;
    private String resultMsg;
    private String exceptMsg;

    public ResultBean(int resultCode, String resultMsg, String exceptMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
        this.exceptMsg = exceptMsg;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public String getExceptMsg() {
        return exceptMsg;
    }

    public void setExceptMsg(String exceptMsg) {
        this.exceptMsg = exceptMsg;
    }

    public ResultBean() {
    }

    @Override
    public String toString() {
        return "{\"resultCode\":" + resultCode +
                ",\"resultMsg\":" + (resultMsg == null ? null : "\"" + resultMsg + "\"") +
                ",\"exceptMsg\":" + (exceptMsg == null ? null : "\"" + exceptMsg + "\"") +
                "}";
    }
}