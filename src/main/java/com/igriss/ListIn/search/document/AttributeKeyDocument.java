package com.igriss.ListIn.search.document;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TypeAlias("attributeKeyDocument")
@Document(indexName = "attribute_key_documents")
@JsonIgnoreProperties(ignoreUnknown = true)
public class AttributeKeyDocument {

    @Id
    private UUID id;

    @Field(type = FieldType.Text, analyzer = "standard")
    private String key;

    @Field(type = FieldType.Nested)
    private List<AttributeValueDocument> attributeValues;
}
