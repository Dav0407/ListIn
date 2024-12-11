package com.igriss.ListIn.publication.dto.category_tree;

import com.igriss.ListIn.publication.dto.GroupedAttributeDTO;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChildNode {
    private UUID id;
    private String name;
    private String description;
    private List<GroupedAttributeDTO> attributes;
}
