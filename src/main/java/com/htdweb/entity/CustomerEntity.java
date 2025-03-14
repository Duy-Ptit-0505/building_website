package com.htdweb.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "customers")
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username")
    private String userName;
    @Column(name = "password")
    private String password;
    @Column(name = "email")
    private String email;
    @Column(name = "telephone")
    private String telephone;
    @Column(name = "address")
    private String address;
    @Column(name = "image")
    private String image;
    @Column(name = "firstname")
    private String firstName;
    @Column(name = "lastname")
    private String lastName;
    @Column(name = "enabled")
    private Integer enabled;
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "modified_at")
    private Date modifiedAt;
    @OneToMany(mappedBy = "customerEntity", fetch = FetchType.LAZY)
    private List<AssignmentCustomer> assignmentCustomerList = new ArrayList<>();
    @OneToMany(mappedBy = "customerEntity", fetch = FetchType.LAZY)
    private List<TransactionEntity> transactionEntityList = new ArrayList<>();
    @ManyToMany(mappedBy = "customerEntityList", fetch = FetchType.LAZY)
    private List<BuildingEntity> buildingEntityList = new ArrayList<>();
    @OneToMany(mappedBy = "customerEntity", fetch = FetchType.LAZY)
    private List<OrderEntity> orderEntityList = new ArrayList<>();

}
