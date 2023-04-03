package com.ffs.domain.employee;

import com.ffs.domain.branch.entity.Branch;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "EMPLOYEE")
public class Employee {

    @Id
    @Column(name = "EMPLOYEE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "BRANCH_ID")
    private Branch branch;

    @Column(name = "NAME")
    private String name;

    @Column(name = "RESPONSIBILITY")
    private String responsibility; //(대표, 점장, 매니저, 트레이너, FC)

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "LOGIN_ID")
    private String loginId;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "PASSWORD_TYPE")
    private String passwordType;

    @Column(name = "PASSWORD_SALT")
    private String passwordSalt;

}
