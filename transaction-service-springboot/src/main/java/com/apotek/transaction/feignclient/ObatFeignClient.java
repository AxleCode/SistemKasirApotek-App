package com.apotek.transaction.feignclient;

import com.apotek.transaction.model.ObatDTO;
import com.apotek.transaction.model.ObatHistoryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@FeignClient(name = "obat", url = "https://obat-service-springboot-production.up.railway.app/")
public interface ObatFeignClient {
    @GetMapping("/obat/list")
    List<ObatDTO> getAllObats();

    @GetMapping("/obat/history/all")
    List<ObatHistoryDTO> getAllObatsHistory();

    @GetMapping("/obat/{id}")
    ObatDTO getObatById(@PathVariable Long id);

}
