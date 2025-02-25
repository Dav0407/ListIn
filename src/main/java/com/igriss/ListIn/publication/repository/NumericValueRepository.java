package com.igriss.ListIn.publication.repository;

import com.igriss.ListIn.publication.entity.NumericValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface NumericValueRepository extends JpaRepository<NumericValue, UUID> {
    List<NumericValue> findAllByPublication_Id(UUID publicationId);

    @Modifying
    @Query("DELETE FROM NumericValue nm WHERE nm.publication.id = :publicationId")
    void deleteAllByPublication_Id(UUID publicationId);
}
