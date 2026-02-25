package com.example.projeto_autoflex.dto.response;

import com.example.projeto_autoflex.enums.Size;

import java.math.BigDecimal;

public record ProductResponse(
        Long id,
        String name,
        BigDecimal value,
        Size size
) {
}
