package com.igriss.ListIn.publication.controller;

import com.igriss.ListIn.publication.dto.PublicationRequestDTO;
import com.igriss.ListIn.publication.dto.PublicationResponseDTO;
import com.igriss.ListIn.publication.service.PublicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/publications")
@RequiredArgsConstructor
public class PublicationController {

    private final PublicationService publicationService;
    //todo -> add a service layer
    @PostMapping()
    public ResponseEntity<Void> savePublication(@RequestBody PublicationRequestDTO request, Authentication connectedUser) {
         publicationService.savePublication(request, connectedUser);
         return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
