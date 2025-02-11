package com.igriss.ListIn.publication.service;

import com.igriss.ListIn.publication.entity.PublicationAttributeValue;
import com.igriss.ListIn.publication.repository.PublicationAttributeValueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PublicationAttributeValueServiceImpl implements PublicationAttributeValueService {

    private final PublicationAttributeValueRepository repository;

    @Override
    public void savePublicationAttributeValue(PublicationAttributeValue publicationAttributeValue) {
        repository.save(publicationAttributeValue);
    }

    @Override
    public void deletePublicationAttributes(UUID publicationId) {
        repository.deleteAllByPublication_Id(publicationId);
    }
}
