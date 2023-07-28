package com.ffs.employee.domain;

import com.ffs.branch.domain.Branch;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
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

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

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

    @Builder
    public Employee(Branch branch, String name, String responsibility, String address, String phoneNumber, String status,
                    String loginId, String password, String passwordType, String passwordSalt) {
        this.branch = branch;
        this.name = name;
        this.responsibility = responsibility;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.status = status;
        this.loginId = loginId;
        this.password = password;
        this.passwordType = passwordType;
        this.passwordSalt = passwordSalt;
    }

    public void changeStatus(EmployeeStatus status) {
        this.status = status.getName();
    }

    public void update(String address, String phoneNumber) {
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public void changeBranch(Branch branch) {
        this.branch = branch;
    }
}
