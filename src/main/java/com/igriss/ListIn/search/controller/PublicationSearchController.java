package com.igriss.ListIn.search.controller;


import com.igriss.ListIn.exceptions.SearchQueryException;
import com.igriss.ListIn.publication.dto.PublicationResponseDTO;
import com.igriss.ListIn.publication.dto.page.PageResponse;
import com.igriss.ListIn.search.dto.FoundPublicationsDTO;
import com.igriss.ListIn.search.dto.InputPredictionResponseDTO;
import com.igriss.ListIn.search.service.InputPredictionService;
import com.igriss.ListIn.search.service.PublicationSearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Slf4j
@RestController
@RequestMapping("/api/v1/publications/search")
@RequiredArgsConstructor
public class PublicationSearchController {


    private final PublicationSearchService searchService;
    private final InputPredictionService inputPredictionService;

    @Operation(summary = "${search-controller.deepSearch.summary}", description = "${search-controller.deepSearch.description}")
    @GetMapping(value = {"", "/{pCategory}", "/{pCategory}/{category}"}, produces = "application/json")
    public PageResponse<PublicationResponseDTO> deepSearch(@PathVariable(required = false) UUID pCategory,
                                                   @PathVariable(required = false) UUID category,
                                                   @RequestParam(required = false) String query,
                                                   @RequestParam(defaultValue = "0") Integer page,
                                                   @RequestParam(defaultValue = "5") Integer size,
                                                   @RequestParam(required = false) Boolean bargain,
                                                   @RequestParam(value = "condition", required = false) String productCondition,
                                                   @RequestParam(required = false) Float from,
                                                   @RequestParam(required = false) Float to,
                                                   @RequestParam(required = false) String locationName,
                                                   @RequestParam(required = false) Boolean isFree,
                                                   @RequestParam(required = false) String sellerType,
                                                   @RequestParam(required = false) String searchText,
                                                   @RequestParam(required = false) String locationIds,
                                                   @RequestParam(value = "filter", required = false) List<String> filters,
                                                   @RequestParam(value = "numeric", required = false) List<String> numericFilter,
                                                   Authentication connectedUser
    ) throws SearchQueryException {
        return searchService.searchWithAdvancedFilter(pCategory, category, query, page, size, bargain, productCondition, from, to, locationName, isFree, sellerType, locationIds, searchText, filters, numericFilter, connectedUser);
    }

    @Operation(summary = "${search-controller.inputPrediction.summary}", description = "${search-controller.inputPrediction.description}")
    @GetMapping(value = "/input-predict", produces = "application/json")
    public ResponseEntity<List<InputPredictionResponseDTO>> inputPrediction(@RequestParam String query) throws SearchQueryException {
        return ResponseEntity.ok(inputPredictionService.getInputPredictions(query));
    }

    @Operation(summary = "${search-controller.elasticIndexation.summary}", description = "${search-controller.elasticIndexation.description}")
    @GetMapping(value = "/elastic/indexing-documents", produces = "application/text")
    public ResponseEntity<String> elasticIndexation() {
        return ResponseEntity.ok(inputPredictionService.indexInputPredictionDocuments());
    }

    @Operation(summary = "${publication-controller.get-latest.summary}", description = "${publication-controller.get-latest.description}")
    @GetMapping("/latest")
    public ResponseEntity<PageResponse<PublicationResponseDTO>> findAllLatestPublications(@RequestParam(required = false) Boolean bargain,
                                                                                          @RequestParam(value = "condition", required = false) String productCondition,
                                                                                          @RequestParam(required = false) Float from,
                                                                                          @RequestParam(required = false) Float to,
                                                                                          @RequestParam(required = false) String locationName,
                                                                                          @RequestParam(value = "filter", required = false) List<String> filters,
                                                                                          @RequestParam(value = "numeric", required = false) List<String> numericFilter,
                                                                                          @RequestParam(defaultValue = "0") Integer page,
                                                                                          @RequestParam(defaultValue = "5") Integer size,
                                                                                          Authentication connectedUser
    ) {
        return ResponseEntity.ok(searchService.findAllLatestPublications(page, size, bargain, productCondition, from, to, locationName, filters, numericFilter, connectedUser));
    }

    @GetMapping(value = {"/count", "/count/{pCategory}", "/count/{pCategory}/{category}"}, produces = "application/json")
    public ResponseEntity<FoundPublicationsDTO> getFoundPublicationsCount(@PathVariable(required = false) UUID pCategory,
                                                                          @PathVariable(required = false) UUID category,
                                                                          @RequestParam(required = false) String query,
                                                                          @RequestParam(required = false) Boolean bargain,
                                                                          @RequestParam(value = "condition", required = false) String productCondition,
                                                                          @RequestParam(required = false) Float from,
                                                                          @RequestParam(required = false) Float to,
                                                                          @RequestParam(required = false) String locationName,
                                                                          @RequestParam(required = false) Boolean isFree,
                                                                          @RequestParam(required = false) String sellerType,
                                                                          @RequestParam(required = false) String locationIds,
                                                                          @RequestParam(value = "filter", required = false) List<String> filters,
                                                                          @RequestParam(value = "numeric", required = false) List<String> numericFilter,
                                                                          @RequestParam(defaultValue = "0") Integer page,
                                                                          @RequestParam(defaultValue = "5") Integer size
    ) throws SearchQueryException {
        return ResponseEntity.ok(searchService.getPublicationsCount(pCategory, category, query, page, size, bargain,
                productCondition, from, to, locationName, isFree, sellerType, locationIds, filters, numericFilter));
    }

    @GetMapping(value = "/last-queried", produces = "application/json")
    public ResponseEntity<List<String>> getLastQueriedValues(Authentication connectedUser) {
        return ResponseEntity.of(Optional.ofNullable(searchService.getLastQueriedValues(connectedUser)));
    }

}
