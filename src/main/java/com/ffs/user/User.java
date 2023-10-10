package com.ffs.user;

import com.ffs.branch.domain.Branch;
import lombok.Getter;

import javax.persistence.*;

@Getter
@MappedSuperclass
public abstract class User {

    @Column(name = "NAME")
    private String name;

    @Column(name = "LOGIN_ID")
    private String loginId;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "PASSWORD_TYPE")
    private String passwordType;

    @Column(name = "PASSWORD_SALT")
    private String passwordSalt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BRANCH_ID")
    private Branch branch;

    @Column(name = "ROLE")
    private Role role; //(대표, 점장, 매니저, 트레이너, FC, 회원)

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    protected void setUserInfo(String name, String loginId, String password, String passwordType,
                               String passwordSalt, Branch branch, Role role, String address, String phoneNumber) {
        this.name = name;
        this.loginId = loginId;
        this.password = password;
        this.passwordType = passwordType;
        this.passwordSalt = passwordSalt;
        this.branch = branch;
        this.role = role;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public void update(String address, String phoneNumber) {
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public void changeBranch(Branch branch) {
        this.branch = branch;
    }
}
