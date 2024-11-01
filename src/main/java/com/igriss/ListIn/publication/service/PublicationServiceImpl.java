package com.igriss.ListIn.publication.service;

import com.igriss.ListIn.config.Images.S3Service;
import com.igriss.ListIn.publication.dto.PublicationRequestDTO;
import com.igriss.ListIn.publication.mapper.PublicationMapper;
import com.igriss.ListIn.publication.repository.PublicationRepository;
import com.igriss.ListIn.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

import java.lang.reflect.Array;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class PublicationServiceImpl implements PublicationService {
    private final S3Service service;
    private final PublicationRepository publicationRepository;
    private final PublicationMapper publicationMapper;

    @Override
    public void savePublication(PublicationRequestDTO request, Authentication connectedUser, List<MultipartFile> multipartFiles) {
        User user = (User) connectedUser.getPrincipal();
        log.info(user.toString());
        List<String> userId = List.of(String.valueOf(user.getUserId()));


        service.uploadFile(userId,multipartFiles);

    }
}
