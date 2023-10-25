package com.obat.obatservice.repository;

import com.obat.obatservice.model.Obat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ObatRepository extends JpaRepository<Obat, Long> {

}