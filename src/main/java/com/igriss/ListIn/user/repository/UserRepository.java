package com.igriss.ListIn.user.repository;

import com.igriss.ListIn.user.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

   Optional<User> findByEmail(String email);

   void deleteByEmail(String email);

   @Modifying
   @Transactional
   @Query("UPDATE User u SET u.password = :password WHERE u.email = :email")
   void updatePassword(@Param("email") String email, @Param("password") String password);

   User getUserByUserId(UUID userId);

   @Modifying
   @Query(value = """
    UPDATE users
    SET from_time = CASE
            WHEN CAST(:fromTime AS time) IS NOT NULL THEN CAST(:fromTime AS time)
            ELSE from_time
        END,
        to_time = CASE
            WHEN CAST(:toTime AS time) IS NOT NULL THEN CAST(:toTime AS time)
            ELSE to_time
        END,
        phone_number = CASE
            WHEN CAST(:phoneNumber AS varchar) IS NOT NULL THEN CAST(:phoneNumber AS varchar)
            ELSE phone_number
        END,
        is_granted_for_precise_location = CASE
            WHEN CAST(:isGranted AS boolean) IS NOT NULL THEN CAST(:isGranted AS boolean)
            ELSE is_granted_for_precise_location
        END
    WHERE user_id = :userId
    """, nativeQuery = true)
   void updateContactDetails(
           @Param("userId") UUID userId,
           @Param("fromTime") LocalTime fromTime,
           @Param("toTime") LocalTime toTime,
           @Param("phoneNumber") String phoneNumber,
           @Param("isGranted") Boolean isGranted
   );

}

