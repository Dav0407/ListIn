package com.igriss.ListIn.search.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(indexName = "attributeValueDocument")
@JsonIgnoreProperties(ignoreUnknown = true)
public class AttributeValueDocument {
    @Id
    private UUID id;

    @Field(type = FieldType.Text, analyzer = "standard")
    private String value;
}
