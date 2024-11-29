package com.igriss.ListIn.publication.service;

import com.igriss.ListIn.publication.entity.PublicationAttributeValue;
import com.igriss.ListIn.publication.repository.PublicationAttributeValueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PublicationAttributeValueServiceImpl implements PublicationAttributeValueService {

    private final PublicationAttributeValueRepository repository;

    @Override
    public void savePublicationAttributeValue(PublicationAttributeValue publicationAttributeValue) {
        repository.save(publicationAttributeValue);
    }
}
