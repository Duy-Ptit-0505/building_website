package com.htdweb.service.impl;

import com.htdweb.converter.BuildingDTOtoEntityConverter;
import com.htdweb.converter.BuildingEntityToDTOConverter;
import com.htdweb.entity.BuildingEntity;
import com.htdweb.entity.UserEntity;
import com.htdweb.model.dto.BuildingDTO;
import com.htdweb.model.dto.SearchDTO;
import com.htdweb.repository.BuildingRepository;
import com.htdweb.repository.UserRepository;
import com.htdweb.service.BuildingService;
import com.htdweb.utils.ImageUpload;
import com.htdweb.utils.UploadFileUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
@Transactional
@Service
public class BuildingServiceImpl implements BuildingService {
    @Autowired
    private BuildingRepository buildingRepository;
    @Autowired
    private BuildingEntityToDTOConverter buildingEntityToDTOConverter;
    @Autowired
    private BuildingDTOtoEntityConverter buildingDTOtoEntityConverter;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ImageUpload imageUpload;
    @Override
    public List<BuildingDTO> getAllBuildingDTO() {
        List<BuildingEntity> buildingEntityList = buildingRepository.findAll();
        List<BuildingDTO> buildingDTOList = new ArrayList<>();
        for(BuildingEntity x : buildingEntityList){
            buildingDTOList.add(buildingEntityToDTOConverter.toBuildingDTO(x));
        }
        return buildingDTOList;
    }

    @Override
    public List<BuildingDTO> getAllBuildingStatus1(SearchDTO searchDTO) {
        List<BuildingEntity> buildingEntityList = buildingRepository.getAllBuildingStatus1(searchDTO);
        List<BuildingDTO> buildingDTOList = new ArrayList<>();
        for(BuildingEntity x : buildingEntityList){
            buildingDTOList.add(buildingEntityToDTOConverter.toBuildingDTO(x));
        }
        return buildingDTOList;
    }

    @Override
    public BuildingDTO getBuildingDTOById(Long id) {
        BuildingEntity buildingEntity = buildingRepository.findById(id).get();
        buildingEntity.setView(buildingEntity.getView() + 1l);
        buildingRepository.save(buildingEntity);
        return buildingEntityToDTOConverter.toBuildingDTO(buildingEntity);
    }

    @Override
    public List<BuildingDTO> getTop5BuildingDTOByView() {
        List<BuildingEntity> buildingEntityList = buildingRepository.getTop5BuildingByView();
        List<BuildingDTO> list = new ArrayList<>();
        for(BuildingEntity x : buildingEntityList){
            list.add(buildingEntityToDTOConverter.toBuildingDTO(x));
        }
        return list;
    }

