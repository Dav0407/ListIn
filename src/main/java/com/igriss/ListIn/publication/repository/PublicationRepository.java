package com.igriss.ListIn.publication.repository;

import com.igriss.ListIn.publication.entity.Publication;
import com.igriss.ListIn.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PublicationRepository extends JpaRepository<Publication, UUID> {
    Publication findByIdOrderByDateUpdatedDesc(UUID id);

    List<Publication> findAllBySeller(User seller);
}