package com.htdweb.controller.customer;
import com.htdweb.converter.RegisterDTOtoUserEntity;
import com.htdweb.entity.BuildingEntity;
import com.htdweb.entity.TransactionEntity;
import com.htdweb.entity.UserEntity;
import com.htdweb.model.dto.*;
import com.htdweb.repository.BuildingRepository;
import com.htdweb.repository.CustomerRepository;
import com.htdweb.repository.UserRepository;
import com.htdweb.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class CustomerController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private UserService userService;
    @GetMapping("/web/customer-order")
    ModelAndView homeOrderCustomer(@RequestParam(name = "pageNo", defaultValue = "1") Long pageNo){
        ModelAndView mav = new ModelAndView("/customer/my-order");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
//        List<OrderDTO> orderDTOList = orderService.getOrderListByUserName(name);
//        mav.addObject("orderDTOList", orderDTOList);
        Page<OrderDTO> orderDTOPage = orderService.getAllPageOrderDTOForCustomer(name, pageNo);
        mav.addObject("orderDTOPage", orderDTOPage);
        int totalPage = orderDTOPage.getTotalPages();

        mav.addObject("totalPage",totalPage);
        mav.addObject("currentPage",pageNo);
        return mav;
    }
    @GetMapping("/web/customer-transaction")
    ModelAndView homeTransactionCustomer(@RequestParam(name = "pageNo", defaultValue = "1") Long pageNo){
        ModelAndView mav = new ModelAndView("/customer/my-transaction");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
//        List<TransactionDTO> transactionDTOList = transactionService.getAllTransactionForCustomer(name);
//        mav.addObject("transactionDTOList", transactionDTOList);
        Page<TransactionDTO> transactionDTOPage  = transactionService.getAllPageTransactionForCustomer(name, pageNo);
        mav.addObject("transactionDTOPage", transactionDTOPage);
        int totalPage = transactionDTOPage.getTotalPages();

        mav.addObject("totalPage",totalPage);
        mav.addObject("currentPage",pageNo);
        return mav;
    }
    @GetMapping("/web/customer-profile")
    ModelAndView homeProfile(){
        ModelAndView mav = new ModelAndView("/customer/my-profile");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        UserDTO userDTO = userService.findUserByName(name);
        mav.addObject("userDTO", userDTO);

        return mav;
    }
}
