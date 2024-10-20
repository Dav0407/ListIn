package com.igriss.ListIn.service;

import com.igriss.ListIn.dto.main_dto.CategoryDTO;
import com.igriss.ListIn.dto.main_dto.CategoryRequestDTO;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    CategoryDTO createCategory(CategoryRequestDTO categoryRequestDTO);
    CategoryDTO updateCategory(UUID categoryId, CategoryRequestDTO categoryRequestDTO);
    CategoryDTO getCategoryById(UUID categoryId);
    List<CategoryDTO> getAllCategories();
    void deleteCategory(UUID categoryId);
}
