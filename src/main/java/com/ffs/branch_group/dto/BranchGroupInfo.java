package com.ffs.branch_group.dto;

import lombok.Getter;

@Getter
public class BranchGroupInfo {

    private Long id;
    private String name;

    public BranchGroupInfo(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
