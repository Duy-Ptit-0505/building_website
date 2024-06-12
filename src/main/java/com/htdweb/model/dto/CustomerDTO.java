package com.htdweb.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CustomerDTO {
    private Long id;

    private String userName;

    private String password;

    private String email;

    private String telephone;

    private String address;

    private String image;

    private String firstName;

    private String lastName;

    private Integer enabled;

    private Date createdAt;

    private Date modifiedAt;
}
