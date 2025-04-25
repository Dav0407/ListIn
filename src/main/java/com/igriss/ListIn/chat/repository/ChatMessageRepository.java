package com.igriss.ListIn.chat.repository;

import com.igriss.ListIn.chat.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, UUID> {
    List<ChatMessage> findByChatRoom_ChatRoomId(String chatRoomChatRoomId);
    Optional<ChatMessage> findTopByChatRoom_ChatRoomIdOrderByCreatedAtDesc(String chatRoomId);
}