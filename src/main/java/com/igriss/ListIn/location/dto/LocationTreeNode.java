package com.igriss.ListIn.location.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LocationTreeNode {
    private List<CountryNode> countries;
}
