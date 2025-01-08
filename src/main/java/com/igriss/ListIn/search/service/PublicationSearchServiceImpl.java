package com.igriss.ListIn.search.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.util.ObjectBuilder;
import com.igriss.ListIn.exceptions.SearchQueryException;
import com.igriss.ListIn.publication.dto.PublicationResponseDTO;
import com.igriss.ListIn.publication.entity.Publication;
import com.igriss.ListIn.publication.mapper.PublicationMapper;
import com.igriss.ListIn.publication.repository.PublicationRepository;
import com.igriss.ListIn.search.entity.PublicationDocument;
import com.igriss.ListIn.search.service.supplier.QuerySupplier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PublicationSearchServiceImpl implements PublicationSearchService {

    @Value("${elasticsearch.index-name}")
    private String indexName;

    private final ElasticsearchClient elasticsearchClient;
    private final PublicationRepository publicationRepository;
    private final PublicationMapper publicationMapper;

    @Override
    public List<PublicationResponseDTO> search() {
        return publicationRepository
                .findAll()
                .stream()
                .map(publicationMapper::toPublicationResponseDTO).toList();

    }

    @Override
    public List<PublicationDocument> searchDocuments(String query, Integer page, Integer size) throws SearchQueryException {

        List<PublicationDocument> publicationDocuments = new ArrayList<>();
        SearchResponse<PublicationDocument> publicationDocumentSearchResponse;
        try {
            publicationDocumentSearchResponse = elasticsearchClient.search(q -> q
                            .index(indexName)
                            .query(QuerySupplier.querySearchSupplier(query).get())
                            .size(size)
                            .trackScores(false)
                            .from(size*page),
                    PublicationDocument.class);

            if (publicationDocumentSearchResponse.hits() != null && publicationDocumentSearchResponse.hits().hits() != null) {
                for (var hit : publicationDocumentSearchResponse.hits().hits()) {
                    publicationDocuments.add(hit.source());
                }
            }
        } catch (IOException ioException) {
            throw new SearchQueryException("Exception on search query: " + ioException.getMessage());
        }
        log.info("{}",publicationDocuments);
        return publicationDocuments;
    }

    @Override
    public List<PublicationResponseDTO> search(String query, Integer page, Integer size) throws SearchQueryException {

        List<Publication> optionalPublicationList =
                searchDocuments(query, page, size).stream()
                        .map(document -> publicationRepository
                                .findByIdOrderByDateUpdatedDesc(document.getId())).toList();

        return optionalPublicationList
                .stream()
                .map(publicationMapper::toPublicationResponseDTO).toList();

    }
}
