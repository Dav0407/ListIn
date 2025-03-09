package com.igriss.ListIn.search.repository;

import com.igriss.ListIn.search.document.PublicationDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PublicationDocumentRepository extends ElasticsearchRepository<PublicationDocument, UUID> {
}