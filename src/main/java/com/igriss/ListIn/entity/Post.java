package com.igriss.ListIn.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private UUID id;

    private String title;
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "subcategory_id")
    private Subcategory subcategory;

    @ElementCollection
    @CollectionTable(name = "image_urls", joinColumns = @JoinColumn(name = "post_id"))
    @Column(name = "image_url")
    private List<String> imageUrls;  // Store URL or identifier for the post image

    @ElementCollection
    @CollectionTable(name = "post_attributes", joinColumns = @JoinColumn(name = "post_id"))
    @MapKeyJoinColumn(name = "attribute_id")
    @Column(name = "attribute_value")
    private Map<Attribute, String> attributes; // Map for storing attribute-value pairs for the post

    private double price;  // Assuming a price field for the post

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;  // Assuming you have a User entity for tracking who posted

    // Add additional fields as needed, like creation and update timestamps
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
