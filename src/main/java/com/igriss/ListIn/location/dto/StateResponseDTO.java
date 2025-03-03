package com.igriss.ListIn.location.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StateResponseDTO {
    private String valueEng;
    private String valueUz;
    private String valueRu;
}
