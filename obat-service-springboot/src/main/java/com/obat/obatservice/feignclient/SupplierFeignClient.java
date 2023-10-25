package com.obat.obatservice.feignclient;
import com.obat.obatservice.model.SupplierDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@FeignClient(name = "supplier", url = "https://supplier-service-production.up.railway.app/") // iki isi en sesuai nama app microservice nak eureka e
public interface SupplierFeignClient {
    @GetMapping("/supplier/all")
    List<SupplierDTO> getAllSupplier();

    @GetMapping("/supplier/{id}")
    SupplierDTO getSupplierById(@PathVariable Long id);
}
