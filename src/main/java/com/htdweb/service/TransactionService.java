package com.htdweb.service;

import com.htdweb.model.dto.TransactionDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TransactionService {
    List<TransactionDTO> getAllTransactionForCustomer(String name);
    List<TransactionDTO> getAllTransacactionForAdmin();
    Page<TransactionDTO> getAllPageTransactionForCustomer(String name, Long pageNo);
    Page<TransactionDTO> getAllPageTransacactionForSeller(String name, Long pageNo);
}
