package com.igriss.ListIn.location.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StateNode  implements Serializable {

    private String value;
    private String valueUz;
    private String valueRu;

    private List<CountyNode> counties;

    private List<CityNode> cities;

}
