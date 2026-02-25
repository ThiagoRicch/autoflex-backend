package com.example.projeto_autoflex;

import com.example.projeto_autoflex.dto.request.RawMaterialRequest;
import com.example.projeto_autoflex.entity.RawMaterial;
import com.example.projeto_autoflex.repository.RawMaterialRepository;
import com.example.projeto_autoflex.service.RawMaterialService;
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
class RawMaterialServiceTest {

    @Mock
    private RawMaterialRepository repository;

    @InjectMocks
    private RawMaterialService service;

    private RawMaterial rawMaterial;

    @BeforeEach
    void setup() {
        rawMaterial = new RawMaterial();
        rawMaterial.setId(1L);
        rawMaterial.setName("Steel");
        rawMaterial.setStockQuantity(BigDecimal.valueOf(100));
    }

    @Test
    void shouldCreateRawMaterialSuccessfully() {
        when(repository.save(any(RawMaterial.class))).thenReturn(rawMaterial);

        var response = service.create(
                new RawMaterialRequest("Steel", BigDecimal.valueOf(100)));

        assertEquals("Steel", response.name());
        verify(repository, times(1)).save(any(RawMaterial.class));
    }

    @Test
    void shouldReturnAllRawMaterials() {
        when(repository.findAll()).thenReturn(List.of(rawMaterial));

        var list = service.findAll();

        assertEquals(1, list.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void shouldUpdateRawMaterialSuccessfully() {
        when(repository.findById(1L)).thenReturn(Optional.of(rawMaterial));
        when(repository.save(any(RawMaterial.class))).thenReturn(rawMaterial);

        var response = service.update(
                1L,
                new RawMaterialRequest("Updated", BigDecimal.valueOf(200)));

        assertEquals("Updated", response.name());
        verify(repository, times(1)).save(any(RawMaterial.class));
    }

    @Test
    void shouldThrowExceptionWhenRawMaterialNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> service.update(1L,
                        new RawMaterialRequest("Test", BigDecimal.TEN)));
    }

    @Test
    void shouldDeleteRawMaterialSuccessfully() {
        when(repository.findById(1L)).thenReturn(Optional.of(rawMaterial));

        service.delete(1L);

        verify(repository, times(1)).delete(rawMaterial);
    }
}