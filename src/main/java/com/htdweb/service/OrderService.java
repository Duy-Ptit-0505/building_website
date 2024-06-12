package com.htdweb.service;

import com.htdweb.model.dto.OrderDTO;
import com.htdweb.model.dto.TransactionDTO;
import org.springframework.data.domain.Page;

import java.text.ParseException;
import java.util.List;

public interface OrderService {
    void saveOrder(OrderDTO orderDTO) throws ParseException;
    List<OrderDTO> getOrderListByUserName(String name);
    List<OrderDTO> getAllOrderByAdmin();
    void failedOrder(Long id);
    void doneOrder(TransactionDTO transactionDTO);
    Page<OrderDTO> getAllPageOrderDTOForSeller(String name, Long pageNo);
    Page<OrderDTO> getAllPageOrderDTOForCustomer(String name, Long pageNo);
}
