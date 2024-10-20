package com.igriss.ListIn.service_impl;

import com.igriss.ListIn.dto.main_dto.CategoryDTO;
import com.igriss.ListIn.dto.main_dto.SubcategoryDTO;
import com.igriss.ListIn.dto.main_dto.PostRequestDTO;
import com.igriss.ListIn.dto.main_dto.PostResponseDTO;
import com.igriss.ListIn.entity.Category;
import com.igriss.ListIn.entity.Post;
import com.igriss.ListIn.entity.Subcategory;
import com.igriss.ListIn.entity.User;
import com.igriss.ListIn.exceptions.ResourceNotFoundException;
import com.igriss.ListIn.exceptions.UnauthorizedAccessException;
import com.igriss.ListIn.mapper.PostMapper;
import com.igriss.ListIn.repository.PostRepository;
import com.igriss.ListIn.service.CategoryService;
import com.igriss.ListIn.service.PostService;
import com.igriss.ListIn.service.SubcategoryService;
import com.igriss.ListIn.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final CategoryService categoryService;
    private final SubcategoryService subcategoryService;
    private final UserService userService;
    private final PostMapper postMapper;

    @Override
    public PostResponseDTO createPost(PostRequestDTO postRequestDTO, UUID userId) {
        // Fetch DTOs
        CategoryDTO categoryDTO = categoryService.getCategoryById(postRequestDTO.getCategoryId());
        SubcategoryDTO subcategoryDTO = subcategoryService.getSubcategoryById(postRequestDTO.getSubcategoryId());
        User user = userService.getUserById(userId);

        // Convert DTOs to Entities
        Category category = convertCategoryDTOToEntity(categoryDTO);
        Subcategory subcategory = convertSubcategoryDTOToEntity(subcategoryDTO);

        // Map to Post Entity
        Post post = postMapper.toPostEntity(postRequestDTO, category, subcategory, user);
        post.setCreatedAt(LocalDateTime.now());

        // Save the post
        Post savedPost = postRepository.save(post);

        return postMapper.toPostResponseDTO(savedPost);
    }

    @Override
    public PostResponseDTO updatePost(UUID postId, PostRequestDTO postRequestDTO, UUID userId) {
        // Fetch the existing post
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with ID: " + postId));

        // Check if the user is authorized (optional, based on your logic)
        if (!post.getUser().getUserId().equals(userId)) {
            throw new UnauthorizedAccessException("You are not allowed to update this post.");
        }

        // Fetch DTOs and convert to entities
        CategoryDTO categoryDTO = categoryService.getCategoryById(postRequestDTO.getCategoryId());
        SubcategoryDTO subcategoryDTO = subcategoryService.getSubcategoryById(postRequestDTO.getSubcategoryId());
        Category category = convertCategoryDTOToEntity(categoryDTO);
        Subcategory subcategory = convertSubcategoryDTOToEntity(subcategoryDTO);

        // Update post details
        postMapper.updatePostEntity(post, postRequestDTO, category, subcategory);

        // Save updated post
        post.setUpdatedAt(LocalDateTime.now());
        Post updatedPost = postRepository.save(post);

        return postMapper.toPostResponseDTO(updatedPost);
    }

    @Override
    public Page<PostResponseDTO> getAllPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> postsPage = postRepository.findAll(pageable);
        return postsPage.map(postMapper::toPostResponseDTO);
    }

    @Override
    public PostResponseDTO getPostById(UUID postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with ID: " + postId));
        return postMapper.toPostResponseDTO(post);
    }

    @Override
    public void deletePost(UUID postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found with ID: " + postId));
        postRepository.delete(post);
    }

    // Helper methods to convert DTOs to Entities
    private Category convertCategoryDTOToEntity(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setId(categoryDTO.getId());
        category.setName(categoryDTO.getName());
        // Set other fields as necessary
        return category;
    }

    private Subcategory convertSubcategoryDTOToEntity(SubcategoryDTO subcategoryDTO) {
        Subcategory subcategory = new Subcategory();
        subcategory.setId(subcategoryDTO.getId());
        subcategory.setName(subcategoryDTO.getName());
        // Set other fields as necessary
        return subcategory;
    }
}
