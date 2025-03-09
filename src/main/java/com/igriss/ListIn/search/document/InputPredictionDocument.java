package com.igriss.ListIn.search.document;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.annotation.TypeAlias;
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
@TypeAlias("search_predictions")
@Document(indexName = "search_predictions")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.ALWAYS)
public class InputPredictionDocument {

    @Id
    private UUID id;

    private String childAttributeValue;

    @Field(storeNullValue = true)
    private UUID parentAttributeValueId;

    @Field(storeNullValue = true)
    private String parentAttributeValue;

    @Field(storeNullValue = true)
    private UUID parentAttributeKeyId;

    @Field(storeNullValue = true)
    private UUID childAttributeKeyId;

    private UUID parentCategoryId;

    private String parentCategoryName;

    private UUID categoryId;

    private String categoryName;

}
