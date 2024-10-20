package com.igriss.ListIn.mapper;

import com.igriss.ListIn.entity.Image;
import com.igriss.ListIn.entity.Post;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ImageMapper {

    public List<String> toImageUrls(List<Image> images) {
        return images.stream()
                .map(Image::getImageUrl)
                .collect(Collectors.toList());
    }

    public List<Image> toImageEntities(List<String> imageUrls, Post post) {
        return imageUrls.stream()
                .map(url -> Image.builder()
                        .imageUrl(url)
                        .post(post)
                        .build()
                ).collect(Collectors.toList());
    }
}
