package com.obat.obatservice.controller;

import com.obat.obatservice.model.ObatDTO;
import com.obat.obatservice.model.ObatHistoryDTO;
import com.obat.obatservice.repository.ObatRepository;
import com.obat.obatservice.service.ObatService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/obat")
public class ObatController {
    private final ObatService obatService;
    private final ObatRepository obatRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public ObatController(ObatService obatService, ObatRepository obatRepository, ModelMapper modelMapper) {
        this.obatService = obatService;
        this.obatRepository = obatRepository;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/history/all")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CASHIER')")
    public List<ObatHistoryDTO> getAllObatsHistory(){
        return obatService.getAllObatsHistory();
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CASHIER')")
    public List<ObatDTO> getAllObats() {
        return obatService.getAllObats();
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ObatDTO createObat(@RequestBody ObatDTO obatDTO) {
        return obatService.createObat(obatDTO);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ObatDTO updateObat(@PathVariable Long id, @RequestBody ObatDTO obatDTO) {
        return obatService.updateObat(id, obatDTO);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteObat(@PathVariable Long id) {
        obatService.deleteObat(id);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CASHIER')")
    public ObatDTO getObatById(@PathVariable Long id) {
        return obatService.getObatById(id);
    }

}
