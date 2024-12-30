package com.igriss.ListIn.search.service;

import com.igriss.ListIn.exceptions.SearchQueryException;
import com.igriss.ListIn.publication.dto.PublicationResponseDTO;
import com.igriss.ListIn.search.entity.PublicationDocument;

import java.util.List;


public interface PublicationSearchService {

    List<PublicationDocument> searchDocuments(String query) throws SearchQueryException;
    List<PublicationResponseDTO> search(String query) throws SearchQueryException;
    List<PublicationResponseDTO> search();

}
