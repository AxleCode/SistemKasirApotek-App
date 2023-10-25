package com.obat.obatservice.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "master_obat")
public class Obat {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nama_obat;
    private Long id_kategori;
    private Integer stok;
    private Integer harga;
    private Long id_supplier;

    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private Date updateAt;

}
