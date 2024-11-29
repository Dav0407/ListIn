package com.igriss.ListIn.publication.repository;

import com.igriss.ListIn.publication.entity.static_entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    @Query(nativeQuery = true, value = "select categories.category_id from categories where categories.category_name = :name")
    Optional<UUID> getIdByName(String name);

    Category findByName(String parentCategory);

    @Query(nativeQuery = true, value = "select * from categories where categories.parent_id IS NULL")
    List<Category> findAllParentCategories();

    List<Category> findAllByParentCategory_Id(UUID id);
}