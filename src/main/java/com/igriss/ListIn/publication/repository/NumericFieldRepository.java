package com.igriss.ListIn.publication.repository;

import com.igriss.ListIn.publication.entity.NumericField;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NumericFieldRepository extends JpaRepository<NumericField, UUID> {
    List<NumericField> findAllByCategory_Id(UUID categoryId);
}
