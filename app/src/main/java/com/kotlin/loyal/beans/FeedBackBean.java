package com.kotlin.loyal.beans;

/**
 * 反馈页面数据
 */
public class FeedBackBean {
    private String account;
    private String content;
    private String time;

    public FeedBackBean(String account, String content, String time) {
        this.account = account;
        this.content = content;
        this.time = time;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "{\"account\":\"" + account +
                "\",\"content\":\"" + content +
                "\",\"time\":\"" + time +
                "\"}";
    }
}