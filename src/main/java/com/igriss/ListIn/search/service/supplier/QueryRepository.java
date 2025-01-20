package com.igriss.ListIn.search.service.supplier;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.query_dsl.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
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
            "title",
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

        List<Query> queries = new ArrayList<>();

        if (filters != null)
            queries.addAll(getDeepQuery(filters));

        queries.add(matchCategoryQuery(pCategory, category));
        queries.add(getShallowQuery(bargain, productCondition, from, to, cleanedInput, noSpacesKeyword));

        return () -> BoolQuery.of(q -> q.should(queries))._toQuery();
    }

    public static Supplier<Query> shallowSearchQuerySupplier(
            String query,
            Boolean bargain,
            String productCondition,
            Float from,
            Float to) {

        String cleanedInput = preprocessInput(query);
        String noSpacesKeyword = cleanedInput.replace(" ", "");

        return () -> getShallowQuery(bargain, productCondition, from, to, cleanedInput, noSpacesKeyword);
    }

    public static Query searchFromInput(String query) {
       return   Query.of(q->q.matchPhrasePrefix(matchPhrasePrefixQuery(query)));
    }


    private static String preprocessInput(String input) {
        return input.trim()
                .replaceAll("[^а-яА-ЯёЁa-zA-Z0-9\\s]", "")
                .toLowerCase();
    }

    private static Query matchCategoryQuery(UUID pCategory, UUID category) {
        return Query.of(q -> q.bool(b -> b
                .must(m -> m.match(mustMatchParentCategoryId(pCategory)))
                .must(m -> m.match(mustMatchCategoryId(category)))
        ));
    }

    private static List<Query> getDeepQuery(Map<String, List<String>> filters) {
        return filters.entrySet().stream()
                .map(entry -> Query.of(q -> q.nested(n -> n
                        .path("attributeKeys")
                        .query(aq -> aq.bool(b -> b
                                .must(m -> m.match(mustMatchAttributeKeyId(entry.getKey())))
                                .must(m -> m.nested(vn -> vn
                                        .path("attributeKeys.attributeValues")
                                        .query(vq -> vq.bool(vb -> vb
                                                .must(vm -> vm.terms(mustMatchAttributeValueId(entry)))
                                        ))
                                ))
                        ))
                )))
                .toList();
    }

    private static Query getShallowQuery(
            Boolean bargain,
            String productCondition,
            Float from,
            Float to,
            String cleanedInput,
            String noSpacesKeyword) {

        return Query.of(q -> q.bool(b -> {

            if (bargain != null)
                b.must(m -> m.match(mustMatchBargain(bargain)));

            if (productCondition != null)
                b.must(m -> m.match(mustMatchCondition(productCondition)));

            if (from != null || to != null)
                addPriceRangeQueries(b, from, to);


            b.should(fieldsQuery(cleanedInput));
            b.should(fieldsQuery(noSpacesKeyword));
            b.should(fuzzyFieldsQuery(cleanedInput));
            b.should(matchPhraseFieldsQuery(cleanedInput));

            b.minimumShouldMatch("1");
            return b;
        }));
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

    private static List<Query> fieldsQuery(String value) {
        return SEARCHABLE_FIELDS.stream()
                .map(field -> {
                            if (field.equals("title") || field.equals("description"))
                                return Query.of(q -> q
                                        .wildcard(w -> w
                                                .field(field)
                                                .boost(1.0f)
                                                .caseInsensitive(true)
                                                .value("*" + value + "*")));
                            else
                                return Query.of(q -> q
                                        .wildcard(w -> w
                                                .field(field)
                                                .caseInsensitive(true)
                                                .value("*" + value + "*")));
                        }
                )
                .toList();
    }

    private static List<Query> fuzzyFieldsQuery(String value) {
        return SEARCHABLE_FIELDS.stream()
                .map(field -> Query.of(q -> q
                        .fuzzy(f -> f
                                .field(field)
                                .value(value)
                                .fuzziness("AUTO")
                                .prefixLength(3)
                                .maxExpansions(50))))
                .toList();
    }

    private static List<Query> matchPhraseFieldsQuery(String value) {
        return SEARCHABLE_FIELDS.stream()
                .map(field -> Query.of(q -> q
                        .matchPhrase(mp -> mp
                                .field(field)
                                .query(value))))
                .toList();
    }

    // Query builders
    private static MatchQuery mustMatchBargain(Boolean value) {
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

    private static MatchQuery mustMatchCondition(String query) {
        return MatchQuery.of(q -> q
                .field(PRODUCT_CONDITION)
                .query(query));
    }

    private static MatchQuery mustMatchAttributeKeyId(String query) {
        return MatchQuery.of(q -> q
                .field(ATTRIBUTE_KEY_ID)
                .query(query));
    }

    private static TermsQuery mustMatchAttributeValueId(Map.Entry<String, List<String>> query) {
        return TermsQuery.of(q -> q
                .field(ATTRIBUTE_VALUE_ID)
                .terms(tt -> tt
                        .value(query.getValue().stream()
                                .map(FieldValue::of)
                                .toList())));
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