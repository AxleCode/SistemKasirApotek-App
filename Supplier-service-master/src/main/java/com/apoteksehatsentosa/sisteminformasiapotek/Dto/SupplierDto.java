package com.apoteksehatsentosa.sisteminformasiapotek.Dto;

public class SupplierDto {

    private Integer id;
    private String nama_supplier;
    private String alamat;

    public Integer getId() {
        return id;
    }

    public SupplierDto(){

    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getNama_supplier() {
        return nama_supplier;
    }

    public void setNama_supplier(String nama_supplier) {
        this.nama_supplier = nama_supplier;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public Integer getNo_telepon() {
        return no_telepon;
    }

    public void setNo_telepon(Integer no_telepon) {
        this.no_telepon = no_telepon;
    }

    private Integer no_telepon;
}
