package com.igriss.ListIn.publication.repository;

import com.igriss.ListIn.publication.entity.AttributeKey;
import com.igriss.ListIn.publication.entity.CategoryAttribute;
import com.igriss.ListIn.publication.entity.static_entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryAttributeRepository extends JpaRepository<CategoryAttribute, UUID> {
    @Query(value = """
                SELECT
                    ak.attribute_id AS attributeKeyId,
                    ak.name AS attributeKey,
                    ak.helper_text AS helperText,
                    ak.sub_helper_text AS subHelperText,
                    ak.widget_type AS widgetType,
                    ak.sub_widget_type AS subWidgetType,
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
                    ca.category_id = :categoryId AND av.parent_id IS NULL
            """, nativeQuery = true)
    List<Object[]> findAttributeKeysAndValuesByCategoryId(@Param("categoryId") UUID categoryId);

    Optional<CategoryAttribute> findByCategoryAndAttributeKey(Category category, AttributeKey attributeKey);

    List<CategoryAttribute> findByCategory_Id(UUID categoryId);
}