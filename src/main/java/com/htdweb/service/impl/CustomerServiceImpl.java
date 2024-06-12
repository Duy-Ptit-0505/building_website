package com.htdweb.service.impl;

import com.htdweb.converter.CustomerEntityToDTO;
import com.htdweb.entity.CustomerEntity;
import com.htdweb.model.dto.CustomerDTO;
import com.htdweb.repository.CustomerRepository;
import com.htdweb.service.BuildingService;
import com.htdweb.service.CustomerService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerEntityToDTO customerEntityToDTO;

    @Override
    public void requestBeSeller(String name) {
        CustomerEntity customer = customerRepository.findOneByUserName(name);
        customer.setEnabled(2);
        customerRepository.save(customer);
    }

    @Override
    public List<CustomerDTO> getAllCustomerWantToSeller() {
        List<CustomerDTO> customerDTOList = new ArrayList<>();
        List<CustomerEntity> customerEntityList = customerRepository.findAllByEnabled(2);
        for(CustomerEntity x:customerEntityList){
            customerDTOList.add(customerEntityToDTO.toCustomerDTO(x));
        }
        return customerDTOList;
    }

    @Override
    public Page<CustomerDTO> getAllCustomerWantoSellerForAmin(Long pageNo) {
        List<CustomerDTO> customerDTOList = new ArrayList<>();
        List<CustomerEntity> customerEntityList = customerRepository.findAllByEnabled(2);
        for(CustomerEntity x:customerEntityList){
            customerDTOList.add(customerEntityToDTO.toCustomerDTO(x));
        }
        Pageable pageable = PageRequest.of((int) (pageNo - 1l), 2);
        Long start = pageable.getOffset();
        Long end = ((pageable.getOffset() + pageable.getPageSize()) > customerDTOList.size() ? customerDTOList.size() : pageable.getOffset() + pageable.getPageSize());
        customerDTOList = customerDTOList.subList(Math.toIntExact(start), Math.toIntExact(end));
        return new PageImpl<>(customerDTOList, pageable, customerRepository.findAllByEnabled(2).size());
    }

    @Override
    public void failedRequestBeSeller(Long id) {
        CustomerEntity customerEntity = customerRepository.findById(id).get();
        customerEntity.setEnabled(1);
        customerRepository.save(customerEntity);
    }
}
