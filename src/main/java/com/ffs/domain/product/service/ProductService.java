package com.ffs.domain.product.service;

import com.ffs.common.exception.ServiceResultCodeException;
import com.ffs.domain.branch.BranchResultCode;
import com.ffs.domain.branch.entity.Branch;
import com.ffs.domain.branch.repository.BranchRepository;
import com.ffs.domain.product.ProductResultCode;
import com.ffs.domain.product.entity.Product;
import com.ffs.domain.product.repository.ProductRepository;
import com.ffs.web.product.request.RegisterProductRequest;
import com.ffs.web.product.request.UpdateUseYnRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final BranchRepository branchRepository;
    private final ProductRepository productRepository;

    public Product registerNewProduct(RegisterProductRequest request) {
        log.debug("Register new product. branchId={}, name={}", request.getBranchId(), request.getName());
        Product product = makeNewProduct(request);
        return productRepository.save(product);
    }

    public List<Product> getAllProductByBranchId(Long branchId) {
        List<Product> productList = productRepository.findAllByBranchId(branchId);

        if(productList.isEmpty()) {
            log.debug("Nothing registered product for branch. branchId={}", branchId);
            throw new ServiceResultCodeException(ProductResultCode.NOTHING_REGISTERED, branchId);
        }

        log.debug("Found product list by branchId. branchId={}", branchId);
        return productList;
    }

    public Product getProductById(Long id) {
        log.debug("Search product by id. id={}", id);
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isEmpty()) {
            log.debug("Not found pruduct. id={}", id);
            throw new ServiceResultCodeException(ProductResultCode.NOT_EXIST_PRODUCT, id);
        }

        return optionalProduct.get();
    }

    public Product updateProductUseYn(Long productId, UpdateUseYnRequest request) {
        log.debug("Update product use yn. productId={}, useYn={}", productId, request.getUseYn());
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if(optionalProduct.isEmpty()) {
            log.debug("Not found product. id={}", productId);
            throw new ServiceResultCodeException(ProductResultCode.NOT_EXIST_PRODUCT, productId);
        }

        Product product = optionalProduct.get();
        boolean isUse = request.getUseYn().equals("Y");
        product.updateUseYn(isUse);

        return product;
    }

    private Product makeNewProduct(RegisterProductRequest request) {
        Long branchId = request.getBranchId();
        Optional<Branch> optionalBranch = branchRepository.findById(branchId);
        if(optionalBranch.isEmpty()) {
            log.debug("Not exist branch. branchId={}", branchId);
            throw new ServiceResultCodeException(BranchResultCode.NOT_EXIST_BRANCH, branchId);
        }

        //TODO type 검사하기
        Branch branch = optionalBranch.get();
        return Product
                .builder()
                .branch(branch)
                .name(request.getName())
                .price(request.getPrice())
                .type(request.getType())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .build();

    }
}
