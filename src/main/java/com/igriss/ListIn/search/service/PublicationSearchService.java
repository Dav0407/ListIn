package com.igriss.ListIn.search.service;

import com.igriss.ListIn.exceptions.SearchQueryException;
import com.igriss.ListIn.search.dto.PublicationNode;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.UUID;


public interface PublicationSearchService {

    List<PublicationNode> searchWithDefaultFilter(String query, Integer page, Integer size,
                                                  Boolean bargain, String productCondition, Float from, Float to, String locationName, Authentication connectedUser) throws SearchQueryException;

    List<PublicationNode> searchWithAdvancedFilter(UUID pCategory, UUID category, String query,
                                                   Integer page, Integer size, Boolean bargain, String productCondition,
                                                   Float from, Float to, String locationName, List<String> filters, Authentication connectedUser) throws SearchQueryException;
}
