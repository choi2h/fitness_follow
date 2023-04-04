package com.ffs.web.product;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ffs.common.AbstractResponse;
import com.ffs.domain.product.entity.Product;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@JsonInclude
public class ProductResult extends AbstractResponse {

    private static final long serialVersionUID = 4494064209636802405L;

    private Long id;

    private Product product;

    private List<Product> productList;
}
