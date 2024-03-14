package com.ffs.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserInfo {

    private final Long id;
    private final Long branchId;
    private final String branchName;
    private final String name;
    private final String status;
    private final String address;
    private final String phoneNumber;
    private final String loginId;

    @Builder
    public UserInfo(Long id, Long branchId, String branchName, String name, String status,
                    String address, String phoneNumber, String loginId) {
        this.id = id;
        this.branchId = branchId;
        this.branchName = branchName;
        this.name = name;
        this.status = status;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.loginId = loginId;
    }
}
