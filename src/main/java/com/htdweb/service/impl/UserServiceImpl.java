package com.htdweb.service.impl;

import com.htdweb.converter.UserEntityToDTO;
import com.htdweb.entity.CustomerEntity;
import com.htdweb.entity.RoleEntity;
import com.htdweb.entity.UserEntity;
import com.htdweb.model.dto.UserDTO;
import com.htdweb.repository.CustomerRepository;
import com.htdweb.repository.RoleRepository;
import com.htdweb.repository.UserRepository;
import com.htdweb.service.CustomerService;
import com.htdweb.service.UserService;
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
public class UserServiceImpl implements UserService {
    @Autowired
    private UserEntityToDTO userEntityToDTO;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Override
    public UserDTO findUserByName(String name) {
        return userEntityToDTO.toUserDTO(userRepository.findOneByUserName(name));
    }

    @Override
    public void doneRequestBeSeller(Long id) {
        UserEntity userEntity = userRepository.findById(id).get();
        CustomerEntity customerEntity = customerRepository.findById(id).get();
        customerEntity.setEnabled(3);
        customerRepository.save(customerEntity);
        RoleEntity roleEntity = roleRepository.findByCode("ROLE_SELLER");
        userEntity.getRoleEntityList().add(roleEntity);
        userRepository.save(userEntity);
    }

    @Override
    public Page<UserDTO> getAllUserDTObyRole(String role, Long pageNo) {
        List<UserEntity> userEntityList = roleRepository.findByCode(role).getUserEntityList();
        List<UserDTO> userDTOList = new ArrayList<>();
        for (UserEntity userEntity : userEntityList) {
            if(userEntity.getEnabled() == 1){
                userDTOList.add(userEntityToDTO.toUserDTO(userEntity));
            }

        }
        Integer size = userDTOList.size();
        Pageable pageable = PageRequest.of((int) (pageNo - 1l), 6);
        Long start = pageable.getOffset();
        Long end = ((pageable.getOffset() + pageable.getPageSize()) > userDTOList.size() ? userDTOList.size() : pageable.getOffset() + pageable.getPageSize());
        userDTOList = userDTOList.subList(Math.toIntExact(start), Math.toIntExact(end));
        return new PageImpl<>(userDTOList, pageable, size);

    }

    @Override
    public void deleteUserbyId(Long id) {
        UserEntity userEntity = userRepository.findById(id).get();
        CustomerEntity customerEntity = customerRepository.findById(id).get();
        if(customerEntity != null) {
            customerEntity.setEnabled(0);
            customerRepository.save(customerEntity);
        }
        userEntity.setEnabled(0);
        userRepository.save(userEntity);
    }


}
