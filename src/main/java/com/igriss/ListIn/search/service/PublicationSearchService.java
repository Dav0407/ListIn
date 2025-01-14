package com.igriss.ListIn.search.service;

import com.igriss.ListIn.exceptions.SearchQueryException;
import com.igriss.ListIn.publication.dto.PublicationResponseDTO;
import com.igriss.ListIn.publication.dto.page.PageResponse;

import java.util.List;


public interface PublicationSearchService {

    PageResponse<PublicationResponseDTO> searchWithDefaultFilter(String query, Integer page, Integer size,
                                                                 Boolean bargain, Float from, Float to) throws SearchQueryException;

    PageResponse<PublicationResponseDTO> searchWithAdvancedFilter(String pCategory, String category, String query,
                                                                  Integer page, Integer size, Boolean bargain,
                                                                  Float from, Float to, List<String> filters) throws SearchQueryException;
}
