package com.igriss.ListIn.search.controller;


import com.igriss.ListIn.exceptions.SearchQueryException;
import com.igriss.ListIn.publication.dto.PublicationResponseDTO;
import com.igriss.ListIn.publication.dto.page.PageResponse;
import com.igriss.ListIn.search.dto.InputPredictionResponseDTO;
import com.igriss.ListIn.search.dto.PublicationNode;
import com.igriss.ListIn.search.service.InputPredictionService;
import com.igriss.ListIn.search.service.PublicationSearchService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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

    @Operation(summary = "${search-controller.shallowSearch.summary}", description = "${search-controller.shallowSearch.description}")
    @GetMapping("/all")
    public List<PublicationNode> shallowSearch(@RequestParam("query") String query,
                                               @RequestParam(defaultValue = "0") Integer page,
                                               @RequestParam(defaultValue = "5") Integer size,
                                               @RequestParam(required = false) Boolean bargain,
                                               @RequestParam(value = "condition", required = false) String productCondition,
                                               @RequestParam(required = false) Float from,
                                               @RequestParam(required = false) Float to,
                                               @RequestParam(required = false) String locationName
    ) throws SearchQueryException {
        return searchService.searchWithDefaultFilter(query, page, size, bargain, productCondition, from, to, locationName);
    }

    @Operation(summary = "${search-controller.deepSearch.summary}", description = "${search-controller.deepSearch.description}")
    @GetMapping("/all/{pCategory}/{category}")
    public List<PublicationNode> deepSearch(@PathVariable UUID pCategory,
                                            @PathVariable UUID category,
                                            @RequestParam(required = false) String query,
                                            @RequestParam(defaultValue = "0") Integer page,
                                            @RequestParam(defaultValue = "5") Integer size,
                                            @RequestParam(required = false) Boolean bargain,
                                            @RequestParam(value = "condition", required = false) String productCondition,
                                            @RequestParam(required = false) Float from,
                                            @RequestParam(required = false) Float to,
                                            @RequestParam(required = false) String locationName,
                                            @RequestParam(value = "filter", required = false) List<String> filters
    ) throws SearchQueryException {
        return searchService.searchWithAdvancedFilter(pCategory, category, query, page, size, bargain, productCondition, from, to, locationName, filters);
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

}
