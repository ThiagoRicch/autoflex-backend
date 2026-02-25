package com.example.projeto_autoflex.dto.response;

import com.example.projeto_autoflex.enums.Weight;

import java.math.BigDecimal;

public record RawMaterialResponse(
        Long id,
        String name,
        BigDecimal stockQuantity,
        Weight weight
) {
}
