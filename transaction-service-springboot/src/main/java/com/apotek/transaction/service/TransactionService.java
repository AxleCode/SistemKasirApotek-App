package com.apotek.transaction.service;


import com.apotek.transaction.feignclient.ObatFeignClient;
import com.apotek.transaction.model.ObatDTO;
import com.apotek.transaction.model.ObatHistoryDTO;
import com.apotek.transaction.model.Transaction;
import com.apotek.transaction.model.TransactionDTO;
import com.apotek.transaction.repository.TransactionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    private final ModelMapper modelMapper;

    private final ObatFeignClient obatFeignClient;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, ObatFeignClient obatFeignClient, ModelMapper modelMapper) {
        this.transactionRepository = transactionRepository;
        this.modelMapper = modelMapper;
        this.obatFeignClient = obatFeignClient;
    }

    public List<TransactionDTO> getAllTransactions() {
        List<Transaction> transactions = transactionRepository.findAll();
        return transactions.stream()
                .map(transaction -> getTransactionById(transaction.getId()))
                .collect(Collectors.toList());
    }

    public TransactionDTO getTransactionById(Long id) {
        Transaction transaction = transactionRepository.findById(id).orElse(null);

        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setId(transaction.getId());
//        transactionDTO.setUser_id(transaction.getUser_id());
        transactionDTO.setTransaction_code(transaction.getTransaction_code());
        transactionDTO.setMedicine_id(transaction.getMedicine_id());
        transactionDTO.setQuantity(transaction.getQuantity());
        transactionDTO.setCreatedAt(transaction.getCreatedAt());

        List<Object> obatDataList = new ArrayList<>();

        ObatDTO obat = obatFeignClient.getObatById(transaction.getMedicine_id());
        if (obat.getUpdateAt() == null || obat.getUpdateAt().before(transactionDTO.getCreatedAt())) {
            obatDataList.add(obat);
        } else {
            List<ObatHistoryDTO> obatHistories = obatFeignClient.getAllObatsHistory();
            for (ObatHistoryDTO obatHistoryDTO : obatHistories) {
                if (obatHistoryDTO.getId_obat().equals(transaction.getMedicine_id()) && obatHistoryDTO.getCreateAt().after(transactionDTO.getCreatedAt())) {
                    obatDataList.add(obatHistoryDTO);
                    break;
                }
            }
        }

        transactionDTO.setObatDataList(obatDataList);

        return transactionDTO;
    }

    public TransactionDTO createTransaction(TransactionDTO transactionDTO) {
        Transaction transaction = modelMapper.map(transactionDTO, Transaction.class);
        transaction.setCreatedAt(new Date());
        Transaction savedTransaction = transactionRepository.save(transaction);
        return modelMapper.map(savedTransaction, TransactionDTO.class);
    }

    public TransactionDTO updateTransaction(Long id, TransactionDTO transactionDTO) {
        Transaction transaction = transactionRepository.findById(id).orElse(null);
//        modelMapper.map(transactionDTO, transaction);

        if (transaction != null) {
//            if(transactionDTO.getUser_id() != null) {
//                transaction.setUser_id(transactionDTO.getUser_id());
//            }
            if(transactionDTO.getTransaction_code() != null) {
                transaction.setTransaction_code(transactionDTO.getTransaction_code());
            } if(transactionDTO.getMedicine_id() != null) {
                transaction.setMedicine_id(transactionDTO.getMedicine_id());
            }
            Integer quantity = transactionDTO.getQuantity();
            if(quantity != null && quantity != 0) {
                transaction.setQuantity(transactionDTO.getQuantity());
            }
            transaction.setUpdatedAt(new Date());

            Transaction savedTransaction = transactionRepository.save(transaction);

            return modelMapper.map(savedTransaction, TransactionDTO.class);
        }
        return null;
    }

    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }
}
