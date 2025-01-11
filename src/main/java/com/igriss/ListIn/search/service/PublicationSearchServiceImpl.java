package com.igriss.ListIn.search.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import com.igriss.ListIn.exceptions.SearchQueryException;
import com.igriss.ListIn.publication.dto.PublicationResponseDTO;
import com.igriss.ListIn.publication.dto.page.PageResponse;
import com.igriss.ListIn.publication.mapper.PublicationMapper;
import com.igriss.ListIn.publication.repository.PublicationRepository;
import com.igriss.ListIn.search.entity.PublicationDocument;
import com.igriss.ListIn.search.repository.PublicationDocumentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class PublicationSearchServiceImpl implements PublicationSearchService {

    @Value("${elasticsearch.index-name}")
    private String indexName;

    private final ElasticsearchClient elasticsearchClient;
    private final PublicationRepository publicationRepository;
    private final PublicationMapper publicationMapper;
    private final PublicationDocumentRepository publicationDocumentRepository;

    @Override
    public List<PublicationResponseDTO> search() {
        return publicationRepository
                .findAll()
                .stream()
                .map(publicationMapper::toPublicationResponseDTO).toList();
    }

    @Override
    public PageResponse<PublicationResponseDTO> search(String query, Integer page, Integer size) throws SearchQueryException {
        try {
            Pageable pageable = PageRequest.of(page, size);

            Page<PublicationDocument> publications = publicationDocumentRepository.searchByQuery(pageable, query);

            List<PublicationResponseDTO> publicationResponseDTOs = publicationRepository.findAllById(
                    publications.getContent().stream()
                            .map(PublicationDocument::getId)
                            .toList()
            ).stream().map(publicationMapper::toPublicationResponseDTO).toList();

            return new PageResponse<>(
                    publicationResponseDTOs,
                    publications.getNumber(),
                    publications.getSize(),
                    publications.getTotalElements(),
                    publications.getTotalPages(),
                    publications.isFirst(),
                    publications.isLast()
            );
        } catch (Exception e) {
            log.error("Error during search operation: ", e);
            throw new SearchQueryException("Failed to execute search query: " + e.getMessage());
        }
    }

}
