package com.igriss.ListIn.service_impl;

import com.igriss.ListIn.dto.main_dto.CategoryDTO;
import com.igriss.ListIn.dto.main_dto.CategoryRequestDTO;
import com.igriss.ListIn.entity.Category;
import com.igriss.ListIn.exceptions.ResourceNotFoundException;
import com.igriss.ListIn.mapper.CategoryMapper;
import com.igriss.ListIn.repository.CategoryRepository;
import com.igriss.ListIn.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDTO createCategory(CategoryRequestDTO categoryRequestDTO) {
        Category category = categoryMapper.toCategoryEntity(categoryRequestDTO);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toCategoryDTO(savedCategory);
    }

    @Override
    public CategoryDTO updateCategory(UUID categoryId, CategoryRequestDTO categoryRequestDTO) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + categoryId));

        categoryMapper.updateCategoryEntity(category, categoryRequestDTO);
        Category updatedCategory = categoryRepository.save(category);

        return categoryMapper.toCategoryDTO(updatedCategory);
    }

    @Override
    public CategoryDTO getCategoryById(UUID categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + categoryId));
        return categoryMapper.toCategoryDTO(category);
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(categoryMapper::toCategoryDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCategory(UUID categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + categoryId));
        categoryRepository.delete(category);
    }
}
