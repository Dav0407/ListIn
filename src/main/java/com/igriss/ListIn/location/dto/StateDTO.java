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
public class StateDTO {

    private UUID stateId;

    private String value;
    private String valueUz;
    private String valueRu;
}
