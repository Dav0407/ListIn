package com.igriss.ListIn.publication.repository;

import com.igriss.ListIn.publication.entity.CategoryAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CategoryAttributeRepository extends JpaRepository<CategoryAttribute, UUID> {
    @Query(value = """
                SELECT
                    ak.attribute_id AS attributeKeyId,
                    ak.name AS attributeKey,
                    ak.helper_text AS helperText,
                    ak.data_type AS dataType,
                    av.attribute_value_id AS attributeValueId,
                    av.value AS attributeValue
                FROM
                    category_attributes ca
                INNER JOIN
                    attribute_keys ak ON ca.attribute_id = ak.attribute_id
                LEFT JOIN
                    attribute_values av ON ak.attribute_id = av.attribute_id
                WHERE
                    ca.category_id = :categoryId
            """, nativeQuery = true)
    List<Object[]> findAttributeKeysAndValuesByCategoryId(@Param("categoryId") UUID categoryId);

}