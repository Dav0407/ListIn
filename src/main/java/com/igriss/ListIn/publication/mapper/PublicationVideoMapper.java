package com.igriss.ListIn.publication.mapper;

import com.igriss.ListIn.publication.entity.Publication;
import com.igriss.ListIn.publication.entity.PublicationVideo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class PublicationVideoMapper {

    public PublicationVideo toProductVideo(String videoUrl, Publication publication) {
        return PublicationVideo.builder()
                .videoUrl(videoUrl)
                .publication(publication)
                .videoName(videoUrl.substring(videoUrl.lastIndexOf("/") + 1))
                .build();
    }

}
