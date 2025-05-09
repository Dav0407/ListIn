package com.igriss.ListIn.location.service;

import com.igriss.ListIn.location.dto.LocationDTO;
import com.igriss.ListIn.location.dto.node.LocationTreeNode;
import com.igriss.ListIn.security.security_dto.RegisterRequestDTO;

import java.util.Map;
import java.util.UUID;

public interface LocationService {
    LocationTreeNode getLocationTree();
    LocationDTO getLocation(String country, String state, String county, String language);
}