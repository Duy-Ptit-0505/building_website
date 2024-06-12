package com.htdweb.converter;

import com.htdweb.entity.TransactionEntity;
import com.htdweb.model.dto.TransactionDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

@Component
public class TransactionEntityToDTO {
    @Autowired
    private ModelMapper modelMapper;
    public TransactionDTO toTransactionDTO(TransactionEntity transactionEntity){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        TransactionDTO transactionDTO = modelMapper.map(transactionEntity, TransactionDTO.class);
//        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setSigning_date(simpleDateFormat.format(transactionEntity.getCreatedAt()));
        transactionDTO.setImageBase64(transactionEntity.getBuildingEntity().getImage());
        transactionDTO.setBuilding_name(transactionEntity.getBuildingEntity().getBuildingName());
        transactionDTO.setIdBuilding(transactionEntity.getBuildingEntity().getId());
        transactionDTO.setImageContractBase64(transactionEntity.getImage());
        transactionDTO.setPurchased_by(transactionEntity.getCustomerEntity().getFirstName() + " " + transactionEntity.getCustomerEntity().getLastName());
        transactionDTO.setSold_by(transactionEntity.getBuildingEntity().getUserEntity().getFirstName() + " " + transactionEntity.getBuildingEntity().getUserEntity().getLastName());
        transactionDTO.setDescription_building(transactionEntity.getBuildingEntity().getDescription());
        transactionDTO.setPriceFinal(new DecimalFormat("###,###").format(transactionEntity.getBuildingEntity().getPrice()));
        return transactionDTO;
    }
}
