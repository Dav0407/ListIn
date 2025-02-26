package com.igriss.ListIn.location.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CountryNode implements Serializable {

    private String value;
    private String valueUz;
    private String valueRu;

    private List<StateNode> states;

}
