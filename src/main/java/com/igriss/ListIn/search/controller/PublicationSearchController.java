package com.igriss.ListIn.search.controller;


import com.igriss.ListIn.exceptions.SearchQueryException;
import com.igriss.ListIn.publication.dto.PublicationResponseDTO;
import com.igriss.ListIn.publication.dto.page.PageResponse;
import com.igriss.ListIn.search.service.PublicationSearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
                                                       @RequestParam(required = false) Float from,
                                                       @RequestParam(required = false) Float to) throws SearchQueryException {
        return searchService.searchWithDefaultFilter(query, page, size,bargain,from,to);
    }

    @GetMapping("/search/all/{pCategory}/{category}")
    public PageResponse<PublicationResponseDTO> deepSearch(@PathVariable String pCategory,
                                                           @PathVariable String category,
                                                           @RequestParam("query") String query,
                                                           @RequestParam(defaultValue = "0") Integer page,
                                                           @RequestParam(defaultValue = "5") Integer size,
                                                           @RequestParam(required = false) Boolean bargain,
                                                           @RequestParam(required = false) Float from,
                                                           @RequestParam(required = false) Float to,
                                                           @RequestParam(value = "filter", required = false) List<String> filters
    ) throws SearchQueryException {
        return searchService.searchWithAdvancedFilter(pCategory, category, query, page, size, bargain, from, to, filters);
    }

}
