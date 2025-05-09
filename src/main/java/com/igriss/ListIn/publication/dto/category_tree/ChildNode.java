package com.igriss.ListIn.publication.dto.category_tree;

import com.igriss.ListIn.publication.dto.GroupedAttributeDTO;
import com.igriss.ListIn.publication.dto.NumericFieldDTO;
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
    private String nameUz;
    private String nameRu;
    private String description;
    private String descriptionUz;
    private String descriptionRu;
    private String logoUrl;
    private List<GroupedAttributeDTO> attributes;
    private List<NumericFieldDTO> numericFields;
}
