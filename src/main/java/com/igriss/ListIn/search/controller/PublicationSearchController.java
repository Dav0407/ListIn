package com.igriss.ListIn.search.controller;


import com.igriss.ListIn.exceptions.SearchQueryException;
import com.igriss.ListIn.publication.dto.PublicationResponseDTO;
import com.igriss.ListIn.search.service.PublicationSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/v1/publications")
@RequiredArgsConstructor
public class PublicationSearchController {

    private final PublicationSearchService searchService;

    @GetMapping("/search")
    public List<PublicationResponseDTO> search(@RequestParam("query") String query,
                                               @RequestParam(defaultValue = "0") Integer page,
                                               @RequestParam(value = "size", defaultValue = "10") Integer size
                                               ) throws SearchQueryException {
        return searchService.search(query, page, size);
    }

    @GetMapping
    public List<PublicationResponseDTO> searchAll() {
        return searchService.search();
    }
}
