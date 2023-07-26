package com.ffs.branch.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BranchInfo {

    private Long id;
    private String name;
    private String address;
    private String phoneNumber;
    private Long groupId;

    @Builder
    public BranchInfo(Long id, String name, String address, String phoneNumber, Long groupId) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.groupId = groupId;
    }
}
