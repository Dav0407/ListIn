package com.igriss.ListIn.location.dto.node;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CountyNode  implements Serializable {
    private String value;
    private String valueUz;
    private String valueRu;
}
