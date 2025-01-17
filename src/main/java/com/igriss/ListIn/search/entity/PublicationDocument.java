package com.igriss.ListIn.search.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.igriss.ListIn.publication.enums.ProductCondition;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(indexName = "publications")
@JsonIgnoreProperties(ignoreUnknown = true)
public class PublicationDocument {

    @Id
    private UUID id;

    /*
    standard analyzer means saving coming data in form of tokens and
    in small cases by removing 'the' 'is' ...
    */
    @Field(type = FieldType.Text, analyzer = "standard")
    private String title;

    @Field(type = FieldType.Text, analyzer = "standard" )
    private String description;

    @Field(type = FieldType.Text, analyzer = "standard" )
    private String locationName;

    @Field(type = FieldType.Float)
    private Float price;

    @Field(type = FieldType.Boolean)
    private Boolean bargain;

    @Field(type = FieldType.Keyword)
    private ProductCondition productCondition;

    @Field(type = FieldType.Keyword)
    private UUID categoryId;

    @Field(type = FieldType.Text, analyzer = "standard" )
    private String categoryDescription;

    @Field(type = FieldType.Keyword)
    private UUID parentCategoryId;

    @Field(type = FieldType.Text, analyzer = "standard", storeNullValue = true)
    private String parentCategoryDescription;

    @Field(type = FieldType.Nested)
    private List<AttributeKeyDocument> attributeKeys;
}
