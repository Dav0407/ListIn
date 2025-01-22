package com.igriss.ListIn.search.dto;

import com.igriss.ListIn.publication.dto.PublicationResponseDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class PublicationNode {

    private Boolean isSponsored;

    private PublicationResponseDTO firstPublication;

    private PublicationResponseDTO secondPublication;

}
