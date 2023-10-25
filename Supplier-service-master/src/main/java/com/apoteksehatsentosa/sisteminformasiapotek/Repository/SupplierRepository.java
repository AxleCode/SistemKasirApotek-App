package com.apoteksehatsentosa.sisteminformasiapotek.Repository;

import com.apoteksehatsentosa.sisteminformasiapotek.Model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Integer> {

}
