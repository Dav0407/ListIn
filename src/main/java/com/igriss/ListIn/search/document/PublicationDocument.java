package com.igriss.ListIn.search.document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.igriss.ListIn.publication.enums.ProductCondition;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TypeAlias("publications")
@Document(indexName = "publications")
@JsonIgnoreProperties(ignoreUnknown = true)
public class PublicationDocument {

    @Id
    private UUID id;

    @Field(type = FieldType.Text, analyzer = "standard")
    private String title;

    @Field(type = FieldType.Text, analyzer = "standard")
    private String description;

    @Field(type = FieldType.Text, analyzer = "standard")
    private String locationName;

    @Field(type = FieldType.Float)
    private Float price;

    @Field(type = FieldType.Boolean)
    private Boolean bargain;

    @Field(type = FieldType.Keyword)
    private String sellerType;

    @Field(type = FieldType.Keyword)
    private ProductCondition productCondition;

    @Field(type = FieldType.Keyword)
    private UUID categoryId;

    @Field(type = FieldType.Text, analyzer = "standard")
    private String categoryDescription;

    @Field(type = FieldType.Keyword)
    private UUID parentCategoryId;

    @Field(type = FieldType.Text, analyzer = "standard", storeNullValue = true)
    private String parentCategoryDescription;

    @Field(type = FieldType.Nested)
    private List<AttributeKeyDocument> attributeKeys;

    @Field(type = FieldType.Nested)
    @Builder.Default
    private List<NumericFieldDocument> numericFields = new ArrayList<>();

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @TypeAlias("numericFields")
    @Document(indexName = "numericFields")
    @JsonIgnoreProperties(ignoreUnknown = true)
    static public class NumericFieldDocument {
        private UUID fieldId;
        private String value;
    }


}
