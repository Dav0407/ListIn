package com.igriss.ListIn.service_impl;

import com.igriss.ListIn.dto.main_dto.SubcategoryDTO;
import com.igriss.ListIn.dto.main_dto.SubcategoryRequestDTO;
import com.igriss.ListIn.entity.Category;
import com.igriss.ListIn.entity.Subcategory;
import com.igriss.ListIn.exceptions.ResourceNotFoundException;
import com.igriss.ListIn.mapper.SubcategoryMapper;
import com.igriss.ListIn.repository.CategoryRepository;
import com.igriss.ListIn.repository.SubcategoryRepository;
import com.igriss.ListIn.service.SubcategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubcategoryServiceImpl implements SubcategoryService {

    private final SubcategoryRepository subcategoryRepository;

    private final SubcategoryMapper subcategoryMapper;

    private final CategoryRepository categoryRepository;

    @Override
    public SubcategoryDTO createSubcategory(SubcategoryRequestDTO subcategoryRequestDTO) {
        Category category = categoryRepository.findById(subcategoryRequestDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + subcategoryRequestDTO.getCategoryId()));

        Subcategory subcategory = subcategoryMapper.toSubcategoryEntity(subcategoryRequestDTO, category);
        Subcategory savedSubcategory = subcategoryRepository.save(subcategory);

        return subcategoryMapper.toSubcategoryDTO(savedSubcategory);
    }

    @Override
    public SubcategoryDTO updateSubcategory(UUID subcategoryId, SubcategoryRequestDTO subcategoryRequestDTO) {
        Subcategory subcategory = subcategoryRepository.findById(subcategoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Subcategory not found with ID: " + subcategoryId));

        Category category = categoryRepository.findById(subcategoryRequestDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + subcategoryRequestDTO.getCategoryId()));

        subcategoryMapper.updateSubcategoryEntity(subcategory, subcategoryRequestDTO, category);
        Subcategory updatedSubcategory = subcategoryRepository.save(subcategory);

        return subcategoryMapper.toSubcategoryDTO(updatedSubcategory);
    }

    @Override
    public SubcategoryDTO getSubcategoryById(UUID subcategoryId) {
        Subcategory subcategory = subcategoryRepository.findById(subcategoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Subcategory not found with ID: " + subcategoryId));
        return subcategoryMapper.toSubcategoryDTO(subcategory);
    }

    @Override
    public List<SubcategoryDTO> getAllSubcategories() {
        List<Subcategory> subcategories = subcategoryRepository.findAll();
        return subcategories.stream()
                .map(subcategoryMapper::toSubcategoryDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<SubcategoryDTO> getSubcategoriesByCategoryId(UUID categoryId) {
        List<Subcategory> subcategories = subcategoryRepository.findByCategoryId(categoryId);
        return subcategories.stream()
                .map(subcategoryMapper::toSubcategoryDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteSubcategory(UUID subcategoryId) {
        Subcategory subcategory = subcategoryRepository.findById(subcategoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Subcategory not found with ID: " + subcategoryId));
        subcategoryRepository.delete(subcategory);
    }
}
