package com.igriss.ListIn.publication.mapper;

import com.igriss.ListIn.publication.dto.CategoryResponseDTO;
import com.igriss.ListIn.publication.entity.Category;
import org.springframework.stereotype.Service;

@Service
public class CategoryMapper {

    public Category toCategory(CategoryResponseDTO categoryDTO) {
        return Category.builder()
                .name(categoryDTO.getName())
                .parentCategory(categoryDTO.getParentCategory() != null ? toCategory(categoryDTO.getParentCategory()) : null)
                .build();
    }

    public CategoryResponseDTO toCategoryResponseDTO(Category category) {
        return CategoryResponseDTO.builder()
                .name(category.getName())
                .parentCategory(category.getParentCategory() != null ? toCategoryResponseDTO(category.getParentCategory()) : null)
                .build();
    }
}
