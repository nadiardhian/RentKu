package com.example.zulfikaranshari.rentku;

/**
 * Created by OJI on 09/04/2018.
 */

public class pencarianModel {

    public String gambar,harga,merk,no_polisi,status, nama_pemilik,kota, pertanyaan, jawaban;
    public pencarianModel(){

    }

    public pencarianModel(String gambar,  String harga, String merk, String no_polisi, String status, String namaPemilik, String kota, String pertanyaan, String jawaban){
        this.gambar = gambar;
        this.harga = harga;
        this.merk = merk;
        this.no_polisi = no_polisi;
        this.status = status;
        this.nama_pemilik = namaPemilik;
        this.kota = kota;
        this.pertanyaan = pertanyaan;
        this.jawaban = jawaban;

    }

    public String getGambar() {
        return gambar;
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

    public String getKota() {
        return kota;
    }
}
