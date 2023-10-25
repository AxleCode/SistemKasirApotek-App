package com.apotek.transaction.model;

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
    private Integer id_kategori;
    private List<Object> obatDataKategori;
    private Integer stok;
    private Integer harga;
    private Integer id_supplier;
    private List<Object> obatDataSupplier;
    private Date createAt;
}
