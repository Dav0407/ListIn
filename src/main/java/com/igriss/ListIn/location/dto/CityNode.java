package com.igriss.ListIn.location.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CityNode  implements Serializable {
    private String value;
    private String valueUz;
    private String valueRu;
}
