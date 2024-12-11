package com.igriss.ListIn.publication.service;

import com.igriss.ListIn.publication.dto.GroupedAttributeDTO;
import com.igriss.ListIn.publication.dto.category_tree.ChildNode;
import com.igriss.ListIn.publication.dto.category_tree.ParentNode;
import com.igriss.ListIn.publication.entity.static_entity.Category;
import com.igriss.ListIn.publication.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryTreeService {

    private final CategoryRepository categoryRepository;
    private final CategoryAttributeService categoryAttributeService;

    public List<ParentNode> getCategoryTree() {

        List<Category> allParentCategories = categoryRepository.findAllParentCategories();

        List<ParentNode> parentNodes = new ArrayList<>();

        for (Category parentCategory : allParentCategories) {
            List<Category> childCategories = categoryRepository.findAllByParentCategory_Id(parentCategory.getId());

            List<ChildNode> childNodes = new ArrayList<>();

            for (Category childCategory : childCategories) {

                List<GroupedAttributeDTO> attributeKVPairs = categoryAttributeService.getGroupedAttributesByCategoryId(childCategory.getId());

                var childNode = ChildNode.builder()
                        .id(childCategory.getId())
                        .name(childCategory.getName())
                        .description(childCategory.getDescription())
                        .attributes(attributeKVPairs)
                        .build();

                childNodes.add(childNode);
            }

            var parentNode = ParentNode.builder()
                    .id(parentCategory.getId())
                    .name(parentCategory.getName())
                    .description(parentCategory.getDescription())
                    .childCategories(childNodes)
                    .build();
            parentNodes.add(parentNode);
        }

        return parentNodes;
    }
}
