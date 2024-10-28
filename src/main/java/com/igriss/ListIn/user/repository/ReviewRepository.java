package com.igriss.ListIn.user.repository;

import com.igriss.ListIn.user.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
}