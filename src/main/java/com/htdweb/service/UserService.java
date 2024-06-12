package com.htdweb.service;

import com.htdweb.model.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    UserDTO findUserByName(String name);
    void doneRequestBeSeller(Long id);
    Page<UserDTO> getAllUserDTObyRole(String role, Long pageNo);
    void deleteUserbyId(Long id);
}
