package com.igriss.ListIn.search.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.igriss.ListIn.exceptions.SearchQueryException;
import com.igriss.ListIn.publication.dto.PublicationResponseDTO;
import com.igriss.ListIn.publication.dto.page.PageResponse;
import com.igriss.ListIn.publication.entity.Publication;
import com.igriss.ListIn.publication.entity.PublicationVideo;
import com.igriss.ListIn.publication.mapper.PublicationImageMapper;
import com.igriss.ListIn.publication.mapper.PublicationMapper;
import com.igriss.ListIn.publication.repository.ProductImageRepository;
import com.igriss.ListIn.publication.repository.ProductVideoRepository;
import com.igriss.ListIn.publication.repository.PublicationRepository;
import com.igriss.ListIn.search.document.PublicationDocument;
import com.igriss.ListIn.search.service.supplier.QueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class PublicationSearchServiceImpl implements PublicationSearchService {

    private final PublicationImageMapper publicationImageMapper;
    @Value("${elasticsearch.index-name}")
    private String indexName;

    private final ElasticsearchClient elasticsearchClient;
    private final PublicationMapper publicationMapper;
    private final PublicationRepository publicationRepository;
    private final ProductImageRepository productImageRepository;
    private final ProductVideoRepository productVideoRepository;

    @Override
    public PageResponse<PublicationResponseDTO> searchWithAdvancedFilter(UUID pCategory, UUID category, String query,
                                                                         Integer page, Integer size, Boolean bargain, String productCondition,
                                                                         Float from, Float to, List<String> filters) throws SearchQueryException {


        List<PublicationDocument> publicationDocuments = new ArrayList<>();

        SearchResponse<PublicationDocument> response;
        try {
            response = elasticsearchClient.search(q -> q
                            .index(indexName)
                            .query(QueryRepository.deepSearchQuerySupplier(query, pCategory, category, bargain, productCondition, from, to, filters != null ? parseFilter(filters) : null).get())
                            .from(page * size)
                            .size(size),
                    PublicationDocument.class);

            if (response.hits().hits() != null) {
                for (var hit : response.hits().hits()) {
                    publicationDocuments.add(hit.source());
                }
            }
        } catch (IOException ioException) {
            log.error("Exception occurred: ", ioException);
            throw new SearchQueryException("Exception on search query: " + ioException.getMessage());
        }

        List<PublicationResponseDTO> content = editQuery(publicationDocuments);

        long totalElements = 0;
        if (response.hits().total() != null)
            totalElements = response.hits().total().value();

        return pagination(page, size, totalElements, content);
    }

    @Override
    public PageResponse<PublicationResponseDTO> searchWithDefaultFilter(String query, Integer page, Integer size,
                                                                        Boolean bargain, String productCondition, Float from, Float to) throws SearchQueryException {
        try {
            SearchResponse<PublicationDocument> response = elasticsearchClient.search(q -> q
                            .index(indexName)
                            .query(QueryRepository.shallowSearchQuerySupplier(query, bargain, productCondition, from, to).get())
                            .from(page * size)
                            .size(size),
                    PublicationDocument.class);

            long totalElements = response.hits().total() != null ? response.hits().total().value() : 0;

            List<PublicationDocument> publicationDocuments = response.hits().hits() != null ?
                    response.hits().hits().stream()
                            .map(Hit::source)
                            .toList() :
                    new ArrayList<>();

            List<PublicationResponseDTO> content = editQuery(publicationDocuments);
            return pagination(page, size, totalElements, content);
        } catch (IOException ioException) {
            log.error("Exception occurred: ", ioException);
            throw new SearchQueryException("Exception on search query: " + ioException.getMessage());
        }
    }

    @Override
    public PageResponse<PublicationResponseDTO> searchWithParentCategory(UUID parentCategoryId, Integer page, Integer size) {
        Page<Publication> publicationPage = publicationRepository.findAllByCategory_ParentCategory_Id(parentCategoryId, PageRequest.of(page, size,  Sort.by("datePosted").descending()));

        return new PageResponse<>(
                publicationPage
                        .getContent()
                        .stream()
                        .map(publication ->
                                publicationMapper
                                        .toPublicationResponseDTO(publication,
                                                productImageRepository
                                                        .findAllByPublication_Id(publication.getId()),
                                                productVideoRepository
                                                        .findByPublication_Id(publication.getId())
                                                        .map(PublicationVideo::getVideoUrl)
                                                        .orElse(null))
                        )
                        .toList(),
                publicationPage.getNumber(),
                publicationPage.getSize(),
                publicationPage.getTotalElements(),
                publicationPage.getTotalPages(),
                publicationPage.isFirst(),
                publicationPage.isLast());
    }


    private Map<String, List<String>> parseFilter(List<String> filters) {
        return filters.stream()
                .map(filter -> filter.split(":"))
                .filter(split -> split.length == 2)
                .collect(Collectors.toMap(
                        split -> split[0],
                        split -> Arrays.stream(split[1].split(",")).toList(),
                        (existing, replacement) -> {
                            List<String> merged = new ArrayList<>(existing);
                            merged.addAll(replacement);
                            return merged;
                        }
                        )
                );
    }

    private PageResponse<PublicationResponseDTO> pagination(Integer page, Integer size, long totalElements, List<PublicationResponseDTO> content) {
        boolean first = page == 0;
        boolean last = ((long) (page + 1) * size) >= totalElements;
        long totalPages = (long) Math.ceil((double) totalElements / size);

        return PageResponse.<PublicationResponseDTO>builder()
                .content(content)
                .number(page)
                .size(size)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .first(first)
                .last(last)
                .build();
    }

    @NotNull
    private List<PublicationResponseDTO> editQuery(List<PublicationDocument> publicationDocuments) {
        return publicationDocuments.stream()
                .map(document -> publicationRepository.findById(document.getId())
                        .map(publication ->
                                publicationMapper.
                                        toPublicationResponseDTO(publication,
                                                productImageRepository
                                                        .findAllByPublication_Id(publication.getId()),
                                                productVideoRepository
                                                        .findByPublication_Id(publication.getId())
                                                        .map(PublicationVideo::getVideoUrl)
                                                        .orElse(null)
                                        ))
                        .orElseThrow())
                .toList();
    }

}
