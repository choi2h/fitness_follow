package com.ffs.auth.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
@AllArgsConstructor
public class LogoutRequest {

    @NotEmpty
    private String accessToken;

    @NotEmpty
    private String refreshToken;
}
