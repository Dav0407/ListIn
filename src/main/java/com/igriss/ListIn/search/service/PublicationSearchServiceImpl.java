package com.igriss.ListIn.search.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.igriss.ListIn.exceptions.SearchQueryException;
import com.igriss.ListIn.publication.dto.PublicationResponseDTO;
import com.igriss.ListIn.publication.entity.Publication;
import com.igriss.ListIn.publication.mapper.PublicationMapper;
import com.igriss.ListIn.publication.repository.*;
import com.igriss.ListIn.publication.service.NumericValueService;
import com.igriss.ListIn.publication.service.ProductFileService;
import com.igriss.ListIn.publication.service.PublicationLikeService;
import com.igriss.ListIn.publication.service.PublicationViewService;
import com.igriss.ListIn.publication.service_impl.PublicationNodeHandler;
import com.igriss.ListIn.search.document.PublicationDocument;
import com.igriss.ListIn.search.dto.FoundPublicationsDTO;
import com.igriss.ListIn.search.dto.PublicationNode;
import com.igriss.ListIn.search.repository.SearchParamMapper;
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
import org.springframework.data.redis.core.RedisTemplate;
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

    private final SearchParamMapper searchParamMapper;
    private final PublicationMapper publicationMapper;
    private final ProductFileService productFileService;
    private final PublicationRepository publicationRepository;
    private final PublicationLikeService publicationLikeService;
    private final PublicationViewService publicationViewService;

    private final NumericValueService numericValueService;

    private final PublicationNodeHandler publicationNodeHandler1;
    private final PublicationNodeHandler publicationNodeHandler2;

    private final UserService userService;


    @Override
    public List<PublicationNode> searchWithAdvancedFilter(UUID pCategory, UUID category, String query,
                                                          Integer page, Integer size, Boolean bargain, String productCondition,
                                                          Float from, Float to, String locationName, Boolean isFree, String sellerType,
                                                          String locationIds, String searchText, List<String> filters, List<String> numericFilter, Authentication connectedUser)
            throws SearchQueryException {

        User user = (User) connectedUser.getPrincipal();

        Map<String, String> locations = locationIds != null ? parseLocations(locationIds) : new HashMap<>();

        Map<String, List<String>> attributeFilter = !(filters == null || filters.isEmpty()) ? parseAttributeFilter(filters) : new HashMap<>();

        Map<String, String[]> numericFilters = !(numericFilter == null || numericFilter.isEmpty()) ? parseNumericFilter(numericFilter) : new HashMap<>();

        List<PublicationDocument> publicationDocuments = new ArrayList<>();

        SearchResponse<PublicationDocument> response;
        try {
            response = getPublicationDocumentSearchResponse(page, size,
                    searchParamMapper.toSearchParams(
                            pCategory, category, query, bargain, productCondition, from, to, locationName, isFree, locations, sellerType, attributeFilter, numericFilters)
            );


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

        if (searchText != null)
            saveIntoSearchHistory(user, searchText);

        return publicationNodeHandler1.handlePublicationNodes(editQuery(publicationDocuments, user), isLast);
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
                                                     Float from, Float to, String locationName, Boolean isFree, String sellerType,
                                                     String locationIds, List<String> filters, List<String> numericFilter)
            throws SearchQueryException {
        try {

            Map<String, String> locations = !(locationIds.isBlank()) ? parseLocations(locationIds) : new HashMap<>();

            Map<String, List<String>> attributeFilter = filters != null && !filters.isEmpty() ? parseAttributeFilter(filters) : new HashMap<>();

            Map<String, String[]> numericFilters = !(numericFilter == null || numericFilter.isEmpty()) ? parseNumericFilter(numericFilter) : new HashMap<>();


            SearchResponse<PublicationDocument> search = getPublicationDocumentSearchResponse(page, size,
                    searchParamMapper.toSearchParams(
                            pCategory, category, query, bargain, productCondition, from, to, locationName, isFree, locations, sellerType, attributeFilter, numericFilters)
            );

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

    private final RedisTemplate<UUID, String> redisHistoryTemplate;


    @Override
    public List<String> getLastQueriedValues(Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        return redisHistoryTemplate.opsForList().range(user.getUserId(), 0, -1);
    }

    private void saveIntoSearchHistory(User user, String query) {
        UUID userId = user.getUserId();
        redisHistoryTemplate.opsForList().leftPush(userId, query);
        redisHistoryTemplate.opsForList().trim(userId, 0, 9);
    }

    private SearchResponse<PublicationDocument> getPublicationDocumentSearchResponse(Integer page, Integer size, SearchParams searchParams) throws IOException {

        return elasticsearchClient.search(q -> q
                        .index(indexName)
                        .query(QueryRepository.deepSearchQuerySupplier(searchParams).get())
                        .from(page * size)
                        .size(size),
                PublicationDocument.class);
    }

    private Map<String, List<String>> parseAttributeFilter(List<String> filters) {
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

    private Map<String, String> parseLocations(String locationIds) {

        List<String> hierarchy = Arrays.asList(locationIds.split("\\."));

        int size = hierarchy.size();

        Map<String, String> locationMap = new HashMap<>(Map.of("countryId", hierarchy.get(0)));

        if (size >= 2)
            locationMap.put("stateId", hierarchy.get(1));

        if (size >= 3)
            locationMap.put("countyId", hierarchy.get(2));

        if (size == 4)
            locationMap.put("cityId", hierarchy.get(3));

        return locationMap;
    }

    @NotNull
    private List<PublicationResponseDTO> editQuery(List<PublicationDocument> publicationDocuments, User user) {
        List<UUID> publicationIds = publicationDocuments.stream()
                .map(PublicationDocument::getId)
                .toList();

        return publicationRepository.findAllByIdInOrderByDatePosted(publicationIds).stream()
                .map(publication -> mapToPublicationDTO(publication, user))
                .toList();
    }

    private List<PublicationNode> getPublicationNodes(Authentication connectedUser, Page<Publication> publicationPage, PublicationNodeHandler publicationNodeHandler1) {
        User user = (User) connectedUser.getPrincipal();

        List<PublicationResponseDTO> publications = publicationPage
                .getContent()
                .stream()
                .map(publication -> mapToPublicationDTO(publication, user)).toList();

        return publicationNodeHandler1.handlePublicationNodes(publications, publicationPage.isLast());
    }

    private PublicationResponseDTO mapToPublicationDTO(Publication publication, User user) {
        PublicationResponseDTO publicationResponseDTO = publicationMapper.toPublicationResponseDTO(
                publication,
                productFileService.findImagesByPublicationId(publication.getId()),
                productFileService.findVideoUrlByPublicationId(publication.getId()),
                numericValueService.findNumericFields(publication.getId()),
                publicationLikeService.isLiked(user.getUserId(), publication.getId()), userService.isFollowingToUser(user, publication.getSeller()));

        publicationResponseDTO.setViews(publicationViewService.views(publication.getId()));
        publicationResponseDTO.setIsViewed(publicationViewService.isViewed(user.getUserId(), publication.getId()));

        return publicationResponseDTO;
    }

}
