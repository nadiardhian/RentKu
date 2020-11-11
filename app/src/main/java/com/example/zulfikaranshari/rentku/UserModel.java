package com.example.zulfikaranshari.rentku;

/**
 * Created by zulfikaranshari on 17/04/2018.
 */

public class UserModel {

    private String uid;
    private String username;
    private String email;
    private String hp;
    private String name;
    private String url;
    private String address;

    public UserModel(){}

    public UserModel(String uid, String username, String name, String email, String hp, String url, String address){
        this.uid = uid;
        this.username = username;
        this.email = email;
        this.hp = hp;
        this.name=name;
        this.url = url;
        this.address = address;

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHp() {
        return hp;
    }

    public void setHp(String hp) {
        this.hp = hp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
