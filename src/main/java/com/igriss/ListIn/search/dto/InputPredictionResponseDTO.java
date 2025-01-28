package com.igriss.ListIn.search.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Builder
public class InputPredictionResponseDTO implements Serializable {

    private String model;

    private UUID parentCategoryId;

    private UUID categoryId;

}
