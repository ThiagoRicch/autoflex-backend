package com.example.projeto_autoflex.service;

import com.example.projeto_autoflex.dto.request.RawMaterialRequest;
import com.example.projeto_autoflex.dto.response.RawMaterialResponse;
import com.example.projeto_autoflex.entity.RawMaterial;
import com.example.projeto_autoflex.repository.RawMaterialRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RawMaterialService {

    private final RawMaterialRepository rawMaterialRepository;

    public RawMaterialService(RawMaterialRepository rawMaterialRepository) {
        this.rawMaterialRepository = rawMaterialRepository;
    }

    public RawMaterialResponse create(RawMaterialRequest request) {

        RawMaterial rawMaterial = new RawMaterial();
        rawMaterial.setName(request.name());
        rawMaterial.setStockQuantity(request.stockQuantity());
        rawMaterial.setWeight(request.weight());

        RawMaterial saved = rawMaterialRepository.save(rawMaterial);

        return new RawMaterialResponse(
                saved.getId(),
                saved.getName(),
                saved.getStockQuantity(),
                saved.getWeight()
        );
    }

    public List<RawMaterialResponse> findAll() {
        return rawMaterialRepository.findAll()
                .stream()
                .map(r -> new RawMaterialResponse(
                        r.getId(),
                        r.getName(),
                        r.getStockQuantity(),
                        r.getWeight()
                ))
                .collect(Collectors.toList());
    }

    public RawMaterialResponse update(Long id, RawMaterialRequest request) {

        RawMaterial rawMaterial = rawMaterialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Raw material not found"));

        rawMaterial.setName(request.name());
        rawMaterial.setStockQuantity(request.stockQuantity());
        rawMaterial.setWeight(request.weight());

        RawMaterial updated = rawMaterialRepository.save(rawMaterial);

        return new RawMaterialResponse(
                updated.getId(),
                updated.getName(),
                updated.getStockQuantity(),
                updated.getWeight()
        );
    }

    public void delete(Long id) {
        RawMaterial rawMaterial = rawMaterialRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Raw material not found"));

        rawMaterialRepository.delete(rawMaterial);
    }
}