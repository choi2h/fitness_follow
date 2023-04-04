package com.ffs.web.product.controller;

import com.ffs.domain.product.entity.Product;
import com.ffs.domain.product.service.ProductService;
import com.ffs.web.product.ProductResult;
import com.ffs.web.product.request.RegisterProductRequest;
import com.ffs.web.product.request.UpdateUseYnRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Object> registerNewProduct(@RequestBody RegisterProductRequest request) {
        Product product = productService.registerNewProduct(request);
        ProductResult result = ProductResult.builder().product(product).build();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/all/{branchId}")
    public ResponseEntity<Object> getAllProductByBranch(@PathVariable Long branchId) {
        List<Product> productList = productService.getAllProductByBranchId(branchId);
        ProductResult result = ProductResult.builder().productList(productList).build();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        ProductResult result = ProductResult.builder().product(product).build();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUseYn(@PathVariable Long id, @RequestBody UpdateUseYnRequest request) {
        Product product = productService.updateProductUseYn(id, request);
        ProductResult result = ProductResult.builder().product(product).build();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
