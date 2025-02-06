package com.igriss.ListIn.search.service.supplier;


import lombok.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SearchParams {
    private String input;
    private UUID parentCategory;
    private UUID category;
    private Boolean bargain;
    private String productCondition;
    private Float priceFrom;
    private Float priceTo;
    private String locationName;
    private Map<String, List<String>> filters;
    private Map<String, String[]> numericFilter;

}

class SearchFields {
    static final String CATEGORY_ID = "categoryId";
    static final String PARENT_CATEGORY_ID = "parentCategoryId";
    static final String ATTRIBUTE_KEY_ID = "attributeKeys.id";
    static final String ATTRIBUTE_VALUE_ID = "attributeKeys.attributeValues.id";
    static final String BARGAIN = "bargain";
    static final String PRICE = "price";
    static final String PRODUCT_CONDITION = "productCondition";
    static final String LOCATION_NAME = "locationName";

    static final String TITLE = "title^4";
    static final String DESCRIPTION = "description^2";
    static final String CATEGORY_NAME = "categoryName^1.5";
    static final String PARENT_CATEGORY_NAME = "parentCategoryName^1";

    public static final String NUMERIC_FIELDS = "numericFields";
    public static final String NUMERIC_FIELD_ID = "numericFields.fieldId";
    public static final String NUMERIC_FIELD_VALUE = "numericFields.value";
}
