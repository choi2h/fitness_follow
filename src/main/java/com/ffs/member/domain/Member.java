package com.ffs.member.domain;

import com.ffs.branch.domain.Branch;
import com.ffs.employee.domain.Employee;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "MEMBER")
public class Member {

    @Id
    @Column(name = "MEMBER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "BRANCH_ID")
    private Branch branch;

    @ManyToOne
    @JoinColumn(name = "EMPLOYEE_ID")
    private Employee employee;

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

    @Builder
    public Member(Branch branch, String name, String status, String address,
                  String phoneNumber, String loginId, String password, String passwordType, String passwordSalt) {
        this.branch = branch;
        this.name = name;
        this.status = status;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.loginId = loginId;
        this.password = password;
        this.passwordType = passwordType;
        this.passwordSalt = passwordSalt;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

}
