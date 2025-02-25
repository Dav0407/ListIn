package com.igriss.ListIn.location.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CountryNode {

    private String value;

    private List<StateNode> states;

}
