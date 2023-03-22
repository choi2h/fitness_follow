package com.ffs.dao;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "MEMBER")
public class Member {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "BRANCH_ID")
    private Long branchId;

    @Column(name = "EMPLOYEE_ID")
    private Long employeeId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "STATUS")
    private String status; //(일반회원, PT회원, 휴면회원, 만기회원)

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "LOGIN_ID")
    private String loginId;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "PASSWORD_TYPE")
    private String passwordType;

    @Column(name = "PASSWORD_SALT")
    private String passwordSalt;
}
