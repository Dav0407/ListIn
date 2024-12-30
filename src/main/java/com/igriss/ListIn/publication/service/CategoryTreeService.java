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

        return allParentCategories.parallelStream().map(parent -> {

            List<Category> childCategories = categoryRepository.findAllByParentCategory_Id(parent.getId());

            List<ChildNode> childNodes = childCategories.parallelStream().map(child -> {
                List<GroupedAttributeDTO> attributePairs = categoryAttributeService.getGroupedAttributesByCategoryId(child.getId());

                return ChildNode.builder()
                        .id(child.getId())
                        .name(child.getName())
                        .description(child.getDescription())
                        .attributes(attributePairs)
                        .build();
            }).toList();
            return ParentNode.builder()
                    .id(parent.getId())
                    .name(parent.getName())
                    .description(parent.getDescription())
                    .childCategories(childNodes)
                    .build();
        }).toList();
    }
}
