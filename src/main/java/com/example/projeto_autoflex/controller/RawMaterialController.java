package com.example.projeto_autoflex.controller;

import com.example.projeto_autoflex.dto.request.RawMaterialRequest;
import com.example.projeto_autoflex.dto.response.RawMaterialResponse;
import com.example.projeto_autoflex.service.RawMaterialService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/raw-materials")
@CrossOrigin(origins = "http://localhost:5173")
public class RawMaterialController {

    private final RawMaterialService rawMaterialService;

    public RawMaterialController(RawMaterialService rawMaterialService) {
        this.rawMaterialService = rawMaterialService;
    }

    @PostMapping
    public ResponseEntity<RawMaterialResponse> create(@RequestBody RawMaterialRequest rawMaterialRequest) {
        return ResponseEntity.ok(rawMaterialService.create(rawMaterialRequest));
    }
    @GetMapping
    public ResponseEntity<List<RawMaterialResponse>> getAllRawMaterials() {
        return ResponseEntity.ok(rawMaterialService.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<RawMaterialResponse> update(@PathVariable Long id,
                                                      @RequestBody RawMaterialRequest rawMaterialRequest) {
        return ResponseEntity.ok(rawMaterialService.update(id, rawMaterialRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        rawMaterialService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
