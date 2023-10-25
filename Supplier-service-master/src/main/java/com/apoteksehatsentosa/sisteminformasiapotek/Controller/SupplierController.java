package com.apoteksehatsentosa.sisteminformasiapotek.Controller;


import com.apoteksehatsentosa.sisteminformasiapotek.Dto.SupplierDto;
import com.apoteksehatsentosa.sisteminformasiapotek.Model.Supplier;
import com.apoteksehatsentosa.sisteminformasiapotek.Repository.SupplierRepository;
import com.apoteksehatsentosa.sisteminformasiapotek.Service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/supplier")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    public SupplierController(SupplierService supplierService){
        this.supplierService = supplierService;
    }
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CASHIER')")
    public List<SupplierDto> geAllSupplier(){
        return supplierService.getallSuppliers();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CASHIER')")
    public ResponseEntity<SupplierDto> getSupplierById(@PathVariable Integer id) {
        SupplierDto supplierDTO = supplierService.getSupplierById(id);
        if (supplierDTO != null) {
            return ResponseEntity.ok(supplierDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<SupplierDto> addSupplier(@RequestBody SupplierDto supplierDTO) {
        SupplierDto addSupplier = supplierService.addSupplier(supplierDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(addSupplier);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteSupplier(@PathVariable Integer id) {
        supplierService.deleteSupplier(id);
        return ResponseEntity.ok("Data berhasil dihapus");
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> updateSupplier(@PathVariable Integer id, @RequestBody SupplierDto supplierDTO) {
        SupplierDto updateSupplier = supplierService.updateSupplier(id, supplierDTO);
        if (updateSupplier != null) {
            return ResponseEntity.ok("Data berhasil diupdate");
        }
        return ResponseEntity.notFound().build();
    }
}
