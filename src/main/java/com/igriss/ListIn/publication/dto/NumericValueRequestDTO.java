package com.igriss.ListIn.publication.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NumericValueRequestDTO {
    private UUID numericFieldId;
    private String numericValue;
}
