package com.igriss.ListIn.publication.dto.category_tree;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParentNode {
    private UUID id;
    private String name;
    private String description;
    private List<ChildNode> childCategories;
}
