package com.igriss.ListIn.search.service.supplier;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.query_dsl.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

@Slf4j
public class QueryRepository {
    private static final String CATEGORY_ID = "categoryId";
    private static final String PARENT_CATEGORY_ID = "parentCategoryId";
    private static final String ATTRIBUTE_KEY_ID = "attributeKeys.id";
    private static final String ATTRIBUTE_VALUE_ID = "attributeKeys.attributeValues.id";
    private static final String BARGAIN = "bargain";
    private static final String PRICE = "price";
    private static final String PRODUCT_CONDITION = "productCondition";

    private static final List<String> SEARCHABLE_FIELDS = List.of(
            "title^4",
            "description",
            "locationName",
            "categoryName",
            "categoryDescription",
            "parentCategoryName",
            "parentCategoryDescription"
    );

    public static Supplier<Query> deepSearchQuerySupplier(
            String input,
            UUID pCategory,
            UUID category,
            Boolean bargain,
            String productCondition,
            Float from,
            Float to,
            Map<String, List<String>> filters) {
        String cleanedInput = preprocessInput(input);
        String noSpacesKeyword = cleanedInput.replace(" ", "");

        return () -> BoolQuery.of(b -> {

            if (filters != null)
                getDeepQuery(filters, b);

            matchCategoryQuery(pCategory, category, b);

            getShallowQuery(bargain, productCondition, from, to, cleanedInput, noSpacesKeyword, b);

            return b;
        })._toQuery();
    }

    public static Supplier<Query> shallowSearchQuerySupplier(
            String query,
            Boolean bargain,
            String productCondition,
            Float from,
            Float to) {

        String cleanedInput = preprocessInput(query);
        String noSpacesKeyword = cleanedInput.replace(" ", "");

        return () -> BoolQuery.of(b -> {
            getShallowQuery(bargain, productCondition, from, to, cleanedInput, noSpacesKeyword, b);
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
        filters.forEach((key, values) -> {
            b.filter(q -> q.nested(n -> n
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
            ));
        });
    }


    private static void getShallowQuery(
            Boolean bargain,
            String productCondition,
            Float from,
            Float to,
            String cleanedInput,
            String noSpacesKeyword,
            BoolQuery.Builder b
    ) {
        if (bargain != null)
            b.filter(m -> m.match(filterBargain(bargain)));

        if (productCondition != null)
            b.filter(m -> m.match(filterCondition(productCondition)));

        if (from != null || to != null)
            addPriceRangeQueries(b, from, to);


        b.filter(matchPhrasePrefixesQuery(cleanedInput));


        b.minimumShouldMatch("1");
    }

    private static void addPriceRangeQueries(BoolQuery.Builder builder, Float from, Float to) {
        if (from != null && to != null) {
            builder.must(m -> m.range(mustMatchRangeQuery(String.valueOf(from), String.valueOf(to))));
        } else if (from != null) {
            builder.must(m -> m.range(mustMatchRangeFromQuery(String.valueOf(from))));
        } else if (to != null) {
            builder.must(m -> m.range(mustMatchRangeToQuery(String.valueOf(to))));
        }
    }

    private static List<Query> matchPhrasePrefixesQuery(String query) {
        return SEARCHABLE_FIELDS.stream().map(field -> Query.of(q -> q.matchPhrasePrefix(MatchPhrasePrefixQuery.of(m -> m
                .field(field)
                .query(query)
                .maxExpansions(50)
                .slop(3)
        )))).toList();
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