package com.igriss.ListIn.location.controller;

import com.igriss.ListIn.location.dto.node.LocationTreeNode;
import com.igriss.ListIn.location.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/location-tree")
@RequiredArgsConstructor
public class LocationTreeController {

    private final LocationService locationService;

    @GetMapping()
    public ResponseEntity<LocationTreeNode> getCategoryTree() {
        return ResponseEntity.ok(locationService.getLocationTree());
    }

}
