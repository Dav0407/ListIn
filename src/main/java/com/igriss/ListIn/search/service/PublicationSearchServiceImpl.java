package com.igriss.ListIn.search.service;

import com.igriss.ListIn.exceptions.SearchQueryException;
import com.igriss.ListIn.publication.dto.PublicationResponseDTO;
import com.igriss.ListIn.publication.dto.page.PageResponse;
import com.igriss.ListIn.publication.mapper.PublicationMapper;
import com.igriss.ListIn.publication.repository.PublicationRepository;
import com.igriss.ListIn.search.entity.PublicationDocument;
import com.igriss.ListIn.search.repository.PublicationDocumentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PublicationSearchServiceImpl implements PublicationSearchService {

    private final PublicationMapper publicationMapper;
    private final PublicationRepository publicationRepository;
    private final PublicationDocumentRepository publicationDocumentRepository;

    @Override
    public PageResponse<PublicationResponseDTO> searchWithDefaultFilter(String query, Integer page, Integer size,
                                                                        Boolean bargain, Float from, Float to) throws SearchQueryException {
        try {
            Pageable pageable = PageRequest.of(page, size);

            Page<PublicationDocument> publications = publicationDocumentRepository.searchByQuery(pageable, query, bargain, from, to);

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











    @Override
    public PageResponse<PublicationResponseDTO> searchWithAdvancedFilter(String pCategory, String category, String query,
                                                                         Integer page, Integer size, Boolean bargain,
                                                                         Float from, Float to, List<String> filters) throws SearchQueryException{
        try {
            Pageable pageable = PageRequest.of(page, size);

            Page<PublicationDocument> publications = publicationDocumentRepository.searchByQuery(pageable, query, bargain, from, to);

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
