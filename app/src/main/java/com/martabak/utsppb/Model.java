package com.martabak.utsppb;

public class Model {
    private int kode,harga,jumlah,terjual,stok;
    private byte[]gambar;
    private String nama, satuan;

    //constructor

    public Model(int kode, String nama, String satuan,int harga,int jumlah, byte[] gambar,int terjual) {
        this.kode = kode;
        this.gambar = gambar;
        this.nama = nama;
        this.satuan = satuan;
        this.harga = harga;
        this.jumlah = jumlah;
        this.terjual = terjual;
        this.stok = stok;
    }

    public int getTerjual() {
        return terjual;
    }

    public void setTerjual(int terjual) {
        this.terjual = terjual;
    }

    public int getKode() {
        return kode;
    }

    public void setKode(int kode) {
        this.kode = kode;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public byte[] getGambar() {
        return gambar;
    }

    public void setGambar(byte[] gambar) {
        this.gambar = gambar;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }
}
