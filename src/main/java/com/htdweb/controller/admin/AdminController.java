package com.htdweb.controller.admin;

import com.htdweb.model.dto.*;
import com.htdweb.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminController {

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private BuildingService buildingService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private CustomerService customerService;
    @GetMapping("/admin/home")
    public ModelAndView homePage(){
        ModelAndView mav = new ModelAndView("/admin/index");
//        ModelAndView mav = new ModelAndView("/admin/test");
        return mav;
    }
//    @GetMapping("/admin/add-building")
//    public ModelAndView addBuilding(@ModelAttribute BuildingDTO buildingDTO){
//        ModelAndView mav = new ModelAndView("/admin/add-building");
//        mav.addObject("modelAdd", buildingDTO);
//        return mav;
//    }
    @GetMapping("/admin/check-building")
    public ModelAndView updateBuilding(@ModelAttribute("modelAddBuilding") BuildingDTO buildingDTO, @RequestParam(name = "pageNo", defaultValue = "1") Long pageNo){
        ModelAndView mav = new ModelAndView("/admin/check-building");
        Page<BuildingDTO> buildingDTOPage = buildingService.getAllBuildingDTOPageForAdmin(pageNo);
        int totalPage = buildingDTOPage.getTotalPages();
        mav.addObject("buildingDTOPage",buildingDTOPage);
        mav.addObject("totalPage",totalPage);
        mav.addObject("currentPage",pageNo);
        return mav;
    }
//    @PostMapping("/admin/update-building")
//    public String UpdateBuilding(@ModelAttribute("modelAdd") BuildingDTO buildingDTO){
//        buildingService.addOrUpdateBuilding(buildingDTO);
//        if(buildingDTO.getId() != null){
//            return "redirect:/admin/update-building";
//        }
//        else{
//            return "redirect:/admin/home";
//        }
//    }
//    @PostMapping("/admin/add-building")
//    public String addBuilding1(@ModelAttribute("modelAddBuilding") BuildingDTO buildingDTO){
//        buildingService.addOrUpdateBuilding(buildingDTO);
//        if(buildingDTO.getId() != null){
//            return "redirect:/admin/update-building";
//        }
//        else{
//            return "redirect:/admin/update-building";
//        }
//    }
//    @GetMapping("/admin/test")
//    public ModelAndView test(){
//        return new ModelAndView("/web/rent");
//    }
//    @GetMapping("/admin/check-order")
//    public ModelAndView adminCheckOrder(@ModelAttribute("modelAddImage") TransactionDTO transactionDTO,  @RequestParam(name = "pageNo", defaultValue = "1") Long pageNo){
//        ModelAndView mav = new ModelAndView("/admin/check-order");
//        Page<OrderDTO> orderDTOPage = orderService.getAllPageOrderDTOForAdmin(pageNo);
//        mav.addObject("orderDTOPage", orderDTOPage);
//        int totalPage = orderDTOPage.getTotalPages();
//
//        mav.addObject("totalPage",totalPage);
//        mav.addObject("currentPage",pageNo);
//        return mav;
//    }
//    @GetMapping("/admin/check-transaction")
//    public ModelAndView adminCheckTransaction(  @RequestParam(name = "pageNo", defaultValue = "1") Long pageNo){
//        ModelAndView mav = new ModelAndView("/admin/check-transaction");
////        List<TransactionDTO> transactionDTOList = transactionService.getAllTransacactionForAdmin();
////        mav.addObject("transactionDTOList", transactionDTOList);
//        Page<TransactionDTO> transactionDTOPage = transactionService.getAllPageTransacactionForAdmin(pageNo);
//        mav.addObject("transactionDTOPage", transactionDTOPage);
//        int totalPage = transactionDTOPage.getTotalPages();
//
//        mav.addObject("totalPage",totalPage);
//        mav.addObject("currentPage",pageNo);
//        return mav;
//
//    }
//
//    @GetMapping("/admin/update/{id}")
//    public ModelAndView checkUpdateBuilding(@PathVariable Long id, @ModelAttribute("modelAdd") BuildingDTO buildingDTO){
//        ModelAndView mav = new ModelAndView("/admin/update-templates");
//        buildingDTO = buildingService.getBuildingDTOById(id);
//        mav.addObject("modelAdd", buildingDTO);
//        return mav;
//    }
    @GetMapping("/admin/refuse/{id}")
    public String deleteBuilding(@PathVariable Long id){
        buildingService.refuseBuilding(id);
        return "redirect:/admin/check-building";
    }
    @GetMapping("/admin/accept/{id}")
    public String acceptBuilding(@PathVariable Long id){
        buildingService.acceptBuilding(id);
        return "redirect:/admin/check-building";
    }
    @GetMapping("/admin/delete-user/{id}")
    public String failedOrder(@PathVariable Long id){
        userService.deleteUserbyId(id);
        return "redirect:/admin/home";
    }
//    @PostMapping("/admin/done-order/")
//    public String doneOrder(@ModelAttribute("modelAddImage") TransactionDTO transactionDTO){
//        orderService.doneOrder(transactionDTO);
//        return "redirect:/admin/check-order";
//    }
    @GetMapping("/admin/check-request-be-seller")
    public ModelAndView checkRequestBeSeller(@RequestParam(name = "pageNo", defaultValue = "1") Long pageNo){
        ModelAndView mav = new ModelAndView("/admin/check-request-be-seller");
        Page<CustomerDTO> customerDTOPage = customerService.getAllCustomerWantoSellerForAmin(pageNo);
        mav.addObject("customerDTOPage", customerDTOPage);
        int totalPage = customerDTOPage.getTotalPages();

        mav.addObject("totalPage",totalPage);
        mav.addObject("currentPage",pageNo);
        return mav;
    }
    @GetMapping("/admin/done-request-be-seller/{id}")
    public String failedRequestBeSeller(@PathVariable Long id){
        userService.doneRequestBeSeller(id);
        return "redirect:/admin/check-request-be-seller";
    }
    @GetMapping("/admin/failed-request-be-seller/{id}")
    public String StringRequestBeSeller(@PathVariable Long id){
        customerService.failedRequestBeSeller(id);
        return "redirect:/admin/check-request-be-seller";
    }
    @GetMapping("/admin/customer-management")
    public ModelAndView userManagement(@ModelAttribute("modelAddBuilding") BuildingDTO buildingDTO, @RequestParam(name = "pageNo", defaultValue = "1") Long pageNo){
        ModelAndView mav = new ModelAndView("/admin/customer-management");
        Page<UserDTO> userDTOPage = userService.getAllUserDTObyRole("ROLE_CUSTOMER", pageNo);
        int totalPage = userDTOPage.getTotalPages();
        mav.addObject("userDTOPage",userDTOPage);
        mav.addObject("totalPage",totalPage);
        mav.addObject("currentPage",pageNo);
        return mav;
    }
    @GetMapping("/admin/seller-management")
    public ModelAndView sellerManagement(@ModelAttribute("modelAddBuilding") BuildingDTO buildingDTO, @RequestParam(name = "pageNo", defaultValue = "1") Long pageNo){
        ModelAndView mav = new ModelAndView("/admin/seller-management");
        Page<UserDTO> userDTOPage = userService.getAllUserDTObyRole("ROLE_SELLER", pageNo);
        int totalPage = userDTOPage.getTotalPages();
        mav.addObject("userDTOPage",userDTOPage);
        mav.addObject("totalPage",totalPage);
        mav.addObject("currentPage",pageNo);
        return mav;
    }
//    @GetMapping("/admin/done-order/{id}")
//    public String doneOrder(@PathVariable Long id){
//        orderService.doneOrder(id);
//        return "redirect:/admin/check-order";
//    }
}
