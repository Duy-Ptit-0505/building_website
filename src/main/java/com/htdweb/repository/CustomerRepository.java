package com.htdweb.repository;

import com.htdweb.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    CustomerEntity findOneByUserName(String name);
    List<CustomerEntity> findAllByEnabled(Integer enabled);
}
