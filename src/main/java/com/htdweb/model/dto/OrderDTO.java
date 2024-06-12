package com.htdweb.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderDTO {
    private Long id;
    private String orderDay;
    private String timeOrder;
    private String message;
    private Long idBuilding;
    private Long idCustomer;
    private String buildingName;
    private String imageBase64;
    private String customer;
    private String ownerName;
    private String phone;
    private String ownerPhone;
    private String addressBuilding;
}
