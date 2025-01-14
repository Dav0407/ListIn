package com.igriss.ListIn.publication.mapper;

import com.igriss.ListIn.publication.entity.PublicationVideo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class PublicationVideoMapper {

    public PublicationVideo toProductVideo(String videoUrl) {
        return PublicationVideo.builder()
                .videoUrl(videoUrl)
                .videoName(videoUrl.substring(videoUrl.lastIndexOf("/") + 1))
                .build();
    }

}
