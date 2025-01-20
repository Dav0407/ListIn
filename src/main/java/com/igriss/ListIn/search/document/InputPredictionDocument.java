package com.igriss.ListIn.search.document;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "search_predictions")
@JsonIgnoreProperties(ignoreUnknown = true)
public class InputPredictionDocument {

    @Id
    private UUID id;

    @Field(type = FieldType.Search_As_You_Type)
    private String model;

    private UUID parentCategoryId;

    private UUID categoryId;

}