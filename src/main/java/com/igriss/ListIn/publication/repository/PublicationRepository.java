package com.igriss.ListIn.publication.repository;

import com.igriss.ListIn.publication.entity.Publication;
import com.igriss.ListIn.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PublicationRepository extends JpaRepository<Publication, UUID> {
    Optional<Publication> findByIdOrderByDateUpdatedDesc(UUID id);

    Page<Publication> findAllBySeller(Pageable pageable, User seller);

    Page<Publication> findAllByOrderByDatePostedDesc(Pageable pageable);
}