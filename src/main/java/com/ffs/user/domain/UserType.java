package com.ffs.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserType {
    EMPLOYEE("직원"),
    MEMBER("회원");

    private String name;

    public static UserType getUserTypeByName(String name) {
        UserType[] userTypes = UserType.values();

        for(UserType userType : userTypes) {
            if(userType.name.equals(name)) {
                return userType;
            }
        }

        throw new IllegalStateException("Not exist user type. name=" + name);
    }
}
