package com.igriss.ListIn.publication.mapper;

import com.igriss.ListIn.publication.entity.static_entity.PublicationStatus;
import com.igriss.ListIn.publication.repository.PublicationStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PublicationStatusMapper {

    private final PublicationStatusRepository publicationStatusRepository;

    public PublicationStatus toPublicationStatus(String publicationStatus) {
        UUID publicationId = publicationStatusRepository.getIdByName(publicationStatus)
                .orElseThrow(() -> new RuntimeException("Publication status not found"));
        return PublicationStatus.builder()
                .id(publicationId)
                .name(publicationStatus)
                .build();
    }
}
