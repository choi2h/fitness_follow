package com.ffs.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 직책
 */
@Getter
@RequiredArgsConstructor
public enum Role {
    ADMIN("ROLE_ADMIN", "관리자"),
    CEO("ROLE_CEO","대표"),
    MANAGER("ROLE_MANAGER", "매니저"),
    TRAINER("ROLE_TRAINER", "트레이너"),
    MEMBER("ROLE_MEMBER", "회원");

    private final String roleText;
    private final String name;

    public static Role getRoleByName(String name) {
        Role[] roles = Role.values();

        for(Role role : roles) {
            if(role.getName().equals(name)) {
                return role;
            }
        }

        throw new IllegalStateException("Not exist role name. name=" + name);
    }
}
