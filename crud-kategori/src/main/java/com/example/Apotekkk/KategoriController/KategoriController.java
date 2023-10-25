package com.example.Apotekkk.KategoriController;


import com.example.Apotekkk.KategoriDTO.KategoriDTO;
import com.example.Apotekkk.KategoriServices.KategoriServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/kategori")
public class KategoriController {
    @Autowired
    private KategoriServices kategoriService;

    public KategoriController(KategoriServices kategoriService) {
        this.kategoriService = kategoriService;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CASHIER')")
    public List<KategoriDTO> geAllKategori() {
        return kategoriService.getAllKategoris();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CASHIER')")
    public ResponseEntity<KategoriDTO> getKategoriById(@PathVariable Integer id) {
        KategoriDTO kategoriDTO = kategoriService.getKategoriById(id);
        if (kategoriDTO != null) {
            return ResponseEntity.ok(kategoriDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<KategoriDTO> addKategori(@RequestBody KategoriDTO kategoriDTO) {
        KategoriDTO addedKategori = kategoriService.addKategori(kategoriDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedKategori);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteKategori(@PathVariable Integer id) {
        KategoriDTO existingKategori = kategoriService.getKategoriById(id);
        if (existingKategori != null) {
            kategoriService.deleteKategori(id);
            return ResponseEntity.ok("Data berhasil dimusnahkan");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> updateKategori(@PathVariable Integer id, @RequestBody KategoriDTO kategoriDTO) {
        KategoriDTO updatedKategori = kategoriService.updateKategori(id, kategoriDTO);
        if (updatedKategori != null) {
            return ResponseEntity.ok("updatedKategori");
        }
        return ResponseEntity.notFound().build();
    }
}



