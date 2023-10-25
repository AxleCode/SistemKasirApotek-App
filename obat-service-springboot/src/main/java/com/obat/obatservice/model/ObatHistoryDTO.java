package com.obat.obatservice.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ObatHistoryDTO {
    private Long id;
    private Long id_obat;
    private String nama_obat;
    private Long id_kategori;
    private List<Object> obatDataKategori;
    private Integer stok;
    private Integer harga;
    private Long id_supplier;
    private List<Object> obatDataSupplier;
    private Date createAt;
}
