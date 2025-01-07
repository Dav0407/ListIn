package com.igriss.ListIn.search.service.supplier;

import co.elastic.clients.elasticsearch._types.query_dsl.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.function.Supplier;

@Slf4j
public class QuerySupplier {

    //constants for field names which are going to be queried while search
    private static final List<String> FIELDS = List.of(
            "title",
            "description",
            "locationName",
            "categoryName",
            "categoryDescription",
            "parentCategoryName",
            "parentCategoryDescription"
    );

    public static Supplier<Query> querySearchSupplier(String query) {
        String cleaned = preprocessInput(query);
        String noSpacesKeyword = cleaned.replace(" ", "");
        log.info("{}  {}", cleaned, noSpacesKeyword);
        //generating query by combining all
        Query query1 = Query.of(q -> q.bool(b -> b
                .should(fieldsQuery(cleaned))
                .should(fieldsQuery(noSpacesKeyword))
                .should(fuzzyFieldsQuery(cleaned))
                .should(matchPhraseFieldsQuery(cleaned))
                .minimumShouldMatch("1")
        ));
        log.info("{}",query1);


        return () -> query1;
    }

    //removing all the letters except (a-z) (a-A) (а-я) (А-Я) (0-9) and spaces
    private static String preprocessInput(String input) {
        return input.trim().replaceAll("[^а-яА-ЯёЁa-zA-Z0-9\\s]", "").toLowerCase();
    }
    /**
    user can type the between texts and search with teh help of it
    for example if the querying text is 'Phon' or 'smart' or etc.
    then it is considered as a chunk of 'Sample iPhone smartphone'
     */
    private static List<Query> fieldsQuery(String value) {
        return QuerySupplier.FIELDS.stream()
                .map(field -> Query.of(q -> q
                        .wildcard(w -> w
                                .field(field)
                                .caseInsensitive(true)
                                .value("*" + value + "*"))))
                .toList();
    }

    /**
     fuzzy querying is used when user types incorrectly some letters of word
     in this case user can type 2-3 letters by mistake.
     however to be considered, prefix with 3 letters has to match
     */
    private static List<Query> fuzzyFieldsQuery(String value) {
        return QuerySupplier.FIELDS.stream()
                .map(field -> Query.of(q -> q
                        .fuzzy(f -> f
                                .field(field)
                                .value(value)
                                .fuzziness("AUTO")
                                .prefixLength(3)
                                .maxExpansions(50))))
                .toList();
    }
    /**
    match phrase queries is used when user tries to fetch with full text query
    like 'iPhone 15 Pro Max', it is needed because
     field querying done by dividing query into small tokens or chunks
    */
    private static List<Query> matchPhraseFieldsQuery(String value) {
        return QuerySupplier.FIELDS.stream()
                .map(field -> Query.of(q -> q
                        .matchPhrase(mp -> mp
                                .field(field)
                                .query(value))))
                .toList();
    }

}
