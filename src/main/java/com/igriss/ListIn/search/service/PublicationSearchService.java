package com.igriss.ListIn.search.service;

import com.igriss.ListIn.exceptions.SearchQueryException;
import com.igriss.ListIn.publication.dto.PublicationResponseDTO;
import com.igriss.ListIn.publication.dto.UpdatePublicationRequestDTO;
import com.igriss.ListIn.publication.dto.page.PageResponse;

import java.util.List;
import java.util.UUID;


public interface PublicationSearchService {

    PageResponse<PublicationResponseDTO> searchWithDefaultFilter(String query, Integer page, Integer size,
                                                                 Boolean bargain, String productCondition, Float from, Float to) throws SearchQueryException;

    PageResponse<PublicationResponseDTO> searchWithAdvancedFilter(UUID pCategory, UUID category, String query,
                                                                  Integer page, Integer size, Boolean bargain, String productCondition,
                                                                  Float from, Float to, List<String> filters) throws SearchQueryException;

    PageResponse<PublicationResponseDTO> searchWithParentCategory(UUID parentCategoryId, Integer page, Integer size);
}
