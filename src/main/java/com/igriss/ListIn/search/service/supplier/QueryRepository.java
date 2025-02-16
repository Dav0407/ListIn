package com.igriss.ListIn.search.service.supplier;

import co.elastic.clients.elasticsearch._types.query_dsl.*;
import co.elastic.clients.json.JsonData;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.function.Supplier;

@Slf4j
public class QueryRepository {

    public static Supplier<Query> deepSearchQuerySupplier(SearchParams params) {
        log.info("Starting to build deep search query with params: {}", params);

        Query boolQuery = BoolQuery.of(builder -> {
            log.info("Building bool query...");

            if (params.getFilters() != null && !params.getFilters().isEmpty()) {
                log.info("Adding nested attribute filters: {}", params.getFilters());
                addNestedAttributeFilters(params.getFilters(), builder);
            }

            if (params.getNumericFilter() != null && !params.getNumericFilter().isEmpty()) {
                log.info("Adding numeric filters: {}", params.getNumericFilter());
                addNumericFilters(params.getNumericFilter(), builder);
            }

            log.info("Adding category filters - parentCategory: {}, category: {}",
                    params.getParentCategory(), params.getCategory());
            addCategoryFilters(params.getParentCategory(), params.getCategory(), builder);

            log.info("Adding basic search filters - bargain: {}, condition: {}, priceFrom: {}, priceTo: {}, " +
                            "input: {}, location: {}, isFree: {}, sellerType: {}",
                    params.getBargain(), params.getProductCondition(), params.getPriceFrom(),
                    params.getPriceTo(), params.getInput(), params.getLocationName(),
                    params.getIsFree(), params.getSellerType());
            addBasicSearchFilters(
                    params.getBargain(),
                    params.getProductCondition(),
                    params.getPriceFrom(),
                    params.getPriceTo(),
                    params.getInput(),
                    params.getLocationName(),
                    params.getIsFree(),
                    params.getSellerType(),
                    builder
            );

            return builder;
        })._toQuery();

        log.info("Generated bool query: {}", boolQuery);
        return () -> boolQuery;
    }

    private static void addCategoryFilters(UUID parentCategory, UUID category, BoolQuery.Builder builder) {
        if (parentCategory != null) {
            log.info("Adding parent category filter: {}", parentCategory);
            builder.filter(m -> m.match(createMatchQuery(SearchFields.PARENT_CATEGORY_ID, parentCategory.toString())));
        }
        if (category != null) {
            log.info("Adding category filter: {}", category);
            builder.filter(m -> m.match(createMatchQuery(SearchFields.CATEGORY_ID, category.toString())));
        }
    }

    private static void addNestedAttributeFilters(Map<String, List<String>> filters, BoolQuery.Builder builder) {
        log.info("😤😤😤Processing nested attribute filters...");
        filters.forEach((key, values) -> {
            log.info("😊😊😊Adding nested filter for key: {} with values: {}", key, values);
            builder.filter(q -> q.nested(n -> n
                    .path("attributeKeys")
                    .query(kq -> kq.bool(kb -> kb
                            .must(mq -> mq.match(createMatchQuery(SearchFields.ATTRIBUTE_KEY_ID, key)))
                            .filter(mq -> mq.nested(nv -> nv
                                    .path("attributeKeys.attributeValues")
                                    .query(vq -> vq.bool(vb -> vb
                                            .should(values.stream()
                                                    .map(value -> Query.of(qv -> qv
                                                            .match(createMatchQuery(SearchFields.ATTRIBUTE_VALUE_ID, value))))
                                                    .toList()
                                            )
                                            .minimumShouldMatch("1")
                                    ))
                            ))
                    ))
            ));
        });
    }

    private static void addNumericFilters(Map<String, String[]> filters, BoolQuery.Builder builder) {
        log.info("😤😤😤Processing numeric filters...");
        filters.forEach((key, range) -> {
            log.info("😊😊😊Adding numeric filter for key: {} with range: {}", key, Arrays.toString(range));
            builder.filter(q -> q.nested(n -> n
                    .path(SearchFields.NUMERIC_FIELDS)
                    .query(nq -> nq.bool(nb -> nb
                            .must(mq -> mq.match(createMatchQuery(SearchFields.NUMERIC_FIELD_ID, key)))
                            .must(mq -> mq.range(r -> r
                                    .field(SearchFields.NUMERIC_FIELD_VALUE)
                                    .gte(JsonData.of(range[0]))
                                    .lte(JsonData.of(range.length > 1 ? range[1] : range[0]))
                            ))
                    ))
            ));
        });
    }

