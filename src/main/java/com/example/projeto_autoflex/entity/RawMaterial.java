package com.example.projeto_autoflex.entity;

import com.example.projeto_autoflex.enums.Weight;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Entity
@Getter @Setter
@Table(name = "rawmaterials")
public class RawMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private BigDecimal stockQuantity;
    @Enumerated(EnumType.STRING)
    private Weight weight;
}
