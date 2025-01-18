package com.igriss.ListIn.publication.controller;

import com.igriss.ListIn.publication.dto.PublicationRequestDTO;
import com.igriss.ListIn.publication.dto.PublicationResponseDTO;
import com.igriss.ListIn.publication.dto.UpdatePublicationRequestDTO;
import com.igriss.ListIn.publication.dto.user_publications.UserPublicationDTO;
import com.igriss.ListIn.publication.dto.page.PageResponse;
import com.igriss.ListIn.publication.entity.Publication;
import com.igriss.ListIn.publication.entity.PublicationAttributeValue;
import com.igriss.ListIn.publication.repository.PublicationAttributeValueRepository;
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

    private final PublicationAttributeValueRepository repo;

    @PostMapping
    public ResponseEntity<UUID> savePublication(@RequestBody PublicationRequestDTO request, Authentication connectedUser) {
        return ResponseEntity.status(HttpStatus.CREATED).body(publicationService.savePublication(request, connectedUser));
    }

    @GetMapping("/user-publications")
    public ResponseEntity<PageResponse<UserPublicationDTO>> getPublicationsOfUser(@RequestParam(name = "page", defaultValue = "0", required = false) int page,
                                                                                  @RequestParam(name = "size", defaultValue = "10", required = false) int size,
                                                                                  Authentication connectedUser) {
        return ResponseEntity.ok(publicationService.findAllByUser(page, size, connectedUser));
    }

    @GetMapping("/{publicationId}") // todo -> to be modified, this is for test used only !
    public ResponseEntity<Publication> getPublicationById(@PathVariable UUID publicationId) {
        return ResponseEntity.ok(publicationRepository.findById(publicationId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    @GetMapping("/PAV/{publicationId}") // todo -> to be modified, this is for test used only !
    public ResponseEntity<List<PublicationAttributeValue>> getPAV(@PathVariable UUID publicationId) {
        return ResponseEntity.ok(repo.findByPublication_Id(publicationId));
    }

    @GetMapping
    public ResponseEntity<PageResponse<PublicationResponseDTO>> findAllLatestPublications(@RequestParam(name = "page", defaultValue = "0", required = false) int page,
                                                                                          @RequestParam(name = "size", defaultValue = "10", required = false) int size
    ) {
        return ResponseEntity.ok(publicationService.findAllLatestPublications(page, size));
    }

    @PatchMapping("/update/{publicationId}")
    public ResponseEntity<PublicationResponseDTO> updatePublication(@PathVariable UUID publicationId, @RequestBody UpdatePublicationRequestDTO updatePublication){
        return ResponseEntity.ok(publicationService.updateUserPublication(publicationId,updatePublication));
    }
}
