package com.igriss.ListIn.search.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class FoundPublicationsDTO {

    private Long foundPublications;

    private Float priceFrom;

    private Float priceTo;
}
