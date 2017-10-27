package com.example.wxy.watertest10.Bean;

/**
 * Created by WXY on 2017/10/20.
 */

public class UserBean {
    private String username = null;
    private String password = null;

    public UserBean(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {

        return username;
    }

    public String getPassword() {
        return password;
    }
}
