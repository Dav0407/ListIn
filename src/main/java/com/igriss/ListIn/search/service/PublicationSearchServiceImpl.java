package com.igriss.ListIn.search.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.igriss.ListIn.exceptions.ResourceNotFoundException;
import com.igriss.ListIn.exceptions.SearchQueryException;
import com.igriss.ListIn.publication.dto.PublicationResponseDTO;
import com.igriss.ListIn.publication.entity.Publication;
import com.igriss.ListIn.publication.entity.PublicationVideo;
import com.igriss.ListIn.publication.mapper.PublicationMapper;
import com.igriss.ListIn.publication.repository.*;
import com.igriss.ListIn.publication.service.PublicationNodeHandler;
import com.igriss.ListIn.search.document.PublicationDocument;
import com.igriss.ListIn.search.dto.CountPublicationsDTO;
import com.igriss.ListIn.search.dto.PublicationNode;
import com.igriss.ListIn.search.service.supplier.QueryRepository;
import com.igriss.ListIn.search.service.supplier.SearchParams;
import com.igriss.ListIn.user.entity.User;
import com.igriss.ListIn.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
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
    private final ProductImageRepository productImageRepository;
    private final ProductVideoRepository productVideoRepository;
    private final PublicationLikeRepository publicationLikeRepository;

    private final NumericValueRepository numericValueRepository;

    private final PublicationNodeHandler publicationNodeHandler1;
    private final PublicationNodeHandler publicationNodeHandler2;

    private final UserService userService;


    @Override
    public List<PublicationNode> searchWithAdvancedFilter(UUID pCategory, UUID category, String query,
                                                          Integer page, Integer size, Boolean bargain, String productCondition,
                                                          Float from, Float to, String locationName, List<String> filters, List<String> numericFilter, Authentication connectedUser) throws SearchQueryException {

        User user = (User) connectedUser.getPrincipal();

        List<PublicationDocument> publicationDocuments = new ArrayList<>();

        SearchResponse<PublicationDocument> response;
        try {
            response = getPublicationDocumentSearchResponse(
                    pCategory, category, query, page, size, bargain, productCondition, from, to, locationName, filters, numericFilter);


            if (response.hits().hits() != null) {
                for (var hit : response.hits().hits()) {
                    publicationDocuments.add(hit.source());
                }
            }
        } catch (IOException ioException) {
            log.error("Exception occurred: ", ioException);
            throw new SearchQueryException("Exception on search query: " + ioException.getMessage());
        }

        long totalElements = response.hits().total() != null ? response.hits().total().value() : 0;
        boolean isLast = ((long) (page + 1) * size) >= totalElements;

        return publicationNodeHandler1.handlePublicationNodes(editQuery(publicationDocuments, user), isLast);
    }

    @Override
    public List<PublicationNode> searchWithDefaultFilter(String query, Integer page, Integer size,
                                                         Boolean bargain, String productCondition, Float from, Float to, String locationName, Authentication connectedUser) throws SearchQueryException {
        try {
            SearchResponse<PublicationDocument> response = elasticsearchClient.search(q -> q
                            .index(indexName)
                            .query(QueryRepository.shallowSearchQuerySupplier(
                                    SearchParams.builder()
                                            .input(query)
                                            .bargain(bargain)
                                            .productCondition(productCondition)
                                            .priceFrom(from)
                                            .priceTo(to)
                                            .locationName(locationName)
                                            .build()
                            ).get())
                            .from(page * size)
                            .size(size),
                    PublicationDocument.class);

            log.info("Access time taken for the query: {}ms", response.took());

            List<PublicationDocument> publicationDocuments = response.hits().hits() != null ?
                    response.hits().hits().stream()
                            .map(Hit::source)
                            .toList() :
                    new ArrayList<>();


            long totalElements = response.hits().total() != null ? response.hits().total().value() : 0;
            boolean isLast = ((long) (page + 1) * size) >= totalElements;

            User user = (User) connectedUser.getPrincipal();
            return publicationNodeHandler2.handlePublicationNodes(editQuery(publicationDocuments, user), isLast);

        } catch (IOException ioException) {
            log.error("Exception occurred: ", ioException);
            throw new SearchQueryException("Exception on search query: " + ioException.getMessage());
        }
    }

    @Override
    public CountPublicationsDTO getPublicationsCount(UUID pCategory, UUID category, String query,
                                                     Integer page, Integer size, Boolean bargain, String productCondition,
                                                     Float from, Float to, String locationName, List<String> filters, List<String> numericFilter) throws SearchQueryException {
        try {
            SearchResponse<PublicationDocument> search = getPublicationDocumentSearchResponse(
                    pCategory, category, query, page, size, bargain, productCondition, from, to, locationName, filters, numericFilter);

            Long found = search.hits().total() != null ? search.hits().total().value() : 0;

            Float maxPrice = search.hits().hits().stream()
                    .map(Hit::source).filter(Objects::nonNull)
                    .map(PublicationDocument::getPrice)
                    .filter(Objects::nonNull)
                    .max(Comparator.naturalOrder())
                    .orElse(null);

            Float minPrice = search.hits().hits().stream()
                    .map(Hit::source).filter(Objects::nonNull)
                    .map(PublicationDocument::getPrice)
                    .filter(Objects::nonNull)
                    .min(Comparator.naturalOrder())
                    .orElse(null);

            return CountPublicationsDTO.builder()
                    .foundPublications(found)
                    .priceFrom(minPrice)
                    .priceTo(maxPrice)
                    .build();

        } catch (IOException ioException) {
            log.error("Exception occurred: ", ioException);
            throw new SearchQueryException("Exception on search query: " + ioException.getMessage());
        }
    }

    private SearchResponse<PublicationDocument> getPublicationDocumentSearchResponse(
            UUID pCategory, UUID category, String query, Integer page, Integer size, Boolean bargain, String productCondition, Float from, Float to, String locationName, List<String> filters, List<String> numericFilter) throws IOException {
        return elasticsearchClient.search(q -> q
                        .index(indexName)
                        .query(QueryRepository.deepSearchQuerySupplier(
                                SearchParams.builder()
                                        .parentCategory(pCategory)
                                        .category(category)
                                        .input(query)
                                        .bargain(bargain)
                                        .productCondition(productCondition)
                                        .priceFrom(from)
                                        .priceTo(to)
                                        .locationName(locationName)
                                        .filters(filters != null ? parseFilter(filters) : null)
                                        .numericFilter(numericFilter != null ? parseNumericFilter(numericFilter) : null)
                                        .build()
                        ).get())
                        .from(page * size)
                        .size(size),
                PublicationDocument.class);
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

    private Map<String, String[]> parseNumericFilter(List<String> filters) {
        return filters.stream()
                .map(filter -> filter.split(":"))
                .filter(split -> split.length == 2)
                .collect(Collectors.toMap(
                        split -> split[0],
                        split -> split[1].split("~", 2)
                ));
    }

    @NotNull
    private List<PublicationResponseDTO> editQuery(List<PublicationDocument> publicationDocuments, User user) {
        List<UUID> publicationIds = publicationDocuments.stream()
                .map(PublicationDocument::getId)
                .toList();

        return publicationRepository.findAllByIdInOrderByDatePosted(publicationIds).stream()
                .map(publication -> publicationMapper.toPublicationResponseDTO(
                        publication,
                        productImageRepository.findAllByPublication_IdIn(publicationIds),
                        productVideoRepository
                                .findByPublication_Id(publication.getId())
                                .map(PublicationVideo::getVideoUrl)
                                .orElse(null),
                        numericValueRepository.findAllByPublication_IdIn(publicationIds),
                        isLiked(user, publication),
                        userService.isFollowingToUser(publication.getSeller(), user)))
                .toList();
    }

    private Boolean isLiked(User user, Publication publication) {
        return publicationLikeRepository.existsByUserAndPublication(user, publication);
    }

}
