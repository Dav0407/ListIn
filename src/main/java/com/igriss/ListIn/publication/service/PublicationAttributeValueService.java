package com.igriss.ListIn.publication.service;

import com.igriss.ListIn.publication.entity.PublicationAttributeValue;

import java.util.UUID;

public interface PublicationAttributeValueService {
    void savePublicationAttributeValue(PublicationAttributeValue publicationAttributeValue);

    void deletePublicationAttributes(UUID publicationId);
}
