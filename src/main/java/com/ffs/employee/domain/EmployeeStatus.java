package com.ffs.employee.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 재직상태
 */
@Getter
@AllArgsConstructor
public enum EmployeeStatus {
    RESIGN("퇴사"),
    EMPLOYED("재직중");

    private String name;

    public static EmployeeStatus getStatusByName(String name) {
        EmployeeStatus result = null;

        for(EmployeeStatus status : EmployeeStatus.values()) {
            if(name.equals(status.getName())){
                result = status;
                break;
            }
        }

        return result;
    }
}