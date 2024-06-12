package com.htdweb.service.impl;

import com.htdweb.converter.OrderDTOtoEntity;
import com.htdweb.converter.OrderEntitytoDTO;
import com.htdweb.entity.*;
import com.htdweb.model.dto.OrderDTO;
import com.htdweb.model.dto.TransactionDTO;
import com.htdweb.repository.*;
import com.htdweb.service.BuildingService;
import com.htdweb.service.OrderService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.htdweb.utils.ImageUpload;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Transactional
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private BuildingRepository buildingRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ImageUpload imageUpload;
    @Autowired
    private OrderDTOtoEntity orderDTOtoEntity;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private OrderEntitytoDTO orderEntitytoDTO;
    @Autowired
    private UserRepository userRepository;
    @Override
    public void saveOrder(OrderDTO orderDTO) throws ParseException {

        orderRepository.save(orderDTOtoEntity.toOrderEntity(orderDTO));
    }

    @Override
    public List<OrderDTO> getOrderListByUserName(String name) {
        List<OrderEntity> orderEntityList = customerRepository.findOneByUserName(name).getOrderEntityList();
        List<OrderDTO> orderDTOList = new ArrayList<>();
        for (OrderEntity x : orderEntityList){
            if(x.getStatus() == 1){
                orderDTOList.add(orderEntitytoDTO.toOrderDTO(x));
            }

        }
        return orderDTOList;
    }

    @Override
    public List<OrderDTO> getAllOrderByAdmin() {
        List<OrderEntity> orderEntityList = orderRepository.findAllByStatus(1);
        List<OrderDTO> orderDTOList = new ArrayList<>();
        for(OrderEntity x : orderEntityList){
            orderDTOList.add(orderEntitytoDTO.toOrderDTO(x));
        }
        return orderDTOList;
    }

    @Override
    public void failedOrder(Long id) {
        OrderEntity orderEntity = orderRepository.findById(id).get();
        orderEntity.setStatus(3);
        orderRepository.save(orderEntity);

    }

    @Override
    public void doneOrder(TransactionDTO transactionDTO) {
        OrderEntity orderEntity = orderRepository.findById(transactionDTO.getIdOrder()).get();
        orderEntity.setStatus(2);
        BuildingEntity buildingEntity = orderEntity.getBuildingEntity();
        buildingEntity.setStatus(3);
        orderRepository.save(orderEntity);
        buildingRepository.save(buildingEntity);
        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setBuildingEntity(orderEntity.getBuildingEntity());
        transactionEntity.setCustomerEntity(orderEntity.getCustomerEntity());
        transactionEntity.setCreatedAt(new Date());
        MultipartFile image = transactionDTO.getImage();
        if (image == null) {
            transactionEntity.setImage(null);
        } else {
            imageUpload.uploadFile(image);
            try {
                transactionEntity.setImage(Base64.getEncoder().encodeToString(image.getBytes()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        transactionRepository.save(transactionEntity);
    }

    @Override
    public Page<OrderDTO> getAllPageOrderDTOForSeller(String name, Long pageNo) {
        List<OrderEntity> orderEntityList = orderRepository.findAllByStatus(1);
        List<OrderDTO> orderDTOList = new ArrayList<>();
        UserEntity userEntity = userRepository.findOneByUserName(name);
        for(OrderEntity x : orderEntityList){
            if(x.getBuildingEntity().getUserEntity().equals(userEntity)){
                orderDTOList.add(orderEntitytoDTO.toOrderDTO(x));
            }

        }
        Integer size = orderDTOList.size();
        Pageable pageable = PageRequest.of((int) (pageNo - 1l), 2);
        Long start = pageable.getOffset();
        Long end = ((pageable.getOffset() + pageable.getPageSize()) > orderDTOList.size() ? orderDTOList.size() : pageable.getOffset() + pageable.getPageSize());
        orderDTOList = orderDTOList.subList(Math.toIntExact(start), Math.toIntExact(end));
        return new PageImpl<>(orderDTOList, pageable, size);
    }

    @Override
    public Page<OrderDTO> getAllPageOrderDTOForCustomer(String name, Long pageNo) {
        List<OrderEntity> orderEntityList = customerRepository.findOneByUserName(name).getOrderEntityList();
        List<OrderDTO> orderDTOList = new ArrayList<>();
        for (OrderEntity x : orderEntityList){
            if(x.getStatus() == 1){
                orderDTOList.add(orderEntitytoDTO.toOrderDTO(x));
            }

        }
        Integer size = orderDTOList.size();
        Pageable pageable = PageRequest.of((int) (pageNo - 1l), 2);
        Long start = pageable.getOffset();
        Long end = ((pageable.getOffset() + pageable.getPageSize()) > orderDTOList.size() ? orderDTOList.size() : pageable.getOffset() + pageable.getPageSize());
        orderDTOList = orderDTOList.subList(Math.toIntExact(start), Math.toIntExact(end));
        return new PageImpl<>(orderDTOList, pageable, size);
    }

}
