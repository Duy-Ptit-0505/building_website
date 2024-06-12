package com.htdweb.service.impl;

import com.htdweb.converter.TransactionEntityToDTO;
import com.htdweb.entity.TransactionEntity;
import com.htdweb.entity.UserEntity;
import com.htdweb.model.dto.TransactionDTO;
import com.htdweb.repository.CustomerRepository;
import com.htdweb.repository.TransactionRepository;
import com.htdweb.repository.UserRepository;
import com.htdweb.service.TransactionService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private TransactionEntityToDTO transactionEntityToDTO;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public List<TransactionDTO> getAllTransactionForCustomer(String name) {
        List<TransactionDTO> transactionDTOList = new ArrayList<>();
        List<TransactionEntity> transactionEntityList = customerRepository.findOneByUserName(name).getTransactionEntityList();
        for(TransactionEntity x: transactionEntityList){
            transactionDTOList.add(transactionEntityToDTO.toTransactionDTO(x));
        }
        return transactionDTOList;
    }

    @Override
    public List<TransactionDTO> getAllTransacactionForAdmin() {
        List<TransactionDTO> transactionDTOList = new ArrayList<>();
        List<TransactionEntity> transactionEntityList = transactionRepository.findAll();
        for(TransactionEntity x: transactionEntityList){
            transactionDTOList.add(transactionEntityToDTO.toTransactionDTO(x));
        }
        return transactionDTOList;
    }

    @Override
    public Page<TransactionDTO> getAllPageTransactionForCustomer(String name, Long pageNo) {
        List<TransactionDTO> transactionDTOList = new ArrayList<>();
        List<TransactionEntity> transactionEntityList = customerRepository.findOneByUserName(name).getTransactionEntityList();
        for(TransactionEntity x: transactionEntityList){
            transactionDTOList.add(transactionEntityToDTO.toTransactionDTO(x));
        }
        Integer size = transactionDTOList.size();
        Pageable pageable = PageRequest.of((int) (pageNo - 1l), 12);
        Long start = pageable.getOffset();
        Long end = ((pageable.getOffset() + pageable.getPageSize()) > transactionEntityList.size() ? transactionEntityList.size() : pageable.getOffset() + pageable.getPageSize());
        transactionDTOList = transactionDTOList.subList(Math.toIntExact(start), Math.toIntExact(end));
        return new PageImpl<>(transactionDTOList, pageable, size );
    }

    @Override

    public Page<TransactionDTO> getAllPageTransacactionForSeller(String name, Long pageNo) {
        List<TransactionDTO> transactionDTOList = new ArrayList<>();
        List<TransactionEntity> transactionEntityList = transactionRepository.findAll();
        UserEntity userEntity = userRepository.findOneByUserName(name);
        for(TransactionEntity x: transactionEntityList){
            if(x.getBuildingEntity().getUserEntity().equals(userEntity)){
                transactionDTOList.add(transactionEntityToDTO.toTransactionDTO(x));
            }

        }
        Integer size = transactionDTOList.size();
        Pageable pageable = PageRequest.of((int) (pageNo - 1l), 12);
        Long start = pageable.getOffset();
        Long end = ((pageable.getOffset() + pageable.getPageSize()) > transactionDTOList.size() ? transactionDTOList.size() : pageable.getOffset() + pageable.getPageSize());
        transactionDTOList = transactionDTOList.subList(Math.toIntExact(start), Math.toIntExact(end));
        return new PageImpl<>(transactionDTOList, pageable, size);
    }
}
