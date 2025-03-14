package com.igriss.ListIn.search.service;

import com.igriss.ListIn.exceptions.SearchQueryException;
import com.igriss.ListIn.publication.dto.PublicationResponseDTO;
import com.igriss.ListIn.publication.dto.page.PageResponse;
import com.igriss.ListIn.search.dto.FoundPublicationsDTO;
import com.igriss.ListIn.search.dto.PublicationNode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.UUID;


public interface PublicationSearchService {

    PageResponse<PublicationResponseDTO> searchWithAdvancedFilter(UUID pCategory, UUID category, String query,
                                                          Integer page, Integer size, Boolean bargain, String productCondition,
                                                          Float from, Float to, String locationName, Boolean isFree, String sellerType,
                                                          String locationIds, String searchText, List<String> filters, List<String> numericFilter, Authentication connectedUser) throws SearchQueryException;

    FoundPublicationsDTO getPublicationsCount(UUID pCategory, UUID category, String query,
                                              Integer page, Integer size, Boolean bargain, String productCondition,
                                              Float from, Float to, String locationName, Boolean isFree, String sellerType,
                                              String locationIds, List<String> filters, List<String> numericFilter) throws SearchQueryException;

    PageResponse<PublicationResponseDTO> findAllLatestPublications(Integer page, Integer size, Boolean bargain, String productCondition,
                                                                     Float from, Float to, String locationName, List<String> filters,
                                                                     List<String> numericFilter, Authentication connectedUser);

    List<String> getLastQueriedValues(Authentication connectedUser);
}
