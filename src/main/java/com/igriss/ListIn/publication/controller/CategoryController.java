package com.igriss.ListIn.publication.controller;

import com.igriss.ListIn.publication.entity.static_entity.Category;
import com.igriss.ListIn.publication.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class CategoryController {
    private final CategoryRepository categoryRepository;

    @GetMapping("/parents")
    public ResponseEntity<List<Category>> getParents() {
        return ResponseEntity.ok(categoryRepository.findAllParentCategories());
    }

    @GetMapping("/children/{id}")
    public ResponseEntity<List<Category>> getChildren(@PathVariable UUID id) {
        return ResponseEntity.ok(categoryRepository.findAllByParentCategory_Id(id));
    }
}
