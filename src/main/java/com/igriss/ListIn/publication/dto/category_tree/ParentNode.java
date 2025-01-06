package com.igriss.ListIn.publication.dto.category_tree;

import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ParentNode implements Serializable {
    private UUID id;
    private String name;
    private String description;
    private List<ChildNode> childCategories;
}
