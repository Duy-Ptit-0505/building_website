package com.htdweb.converter;

import com.htdweb.entity.CustomerEntity;
import com.htdweb.entity.RoleEntity;
import com.htdweb.entity.UserEntity;
import com.htdweb.model.dto.RegisterDTO;
import com.htdweb.service.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Component
public class TrantoCusEntity {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private RoleService roleService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    public CustomerEntity toCustomerEntity(RegisterDTO registerDTO){
        CustomerEntity customerEntity = modelMapper.map(registerDTO, CustomerEntity.class);
        customerEntity.setPassword(bCryptPasswordEncoder.encode(registerDTO.getPassword()));
        customerEntity.setEnabled(1);
        customerEntity.setCreatedAt(new Date());
        return customerEntity;
    }
}
