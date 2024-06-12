package com.htdweb.controller.seller;

import com.htdweb.entity.UserEntity;
import com.htdweb.model.dto.BuildingDTO;
import com.htdweb.model.dto.OrderDTO;
import com.htdweb.model.dto.TransactionDTO;
import com.htdweb.model.dto.UserDTO;
import com.htdweb.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
@Controller
public class SellerController {
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
    @GetMapping("/seller/home")
    public ModelAndView homePage(){
        ModelAndView mav = new ModelAndView("/seller/index");
//        ModelAndView mav = new ModelAndView("/admin/test");
        return mav;
    }
    @GetMapping("/seller/add-building")
    public ModelAndView addBuilding(@ModelAttribute BuildingDTO buildingDTO){
        ModelAndView mav = new ModelAndView("/seller/add-building");
        mav.addObject("modelAdd", buildingDTO);
        return mav;
    }
    @GetMapping("/seller/update-building")
    public ModelAndView updateBuilding(@ModelAttribute("modelAddBuilding") BuildingDTO buildingDTO, @RequestParam(name = "pageNo", defaultValue = "1") Long pageNo){
        ModelAndView mav = new ModelAndView("/seller/update-building");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        Page<BuildingDTO> buildingDTOPage = buildingService.getAllBuildingDTOPageForSeller(name, pageNo);

        int totalPage = buildingDTOPage.getTotalPages();
        mav.addObject("buildingDTOPage",buildingDTOPage);
        mav.addObject("totalPage",totalPage);
        mav.addObject("currentPage",pageNo);
        return mav;
    }
    @PostMapping("/seller/update-building")
    public String UpdateBuilding(@ModelAttribute("modelAdd") BuildingDTO buildingDTO){
        buildingService.addOrUpdateBuilding(buildingDTO);
        if(buildingDTO.getId() != null){
            return "redirect:/seller/update-building";
        }
        else{
            return "redirect:/seller/home";
        }
    }
    @PostMapping("/seller/add-building")
    public String addBuilding1(@ModelAttribute("modelAddBuilding") BuildingDTO buildingDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        UserDTO userDTO = userService.findUserByName(name);
        buildingDTO.setId_user(userDTO.getId());
        buildingService.addOrUpdateBuilding(buildingDTO);
        return "redirect:/seller/update-building";
    }
//    @GetMapping("/admin/test")
//    public ModelAndView test(){
//        return new ModelAndView("/web/rent");
//    }
    @GetMapping("/seller/check-order")
    public ModelAndView adminCheckOrder(@ModelAttribute("modelAddImage") TransactionDTO transactionDTO, @RequestParam(name = "pageNo", defaultValue = "1") Long pageNo){
        ModelAndView mav = new ModelAndView("/seller/check-order");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        Page<OrderDTO> orderDTOPage = orderService.getAllPageOrderDTOForSeller(name, pageNo);
        mav.addObject("orderDTOPage", orderDTOPage);
        int totalPage = orderDTOPage.getTotalPages();

        mav.addObject("totalPage",totalPage);
        mav.addObject("currentPage",pageNo);
        return mav;
    }
    @GetMapping("/seller/check-transaction")
    public ModelAndView adminCheckTransaction(  @RequestParam(name = "pageNo", defaultValue = "1") Long pageNo){
        ModelAndView mav = new ModelAndView("/seller/check-transaction");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
//        List<TransactionDTO> transactionDTOList = transactionService.getAllTransacactionForAdmin();
//        mav.addObject("transactionDTOList", transactionDTOList);
        Page<TransactionDTO> transactionDTOPage = transactionService.getAllPageTransacactionForSeller(name, pageNo);
        mav.addObject("transactionDTOPage", transactionDTOPage);
        int totalPage = transactionDTOPage.getTotalPages();

        mav.addObject("totalPage",totalPage);
        mav.addObject("currentPage",pageNo);
        return mav;

    }

    @GetMapping("/seller/update/{id}")
    public ModelAndView checkUpdateBuilding(@PathVariable Long id, @ModelAttribute("modelAdd") BuildingDTO buildingDTO){
        ModelAndView mav = new ModelAndView("/seller/update-templates");
        buildingDTO = buildingService.getBuildingDTOById(id);
        mav.addObject("modelAdd", buildingDTO);
        return mav;
    }
    @GetMapping("/seller/delete/{id}")
    public String deleteBuilding(@PathVariable Long id){
        buildingService.deleteBuilding(id);
        return "redirect:/seller/update-building";
    }
    @GetMapping("/seller/failed-order/{id}")
    public String failedOrder(@PathVariable Long id){
        orderService.failedOrder(id);
        return "redirect:/seller/check-order";
    }
    @PostMapping("/seller/done-order/")
    public String doneOrder(@ModelAttribute("modelAddImage") TransactionDTO transactionDTO){
        orderService.doneOrder(transactionDTO);
        return "redirect:/seller/check-order";
    }
}
