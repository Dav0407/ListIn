package com.igriss.ListIn.service;

import com.igriss.ListIn.dto.main_dto.PostRequestDTO;
import com.igriss.ListIn.dto.main_dto.PostResponseDTO;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface PostService {

    PostResponseDTO createPost(PostRequestDTO requestDTO, UUID userId);

    PostResponseDTO getPostById(UUID postId);

    Page<PostResponseDTO> getAllPosts(int page, int size);

    PostResponseDTO updatePost(UUID postId, PostRequestDTO requestDTO, UUID userId);

    void deletePost(UUID postId);
}

