package com.igriss.ListIn.location.service;

import com.igriss.ListIn.location.dto.LocationTreeNode;
import com.igriss.ListIn.publication.dto.PublicationRequestDTO;
import com.igriss.ListIn.security.security_dto.RegisterRequestDTO;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface LocationService {

    LocationTreeNode getLocationTree();

    Map<String, UUID> getMapIds(RegisterRequestDTO request);
}