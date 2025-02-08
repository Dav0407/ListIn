package com.igriss.ListIn.publication.repository;

import com.igriss.ListIn.publication.entity.NumericValue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface NumericValueRepository extends JpaRepository<NumericValue, UUID> {
    List<NumericValue> findAllByPublication_Id(UUID publicationId);

    List<NumericValue> findAllByPublication_IdIn(Collection<UUID> publicationIds);
}
