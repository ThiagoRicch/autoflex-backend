package com.example.projeto_autoflex.dto.request;

import com.example.projeto_autoflex.enums.Weight;

import java.math.BigDecimal;

public record RawMaterialRequest(
        String name,
        BigDecimal stockQuantity,
        Weight weight
) {
}
