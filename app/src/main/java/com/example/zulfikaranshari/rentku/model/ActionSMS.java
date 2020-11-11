package com.example.zulfikaranshari.rentku.model;

/**
 * Created by OJI on 18/04/2018.
 */

public class ActionSMS {

    private String userkey;
    private String passkey;
    private String nohp;
    private String pesan;

    public String getUserkey() {
        return userkey;
    }

    public void setUserkey(String userkey) {
        this.userkey = userkey;
    }

    public String getPasskey() {
        return passkey;
    }

    public void setPasskey(String passkey) {
        this.passkey = passkey;
    }

    public String getNohp() {
        return nohp;
    }

    public void setNohp(String nohp) {
        this.nohp = nohp;
    }

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }
}