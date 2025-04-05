package com.igriss.ListIn.publication.dto;


import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NumericFieldDTO implements Serializable {

    private UUID id;

    private String fieldName;

    private String fieldNameUz;

    private String fieldNameRu;

    private String description;

    private String descriptionUz;

    private String descriptionRu;
}
