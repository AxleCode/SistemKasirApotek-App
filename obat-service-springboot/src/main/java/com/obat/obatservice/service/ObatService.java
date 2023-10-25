package com.obat.obatservice.service;

import com.obat.obatservice.feignclient.KategoriFeignClient;
import com.obat.obatservice.feignclient.SupplierFeignClient;
import com.obat.obatservice.model.*;
import com.obat.obatservice.repository.ObatHistoryRepository;
import com.obat.obatservice.repository.ObatRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ObatService {

    private final ObatRepository obatRepository;
    private final ModelMapper modelMapper;
    private final ObatHistoryRepository obatHistoryRepository;
    private final KategoriFeignClient kategoriFeignClient;
    private final SupplierFeignClient supplierFeignClient;

    @Autowired
    public ObatService(ModelMapper modelMapper,
                       ObatRepository obatRepository,
                       ObatHistoryRepository obatHistoryRepository, KategoriFeignClient kategoriFeignClient, SupplierFeignClient supplierFeignClient) {

        this.obatRepository = obatRepository;
        this.modelMapper = modelMapper;
        this.obatHistoryRepository = obatHistoryRepository;
        this.kategoriFeignClient = kategoriFeignClient;
        this.supplierFeignClient = supplierFeignClient;
    }

    public List<ObatDTO> getAllObats() {
        List<Obat> obats = obatRepository.findAll();
        return obats.stream()
                .map(obat -> getObatById(obat.getId()))
                .collect(Collectors.toList());
    }

    public ObatDTO getObatById(Long id) {
        Obat obat = obatRepository.findById(id).orElse(null);

        ObatDTO obatDTO = new ObatDTO();
        obatDTO.setId(obat.getId());
        obatDTO.setNama_obat(obat.getNama_obat());
        obatDTO.setStok(obat.getStok());
        obatDTO.setHarga(obat.getHarga());
        obatDTO.setId_kategori(obat.getId_kategori());
        obatDTO.setId_supplier(obat.getId_supplier());
        obatDTO.setCreateAt(obat.getCreateAt());
        obatDTO.setUpdateAt(obat.getUpdateAt());

        List<Object> obatDataSupplier = new ArrayList<>();
        SupplierDTO supplier = supplierFeignClient.getSupplierById(obat.getId_supplier());
        obatDataSupplier.add(supplier);

        List<Object> obatDataKategori = new ArrayList<>();
        KategoriDTO kategori = kategoriFeignClient.getKategoriById(obat.getId_kategori());
        obatDataKategori.add(kategori);


        obatDTO.setObatDataKategori(obatDataKategori);
        obatDTO.setObatDataSupplier(obatDataSupplier);

        return obatDTO;
    }

    public ObatDTO createObat(ObatDTO obatDTO) {
        Obat obat = modelMapper.map(obatDTO, Obat.class);
        obat.setCreateAt(new Date());
        Obat savedObat = obatRepository.save(obat);
        return modelMapper.map(savedObat, ObatDTO.class);
    }

    public ObatDTO updateObat(Long id, ObatDTO obatDTO) {
        Obat obat = obatRepository.findById(id).orElse(null);

        ObatHistory obatHistory = new ObatHistory();

        obatHistory.setId_obat(obat.getId());
        obatHistory.setNama_obat(obat.getNama_obat());
        obatHistory.setId_kategori(obat.getId_kategori());
        obatHistory.setStok(obat.getStok());
        obatHistory.setHarga(obat.getHarga());
        obatHistory.setId_supplier(obat.getId_supplier());
        obatHistory.setCreateAt(new Date());

        obatHistoryRepository.save(obatHistory);

        if (obat != null) {
//            modelMapper.map(transactionDTO, transaction);
            if(obatDTO.getNama_obat() != null) {
                obat.setNama_obat(obatDTO.getNama_obat());
            } if(obatDTO.getId_kategori() != null) {
                obat.setId_kategori(obatDTO.getId_kategori());
            } if(obatDTO.getStok() != null) {
                obat.setStok(obatDTO.getStok());
            } if(obatDTO.getHarga() != null) {
                obat.setHarga(obatDTO.getHarga());
            } if(obatDTO.getId_supplier() != null) {
                obat.setId_supplier(obatDTO.getId_supplier());
            } obat.setUpdateAt(new Date());

            Obat savedObat = obatRepository.save(obat);
            return modelMapper.map(savedObat, ObatDTO.class);
        }
        return null;
    }

    public void deleteObat(Long id) {
        obatRepository.deleteById(id);
    }

    public List<ObatHistoryDTO> getAllObatsHistory() {
        List<ObatHistory> obats = obatHistoryRepository.findAll();
        return obats.stream()
                .map(obatHistory -> getObatHistoryById(obatHistory.getId()))
                .collect(Collectors.toList());
    }

    public ObatHistoryDTO getObatHistoryById(Long id) {
       ObatHistory obatHistory = obatHistoryRepository.findById(id).orElse(null);

        ObatHistoryDTO obatHistoryDTO = new ObatHistoryDTO();
        obatHistoryDTO.setId(obatHistory.getId());
        obatHistoryDTO.setId_obat(obatHistory.getId_obat());
        obatHistoryDTO.setNama_obat(obatHistory.getNama_obat());
        obatHistoryDTO.setStok(obatHistory.getStok());
        obatHistoryDTO.setHarga(obatHistory.getHarga());
        obatHistoryDTO.setId_kategori(obatHistory.getId_kategori());
        obatHistoryDTO.setId_supplier(obatHistory.getId_supplier());
        obatHistoryDTO.setCreateAt(obatHistory.getCreateAt());

        List<Object> obatDataKategori = new ArrayList<>();
        KategoriDTO kategori = kategoriFeignClient.getKategoriById(obatHistory.getId_kategori());
        obatDataKategori.add(kategori);

        List<Object> obatDataSupplier = new ArrayList<>();
        SupplierDTO supplier = supplierFeignClient.getSupplierById(obatHistory.getId_supplier());
        obatDataSupplier.add(supplier);

        obatHistoryDTO.setObatDataKategori(obatDataKategori);
        obatHistoryDTO.setObatDataSupplier(obatDataSupplier);

        return obatHistoryDTO;
    }


}
