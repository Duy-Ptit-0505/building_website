package com.htdweb.service;

import com.htdweb.model.dto.BuildingDTO;
import com.htdweb.model.dto.SearchDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

public interface BuildingService {
    List<BuildingDTO> getAllBuildingDTO();
    List<BuildingDTO> getAllBuildingStatus1(SearchDTO searchDTO);
    BuildingDTO getBuildingDTOById(Long id);
    List<BuildingDTO> getTop5BuildingDTOByView();
    void addOrUpdateBuilding(BuildingDTO buildingDTO);
    void deleteBuilding(Long id);
    List<BuildingDTO> getTop3BuildingDTOByView(SearchDTO searchDTO);
    void acceptBuilding(Long id);
    void refuseBuilding(Long id);
    Page<BuildingDTO> getAllBuildingDTOStatus1(SearchDTO searchDTO, Long pageNo);
    Page<BuildingDTO> getAllBuildingDTOPageForSeller(String name, Long pageNo);
    Page<BuildingDTO> getAllBuildingDTOPageForAdmin(Long pageNo);

}
