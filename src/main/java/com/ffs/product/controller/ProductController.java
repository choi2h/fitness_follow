package com.ffs.product.controller;

import com.ffs.product.domain.Product;
import com.ffs.product.application.ProductService;
import com.ffs.product.dto.ProductResult;
import com.ffs.product.dto.RegisterProductRequest;
import com.ffs.product.dto.UpdateUseYnRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * 신규 제품 등록
     */
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN,ROLE_CEO,ROLE_MANAGER')")
    public ResponseEntity<Object> registerNewProduct(@RequestBody @Valid RegisterProductRequest request) {
        Product product = productService.registerNewProduct(request);
        ProductResult result = ProductResult.builder().product(product).build();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * 지점에 해당하는 제품들 조회
     */
    @GetMapping("/{branch_id}")
    public ResponseEntity<Object> getAllProductByBranch(@PathVariable("branch_id") Long branchId) {
        List<Product> productList = productService.getAllProductByBranchId(branchId);
        ProductResult result = ProductResult.builder().productList(productList).build();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     *  제품 상세 정보 조회
     */
    @GetMapping("/{product_id}")
    public ResponseEntity<Object> getProductById(@PathVariable("product_id") Long id) {
        Product product = productService.getProductById(id);
        ProductResult result = ProductResult.builder().product(product).build();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /**
     * 제품 사용 여부 수정
     */
    @PutMapping("/{product_id}")
    @PreAuthorize("hasRole('ROLE_ADMIN,ROLE_CEO,ROLE_MANAGER')")
    public ResponseEntity<Object> updateUseYn(@PathVariable("product_id") Long id, @RequestBody @Valid UpdateUseYnRequest request) {
        Product product = productService.updateProductUseYn(id, request);
        ProductResult result = ProductResult.builder().product(product).build();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
