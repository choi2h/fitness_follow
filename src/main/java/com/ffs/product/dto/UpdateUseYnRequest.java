package com.ffs.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
@AllArgsConstructor
public class UpdateUseYnRequest {

    @NotEmpty
    private String useYn;
}
