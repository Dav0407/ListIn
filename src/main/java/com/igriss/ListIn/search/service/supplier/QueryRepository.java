package com.igriss.ListIn.search.service.supplier;

import co.elastic.clients.elasticsearch._types.query_dsl.*;
import co.elastic.clients.json.JsonData;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;

@Slf4j
public class QueryRepository {

    public static Supplier<Query> deepSearchQuerySupplier(SearchParams params) {

        Query boolQuery = BoolQuery.of(builder -> {

            if (params.getFilters() != null && !params.getFilters().isEmpty())
                addNestedAttributeFilters(params.getFilters(), builder);

            if (params.getNumericFilter() != null && !params.getNumericFilter().isEmpty())
                addNumericFilters(params.getNumericFilter(), builder);

            addCategoryFilters(params.getParentCategory(), params.getCategory(), builder);

            if (params.getLocationIds().get("countryId") != null)
                addLocationFilter(params.getLocationIds().get("countryId"), "countryId", builder);

            if (params.getLocationIds().get("stateId") != null)
                addLocationFilter(params.getLocationIds().get("stateId"), "stateId", builder);

            if (params.getLocationIds().get("countyId") != null)
                addLocationFilter(params.getLocationIds().get("countyId"), "countyId", builder);

            if (params.getLocationIds().get("cityId") != null)
                addLocationFilter(params.getLocationIds().get("cityId"), "cityId", builder);

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

        log.info("Generated deep search query: {}", boolQuery);

        return () -> boolQuery;
    }

    private static void addCategoryFilters(UUID parentCategory, UUID category, BoolQuery.Builder builder) {
        if (parentCategory != null) {
            builder.filter(m -> m.match(createMatchQuery(SearchFields.PARENT_CATEGORY_ID, parentCategory.toString())));
        }
        if (category != null) {
            builder.filter(m -> m.match(createMatchQuery(SearchFields.CATEGORY_ID, category.toString())));
        }
    }

    private static void addNestedAttributeFilters(Map<String, List<String>> filters, BoolQuery.Builder builder) {
        filters.forEach((key, values) -> builder.filter(q -> q.nested(n -> n
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
        )));
    }

    private static void addNumericFilters(Map<String, String[]> filters, BoolQuery.Builder builder) {
        filters.forEach((key, range) -> builder.filter(q -> q.nested(n -> n
                .path(SearchFields.NUMERIC_FIELDS)
                .query(nq -> nq.bool(nb -> nb
                        .must(mq -> mq.match(createMatchQuery(SearchFields.NUMERIC_FIELD_ID, key)))
                        .must(mq -> mq.range(r -> r
                                .field(SearchFields.NUMERIC_FIELD_VALUE)
                                .gte(JsonData.of(range[0]))
                                .lte(JsonData.of(range.length > 1 ? range[1] : range[0]))
                        ))
                ))
        )));
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

        addBargainFilter(bargain, builder);

        addIsFreeFilter(isFree, builder);
        addSellerTypeFilter(sellerType, builder);

        addOptionalFilter(productCondition, builder, SearchFields.PRODUCT_CONDITION);
        addOptionalFilter(locationName, builder, SearchFields.LOCATION_NAME);

        addPriceRangeFilter(priceFrom, priceTo, builder);

        if (!Objects.equals(input, null)) {
            addTextSearchQuery(input, builder);
            builder.minimumShouldMatch("1");
        }
    }

    private static void addOptionalFilter(String value, BoolQuery.Builder builder, String field) {
        if (value != null) {
            builder.filter(m -> m.match(createMatchQuery(field, value)));
        }
    }

    private static void addPriceRangeFilter(Float from, Float to, BoolQuery.Builder builder) {
        if (from != null || to != null) {
            RangeQuery.Builder rangeBuilder = new RangeQuery.Builder().field(SearchFields.PRICE);

            if (from != null)
                rangeBuilder.gte(JsonData.of(from));

            if (to != null)
                rangeBuilder.lte(JsonData.of(to));

            builder.filter(f -> f.range(rangeBuilder.build()));
        }
    }

    private static void addTextSearchQuery(String query, BoolQuery.Builder builder) {
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
        if (bargain != null)
            builder.filter(m -> m.term(t -> t
                    .field(SearchFields.BARGAIN)
                    .value(bargain)));
    }

    private static MatchQuery createMatchQuery(String field, String value) {
        return MatchQuery.of(q -> q
                .field(field)
                .query(value));
    }

    private static void addIsFreeFilter(Boolean isFree, BoolQuery.Builder builder) {
        if (isFree != null) {
            builder.filter(m -> m.term(t -> t
                    .field(SearchFields.PRICE)
                    .value(0.0f)));
        }
    }

    private static void addSellerTypeFilter(String sellerType, BoolQuery.Builder builder) {
        if (sellerType != null) {
            builder.filter(m -> m.match(createMatchQuery(SearchFields.SELLER_TYPE, sellerType)));
        }
    }

    private static void addLocationFilter(String locationId, String field, BoolQuery.Builder builder) {
        if (locationId != null)
            builder.filter(m -> m.match(createMatchQuery(field, locationId)));
    }

}



