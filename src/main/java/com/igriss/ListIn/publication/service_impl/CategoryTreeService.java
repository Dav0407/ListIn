package com.igriss.ListIn.publication.service_impl;

import com.igriss.ListIn.publication.dto.GroupedAttributeDTO;
import com.igriss.ListIn.publication.dto.NumericFieldDTO;
import com.igriss.ListIn.publication.dto.category_tree.ChildNode;
import com.igriss.ListIn.publication.dto.category_tree.ParentNode;
import com.igriss.ListIn.publication.entity.NumericField;
import com.igriss.ListIn.publication.entity.static_entity.Category;
import com.igriss.ListIn.publication.repository.CategoryRepository;
import com.igriss.ListIn.publication.repository.NumericFieldRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import org.springframework.cache.annotation.Cacheable;

@Service
@RequiredArgsConstructor
public class CategoryTreeService {

    private final CategoryRepository categoryRepository;
    private final CategoryAttributeService categoryAttributeService;

    private final NumericFieldRepository numericFieldRepository;

    @Cacheable(value = "categoryTreeCache")
    public List<ParentNode> getCategoryTree() {

        List<Category> allParentCategories = categoryRepository.findAllParentCategories();

        return allParentCategories.parallelStream().map(parent -> {

            List<Category> childCategories = categoryRepository.findAllByParentCategory_Id(parent.getId());

            List<ChildNode> childNodes = childCategories.parallelStream().map(child -> {
                List<GroupedAttributeDTO> attributePairs = categoryAttributeService.getGroupedAttributesByCategoryId(child.getId());

                return ChildNode.builder()
                        .id(child.getId())
                        .name(child.getName())
                        .nameUz(child.getNameUz())
                        .nameRu(child.getNameRu())
                        .description(child.getDescription())
                        .descriptionUz(child.getDescriptionUz())
                        .descriptionRu(child.getDescriptionRu())
                        .logoUrl(child.getImageUrl())
                        .attributes(attributePairs)
                        .numericFields(getNumericFields(child.getId()))
                        .build();
            }).toList();
            return ParentNode.builder()
                    .id(parent.getId())
                    .name(parent.getName())
                    .nameUz(parent.getNameUz())
                    .nameRu(parent.getNameRu())
                    .description(parent.getDescription())
                    .descriptionUz(parent.getDescriptionUz())
                    .descriptionRu(parent.getDescriptionRu())
                    .logoUrl(parent.getImageUrl())
                    .childCategories(childNodes)
                    .build();
        }).toList();
    }

    private List<NumericFieldDTO> getNumericFields(UUID categoryId){

        List<NumericField> numericFields = numericFieldRepository.findAllByCategory_Id(categoryId);

        return numericFields.stream().map(numericField -> NumericFieldDTO.builder()
                .id(numericField.getId())
                .fieldName(numericField.getFieldName())
                .description(numericField.getDescription())
                .descriptionUz(numericField.getDescriptionUz())
                .descriptionRu(numericField.getDescriptionRu())
                .build()
        ).toList();
    }
}
