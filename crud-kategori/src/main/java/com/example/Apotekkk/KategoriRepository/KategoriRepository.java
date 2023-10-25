package com.example.Apotekkk.KategoriRepository;

import com.example.Apotekkk.Kategori.Kategori;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KategoriRepository extends JpaRepository<Kategori, Integer> {}

