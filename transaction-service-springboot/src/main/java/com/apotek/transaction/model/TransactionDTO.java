package com.apotek.transaction.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class TransactionDTO {

    private Long id;

//    private Long user_id;

    private String transaction_code;

    private Long medicine_id;

    private List<Object> obatDataList;

    private int quantity;

    private Date createdAt;


}
