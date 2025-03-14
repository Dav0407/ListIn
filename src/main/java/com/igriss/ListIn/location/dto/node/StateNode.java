package com.igriss.ListIn.location.dto.node;

import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StateNode  implements Serializable {

    private UUID stateId;
    private String value;
    private String valueUz;
    private String valueRu;

    private List<CountyNode> counties;
}
