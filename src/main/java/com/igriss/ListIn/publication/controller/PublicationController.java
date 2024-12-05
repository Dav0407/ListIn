package com.igriss.ListIn.publication.controller;

import com.igriss.ListIn.publication.dto.PublicationRequestDTO;
import com.igriss.ListIn.publication.entity.Publication;
import com.igriss.ListIn.publication.repository.PublicationRepository;
import com.igriss.ListIn.publication.service.PublicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/publications")
@RequiredArgsConstructor
@Slf4j
public class PublicationController {

    private final PublicationService publicationService;
    private final PublicationRepository publicationRepository;

    @PostMapping
    public ResponseEntity<UUID> savePublication(@RequestBody PublicationRequestDTO request, Authentication connectedUser) {
        return ResponseEntity.status(HttpStatus.CREATED).body(publicationService.savePublication(request, connectedUser));
    }



    @GetMapping("/{publicationId}") // todo -> to be modified, this is for test used only !
    public ResponseEntity<Publication> getPublicationById(@PathVariable UUID publicationId) {
        return ResponseEntity.ok(publicationRepository.findById(publicationId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }
}