    @Override
    public void addOrUpdateBuilding(BuildingDTO buildingDTO) {
        BuildingEntity buildingEntity = buildingDTOtoEntityConverter.toBuildingEntity(buildingDTO);
        MultipartFile image = buildingDTO.getImage();
        if (image == null) {
            buildingEntity.setImage(null);
        } else {
            imageUpload.uploadFile(image);
            try {
                buildingEntity.setImage(Base64.getEncoder().encodeToString(image.getBytes()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if(buildingDTO.getId() == null){
            buildingEntity.setStatus(2);
        }else{
            buildingEntity.setStatus(5);
        }
        buildingRepository.save(buildingEntity);
    }

    @Override
    public void deleteBuilding(Long id) {
        buildingRepository.deleteById(id);
    }

    @Override
    public List<BuildingDTO> getTop3BuildingDTOByView(SearchDTO searchDTO) {
        List<BuildingEntity> buildingEntityList = buildingRepository.get3BuildingByView(searchDTO);
        List<BuildingDTO> buildingDTOList = new ArrayList<>();
        for(BuildingEntity x : buildingEntityList){
            buildingDTOList.add(buildingEntityToDTOConverter.toBuildingDTO(x));
        }
        return buildingDTOList;
    }

    @Override
    public void acceptBuilding(Long id) {
        BuildingEntity buildingEntity = buildingRepository.findById(id).get();
        buildingEntity.setStatus(1);
        buildingRepository.save(buildingEntity);
    }

    @Override
    public void refuseBuilding(Long id) {
        BuildingEntity buildingEntity = buildingRepository.findById(id).get();
        if(buildingEntity.getStatus() == 2){
            buildingEntity.setStatus(6);
        }
        if (buildingEntity.getStatus() == 5){
            buildingEntity.setStatus(7);
        }
        buildingRepository.save(buildingEntity);
    }

    @Override
    public Page<BuildingDTO> getAllBuildingDTOStatus1(SearchDTO searchDTO, Long pageNo) {
        List<BuildingEntity> buildingEntityList = buildingRepository.getAllBuildingStatus1(searchDTO);
        List<BuildingDTO> buildingDTOList = new ArrayList<>();
        for(BuildingEntity x : buildingEntityList){
            buildingDTOList.add(buildingEntityToDTOConverter.toBuildingDTO(x));
        }
        Pageable pageable = PageRequest.of((int) (pageNo - 1l), 6);
        Long start = pageable.getOffset();
//        if(start > buildingDTOList.size()){
////            start = 0l;
////            pageable = PageRequest.of((int) (1l - 1l), 6);
////            start = pageable.getOffset();
//        }
        Long end = ((pageable.getOffset() + pageable.getPageSize()) > buildingDTOList.size() ? buildingDTOList.size() : pageable.getOffset() + pageable.getPageSize());
        buildingDTOList = buildingDTOList.subList(Math.toIntExact(start), Math.toIntExact(end));
        return new PageImpl<>(buildingDTOList, pageable, buildingRepository.getAllBuildingStatus1(searchDTO).size());
    }

    @Override
    public Page<BuildingDTO> getAllBuildingDTOPageForSeller(String name, Long pageNo) {
//        List<BuildingEntity> buildingEntityList = buildingRepository.findAllByStatus(1);
        List<Integer> listStatus = new ArrayList<>();
        listStatus.add(1);
        listStatus.add(2);
        listStatus.add(5);
        listStatus.add(6);
        listStatus.add(7);

        List<BuildingEntity> buildingEntityList = buildingRepository.findAllByStatusIn(listStatus);
        List<BuildingDTO> buildingDTOList = new ArrayList<>();
        UserEntity userEntity = userRepository.findOneByUserName(name);
        for(BuildingEntity x : buildingEntityList){
            if(x.getUserEntity()==(userEntity)){
                buildingDTOList.add(buildingEntityToDTOConverter.toBuildingDTO(x));
            }

        }
        Integer size = buildingDTOList.size();
        Pageable pageable = PageRequest.of((int) (pageNo - 1l), 6);
        Long start = pageable.getOffset();
        Long end = ((pageable.getOffset() + pageable.getPageSize()) > buildingDTOList.size() ? buildingDTOList.size() : pageable.getOffset() + pageable.getPageSize());
        buildingDTOList = buildingDTOList.subList(Math.toIntExact(start), Math.toIntExact(end));
        return new PageImpl<>(buildingDTOList, pageable, size);
    }
    @Override
    public Page<BuildingDTO> getAllBuildingDTOPageForAdmin(Long pageNo) {
        List<Integer> listStatus = new ArrayList<>();
        listStatus.add(2);
        listStatus.add(5);
        List<BuildingEntity> buildingEntityList = buildingRepository.findAllByStatusIn(listStatus);
        List<BuildingDTO> buildingDTOList = new ArrayList<>();
        for(BuildingEntity x : buildingEntityList){
            buildingDTOList.add(buildingEntityToDTOConverter.toBuildingDTO(x));
        }
        Integer size = buildingDTOList.size();
        Pageable pageable = PageRequest.of((int) (pageNo - 1l), 6);
        Long start = pageable.getOffset();
        Long end = ((pageable.getOffset() + pageable.getPageSize()) > buildingDTOList.size() ? buildingDTOList.size() : pageable.getOffset() + pageable.getPageSize());
        buildingDTOList = buildingDTOList.subList(Math.toIntExact(start), Math.toIntExact(end));
        return new PageImpl<>(buildingDTOList, pageable, size);
    }


}
