package com.igriss.ListIn.location.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CountyDTO {
    private UUID countyId;
    private String value;
    private String valueUz;
    private String valueRu;
}
