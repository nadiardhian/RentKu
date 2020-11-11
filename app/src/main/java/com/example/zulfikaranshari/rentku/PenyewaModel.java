package com.example.zulfikaranshari.rentku;

/**
 * Created by virra PC on 5/1/2018.
 */

public class PenyewaModel {
    private String nama;
    private String nomor;
    private String alamat;
    private String facebook;
    private String line;
    //key to get data

    public PenyewaModel(){}

    public PenyewaModel(String nama, String nomor, String alamat, String facebook, String line){
        this.nama = nama;
        this.nomor = nomor;
        this.alamat = alamat;
        this.facebook = facebook;
        this.line=line;

    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNomor() {
        return nomor;
    }

    public void setNomor(String nomor) {
        this.nomor = nomor;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }
}
