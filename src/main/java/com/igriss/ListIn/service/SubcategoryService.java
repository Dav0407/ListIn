package com.igriss.ListIn.service;

import com.igriss.ListIn.dto.main_dto.SubcategoryDTO;
import com.igriss.ListIn.dto.main_dto.SubcategoryRequestDTO;

import java.util.List;
import java.util.UUID;

public interface SubcategoryService {
    SubcategoryDTO createSubcategory(SubcategoryRequestDTO subcategoryRequestDTO);
    SubcategoryDTO updateSubcategory(UUID subcategoryId, SubcategoryRequestDTO subcategoryRequestDTO);
    SubcategoryDTO getSubcategoryById(UUID subcategoryId);
    List<SubcategoryDTO> getAllSubcategories();
    List<SubcategoryDTO> getSubcategoriesByCategoryId(UUID categoryId);
    void deleteSubcategory(UUID subcategoryId);
}
