package com.igriss.ListIn.publication.mapper;

import com.igriss.ListIn.publication.dto.CategoryResponseDTO;
import com.igriss.ListIn.publication.entity.static_entity.Category;
import com.igriss.ListIn.publication.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryMapper {

    private final CategoryRepository categoryRepository;

    public Category toCategory(CategoryResponseDTO categoryDTO) {

        UUID categoryId = categoryRepository.getIdByName(categoryDTO.getName())
                .orElseThrow(()->new RuntimeException("No category found in Database"));

        Category parentCategory = categoryRepository.findByName(categoryDTO.getParentCategory());
        return Category.builder()
                .id(categoryId)
                .name(categoryDTO.getName())
                .parentCategory(parentCategory)//to prevent NullPointerException
                .build();
    }

    public CategoryResponseDTO toCategoryResponseDTO(Category category) {

        return CategoryResponseDTO.builder()
                .name(category.getName())
                .parentCategory(category.getParentCategory() != null ? category.getParentCategory().getName() : null)//to prevent NullPointerException
                .build();
    }
}
