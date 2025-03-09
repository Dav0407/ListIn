package com.igriss.ListIn.location.dto.node;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class LocationTreeNode implements Serializable {
    private List<CountryNode> countries;
}
