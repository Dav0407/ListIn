package com.igriss.ListIn.publication.mapper;

import com.igriss.ListIn.publication.entity.PublicationType;
import org.springframework.stereotype.Service;

@Service
public class PublicationTypeMapper {

    public PublicationType toPublicationType(String publicationType) {
        return PublicationType.builder()
                .name(publicationType)
                .build();
    }
}
