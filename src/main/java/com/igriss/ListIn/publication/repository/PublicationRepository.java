package com.igriss.ListIn.publication.repository;

import com.igriss.ListIn.publication.entity.Publication;
import com.igriss.ListIn.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PublicationRepository extends JpaRepository<Publication, UUID> {
    Optional<Publication> findByIdOrderByDateUpdatedDesc(UUID id);

    Page<Publication> findAllBySeller(Pageable pageable, User seller);

    Page<Publication> findAllByOrderByDatePostedDesc(Pageable pageable);


    @Modifying(clearAutomatically = true)
    @Query(value = """
            UPDATE publications
            SET  title = CASE
                    WHEN CAST(:title AS varchar) IS NOT NULL THEN CAST(:title AS varchar)
                    ELSE title
                END,
                  description = CASE
                    WHEN CAST(:description AS varchar) IS NOT NULL THEN CAST(:description AS varchar)
                    ELSE description
                END,
                price = CASE
                    WHEN CAST(:price AS float) IS NOT NULL THEN CAST(:price AS float)
                    ELSE price
                END,
                bargain = CASE
                    WHEN CAST(:bargain AS boolean) IS NOT NULL THEN CAST(:bargain AS boolean)
                    ELSE bargain
                END,
                product_condition = CASE
                    WHEN CAST(:productCondition AS varchar) IS NOT NULL THEN CAST(:productCondition AS varchar)
                    ELSE product_condition
                END
            WHERE publication_id = :publicationId
            """, nativeQuery = true)
    Integer updatePublicationById(UUID publicationId, String title, String description, Float price, Boolean bargain, String productCondition);

    Page<Publication> findAllByCategory_ParentCategory_Id(UUID parentCategoryId, Pageable pageable);

    Page<Publication> findAllBySeller_UserId(UUID sellerUserId, Pageable pageable);
}