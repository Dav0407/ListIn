package com.igriss.ListIn.chat.mapper;

import com.igriss.ListIn.chat.dto.ChatMessageResponseDTO;
import com.igriss.ListIn.chat.entity.ChatMessage;
import org.springframework.stereotype.Component;

@Component
public class ChatMessageMapper {

    public ChatMessageResponseDTO toDTO(ChatMessage chatMessage) {
        return ChatMessageResponseDTO.builder()
                .id(chatMessage.getId())
                .senderId(chatMessage.getSender().getUserId())
                .recipientId(chatMessage.getRecipient().getUserId())
                .publicationId(chatMessage.getChatRoom().getPublication().getId())
                .content(chatMessage.getContent())
                .status(chatMessage.getStatus())
                .sentAt(chatMessage.getCreatedAt())
                .updatedAt(chatMessage.getUpdatedAt())
                .build();
    }
}