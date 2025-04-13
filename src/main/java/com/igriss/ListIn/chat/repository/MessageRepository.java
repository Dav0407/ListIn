package com.igriss.ListIn.chat.repository;

import com.igriss.ListIn.chat.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, UUID> {
    Page<Message> findByChat_IdOrderBySentAtAsc(UUID chatId, Pageable pageable);

    @Query("SELECT m FROM Message m WHERE m.chat.id = :chatId AND m.sender.userId != :viewerId AND m.deliveryStatus = 'DELIVERED'")
    List<Message> findUnviewedMessagesByChatIdAndViewerId(UUID chatId, UUID viewerId);

    @Modifying
    @Query("UPDATE Message m SET m.deliveryStatus = 'VIEWED' WHERE m.chat.id = :chatId AND m.sender.userId != :viewerId AND m.deliveryStatus = 'DELIVERED'")
    void markMessagesAsViewed(UUID chatId, UUID viewerId);
}