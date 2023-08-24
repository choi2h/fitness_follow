package com.ffs.user;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

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

    @Column(name = "ROLE")
    private Role role; //(대표, 점장, 매니저, 트레이너, FC, 회원)

    protected void setUserInfo(String name, String loginId, String password,
                               String passwordType, String passwordSalt, Role role) {
        this.name = name;
        this.loginId = loginId;
        this.password = password;
        this.passwordType = passwordType;
        this.passwordSalt = passwordSalt;
        this.role = role;
    }
}
