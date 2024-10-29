package com.igriss.ListIn.publication.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder//todo -> add a jakarta validation to each field
public class CategoryResponseDTO {

    private String name;

    private CategoryResponseDTO parentCategory;
}
