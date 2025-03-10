package com.htdweb.repository.custom;

import com.htdweb.entity.BuildingEntity;
import com.htdweb.model.dto.SearchDTO;

import java.util.List;

public interface BuildingRepositoryCustom {
    List<BuildingEntity> getAllBuildingStatus1(SearchDTO searchDTO);
    List<BuildingEntity> getTop5BuildingByView();
    List<BuildingEntity> get3BuildingByView(SearchDTO searchDTO);
}
