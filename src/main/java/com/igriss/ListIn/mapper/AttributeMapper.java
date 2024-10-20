package com.igriss.ListIn.mapper;

import com.igriss.ListIn.dto.main_dto.AttributeDTO;
import com.igriss.ListIn.entity.Attribute;
import com.igriss.ListIn.entity.Post;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AttributeMapper {

    public List<AttributeDTO> toAttributeDTOs(List<Attribute> attributes) {
        return attributes.stream()
                .map(attribute -> AttributeDTO.builder()
                        .key(attribute.getKey())
                        .value(attribute.getValue())
                        .build())
                .collect(Collectors.toList());
    }

    public List<Attribute> toAttributeEntities(List<AttributeDTO> attributeDTOs, Post post) {
        return attributeDTOs.stream()
                .map(dto -> Attribute.builder()
                        .key(dto.getKey())
                        .value(dto.getValue())
                        .post(post)
                        .build()
                ).collect(Collectors.toList());
    }
}
