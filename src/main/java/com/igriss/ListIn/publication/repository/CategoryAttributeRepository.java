package com.igriss.ListIn.publication.repository;

import com.igriss.ListIn.publication.entity.CategoryAttribute;
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
                    ak.filter_text AS filterText,
                    ak.sub_filter_text AS subFilterText,
                    ak.filter_widget_type AS filterWidgetType,
                    ak.sub_filter_widget_type AS subFilterWidgetType,
                    ak.data_type AS dataType,
                    av.attribute_value_id AS attributeValueId,
                    av.value AS attributeValue,
                    av.value_uz AS attributeValueUz,
                    av.value_ru AS attributeValueRu,
                    ak.helper_text_uz AS helperTextUz,
                    ak.sub_helper_text_uz AS subHelperTextUz,
                    ak.helper_text_ru AS helperTextRu,
                    ak.sub_helper_text_ru AS subHelperTextRu,
                    ak.filter_text_uz AS filterTextUz,
                    ak.sub_filter_text_uz AS subFilterTextUz,
                    ak.filter_text_ru AS filterTextRu,
                    ak.sub_filter_text_ru AS subFilterTextRu,
                    ak.name_uz AS nameUz,
                    ak.name_ru AS nameRu
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

    Optional<CategoryAttribute> findByAttributeKey_Id(UUID attributeKey);

    List<CategoryAttribute> findByCategory_Id(UUID categoryId);
}