package com.igriss.ListIn.search.service;

import com.igriss.ListIn.exceptions.SearchQueryException;
import com.igriss.ListIn.publication.dto.PublicationResponseDTO;
import com.igriss.ListIn.publication.dto.page.PageResponse;
import com.igriss.ListIn.search.entity.PublicationDocument;

import java.util.List;


public interface PublicationSearchService {

    PageResponse<PublicationResponseDTO> search(String query, Integer page, Integer size) throws SearchQueryException;
    List<PublicationResponseDTO> search();

}
