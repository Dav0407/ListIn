package com.igriss.ListIn.mapper;

import com.igriss.ListIn.dto.main_dto.CategoryDTO;
import com.igriss.ListIn.dto.main_dto.CategoryRequestDTO;
import com.igriss.ListIn.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryDTO toCategoryDTO(Category category) {
        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .previewText(category.getPreviewText())
                .imagePath(category.getImagePath())
                .build();
    }

    public Category toCategoryEntity(CategoryRequestDTO categoryRequestDTO) {
        return Category.builder()
                .name(categoryRequestDTO.getName())
                .previewText(categoryRequestDTO.getPreviewText())
                .imagePath(categoryRequestDTO.getImagePath())
                .build();
    }

    public void updateCategoryEntity(Category category, CategoryRequestDTO categoryRequestDTO) {
        category.setName(categoryRequestDTO.getName());
        category.setPreviewText(categoryRequestDTO.getPreviewText());
        category.setImagePath(categoryRequestDTO.getImagePath());
    }
}
