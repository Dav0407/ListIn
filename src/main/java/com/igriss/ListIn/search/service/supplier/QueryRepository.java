package com.igriss.ListIn.search.service.supplier;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.query_dsl.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;

@Slf4j
public class QueryRepository {

    private static final String CATEGORY_NAME = "categoryName";
    private static final String PARENT_CATEGORY_NAME = "parentCategoryName";
    private static final String ATTRIBUTE_KEY_ID = "attributeKeys.id";
    private static final String ATTRIBUTE_VALUE_ID = "attributeKeys.attributeValues.id";

    private static final List<String> FIELDS = List.of(
            "title",
            "description",
            "locationName",
            "categoryName",
            "categoryDescription",
            "parentCategoryName",
            "parentCategoryDescription"
    );

    private static final String BARGAIN = "bargain";
    private static final String PRICE = "price";
    private static final String PRODUCT_CONDITION = "productCondition";


    public static Supplier<Query> deepSearchQuerySupplier(String input, String pCategory, String category, Boolean bargain, String productCondition, Float from, Float to, Map<String, List<String>> filters) {

        String cleaned = preprocessInput(input);
        String noSpacesKeyword = cleaned.replace(" ", "");

        List<Query> query = getDeepQuery(filters);

        query.add(matchCategoryQuery(pCategory, category));

        query.add(getShallowQuery(bargain, productCondition, from, to, cleaned, noSpacesKeyword));

        return () -> BoolQuery.of(
                        q -> q.should(query))
                ._toQuery();

    }

    private static Query matchCategoryQuery(String pCategory, String category) {
        return Query.of(q -> q.bool(
                b -> {
                    b.must(m -> m.match(mustMatchCategoryName(category)));
                    b.must(m -> m.match(mustMatchCategoryName(pCategory)));
                    return b;
                }
        ));
    }

    public static Supplier<Query> shallowSearchQuerySupplier(String query, Boolean bargain, String productCondition, Float from, Float to) {
        String cleaned = preprocessInput(query);
        String noSpacesKeyword = cleaned.replace(" ", "");

        Query query1 = getShallowQuery(bargain, productCondition, from, to, cleaned, noSpacesKeyword);
        return () -> query1;
    }

    private static List<Query> getDeepQuery(Map<String, List<String>> filters) {
        return filters.entrySet().stream()
                .map(entry -> Query.of(q -> q.nested(n -> n
                        .path("attributeKeys")
                        .query(aq -> aq.bool(b -> {

                            b.must(m -> m.match(mustMatchAttributeKeyId(entry.getKey())));

                            b.must(m -> m.nested(vn -> vn
                                    .path("attributeKeys.attributeValues")
                                    .query(vq -> vq.bool(vb -> {
                                        vb.must(vm -> vm.terms(mustMatchAttributeValueId(entry)));
                                        return vb;
                                    }))
                            ));
                            return b;
                        }))
                ))).toList();
    }

    private static Query getShallowQuery(Boolean bargain, String productCondition, Float from, Float to, String cleaned, String noSpacesKeyword) {
        return Query.of(q -> q.bool(b -> {

                    if (bargain != null)
                        b.must(m -> m.match(mustMatchBargain(bargain)));

                    if (productCondition != null)
                        b.must(m -> m.match(mustMatchCondition(productCondition)));

                    if (from != null && to != null)
                        b.must(m -> m.range(
                                mustMatchRangeQuery(String.valueOf(from), String.valueOf(to))));
                    else if (from != null)
                        b.must(m -> m.range(
                                mustMatchRangeFromQuery(String.valueOf(from))));
                    else if (to != null)
                        b.must(m -> m.range(
                                mustMatchRangeToQuery(String.valueOf(to))));

                    if (from != null && to != null)
                        b.must(m -> m.range(
                                mustMatchRangeQuery(String.valueOf(from), String.valueOf(to))));

                    b.should(fieldsQuery(cleaned));

                    b.should(fieldsQuery(noSpacesKeyword));

                    b.should(fuzzyFieldsQuery(cleaned));

                    b.should(matchPhraseFieldsQuery(cleaned));

                    b.minimumShouldMatch("1");
                    return b;
                }
        ));
    }

    private static String preprocessInput(String input) {
        return input.trim().replaceAll("[^а-яА-ЯёЁa-zA-Z0-9\\s]", "").toLowerCase();
    }

    private static List<Query> fieldsQuery(String value) {
        return QueryRepository.FIELDS.stream()
                .map(field -> Query.of(q -> q
                        .wildcard(w -> w
                                .field(field)
                                .caseInsensitive(true)
                                .value("*" + value + "*"))))
                .toList();
    }

    private static List<Query> fuzzyFieldsQuery(String value) {
        return QueryRepository.FIELDS.stream()
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
        return QueryRepository.FIELDS.stream()
                .map(field -> Query.of(q -> q
                        .matchPhrase(mp -> mp
                                .field(field)
                                .query(value))))
                .toList();
    }

    private static MatchQuery mustMatchBargain(Boolean value) {
        return MatchQuery.of(q -> q
                .field(BARGAIN)
                .query(value)
        );
    }

    private static RangeQuery mustMatchRangeFromQuery(String from) {
        return RangeQuery.of(q -> q
                .field(PRICE)
                .from(from)
        );
    }

    private static RangeQuery mustMatchRangeToQuery(String to) {
        return RangeQuery.of(q -> q
                .field(PRICE)
                .from(to)
        );
    }

    private static RangeQuery mustMatchRangeQuery(String from, String to) {
        return RangeQuery.of(q -> q
                .field(PRICE)
                .from(from)
                .to(to)
        );
    }

    private static MatchQuery mustMatchCondition(String query) {
        return MatchQuery.of(q -> q
                .field(PRODUCT_CONDITION)
                .query(query)
        );
    }

    private static MatchQuery mustMatchAttributeKeyId(String query) {
        return MatchQuery.of(q -> q
                .field(ATTRIBUTE_KEY_ID)
                .query(query)
        );
    }

    private static TermsQuery mustMatchAttributeValueId(Map.Entry<String, List<String>> query) {
        return TermsQuery.of(q -> q
                .field(ATTRIBUTE_VALUE_ID)
                .terms(tt -> tt
                        .value(query.getValue().stream()
                                .map(FieldValue::of)
                                .toList())));
    }

    private static MatchQuery mustMatchCategoryName(String query) {
        return MatchQuery.of(q -> q
                .field(CATEGORY_NAME)
                .query(query)
        );
    }

    private static MatchQuery mustMatchParentCategoryName(String query) {
        return MatchQuery.of(q -> q
                .field(PARENT_CATEGORY_NAME)
                .query(query)
        );
    }

}
