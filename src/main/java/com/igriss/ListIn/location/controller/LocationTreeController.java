package com.igriss.ListIn.location.controller;

import com.igriss.ListIn.location.dto.LocationTreeNode;
import com.igriss.ListIn.location.service.LocationTreeService;
import com.igriss.ListIn.publication.dto.category_tree.ParentNode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/location-tree")
@RequiredArgsConstructor
public class LocationTreeController {

    private final LocationTreeService locationTreeService;

    @GetMapping()
    public ResponseEntity<LocationTreeNode> getCategoryTree() {
        return ResponseEntity.ok(locationTreeService.getLocationTree());
    }

}
