package com.igriss.ListIn.search.service.supplier;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.query_dsl.*;
import lombok.extern.slf4j.Slf4j;


import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

import static com.igriss.ListIn.search.service.supplier.DocumentRecords.*;

@Slf4j
public class QueryRepository {

    public static Supplier<Query> deepSearchQuerySupplier(
            String input,
            UUID pCategory,
            UUID category,
            Boolean bargain,
            String productCondition,
            Float from,
            Float to,
            String locationName,
            Map<String, List<String>> filters) {
        String cleanedInput = preprocessInput(input);

        Query bq = BoolQuery.of(b -> {

            if (filters != null)
                getDeepQuery(filters, b);

            matchCategoryQuery(pCategory, category, b);

            getShallowQuery(bargain, productCondition, from, to, cleanedInput, locationName, b);

            return b;

        })._toQuery();

        log.info("Query: {}", bq);
        return () -> bq;
    }

    public static Supplier<Query> shallowSearchQuerySupplier(
            String query,
            Boolean bargain,
            String productCondition,
            Float from,
            Float to,
            String locationName
    ) {

        String cleanedInput = preprocessInput(query);

        return () -> BoolQuery.of(b -> {
            getShallowQuery(bargain, productCondition, from, to, cleanedInput, locationName, b);
            return b;
        })._toQuery();
    }

    private static String preprocessInput(String input) {
        return input.trim()
                .replaceAll("[^а-яА-ЯёЁa-zA-Z0-9\\s]", "")
                .toLowerCase();
    }

    private static void matchCategoryQuery(UUID pCategory, UUID category, BoolQuery.Builder b) {

        b.filter(m -> m.match(mustMatchParentCategoryId(pCategory)));
        b.filter(m -> m.match(mustMatchCategoryId(category)));
    }

    private static void getDeepQuery(Map<String, List<String>> filters, BoolQuery.Builder b) {
        filters.forEach((key, values) -> b.filter(q -> q.nested(n -> n
                .path("attributeKeys")
                .query(kq -> kq.bool(kb -> kb
                        .must(mq -> mq.match(mustMatchAttributeKeyId(key)
                        ))
                        .must(mq -> mq.nested(nv -> nv
                                .path("attributeKeys.attributeValues")
                                .query(vq -> vq.bool(vb -> vb
                                        .should(values.stream()
                                                .map(value -> Query.of(qv -> qv
                                                        .match(mustMatchAttributeValueId(value))))
                                                .toList()
                                        )
                                        .minimumShouldMatch("1")
                                ))
                        ))
                ))
        )));
    }


    private static void getShallowQuery(
            Boolean bargain,
            String productCondition,
            Float from,
            Float to,
            String cleanedInput,
            String locationName,
            BoolQuery.Builder b
    ) {
        if (bargain != null)
            b.filter(m -> m.match(filterBargain(bargain)));

        if (productCondition != null)
            b.filter(m -> m.match(filterCondition(productCondition)));

        if (locationName != null)
            b.filter(m -> m.match(filterLocation(locationName)));

        if (from != null || to != null)
            addPriceRangeQueries(b, from, to);


        matchPhrasePrefixesQuery(cleanedInput,b);


        b.minimumShouldMatch("1");
    }

    private static MatchQuery filterLocation(String locationName) {
        return MatchQuery.of(m -> m
                .field(LOCATION_NAME)
                .query(locationName));
    }

    private static void addPriceRangeQueries(BoolQuery.Builder builder, Float from, Float to) {
        if (from != null && to != null) {
            builder.filter(m -> m.range(mustMatchRangeQuery(String.valueOf(from), String.valueOf(to))));
        } else if (from != null) {
            builder.filter(m -> m.range(mustMatchRangeFromQuery(String.valueOf(from))));
        } else if (to != null) {
            builder.filter(m -> m.range(mustMatchRangeToQuery(String.valueOf(to))));
        }
    }

    private static void matchPhrasePrefixesQuery(String query, BoolQuery.Builder b) {
        b.should(m->m.matchPhrase(
                        MatchPhraseQuery.of(mp->mp
                        .field(TITLE)
                        .query(query))
                )
        );
    }

    // Query builders
    private static MatchQuery filterBargain(Boolean value) {
        return MatchQuery.of(q -> q
                .field(BARGAIN)
                .query(value));
    }

    private static RangeQuery mustMatchRangeFromQuery(String from) {
        return RangeQuery.of(q -> q
                .field(PRICE)
                .from(from));
    }

    private static RangeQuery mustMatchRangeToQuery(String to) {
        return RangeQuery.of(q -> q
                .field(PRICE)
                .to(to));
    }

    private static RangeQuery mustMatchRangeQuery(String from, String to) {
        return RangeQuery.of(q -> q
                .field(PRICE)
                .from(from)
                .to(to));
    }

    private static MatchQuery filterCondition(String query) {
        return MatchQuery.of(q -> q
                .field(PRODUCT_CONDITION)
                .query(query));
    }

    private static MatchQuery mustMatchAttributeKeyId(String query) {
        return MatchQuery.of(q -> q
                .field(ATTRIBUTE_KEY_ID)
                .query(query));
    }

    private static MatchQuery mustMatchAttributeValueId(String query) {
        return MatchQuery.of(q -> q
                .field(ATTRIBUTE_VALUE_ID)
                .query(query));
    }

    private static MatchQuery mustMatchCategoryId(UUID query) {
        return MatchQuery.of(q -> q
                .field(CATEGORY_ID)
                .query(FieldValue.of(query)));
    }


    private static MatchQuery mustMatchParentCategoryId(UUID query) {
        return MatchQuery.of(q -> q
                .field(PARENT_CATEGORY_ID)
                .query(FieldValue.of(query)));
    }

    private static MatchPhrasePrefixQuery matchPhrasePrefixQuery(String query) {
        return MatchPhrasePrefixQuery.of(q -> q
                .field("model")
                .query(query)
                .maxExpansions(50)
                .slop(3)
        );
    }

}

record DocumentRecords() {
    public static final String CATEGORY_ID = "categoryId";
    public static final String PARENT_CATEGORY_ID = "parentCategoryId";
    public static final String ATTRIBUTE_KEY_ID = "attributeKeys.id";
    public static final String ATTRIBUTE_VALUE_ID = "attributeKeys.attributeValues.id";
    public static final String BARGAIN = "bargain";
    public static final String PRICE = "price";
    public static final String PRODUCT_CONDITION = "productCondition";
    public static final String LOCATION_NAME = "locationName";


    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String CATEGORY_NAME = "categoryName";
    public static final String PARENT_CATEGORY_NAME = "parentCategoryName";
}