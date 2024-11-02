package com.igriss.ListIn.publication.mapper;

import com.igriss.ListIn.publication.entity.static_entity.PublicationType;
import com.igriss.ListIn.publication.repository.PublicationTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PublicationTypeMapper {

    private final PublicationTypeRepository publicationTypeRepository;

    public PublicationType toPublicationType(String publicationType) {

        UUID publicationTypeId = publicationTypeRepository.getIdByName(publicationType)
                .orElseThrow(() -> new RuntimeException("Publication type " + publicationType + " not found"));
        return PublicationType.builder()
                .id(publicationTypeId)
                .name(publicationType)
                .build();
    }
}
