package com.igriss.ListIn.publication.dto.category_tree;

import com.igriss.ListIn.publication.dto.GroupedAttributeDTO;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChildNode implements Serializable {
    private UUID id;
    private String name;
    private String description;
    private String logoUrl;
    private List<GroupedAttributeDTO> attributes;
}
