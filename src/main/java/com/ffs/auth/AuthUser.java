package com.ffs.auth;

import com.ffs.user.domain.Role;
import lombok.Builder;
import lombok.Getter;


@Getter
public class AuthUser {
    private Long userId;
    private String name;
    private String loginId;
    private String password;
    private Role role; //(대표, 점장, 매니저, 트레이너, FC, 회원)
    private Long branchId;

    @Builder
    public AuthUser(Long id, String name, String loginId, String password, Role role, Long branchId) {
        this.userId = id;
        this.name = name;
        this.loginId = loginId;
        this.password = password;
        this.role = role;
        this.branchId = branchId;
    }
}
