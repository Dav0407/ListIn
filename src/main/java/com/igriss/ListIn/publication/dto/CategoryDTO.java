package com.igriss.ListIn.publication.dto;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Builder//todo -> add a jakarta validation to each field
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO implements Serializable {

    private UUID id;

    private String name;

    private UUID parentCategoryId;
}
