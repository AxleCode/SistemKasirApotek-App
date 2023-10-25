package com.apotek.transaction.controller;

import com.apotek.transaction.feignclient.ObatFeignClient;
import com.apotek.transaction.model.*;
import com.apotek.transaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    private final ObatFeignClient obatFeignClient;

    @Autowired
    public TransactionController(TransactionService transactionService,
                                 ObatFeignClient obatFeignClient) {
        this.transactionService = transactionService;
        this.obatFeignClient = obatFeignClient;
    }

//  Transaksi Controller
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CASHIER')")
    public List<TransactionDTO> getAllTransactions() {

        return transactionService.getAllTransactions();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CASHIER')")
    public TransactionDTO getTransactionById(@PathVariable Long id) {
        return transactionService.getTransactionById(id);
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('CASHIER')")
    public TransactionDTO createTransaction(@RequestBody TransactionDTO transactionDTO) {
        return transactionService.createTransaction(transactionDTO);
    }

    @PutMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('CASHIER')")
    public TransactionDTO updateTransaction(@PathVariable Long id, @RequestBody TransactionDTO transactionDTO) {
        return transactionService.updateTransaction(id, transactionDTO);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('CASHIER')")
    public void deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
    }

}
