package com.igriss.ListIn.repository;

import com.igriss.ListIn.entity.Category;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category,UUID> {
    void deleteById(@NonNull UUID id);
}
