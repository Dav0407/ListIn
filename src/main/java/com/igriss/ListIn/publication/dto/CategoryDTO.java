package com.igriss.ListIn.publication.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder//todo -> add a jakarta validation to each field
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO implements Serializable {

    private String name;

    private String parentCategory;
}
