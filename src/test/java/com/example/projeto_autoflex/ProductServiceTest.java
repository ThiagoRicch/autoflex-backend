package com.example.projeto_autoflex;

import com.example.projeto_autoflex.dto.request.ProductRequest;
import com.example.projeto_autoflex.entity.Product;
import com.example.projeto_autoflex.repository.ProductRepository;
import com.example.projeto_autoflex.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductService service;

    private Product product;

    @BeforeEach
    void setup() {
        product = new Product();
        product.setId(1L);
        product.setName("Notebook");
        product.setValue(BigDecimal.valueOf(5000));
    }

    @Test
    void shouldCreateProductSuccessfully() {
        when(repository.save(any(Product.class))).thenReturn(product);

        var response = service.create(
                new ProductRequest("Notebook", BigDecimal.valueOf(5000)));

        assertEquals("Notebook", response.name());
        verify(repository, times(1)).save(any(Product.class));
    }

    @Test
    void shouldReturnAllProducts() {
        when(repository.findAll()).thenReturn(List.of(product));

        var list = service.findAll();

        assertEquals(1, list.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void shouldUpdateProductSuccessfully() {
        when(repository.findById(1L)).thenReturn(Optional.of(product));
        when(repository.save(any(Product.class))).thenReturn(product);

        var response = service.update(
                1L,
                new ProductRequest("Updated", BigDecimal.valueOf(6000)));

        assertEquals("Updated", response.name());
        verify(repository, times(1)).save(any(Product.class));
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> service.update(1L,
                        new ProductRequest("Test", BigDecimal.TEN)));
    }

    @Test
    void shouldDeleteProductSuccessfully() {
        when(repository.findById(1L)).thenReturn(Optional.of(product));

        service.delete(1L);

        verify(repository, times(1)).delete(product);
    }
}