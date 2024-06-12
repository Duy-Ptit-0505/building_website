package com.htdweb.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransactionDTO {
    private Long id;
    private String building_name;
    private String signing_date;
    private String imageBase64;
    private Long idBuilding;
    private Long idOrder;
    private MultipartFile image;
    private String imageContractBase64;
    private String priceFinal;
    private String address_building;
    private String description_building;
    private String purchased_by;
    private String sold_by;
}
