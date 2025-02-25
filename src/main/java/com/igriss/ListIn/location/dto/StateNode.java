package com.igriss.ListIn.location.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StateNode {

    private String value;

    private List<CountyNode> counties;

    private List<CityNode> cities;

}
