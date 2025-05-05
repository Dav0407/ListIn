package com.igriss.ListIn.location.dto.node;

import lombok.*;

import java.io.Serializable;

import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CountyNode  implements Serializable {

    private UUID countyId;
    private String value;
    private String valueUz;
    private String valueRu;
}
