package com.example.projeto_autoflex;

import com.example.projeto_autoflex.entity.Product;
import com.example.projeto_autoflex.entity.ProductRawMaterial;
import com.example.projeto_autoflex.entity.RawMaterial;
import com.example.projeto_autoflex.repository.ProductRepository;
import com.example.projeto_autoflex.repository.RawMaterialRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ProductionServiceIntegrationTest {

    @Autowired
    private ProductionService productionService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RawMaterialRepository rawMaterialRepository;

    @Autowired
    private ProductRawMaterialRepository productRawMaterialRepository;

    private Product createProduct(String name, BigDecimal value) {
        Product product = new Product();
        product.setName(name);
        product.setValue(value);
        return productRepository.save(product);
    }

    private RawMaterial createRawMaterial(String name, BigDecimal stock) {
        RawMaterial rawMaterial = new RawMaterial();
        rawMaterial.setName(name);
        rawMaterial.setStockQuantity(stock);
        return rawMaterialRepository.save(rawMaterial);
    }

    private void link(Product product, RawMaterial rawMaterial, BigDecimal quantity) {
        ProductRawMaterial prm = new ProductRawMaterial();
        prm.setProduct(product);
        prm.setRawMaterial(rawMaterial);
        prm.setRequiredQuantity(quantity);
        productRawMaterialRepository.save(prm);
    }

    @Test
    void shouldReturnEmptyWhenNoProducts() {
        var result = productionService.getProductionSuggestion();

        assertNotNull(result);
        assertTrue(result.getItems().isEmpty());
        assertEquals(0, result.getTotalProductionValue().compareTo(BigDecimal.ZERO));
    }

    @Test
    void shouldIgnoreProductWithoutRawMaterial() {

        createProduct("Table", BigDecimal.valueOf(100));

        var result = productionService.getProductionSuggestion();

        assertTrue(result.getItems().isEmpty());
    }

    @Test
    void shouldCalculateProductionCorrectly() {

        Product product = createProduct("Table", BigDecimal.valueOf(100));
        RawMaterial wood = createRawMaterial("Wood", BigDecimal.valueOf(10));

        link(product, wood, BigDecimal.valueOf(2));

        var result = productionService.getProductionSuggestion();

        assertEquals(1, result.getItems().size());

        var item = result.getItems().get(0);

        assertEquals(0, item.getQuantityToProduce().compareTo(BigDecimal.valueOf(5)));
        assertEquals(0, item.getTotalValue().compareTo(BigDecimal.valueOf(500)));
    }

    @Test
    void shouldRespectMinimumStockRule() {

        Product product = createProduct("Table", BigDecimal.valueOf(100));

        RawMaterial wood = createRawMaterial("Wood", BigDecimal.valueOf(10));
        RawMaterial screw = createRawMaterial("Screw", BigDecimal.valueOf(3));

        link(product, wood, BigDecimal.valueOf(2));  // permitiria 5
        link(product, screw, BigDecimal.valueOf(1)); // permite só 3

        var result = productionService.getProductionSuggestion();

        var item = result.getItems().get(0);

        assertEquals(0, item.getQuantityToProduce().compareTo(BigDecimal.valueOf(3)));
    }

    @Test
    void shouldPrioritizeHigherValueProduct() {

        Product cheap = createProduct("Chair", BigDecimal.valueOf(50));
        Product expensive = createProduct("Luxury Table", BigDecimal.valueOf(500));

        RawMaterial wood = createRawMaterial("Wood", BigDecimal.valueOf(10));

        link(cheap, wood, BigDecimal.valueOf(1));
        link(expensive, wood, BigDecimal.valueOf(5));

        var result = productionService.getProductionSuggestion();

        assertFalse(result.getItems().isEmpty());

        var firstItem = result.getItems().get(0);

        assertEquals("Luxury Table", firstItem.getProductName());
    }
}