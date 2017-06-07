package com.dalpeng.finalproject;

/**
 * Created by Park on 2017-06-05.
 */

class Msg {
    private String user;
    private String msg;


    public Msg(String user, String msg) {
        this.user = user;
        this.msg = msg;
    }

    public Msg(){

    }

    public String getUser() {
        return user;
    }


    public String getMsg() {
        return msg;
    }


}
