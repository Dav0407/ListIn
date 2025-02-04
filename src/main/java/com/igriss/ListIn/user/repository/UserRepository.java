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

    Optional<User> getUserByUserId(UUID userId);

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

    @Modifying
    @Query(value = """
            UPDATE users
            SET  profile_image_path = CASE
                    WHEN CAST(:profileImagePath AS varchar) IS NOT NULL THEN CAST(:profileImagePath AS varchar)
                    ELSE profile_image_path
                END,
                  nick_name = CASE
                    WHEN CAST(:nickName AS varchar) IS NOT NULL THEN CAST(:nickName AS varchar)
                    ELSE nick_name
                END,
                phone_number = CASE
                    WHEN CAST(:phoneNumber AS varchar) IS NOT NULL THEN CAST(:phoneNumber AS varchar)
                    ELSE phone_number
                END,
                is_granted_for_precise_location = CASE
                    WHEN CAST(:isGrantedForPreciseLocation AS boolean) IS NOT NULL THEN CAST(:isGrantedForPreciseLocation AS boolean)
                    ELSE is_granted_for_precise_location
                END,
                from_time = CASE
                    WHEN CAST(:fromTime AS time) IS NOT NULL THEN CAST(:fromTime AS time)
                    ELSE from_time
                END,
                to_time = CASE
                    WHEN CAST(:toTime AS time) IS NOT NULL THEN CAST(:toTime AS time)
                    ELSE to_time
                END,
                location_name = CASE
                    WHEN CAST(:locationName AS varchar) IS NOT NULL THEN CAST(:locationName AS varchar)
                    ELSE location_name
                END,
                longitude = CASE
                    WHEN CAST(:longitude AS double precision) IS NOT NULL THEN CAST(:longitude AS double precision)
                    ELSE longitude
                END,
                latitude = CASE
                    WHEN CAST(:latitude AS double precision) IS NOT NULL THEN CAST(:latitude AS double precision)
                    ELSE latitude
                END
            WHERE user_id = :userId
            """, nativeQuery = true)
    Integer updateUserDetails(
            @Param("userId") UUID userId,
            @Param("nickName") String nickName,
            @Param("profileImagePath") String profileImagePath,
            @Param("phoneNumber") String phoneNumber,
            @Param("isGrantedForPreciseLocation") Boolean isGrantedForPreciseLocation,
            @Param("locationName") String locationName,
            @Param("longitude") Double longitude,
            @Param("latitude") Double latitude,
            @Param("fromTime") LocalTime fromTime,
            @Param("toTime") LocalTime toTime
    );

    @Modifying
    @Query(value = "UPDATE users SET role = :role WHERE user_id = :userId", nativeQuery = true)
    void updateUserRole(@Param("userId") UUID userId, @Param("role") String role);

    @Modifying
    @Query(value = """
            UPDATE users
            SET following = following + 1
            WHERE user_id = :userId
            """, nativeQuery = true)
    Integer incrementFollowingColumn(UUID userId);

    @Modifying
    @Query(value = """
            UPDATE users
            SET following = following - 1
            WHERE user_id = :userId
            """, nativeQuery = true)
    Integer decrementFollowingColumn(UUID userId);
}

