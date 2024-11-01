package com.igriss.ListIn.publication.mapper;

import com.igriss.ListIn.publication.entity.PublicationStatus;
import org.springframework.stereotype.Service;

@Service
public class PublicationStatusMapper {

    public PublicationStatus toPublicationStatus(String publicationStatus) {
        return PublicationStatus.builder()
                .name(publicationStatus)
                .build();
    }
}
