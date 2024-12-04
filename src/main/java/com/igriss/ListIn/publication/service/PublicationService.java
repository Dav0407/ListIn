package com.igriss.ListIn.publication.service;

import com.igriss.ListIn.publication.dto.PublicationRequestDTO;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface PublicationService {
    UUID savePublication(PublicationRequestDTO request, Authentication connectedUser);
}
