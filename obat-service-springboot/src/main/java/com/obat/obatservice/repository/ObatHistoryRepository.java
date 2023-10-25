package com.obat.obatservice.repository;

import com.obat.obatservice.model.ObatHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ObatHistoryRepository extends JpaRepository<ObatHistory, Long> {
}