package com.ffs.dao;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "BRANCH")
public class Branch {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;
}
