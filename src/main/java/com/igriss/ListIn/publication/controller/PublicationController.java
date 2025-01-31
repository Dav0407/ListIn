package com.igriss.ListIn.publication.controller;

import com.igriss.ListIn.publication.dto.PublicationRequestDTO;
import com.igriss.ListIn.publication.dto.PublicationResponseDTO;
import com.igriss.ListIn.publication.dto.UpdatePublicationRequestDTO;
import com.igriss.ListIn.publication.dto.page.PageResponse;
import com.igriss.ListIn.publication.dto.user_publications.UserPublicationDTO;
import com.igriss.ListIn.publication.entity.Publication;
import com.igriss.ListIn.publication.entity.PublicationAttributeValue;
import com.igriss.ListIn.publication.repository.PublicationAttributeValueRepository;
import com.igriss.ListIn.publication.repository.PublicationRepository;
import com.igriss.ListIn.publication.service.PublicationService;
import com.igriss.ListIn.search.dto.PublicationNode;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "${publication-controller.save.summary}", description = "${publication-controller.save.description}")
    @PostMapping
    public ResponseEntity<UUID> savePublication(@RequestBody PublicationRequestDTO request, Authentication connectedUser) {
        return ResponseEntity.status(HttpStatus.CREATED).body(publicationService.savePublication(request, connectedUser));
    }

    @Operation(summary = "${publication-controller.user-publications.summary}", description = "${publication-controller.user-publications.description}")
    @GetMapping("/user-publications")
    public ResponseEntity<PageResponse<UserPublicationDTO>> getPublicationsOfUser(@RequestParam(name = "page", defaultValue = "0", required = false) int page,
                                                                                  @RequestParam(name = "size", defaultValue = "10", required = false) int size,
                                                                                  Authentication connectedUser) {
        return ResponseEntity.ok(publicationService.findAllByUser(page, size, connectedUser));
    }

    @Operation(summary = "${publication-controller.get-by-id.summary}", description = "${publication-controller.get-by-id.description}")
    @GetMapping("/{publicationId}") // todo -> to be modified, this is for test used only !
    public ResponseEntity<Publication> getPublicationById(@PathVariable UUID publicationId) {
        return ResponseEntity.ok(publicationRepository.findById(publicationId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    @GetMapping("/PAV/{publicationId}") // todo -> to be modified, this is for test used only !
    public ResponseEntity<List<PublicationAttributeValue>> getPAV(@PathVariable UUID publicationId) {
        return ResponseEntity.ok(repo.findByPublication_Id(publicationId));
    }

    @Operation(summary = "${publication-controller.get-latest.summary}", description = "${publication-controller.get-latest.description}")
    @GetMapping
    public ResponseEntity<List<PublicationNode>> findAllLatestPublications(@RequestParam(name = "page", defaultValue = "0", required = false) int page,
                                                                           @RequestParam(name = "size", defaultValue = "10", required = false) int size,
                                                                           Authentication connectedUser
    ) {
        return ResponseEntity.ok(publicationService.findAllLatestPublications(page, size, connectedUser));
    }

    @Operation(summary = "${publication-controller.update.summary}", description = "${publication-controller.update.description}")
    @PatchMapping("/update/{publicationId}")
    public ResponseEntity<PublicationResponseDTO> updatePublication(@PathVariable UUID publicationId, @RequestBody UpdatePublicationRequestDTO updatePublication, Authentication authentication) {
        return ResponseEntity.ok(publicationService.updateUserPublication(publicationId, updatePublication, authentication));
    }

    @Operation(summary = "${publication-controller.find-by-user.summary}", description = "${publication-controller.find-by-user.description}")
    @GetMapping("/user/{userId}")
    public PageResponse<PublicationResponseDTO> findByUser(@PathVariable UUID userId,
                                                           @RequestParam(name = "page", defaultValue = "0", required = false) int page,
                                                           @RequestParam(name = "size", defaultValue = "10", required = false) int size,
                                                           Authentication connectedUser) {
        return publicationService.findAllByUserId(userId, page, size, connectedUser);
    }

    @Operation(summary = "${publication-controller.parent-category.summary}", description = "${publication-controller.parent-category.description}")
    @GetMapping("/p/{pCategory}")
    public List<PublicationNode> parentCategorySearch(@PathVariable UUID pCategory,
                                                      @RequestParam(defaultValue = "0") Integer page,
                                                      @RequestParam(defaultValue = "5") Integer size,
                                                      Authentication connectedUser
    ) {
        return publicationService.findWithParentCategory(pCategory, page, size, connectedUser);
    }

    @Operation(summary = "${publication-controller.videos.summary}", description = "${publication-controller.videos.description}")
    @GetMapping("/videos")
    public PageResponse<PublicationResponseDTO> getVideos(@RequestParam(name = "page", defaultValue = "0", required = false) int page,
                                                          @RequestParam(name = "size", defaultValue = "10", required = false) int size,
                                                          Authentication connectedUser
    ) {
        return publicationService.findPublicationsContainingVideo(page, size, connectedUser);
    }

    @PatchMapping("/like/{publicationId}")
    public UUID likePublication(@PathVariable UUID publicationId, Authentication connectedUser) {
        return publicationService.likePublication(publicationId, connectedUser);
    }

    @GetMapping("/like")
    public PageResponse<PublicationResponseDTO> getLikedPublications(@RequestParam(defaultValue = "0") Integer page,
                                                                     @RequestParam(defaultValue = "5") Integer size,
                                                                     Authentication connectedUser) {
        return publicationService.findAllLikedPublications(page, size, connectedUser);
    }
}
