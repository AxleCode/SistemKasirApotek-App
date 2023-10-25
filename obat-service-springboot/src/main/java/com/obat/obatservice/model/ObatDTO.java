package com.obat.obatservice.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ObatDTO {

    private Long id;
    private String nama_obat;
    private Long id_kategori;
    private List<Object> obatDataKategori;
    private Integer stok;
    private Integer harga;
    private Long id_supplier;
    private List<Object> obatDataSupplier;
    private Date createAt;
    private Date updateAt;
}
