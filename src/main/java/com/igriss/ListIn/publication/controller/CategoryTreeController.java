package com.igriss.ListIn.publication.controller;

import com.igriss.ListIn.publication.dto.category_tree.ParentNode;
import com.igriss.ListIn.publication.service_impl.CategoryTreeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category-tree")
@RequiredArgsConstructor
public class CategoryTreeController {

    private final CategoryTreeService categoryTreeService;

    @GetMapping()
    public ResponseEntity<List<ParentNode>> getCategoryTree() {
        return ResponseEntity.ok(categoryTreeService.getCategoryTree());
    }
}
