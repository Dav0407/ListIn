package com.igriss.ListIn.mapper;

import com.igriss.ListIn.dto.main_dto.SubcategoryDTO;
import com.igriss.ListIn.dto.main_dto.SubcategoryRequestDTO;
import com.igriss.ListIn.entity.Category;
import com.igriss.ListIn.entity.Subcategory;
import org.springframework.stereotype.Component;

@Component
public class SubcategoryMapper {

    public SubcategoryDTO toSubcategoryDTO(Subcategory subcategory) {
        return SubcategoryDTO.builder()
                .id(subcategory.getId())
                .name(subcategory.getName())
                .previewText(subcategory.getPreviewText())
                .imagePath(subcategory.getImagePath())
                .build();
    }

    public Subcategory toSubcategoryEntity(SubcategoryRequestDTO subcategoryRequestDTO, Category category) {
        return Subcategory.builder()
                .name(subcategoryRequestDTO.getName())
                .previewText(subcategoryRequestDTO.getPreviewText())
                .imagePath(subcategoryRequestDTO.getImagePath())
                .category(category)
                .build();
    }

    public void updateSubcategoryEntity(Subcategory subcategory, SubcategoryRequestDTO subcategoryRequestDTO, Category category) {
        subcategory.setName(subcategoryRequestDTO.getName());
        subcategory.setPreviewText(subcategoryRequestDTO.getPreviewText());
        subcategory.setImagePath(subcategoryRequestDTO.getImagePath());
        subcategory.setCategory(category);
    }
}