    private static void addBasicSearchFilters(
            Boolean bargain,
            String productCondition,
            Float priceFrom,
            Float priceTo,
            String input,
            String locationName,
            Boolean isFree,
            String sellerType,
            BoolQuery.Builder builder) {

        log.info("Adding basic search filters...");

        if (bargain != null) {
            log.info("Adding bargain filter: {}", bargain);
            addBargainFilter(bargain, builder);
        }

        if (isFree != null) {
            log.info("Adding isFree filter: {}", isFree);
            addIsFreeFilter(isFree, builder);
        }

        if (sellerType != null) {
            log.info("Adding sellerType filter: {}", sellerType);
            addSellerTypeFilter(sellerType, builder);
        }

        if (productCondition != null) {
            log.info("Adding product condition filter: {}", productCondition);
            addOptionalFilter(productCondition, builder, SearchFields.PRODUCT_CONDITION);
        }

        if (locationName != null) {
            log.info("Adding location name filter: {}", locationName);
            addOptionalFilter(locationName, builder, SearchFields.LOCATION_NAME);
        }

        if (priceFrom != null || priceTo != null) {
            log.info("Adding price range filter - from: {}, to: {}", priceFrom, priceTo);
            addPriceRangeFilter(priceFrom, priceTo, builder);
        }

        if (!Objects.equals(input, null)) {
            log.info("Adding text search query: {}", input);
            addTextSearchQuery(input, builder);
            builder.minimumShouldMatch("1");
        }
    }

    private static void addOptionalFilter(String value, BoolQuery.Builder builder, String field) {
        log.trace("Adding optional filter - field: {}, value: {}", field, value);
        if (value != null) {
            builder.filter(m -> m.match(createMatchQuery(field, value)));
        }
    }

    private static void addPriceRangeFilter(Float from, Float to, BoolQuery.Builder builder) {
        log.trace("Adding price range filter - from: {}, to: {}", from, to);
        if (from != null || to != null) {
            RangeQuery.Builder rangeBuilder = new RangeQuery.Builder().field(SearchFields.PRICE);

            if (from != null) {
                rangeBuilder.gte(JsonData.of(from));
            }

            if (to != null) {
                rangeBuilder.lte(JsonData.of(to));
            }

            builder.filter(f -> f.range(rangeBuilder.build()));
        }
    }

    private static void addTextSearchQuery(String query, BoolQuery.Builder builder) {
        log.trace("Adding text search query: {}", query);
        if (query != null && !query.trim().isEmpty()) {
            builder.should(m -> m.multiMatch(mm -> mm
                    .fields(SearchFields.TITLE, SearchFields.DESCRIPTION,
                            SearchFields.CATEGORY_NAME, SearchFields.PARENT_CATEGORY_NAME)
                    .type(TextQueryType.PhrasePrefix)
                    .query(query))
            );
        }
    }

    private static void addBargainFilter(Boolean bargain, BoolQuery.Builder builder) {
        log.trace("Adding bargain filter: {}", bargain);
        if (bargain != null)
            builder.filter(m -> m.term(t -> t
                    .field(SearchFields.BARGAIN)
                    .value(bargain)));
    }

    private static MatchQuery createMatchQuery(String field, String value) {
        log.trace("Creating match query - field: {}, value: {}", field, value);
        return MatchQuery.of(q -> q
                .field(field)
                .query(value));
    }

    private static void addIsFreeFilter(Boolean isFree, BoolQuery.Builder builder) {
        log.trace("Adding isFree filter: {}", isFree);
        if (isFree != null) {
            builder.filter(m -> m.term(t -> t
                    .field(SearchFields.PRICE)
                    .value(0.0f)));
        }
    }

    private static void addSellerTypeFilter(String sellerType, BoolQuery.Builder builder) {
        log.trace("Adding seller type filter: {}", sellerType);
        if (sellerType != null) {
            builder.filter(m -> m.match(createMatchQuery(SearchFields.SELLER_TYPE, sellerType)));
        }
    }
}

