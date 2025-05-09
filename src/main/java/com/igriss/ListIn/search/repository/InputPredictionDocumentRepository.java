package com.igriss.ListIn.search.repository;

import com.igriss.ListIn.search.document.InputPredictionDocument;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;
import java.util.UUID;

public interface InputPredictionDocumentRepository extends ElasticsearchRepository<InputPredictionDocument, UUID> {

    @Query("""
            {
              "bool": {
                "should": [
                  {
                    "match_phrase_prefix": {
                      "childAttributeValue": {
                        "query": "?0",
                        "slop": 3
                      }
                    }
                  },
                  {
                    "match_phrase_prefix": {
                      "parentAttributeValue": {
                        "query": "?0",
                        "slop": 3,
                        "boost": 0.5
                      }
                    }
                  }
                ]
              }
            }
            """)
    List<InputPredictionDocument> findByModelValueContainingIgnoreCase(String query, Pageable pageable);
}
