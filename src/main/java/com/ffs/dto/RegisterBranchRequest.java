package com.ffs.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;


@Getter
@NoArgsConstructor
public class RegisterBranchRequest {

    private Long groupId;

    private String name;

    private String address;

    private String phoneNumber;

    @Builder
    public RegisterBranchRequest
            (@NonNull Long groupId, @NonNull String name, @NonNull String address, String phoneNumber) {
        this.groupId = groupId;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

}
