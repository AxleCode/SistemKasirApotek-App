package com.apoteksehatsentosa.sisteminformasiapotek.Service;

import com.apoteksehatsentosa.sisteminformasiapotek.Dto.SupplierDto;
import com.apoteksehatsentosa.sisteminformasiapotek.Model.Supplier;
import com.apoteksehatsentosa.sisteminformasiapotek.Repository.SupplierRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {

    private final SupplierRepository supplierRepository;

    public SupplierService(SupplierRepository supplierRepository){

        this.supplierRepository = supplierRepository;
    }

    public List<SupplierDto> getallSuppliers(){
        List<Supplier> suppliers = supplierRepository.findAll();
        return mapSuppliersToDTO(suppliers);
    }

    private List<SupplierDto> mapSuppliersToDTO(List<Supplier> suppliers) {
        List<SupplierDto> supplierDtos = new ArrayList<>();
        for (Supplier supplier: suppliers){
            SupplierDto supplierDto = new SupplierDto();
            supplierDto.setId(supplier.getId());
            supplierDto.setNama_supplier(supplier.getNama_supplier());
            supplierDto.setAlamat(supplier.getAlamat());
            supplierDto.setNo_telepon(supplier.getNo_telepon());
            supplierDtos.add(supplierDto);
        }
        return supplierDtos;
    }

    public SupplierDto getSupplierById(Integer id){
        Optional<Supplier> optionalSupplier =supplierRepository.findById(id);
        if (optionalSupplier.isPresent()){
            Supplier supplier = optionalSupplier.get();
            return mapSuppliersToDTO(supplier);
        }
        return null;
    }

    private SupplierDto mapSuppliersToDTO(Supplier supplier) {
        SupplierDto supplierDTO = new SupplierDto();
        supplierDTO.setId(supplier.getId());
        supplierDTO.setNama_supplier(supplier.getNama_supplier());
        supplierDTO.setAlamat(supplier.getAlamat());
        supplierDTO.setNo_telepon(supplier.getNo_telepon());
        return supplierDTO;
    }

    public SupplierDto addSupplier(SupplierDto supplierDTO){
        Supplier supplier = mapSuppliersToDTO(supplierDTO);
        Supplier savedSupplier = supplierRepository.save(supplier);
        return mapSuppliersToDTO(savedSupplier);
    }

    private Supplier mapSuppliersToDTO(SupplierDto supplierDTO) {
        Supplier supplier = new Supplier();
        supplier.setNama_supplier(supplierDTO.getNama_supplier());
        supplier.setAlamat(supplierDTO.getAlamat());
        supplier.setNo_telepon(supplierDTO.getNo_telepon());
        return supplier;
    }

    public void deleteSupplier(Integer id){

        supplierRepository.deleteById(id);
    }

    public SupplierDto updateSupplier(Integer id, SupplierDto supplierDTO) {
        Optional<Supplier> optionalSupplier = supplierRepository.findById(id);
        if (optionalSupplier.isPresent()) {
            Supplier existingSupplier = optionalSupplier.get();

            // Pengecekan dan pengaturan hanya jika data tidak null atau kosong
            if (supplierDTO.getNama_supplier() != null && !supplierDTO.getNama_supplier().isEmpty()) {
                existingSupplier.setNama_supplier(supplierDTO.getNama_supplier());
            }
            if (supplierDTO.getAlamat() != null && !supplierDTO.getAlamat().isEmpty()) {
                existingSupplier.setAlamat(supplierDTO.getAlamat());
            }
            if (supplierDTO.getNo_telepon() != null ) {
                existingSupplier.setNo_telepon(supplierDTO.getNo_telepon());
            }

            Supplier updatedSupplier = supplierRepository.save(existingSupplier);
            return mapSuppliersToDTO(updatedSupplier);
        }
        return null;
    }

}
