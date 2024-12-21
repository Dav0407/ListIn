package com.igriss.ListIn.publication.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PublicationType {

    BUSINESS_PUBLICATION("Publications from a sellers or market"),
    INDIVIDUAL_PUBLICATION("Publications from ordinary people"),
    ADVERTISEMENT_PUBLICATION("Publications from an advertisers or market");

    private final String description;
}
