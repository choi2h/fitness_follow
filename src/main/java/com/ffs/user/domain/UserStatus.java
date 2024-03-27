package com.ffs.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserStatus {

    // Member
    PUBLIC_MEMBER("일반회원"),
    PT_MEMBER("PT회원"),
    SLEEP_MEMBER("휴면회원"),
    EXPIRED_MEMBER("만기회원"),

    // employee
    EMPLOYED("재직중"),
    RESIGN("퇴사");

    private String name;

    public static UserStatus getUserStatusByName(String name) {
        UserStatus result = null;

        for(UserStatus status : UserStatus.values()) {
            if(name.equals(status.getName())) {
                result = status;
                break;
            }
        }

        return result;
    }
}
