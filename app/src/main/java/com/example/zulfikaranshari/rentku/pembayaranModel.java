package com.example.zulfikaranshari.rentku;

/**
 * Created by OJI on 11/04/2018.
 */

public class pembayaranModel {
    public String user,harga,merk,no_polisi,status, nama_pemilik,tanggalPinjam,tanggalKembali,key;
    public pembayaranModel(){

    }

    public pembayaranModel(String user,  String harga, String merk, String no_polisi, String status, String namaPemilik,String tanggalKembali, String tanggalPinjam, String key){
        this.user = user;
        this.harga = harga;
        this.merk = merk;
        this.no_polisi = no_polisi;
        this.status = status;
        this.nama_pemilik = namaPemilik;
        this.tanggalKembali = tanggalKembali;
        this.tanggalPinjam = tanggalPinjam;
        this.key = key;

    }

    public String getUser() {
        return user;
    }

    public String getHarga() {
        return harga;
    }

    public String getMerk() {
        return merk;
    }

    public String getNo_polisi() {
        return no_polisi;
    }

    public String getStatus() {
        return status;
    }

    public String getNama_pemilik() {
        return nama_pemilik;
    }

    public String getTanggalPinjam() {
        return tanggalPinjam;
    }

    public String getTanggalKembali() {
        return tanggalKembali;
    }

    public String getKey() {
        return key;
    }
}
