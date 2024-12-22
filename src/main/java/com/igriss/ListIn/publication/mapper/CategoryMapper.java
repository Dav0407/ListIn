package com.igriss.ListIn.publication.mapper;

import com.igriss.ListIn.publication.dto.CategoryDTO;
import com.igriss.ListIn.publication.entity.static_entity.Category;
import com.igriss.ListIn.publication.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryMapper {

    private final CategoryRepository categoryRepository;

    public Category toCategory(UUID categoryId) {

        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
    }

    public CategoryDTO toCategoryResponseDTO(Category category) {

        return CategoryDTO.builder()
                .name(category.getName())
                .parentCategory(category.getParentCategory().getName())
                .build();
    }
}
