package com.htdweb.repository;

import com.htdweb.entity.BuildingEntity;
import com.htdweb.repository.custom.BuildingRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Repository
public interface BuildingRepository extends JpaRepository<BuildingEntity, Long>, BuildingRepositoryCustom {
    List<BuildingEntity> findAllByStatus(Integer status);
    List<BuildingEntity> findAllByStatusIn(List<Integer> listStatus);
}
