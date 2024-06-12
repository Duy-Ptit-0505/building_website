package com.htdweb.converter;

import com.htdweb.entity.RoleEntity;
import com.htdweb.entity.UserEntity;
import com.htdweb.model.dto.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserEntityToDTO {
    @Autowired
    private ModelMapper modelMapper;
    public UserDTO toUserDTO(UserEntity userEntity){

        UserDTO userDTO = modelMapper.map(userEntity, UserDTO.class);
        List<RoleEntity>  roleEntityList = userEntity.getRoleEntityList();
        List<String> list = new ArrayList<>();
        for(RoleEntity x : roleEntityList){
            list.add(x.getName());
        }
        userDTO.setRole(list);
        userDTO.setPassword("************");
        return userDTO;
    }
}
