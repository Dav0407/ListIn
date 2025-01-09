package com.igriss.ListIn.search.repository;

import com.igriss.ListIn.publication.entity.Publication;
import com.igriss.ListIn.search.entity.PublicationDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.UUID;
public interface PublicationDocumentRepository extends ElasticsearchRepository<PublicationDocument, UUID> {

    @Query("""
            {
              "bool": {
                "should": [
                  {
                    "bool": {
                      "should": [
                        {
                          "match_phrase_prefix": {
                            "title": {
                              "query": "?0",
                              "max_expansions": 50
                            }
                          }
                        },
                        {
                          "match_phrase_prefix": {
                            "description": {
                              "query": "?0",
                              "max_expansions": 50
                            }
                          }
                        },
                        {
                          "match_phrase_prefix": {
                            "locationName": {
                              "query": "?0",
                              "max_expansions": 50
                            }
                          }
                        },
                        {
                          "match_phrase_prefix": {
                            "categoryName": {
                              "query": "?0",
                              "max_expansions": 50
                            }
                          }
                        },
                        {
                          "match_phrase_prefix": {
                            "categoryDescription": {
                              "query": "?0",
                              "max_expansions": 50
                            }
                          }
                        },
                        {
                          "match_phrase_prefix": {
                            "parentCategoryName": {
                              "query": "?0",
                              "max_expansions": 50
                            }
                          }
                        },
                        {
                          "match_phrase_prefix": {
                            "parentCategoryDescription": {
                              "query": "?0",
                              "max_expansions": 50
                            }
                          }
                        }
                      ]
                    }
                  },
                  {
                    "bool": {
                      "should": [
                        {
                          "fuzzy": {
                            "title": {
                              "value": "?0",
                              "fuzziness": "AUTO",
                              "prefix_length": 2,
                              "max_expansions": 50
                            }
                          }
                        },
                        {
                          "fuzzy": {
                            "description": {
                              "value": "?0",
                              "fuzziness": "AUTO",
                              "prefix_length": 2,
                              "max_expansions": 50
                            }
                          }
                        },
                        {
                          "fuzzy": {
                            "locationName": {
                              "value": "?0",
                              "fuzziness": "AUTO",
                              "prefix_length": 2,
                              "max_expansions": 50
                            }
                          }
                        },
                        {
                          "fuzzy": {
                            "categoryName": {
                              "value": "?0",
                              "fuzziness": "AUTO",
                              "prefix_length": 2,
                              "max_expansions": 50
                            }
                          }
                        }
                      ]
                    }
                  }
                ],
                "minimum_should_match": 1
              }
            }
            """)
    Page<PublicationDocument> searchByQuery(Pageable pageable, String query);
}