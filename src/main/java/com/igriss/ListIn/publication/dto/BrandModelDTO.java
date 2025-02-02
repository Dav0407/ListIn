package com.igriss.ListIn.publication.dto;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BrandModelDTO implements Serializable {
    private UUID modelId;
    private String name;
    private String nameUz;
    private String nameRu;
    private String attributeId;
}
