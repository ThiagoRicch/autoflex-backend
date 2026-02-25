package com.example.projeto_autoflex.dto.request;


import com.example.projeto_autoflex.enums.Size;

import java.math.BigDecimal;

public record ProductRequest(
        String name,
        BigDecimal value,
        Size size
) {}

