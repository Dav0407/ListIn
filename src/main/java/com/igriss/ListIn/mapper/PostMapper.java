package com.igriss.ListIn.mapper;

import com.igriss.ListIn.dto.main_dto.PostRequestDTO;
import com.igriss.ListIn.dto.main_dto.PostResponseDTO;
import com.igriss.ListIn.entity.Category;
import com.igriss.ListIn.entity.Post;
import com.igriss.ListIn.entity.Subcategory;
import com.igriss.ListIn.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PostMapper {

    private final CategoryMapper categoryMapper;
    private final SubcategoryMapper subcategoryMapper;
    private final AttributeMapper attributeMapper;
    private final ImageMapper imageMapper;
    private final UserMapper userMapper;

    public PostMapper(CategoryMapper categoryMapper, SubcategoryMapper subcategoryMapper,
                      AttributeMapper attributeMapper, ImageMapper imageMapper, UserMapper userMapper) {
        this.categoryMapper = categoryMapper;
        this.subcategoryMapper = subcategoryMapper;
        this.attributeMapper = attributeMapper;
        this.imageMapper = imageMapper;
        this.userMapper = userMapper;
    }

    public PostResponseDTO toPostResponseDTO(Post post) {
        return PostResponseDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .description(post.getDescription())
                .price(post.getPrice())
                .category(categoryMapper.toCategoryDTO(post.getCategory()))
                .subcategory(subcategoryMapper.toSubcategoryDTO(post.getSubcategory()))
                .attributes(attributeMapper.toAttributeDTOs(post.getAttributes()))
                .imageUrls(imageMapper.toImageUrls(post.getImages()))
                .user(userMapper.toUserDTO(post.getUser()))
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }

    public Post toPostEntity(PostRequestDTO postRequestDTO, Category category, Subcategory subcategory, User user) {
        Post post = Post.builder()
                .title(postRequestDTO.getTitle())
                .description(postRequestDTO.getDescription())
                .price(postRequestDTO.getPrice())
                .category(category)
                .subcategory(subcategory)
                .user(user)
                .createdAt(LocalDateTime.now())
                .build();

        post.setAttributes(attributeMapper.toAttributeEntities(postRequestDTO.getAttributes(), post));
        post.setImages(imageMapper.toImageEntities(postRequestDTO.getImageUrls(), post));

        return post;
    }

    public void updatePostEntity(Post post, PostRequestDTO postRequestDTO, Category category, Subcategory subcategory) {
        post.setTitle(postRequestDTO.getTitle());
        post.setDescription(postRequestDTO.getDescription());
        post.setPrice(postRequestDTO.getPrice());
        post.setCategory(category);
        post.setSubcategory(subcategory);

        post.setAttributes(attributeMapper.toAttributeEntities(postRequestDTO.getAttributes(), post));
        post.setImages(imageMapper.toImageEntities(postRequestDTO.getImageUrls(), post));
    }
}
