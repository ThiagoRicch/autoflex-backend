package com.example.projeto_autoflex.repository;

import com.example.projeto_autoflex.entity.RawMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RawMaterialRepository extends JpaRepository<RawMaterial,Long> {
}
