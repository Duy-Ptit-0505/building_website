package com.htdweb.service;

import com.htdweb.model.dto.CustomerDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomerService {
    void requestBeSeller(String name);
    List<CustomerDTO> getAllCustomerWantToSeller();
    Page<CustomerDTO> getAllCustomerWantoSellerForAmin(Long pageNo);
    void failedRequestBeSeller(Long id);
}
