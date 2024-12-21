package com.igriss.ListIn.publication.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductCondition {
    NEW_PRODUCT("Brand new, unopened items."),
    USED_PRODUCT("Used with visible signs of wear.");

    private final String description;
}
