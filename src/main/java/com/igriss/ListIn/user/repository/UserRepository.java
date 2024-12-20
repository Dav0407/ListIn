package com.igriss.ListIn.user.repository;

import com.igriss.ListIn.user.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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
}

