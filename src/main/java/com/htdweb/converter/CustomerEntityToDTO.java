package com.htdweb.converter;

import com.htdweb.entity.CustomerEntity;
import com.htdweb.model.dto.CustomerDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerEntityToDTO {
    @Autowired
    private ModelMapper modelMapper;
    public CustomerDTO toCustomerDTO(CustomerEntity customerEntity){
        return modelMapper.map(customerEntity, CustomerDTO.class);
    }
}
