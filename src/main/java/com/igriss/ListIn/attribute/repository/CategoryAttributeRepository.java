package com.igriss.ListIn.attribute.repository;

import com.igriss.ListIn.attribute.entity.CategoryAttribute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryAttributeRepository extends JpaRepository<CategoryAttribute, UUID> {
}