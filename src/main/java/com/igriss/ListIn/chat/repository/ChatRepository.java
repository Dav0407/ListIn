package com.igriss.ListIn.chat.repository;


import com.igriss.ListIn.chat.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChatRepository extends JpaRepository<Chat, UUID> {

    Optional<Chat> findByPublication_IdAndBuyer_userIdAndSeller_userId(UUID publicationId, UUID buyerId, UUID sellerId);
    List<Chat> findByBuyer_userIdOrSeller_userId(UUID buyerId, UUID sellerId);
}