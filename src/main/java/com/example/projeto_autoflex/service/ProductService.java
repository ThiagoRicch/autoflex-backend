package com.example.projeto_autoflex.service;

import com.example.projeto_autoflex.dto.request.ProductRequest;
import com.example.projeto_autoflex.dto.response.ProductResponse;
import com.example.projeto_autoflex.entity.Product;
import com.example.projeto_autoflex.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository
                          ) {
        this.productRepository = productRepository;

    }

    public ProductResponse create(ProductRequest request) {
        Product product = new Product();
        product.setName(request.name());
        product.setValue(request.value());
        product.setSize(request.size());

        Product saved = productRepository.save(product);

        return new ProductResponse(
                saved.getId(),
                saved.getName(),
                saved.getValue(),
                saved.getSize()
        );
    }

    public List<ProductResponse> findAll() {
        return productRepository.findAll()
                .stream()
                .map(product -> new ProductResponse(
                        product.getId(),
                        product.getName(),
                        product.getValue(),
                        product.getSize()
                ))
                .collect(Collectors.toList());
    }

    public ProductResponse update(Long id, ProductRequest request) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setName(request.name());
        product.setValue(request.value());
        product.setSize(request.size());

        Product updated = productRepository.save(product);

        return new ProductResponse(
                updated.getId(),
                updated.getName(),
                updated.getValue(),
                updated.getSize()
        );
    }

    public void delete(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        productRepository.delete(product);
    }

}