package com.igriss.ListIn.search.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.igriss.ListIn.exceptions.SearchQueryException;
import com.igriss.ListIn.publication.dto.PublicationResponseDTO;
import com.igriss.ListIn.publication.dto.page.PageResponse;
import com.igriss.ListIn.publication.mapper.PublicationMapper;
import com.igriss.ListIn.publication.repository.PublicationRepository;
import com.igriss.ListIn.search.entity.PublicationDocument;
import com.igriss.ListIn.search.service.supplier.QueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PublicationSearchServiceImpl implements PublicationSearchService {

    @Value("${elasticsearch.index-name}")
    private String indexName;

    private final ElasticsearchClient elasticsearchClient;
    private final PublicationMapper publicationMapper;
    private final PublicationRepository publicationRepository;

    @Override
    public PageResponse<PublicationResponseDTO> searchWithAdvancedFilter(
            String pCategory,
            String category,
            String query,
            Integer page,
            Integer size,
            Boolean bargain,
            String productCondition,
            Float from,
            Float to,
            List<String> filters) throws SearchQueryException {

        try {
            SearchResponse<PublicationDocument> response = executeAdvancedSearch(
                    pCategory, category, query, page, size,
                    bargain, productCondition, from, to, filters
            );

            List<PublicationDocument> documents = extractDocuments(response);
            List<PublicationResponseDTO> content = editQuery(documents);
            long totalElements = getTotalElements(response);

            return pagination(page, size, totalElements, content);
        } catch (IOException e) {
            throw new SearchQueryException("Exception on search query: " + e.getMessage());
        }
    }

    @Override
    public PageResponse<PublicationResponseDTO> searchWithDefaultFilter(
            String query,
            Integer page,
            Integer size,
            Boolean bargain,
            String productCondition,
            Float from,
            Float to) throws SearchQueryException {

        try {
            SearchResponse<PublicationDocument> response = executeDefaultSearch(
                    query, page, size, bargain, productCondition, from, to
            );

            List<PublicationDocument> documents = extractDocuments(response);
            List<PublicationResponseDTO> content = editQuery(documents);
            long totalElements = getTotalElements(response);

            return pagination(page, size, totalElements, content);
        } catch (IOException e) {
            throw new SearchQueryException("Exception on search query: " + e.getMessage());
        }
    }

    private SearchResponse<PublicationDocument> executeAdvancedSearch(
            String pCategory, String category, String query,
            Integer page, Integer size,
            Boolean bargain, String productCondition,
            Float from, Float to, List<String> filters) throws IOException {

        Map<String, List<String>> parsedFilters = parseFilter(filters);
        return elasticsearchClient.search(q -> q
                        .index(indexName)
                        .query(QueryRepository.deepSearchQuerySupplier(
                                query, pCategory, category, bargain,
                                productCondition, from, to, parsedFilters).get())
                        .from(page * size)
                        .size(size),
                PublicationDocument.class);
    }

    private SearchResponse<PublicationDocument> executeDefaultSearch(
            String query, Integer page, Integer size,
            Boolean bargain, String productCondition,
            Float from, Float to) throws IOException {

        return elasticsearchClient.search(q -> q
                        .index(indexName)
                        .query(QueryRepository.shallowSearchQuerySupplier(
                                query, bargain, productCondition, from, to).get())
                        .from(page * size)
                        .size(size),
                PublicationDocument.class);
    }

    private Map<String, List<String>> parseFilter(List<String> filters) {
        return filters.stream()
                .map(filter -> filter.split(":"))
                .collect(Collectors.toMap(
                        split -> split[0],
                        split -> Arrays.stream(split[1].split(",")).toList()
                ));
    }

    private List<PublicationDocument> extractDocuments(SearchResponse<PublicationDocument> response) {
        if (response.hits().hits() == null) {
            return new ArrayList<>();
        }
        return response.hits().hits().stream()
                .map(Hit::source)
                .toList();
    }

    private long getTotalElements(SearchResponse<PublicationDocument> response) {
        return response.hits().total() != null ? response.hits().total().value() : 0;
    }


    @NotNull
    private List<PublicationResponseDTO> editQuery(List<PublicationDocument> publicationDocuments) {
        return publicationDocuments.stream()
                .map(document -> publicationRepository.findById(document.getId())
                        .map(publicationMapper::toPublicationResponseDTO)
                        .orElseThrow())
                .toList();
    }

    private PageResponse<PublicationResponseDTO> pagination(
            Integer page,
            Integer size,
            long totalElements,
            List<PublicationResponseDTO> content) {

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
}