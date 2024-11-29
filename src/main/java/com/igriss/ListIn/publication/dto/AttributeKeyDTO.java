package com.igriss.ListIn.publication.dto;

import lombok.*;

@Getter
@Setter
@Builder//todo -> add a jakarta validation to each field
@AllArgsConstructor
@NoArgsConstructor
public class AttributeKeyDTO {
    private String name;
}
