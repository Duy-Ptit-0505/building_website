package com.htdweb.service;

import com.htdweb.entity.RoleEntity;
import com.htdweb.model.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface RoleService {

    RoleEntity findByCode(String code);
}
