package com.igriss.ListIn.publication.repository;

import com.igriss.ListIn.publication.entity.CategoryAttribute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryAttributeRepository extends JpaRepository<CategoryAttribute, UUID> {
}