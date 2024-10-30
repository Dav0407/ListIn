package com.igriss.ListIn.publication.service;

import com.igriss.ListIn.publication.dto.PublicationUploadRequestDTO;
import org.springframework.security.core.Authentication;

public interface PublicationService {
    void savePublication(PublicationUploadRequestDTO request, Authentication connectedUser);
}
