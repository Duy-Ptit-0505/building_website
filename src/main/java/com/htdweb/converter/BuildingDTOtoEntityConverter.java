package com.htdweb.converter;

import com.htdweb.entity.BuildingEntity;
import com.htdweb.entity.CategoriesEntity;
import com.htdweb.entity.UserEntity;
import com.htdweb.model.dto.BuildingDTO;
import com.htdweb.repository.BuildingRepository;
import com.htdweb.repository.UserRepository;
import com.htdweb.service.CategoriesService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class BuildingDTOtoEntityConverter {
    @Autowired
    private BuildingRepository buildingRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CategoriesService categoriesService;
    @Autowired
    private UserRepository userRepository;
    public BuildingEntity toBuildingEntity(BuildingDTO buildingDTO){
        BuildingEntity buildingEntity = modelMapper.map(buildingDTO, BuildingEntity.class);
        buildingEntity.setArea(Double.parseDouble(buildingDTO.getArea()));
        CategoriesEntity categoriesEntity = categoriesService.findCategoriesByCode(buildingDTO.getCategory());
        buildingEntity.setCategoriesEntity(categoriesEntity);
        buildingEntity.setYearBuild(new Date());
        UserEntity userEntity = new UserEntity();
        if(buildingDTO.getId_user() != null){
            userEntity = userRepository.findById(buildingDTO.getId_user()).get();
        }
        else{
            userEntity = buildingRepository.findById(buildingDTO.getId()).get().getUserEntity();
        }
        buildingEntity.setUserEntity(userEntity);
//        buildingEntity.setImage64(buildingDTO.getImageBase64());
        if(buildingDTO.getId() == null){
            buildingEntity.setView(0l);

        }else {
            buildingEntity.setView(Long.parseLong(buildingDTO.getView()));
        }
        buildingEntity.setStatus(1);
        return buildingEntity;
    }
}
