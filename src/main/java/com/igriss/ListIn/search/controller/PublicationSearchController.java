package com.igriss.ListIn.search.controller;


import com.igriss.ListIn.exceptions.SearchQueryException;
import com.igriss.ListIn.search.dto.FoundPublicationsDTO;
import com.igriss.ListIn.search.dto.InputPredictionResponseDTO;
import com.igriss.ListIn.search.dto.PublicationNode;
import com.igriss.ListIn.search.service.InputPredictionService;
import com.igriss.ListIn.search.service.PublicationSearchService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@Slf4j
@RestController
@RequestMapping("/api/v1/publications/search")
@RequiredArgsConstructor
public class PublicationSearchController {


    private final PublicationSearchService searchService;
    private final InputPredictionService inputPredictionService;

    @Operation(summary = "${search-controller.deepSearch.summary}", description = "${search-controller.deepSearch.description}")
    @GetMapping({"/",  "/{pCategory}", "/{pCategory}/{category}"})
    public List<PublicationNode> deepSearch(@PathVariable UUID pCategory,
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
                                            @RequestParam(value = "filter", required = false) List<String> filters,
                                            @RequestParam(value = "numeric", required = false) List<String> numericFilter,
                                            Authentication connectedUser
    ) throws SearchQueryException {
        return searchService.searchWithAdvancedFilter(pCategory, category, query, page, size, bargain, productCondition, from, to, locationName, isFree, sellerType, filters, numericFilter, connectedUser);
    }

    @Operation(summary = "${search-controller.inputPrediction.summary}", description = "${search-controller.inputPrediction.description}")
    @GetMapping("/input-predict")
    public ResponseEntity<List<InputPredictionResponseDTO>> inputPrediction(@RequestParam String query) throws SearchQueryException {
        return ResponseEntity.ok(inputPredictionService.getInputPredictions(query));
    }

    @Operation(summary = "${search-controller.elasticIndexation.summary}", description = "${search-controller.elasticIndexation.description}")
    @GetMapping("/elastic/indexing-documents")
    public ResponseEntity<String> elasticIndexation() {
        return ResponseEntity.ok(inputPredictionService.indexInputPredictionDocuments());
    }

    @Operation(summary = "${publication-controller.get-latest.summary}", description = "${publication-controller.get-latest.description}")
    @GetMapping("/latest")
    public ResponseEntity<List<PublicationNode>> findAllLatestPublications(@RequestParam(required = false) Boolean bargain,
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

    @GetMapping({"/count", "/count/{pCategory}", "/count/{pCategory}/{category}"})
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
                                                                          @RequestParam(value = "filter", required = false) List<String> filters,
                                                                          @RequestParam(value = "numeric", required = false) List<String> numericFilter,
                                                                          @RequestParam(defaultValue = "0") Integer page,
                                                                          @RequestParam(defaultValue = "5") Integer size
    ) throws SearchQueryException {
        return ResponseEntity.ok(searchService.getPublicationsCount(pCategory, category, query, page, size, bargain,
                productCondition, from, to, locationName, isFree, sellerType, filters, numericFilter));
    }

}
