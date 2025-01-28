package com.igriss.ListIn.search.repository;

import com.igriss.ListIn.search.document.PublicationDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.UUID;

public interface PublicationDocumentRepository extends ElasticsearchRepository<PublicationDocument, UUID> {
}