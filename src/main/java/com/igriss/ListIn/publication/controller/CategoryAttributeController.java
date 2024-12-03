package com.igriss.ListIn.publication.controller;

import com.igriss.ListIn.publication.dto.GroupedAttributeDTO;
import com.igriss.ListIn.publication.service.CategoryAttributeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryAttributeController {

    private final CategoryAttributeService service;

    @GetMapping("/{categoryId}")
    public ResponseEntity<List<GroupedAttributeDTO>> getGroupedAttributesByCategory(@PathVariable UUID categoryId) {
        List<GroupedAttributeDTO> attributes = service.getGroupedAttributesByCategoryId(categoryId);
        return ResponseEntity.ok(attributes);
    }
}
