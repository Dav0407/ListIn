package com.igriss.ListIn.search.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.igriss.ListIn.exceptions.SearchQueryException;
import com.igriss.ListIn.publication.dto.PublicationResponseDTO;
import com.igriss.ListIn.publication.entity.NumericValue;
import com.igriss.ListIn.publication.entity.Publication;
import com.igriss.ListIn.publication.entity.PublicationImage;
import com.igriss.ListIn.publication.entity.PublicationVideo;
import com.igriss.ListIn.publication.mapper.PublicationMapper;
import com.igriss.ListIn.publication.repository.*;
import com.igriss.ListIn.publication.service.PublicationNodeHandler;
import com.igriss.ListIn.search.document.PublicationDocument;
import com.igriss.ListIn.search.dto.FoundPublicationsDTO;
import com.igriss.ListIn.search.dto.PublicationNode;
import com.igriss.ListIn.search.service.supplier.QueryRepository;
import com.igriss.ListIn.search.service.supplier.SearchParams;
import com.igriss.ListIn.user.entity.User;
import com.igriss.ListIn.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
                                                          Float from, Float to, String locationName, Boolean isFree,
                                                          String sellerType, List<String> filters, List<String> numericFilter, Authentication connectedUser)
            throws SearchQueryException {

        log.info("😊😊😊Starting advanced filter search with parameters: pCategory={}, category={}, query={}, page={}, size={}, bargain={}, " +
                        "condition={}, priceFrom={}, priceTo={}, location={}, isFree={}, sellerType={}",
                pCategory, category, query, page, size, bargain, productCondition, from, to, locationName, isFree, sellerType);

        if (filters != null) {
            log.info("😤😤😤Filters provided: {}", filters);
        }
        if (numericFilter != null) {
            log.info("😤😤😤Numeric filters provided: {}", numericFilter);
        }

        User user = (User) connectedUser.getPrincipal();
        log.info("User retrieved from authentication: {}", user.getUserId());

        List<PublicationDocument> publicationDocuments = new ArrayList<>();

        try {
            log.info("😤😤😤Executing Elasticsearch query");
            SearchResponse<PublicationDocument> response = getPublicationDocumentSearchResponse(
                    pCategory, category, query, page, size, bargain, productCondition, from, to, locationName, isFree, sellerType, filters, numericFilter);

            log.info("😊😊😊Search response received. Total hits: {}", response.hits().total() != null ? response.hits().total().value() : 0);

            if (response.hits().hits() != null) {
                log.info("😊😊😊Processing {} search hits", response.hits().hits().size());
                for (var hit : response.hits().hits()) {
                    publicationDocuments.add(hit.source());
                }
            }

            long totalElements = response.hits().total() != null ? response.hits().total().value() : 0;
            boolean isLast = ((long) (page + 1) * size) >= totalElements;
            log.info("Pagination info: totalElements={}, isLast={}", totalElements, isLast);

            List<PublicationResponseDTO> editedPublications = editQuery(publicationDocuments, user);
            log.info("Transformed {} publication documents to DTOs", editedPublications.size());

            List<PublicationNode> result = publicationNodeHandler1.handlePublicationNodes(editedPublications, isLast);
            log.info("😅😅😅Successfully processed search results. Returning {} publication nodes", result.size());
            return result;

        } catch (IOException ioException) {
            log.error("IOException occurred during search: ", ioException);
            throw new SearchQueryException("Exception on search query: " + ioException.getMessage());
        }
    }

    @Override
    public List<PublicationNode> findAllLatestPublications(Integer page, Integer size, Boolean bargain, String productCondition, Float from, Float to, String locationName, List<String> filters, List<String> numericFilter, Authentication connectedUser) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("datePosted").descending());

        Page<Publication> publicationPage = publicationRepository.findAllByOrderByDatePostedDesc(pageable);

        return getPublicationNodes(connectedUser, publicationPage, publicationNodeHandler2);
    }

    @Override
    public FoundPublicationsDTO getPublicationsCount(UUID pCategory, UUID category, String query,
                                                     Integer page, Integer size, Boolean bargain, String productCondition,
                                                     Float from, Float to, String locationName,Boolean isFree,
                                                     String sellerType, List<String> filters, List<String> numericFilter) throws SearchQueryException {
        try {
            SearchResponse<PublicationDocument> search = getPublicationDocumentSearchResponse(
                    pCategory, category, query, page, size, bargain, productCondition, from, to, locationName, isFree, sellerType, filters, numericFilter);

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

            return FoundPublicationsDTO.builder()
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
            UUID pCategory, UUID category, String query, Integer page, Integer size, Boolean bargain,
            String productCondition, Float from, Float to, String locationName, Boolean isFree,
            String sellerType, List<String> filters, List<String> numericFilter) throws IOException {

        log.info("😤😤😤Building Elasticsearch query with params: page={}, size={}", page, size);

        if (filters != null) {
            Map<String, List<String>> parsedFilters = parseFilter(filters);
            log.info("😤😤😤Parsed filters: {}", parsedFilters);
        }

        if (numericFilter != null) {
            Map<String, String[]> parsedNumericFilters = parseNumericFilter(numericFilter);
            log.info("😤😤😤Parsed numeric filters: {}", parsedNumericFilters);
        }

        try {
            SearchResponse<PublicationDocument> response = elasticsearchClient.search(q -> q
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
                                            .isFree(isFree)
                                            .sellerType(sellerType)
                                            .filters(filters != null ? parseFilter(filters) : null)
                                            .numericFilter(numericFilter != null ? parseNumericFilter(numericFilter) : null)
                                            .build()
                            ).get())
                            .from(page * size)
                            .size(size),
                    PublicationDocument.class);

            log.info("😊😊😊Elasticsearch query executed successfully. Response: took={}ms, total_hits={}",
                    response.took(), response.hits().total() != null ? response.hits().total().value() : 0);
            return response;

        } catch (IOException e) {
            log.error("🥵🥵🥵Failed to execute Elasticsearch query: ", e);
            throw e;
        }
    }

    private Map<String, List<String>> parseFilter(List<String> filters) {
        log.info("😤😤😤Parsing filters: {}", filters);
        Map<String, List<String>> result = filters.stream()
                .map(filter -> {
                    String[] split = filter.split(":");
                    log.trace("😤😤😤Split filter '{}' into: {}", filter, Arrays.toString(split));
                    return split;
                })
                .filter(split -> {
                    boolean valid = split.length == 2;
                    if (!valid) {
                        log.warn("😤😤😤Invalid filter format: {}", Arrays.toString(split));
                    }
                    return valid;
                })
                .collect(Collectors.toMap(
                        split -> split[0],
                        split -> Arrays.stream(split[1].split(",")).toList(),
                        (existing, replacement) -> {
                            List<String> merged = new ArrayList<>(existing);
                            merged.addAll(replacement);
                            return merged;
                        }
                ));
        log.info("😊😊😊Parsed filters result: {}", result);
        return result;
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
        log.info("😊😊😊Starting editQuery for {} publications", publicationDocuments.size());

        List<UUID> publicationIds = publicationDocuments.stream()
                .map(PublicationDocument::getId)
                .toList();
        log.info("😤😤😤Extracted {} publication IDs", publicationIds.size());

        List<Publication> publications = publicationRepository.findAllByIdInOrderByDatePosted(publicationIds);
        log.info("😤😤😤Retrieved {} publications from repository", publications.size());

        List<PublicationResponseDTO> result = publications.stream()
                .map(publication -> {
                    log.trace("😤😤😤Processing publication ID: {}", publication.getId());
                    List<PublicationImage> images = productImageRepository.findAllByPublication_Id(publication.getId());
                    log.trace("😤😤😤Found {} images for publication {}", images.size(), publication.getId());

                    Optional<String> videoUrl = productVideoRepository.findByPublication_Id(publication.getId())
                            .map(PublicationVideo::getVideoUrl);
                    log.trace("😤😤😤Video URL present: {}", videoUrl.isPresent());

                    List<NumericValue> numericValues = numericValueRepository.findAllByPublication_Id(publication.getId());
                    log.trace("😤😤😤Found {} numeric values for publication {}", numericValues.size(), publication.getId());

                    boolean liked = isLiked(user, publication);
                    boolean following = userService.isFollowingToUser(publication.getSeller(), user);
                    log.trace("😤😤😤Publication {} - liked: {}, following seller: {}", publication.getId(), liked, following);

                    return publicationMapper.toPublicationResponseDTO(
                            publication, images, videoUrl.orElse(null), numericValues, liked, following);
                })
                .toList();

        log.info("😊😊😊Completed editQuery processing. Returning {} DTOs", result.size());
        return result;
    }

    private Boolean isLiked(User user, Publication publication) {
        return publicationLikeRepository.existsByUserAndPublication(user, publication);
    }

    private List<PublicationNode> getPublicationNodes(Authentication connectedUser, Page<Publication> publicationPage, PublicationNodeHandler publicationNodeHandler1) {
        User user = (User) connectedUser.getPrincipal();

        List<PublicationResponseDTO> publications = publicationPage
                .getContent()
                .stream()
                .map(publication -> publicationMapper.toPublicationResponseDTO(
                        publication,
                        productImageRepository.findAllByPublication_Id(publication.getId()),
                        productVideoRepository.findByPublication_Id(publication.getId())
                                .map(PublicationVideo::getVideoUrl)
                                .orElse(null),
                        numericValueRepository.findAllByPublication_Id(publication.getId()),
                        isLiked(user, publication), userService.isFollowingToUser(publication.getSeller(), user)
                )).toList();

        return publicationNodeHandler1.handlePublicationNodes(publications, publicationPage.isLast());
    }

}
