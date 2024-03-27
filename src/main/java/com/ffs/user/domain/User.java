package com.ffs.user.domain;

import com.ffs.branch.domain.Branch;
import com.ffs.user.domain.repository.RoleConverter;
import com.ffs.user.domain.repository.UserStatusConverter;
import com.ffs.user.domain.repository.UserTypeConverter;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "USER")
public class User {

    @Id
    @Column(name = "USER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Convert(converter = UserTypeConverter.class)
    @Column(name = "TYPE")
    private UserType userType;

    @Convert(converter = RoleConverter.class)
    @Column(name = "ROLE")
    private Role role; //(대표, 점장, 매니저, 트레이너, FC, 회원)

    @Convert(converter = UserStatusConverter.class)
    @Column(name = "STATUS")
    private UserStatus status; // 회원 - (일반회원, PT회원, 휴면회원, 만기회원) & 직원 - (재직중 , 퇴사)

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Builder
    protected User(String name, String loginId, String password, String passwordType,
                               String passwordSalt, Branch branch, UserType userType, Role role, UserStatus status,
                               String address, String phoneNumber) {
        this.name = name;
        this.loginId = loginId;
        this.password = password;
        this.passwordType = passwordType;
        this.passwordSalt = passwordSalt;
        this.branch = branch;
        this.userType = userType;
        this.role = role;
        this.status = status;
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

    public void changeStatus(UserStatus status) {
        this.status = status;
    }
}
