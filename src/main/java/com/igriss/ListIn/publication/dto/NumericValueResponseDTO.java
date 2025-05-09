package com.igriss.ListIn.publication.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NumericValueResponseDTO {
    private String numericField;
    private String numericFieldUz;
    private String numericFieldRu;
    private Long numericValue;
}
