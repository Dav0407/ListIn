package com.igriss.ListIn.search.controller;


import com.igriss.ListIn.exceptions.SearchQueryException;
import com.igriss.ListIn.publication.dto.PublicationResponseDTO;
import com.igriss.ListIn.publication.dto.page.PageResponse;
import com.igriss.ListIn.search.service.PublicationSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@Slf4j
@RestController
@RequestMapping("/api/v1/publications")
@RequiredArgsConstructor
public class PublicationSearchController {

    private final PublicationSearchService searchService;

    @GetMapping("/search/all")
    public PageResponse<PublicationResponseDTO> search(@RequestParam("query") String query,
                                                       @RequestParam(defaultValue = "0") Integer page,
                                                       @RequestParam(defaultValue = "5") Integer size,
                                                       @RequestParam(required = false) Boolean bargain,
                                                       @RequestParam(value = "condition", required = false) String productCondition,
                                                       @RequestParam(required = false) Float from,
                                                       @RequestParam(required = false) Float to) throws SearchQueryException {
        return searchService.searchWithDefaultFilter(query, page, size, bargain, productCondition, from, to);
    }

    @GetMapping("/search/all/{pCategory}/{category}")
    public PageResponse<PublicationResponseDTO> deepSearch(@PathVariable UUID pCategory,
                                                           @PathVariable UUID category,
                                                           @RequestParam("query") String query,
                                                           @RequestParam(defaultValue = "0") Integer page,
                                                           @RequestParam(defaultValue = "5") Integer size,
                                                           @RequestParam(required = false) Boolean bargain,
                                                           @RequestParam(value = "condition", required = false) String productCondition,
                                                           @RequestParam(required = false) Float from,
                                                           @RequestParam(required = false) Float to,
                                                           @RequestParam(value = "filter", required = false) List<String> filters
    ) throws SearchQueryException {
        return searchService.searchWithAdvancedFilter(pCategory, category, query, page, size, bargain, productCondition, from, to, filters);
    }

}
