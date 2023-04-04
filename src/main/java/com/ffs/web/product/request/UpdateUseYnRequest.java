package com.ffs.web.product.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
@AllArgsConstructor
public class UpdateUseYnRequest {

    @NotEmpty
    private String useYn;
}
