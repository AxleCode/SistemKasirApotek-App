package com.example.Apotekkk.KategoriServices;

import com.example.Apotekkk.Kategori.Kategori;
import com.example.Apotekkk.KategoriDTO.KategoriDTO;
import com.example.Apotekkk.KategoriRepository.KategoriRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class KategoriServices {
    private final KategoriRepository kategoriRepository;

    public KategoriServices(KategoriRepository kategoriRepository) {
        this.kategoriRepository = kategoriRepository;
    }

    public List<KategoriDTO> getAllKategoris(){
        List<Kategori> kategoris = kategoriRepository.findAll();
        return mapKategorisToDTOs(kategoris);
    }

    private List<KategoriDTO> mapKategorisToDTOs(List<Kategori> kategoris) {
        List<KategoriDTO> kategoriDTOs = new ArrayList<>();
        for (Kategori kategori: kategoris){
            KategoriDTO kategoriDTO = new KategoriDTO();
            kategoriDTO.setId(kategori.getId());
            kategoriDTO.setNama_kategori(kategori.getNama_kategori());
            kategoriDTO.setDeskripsi(kategori.getDeskripsi());
            kategoriDTOs.add(kategoriDTO);
        }
        return kategoriDTOs;
    }

    public KategoriDTO getKategoriById(Integer id){
        Optional<Kategori> optionalKategori =kategoriRepository.findById(id);
        if (optionalKategori.isPresent()){
            Kategori kategori = optionalKategori.get();
            return mapKategorisToDTO(kategori);
        }
        return null;
    }

    private KategoriDTO mapKategorisToDTO(Kategori kategori) {
        KategoriDTO kategoriDTO = new KategoriDTO();
        kategoriDTO.setId(kategori.getId());
        kategoriDTO.setNama_kategori(kategori.getNama_kategori());
        kategoriDTO.setDeskripsi(kategori.getDeskripsi());
        return kategoriDTO;
    }

    public KategoriDTO addKategori(KategoriDTO kategoriDTO){
        Kategori kategori = mapKategorisToDTOs(kategoriDTO);
        Kategori savedKategori = kategoriRepository.save(kategori);
        return mapKategorisToDTO(savedKategori);
    }

    private Kategori mapKategorisToDTOs(KategoriDTO kategoriDTO) {
        Kategori kategori = new Kategori();
        kategori.setNama_kategori(kategoriDTO.getNama_kategori());
        kategori.setDeskripsi(kategoriDTO.getDeskripsi());
        return kategori;
    }

    public void deleteKategori(Integer id){
        kategoriRepository.deleteById(id);
    }

    public KategoriDTO updateKategori(Integer id, KategoriDTO kategoriDTO){
        Optional<Kategori> optionalSupplier = kategoriRepository.findById(id);
        if (optionalSupplier.isPresent()) {
            Kategori existingSupplier = optionalSupplier.get();

            // Pengecekan dan pengaturan hanya jika data tidak null atau kosong
            if (kategoriDTO.getNama_kategori() != null && !kategoriDTO.getNama_kategori().isEmpty()) {
                existingSupplier.setNama_kategori(kategoriDTO.getNama_kategori());
            }
            if (kategoriDTO.getDeskripsi() != null && !kategoriDTO.getDeskripsi().isEmpty()) {
                existingSupplier.setDeskripsi(kategoriDTO.getDeskripsi());
            }


            Kategori updatedKategori = kategoriRepository.save(existingSupplier);
            return mapKategorisToDTO(updatedKategori);
        }
        return null;

    }
}

