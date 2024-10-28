package com.igriss.ListIn.publication.repository;

import com.igriss.ListIn.publication.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
}