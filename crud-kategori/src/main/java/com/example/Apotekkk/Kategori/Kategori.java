package com.example.Apotekkk.Kategori;

import jakarta.persistence.*;

@Entity
@Table(name = "kategori")
public class Kategori {
    public Integer getId() {
        return id;
    }

    public void setId_kat(Integer id_kat) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    public String getNama_kategori() {
        return nama_kategori;
    }

    public void setNama_kategori(String nama_kategori) {
        this.nama_kategori = nama_kategori;
    }

    private String nama_kategori;

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    private String deskripsi;

    public Kategori(){

    }
}
