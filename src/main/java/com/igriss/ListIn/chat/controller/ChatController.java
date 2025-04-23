package com.igriss.ListIn.chat.controller;

import com.igriss.ListIn.chat.dto.ChatMessageRequestDTO;
import com.igriss.ListIn.chat.dto.ChatMessageResponseDTO;
import com.igriss.ListIn.chat.entity.ChatMessage;
import com.igriss.ListIn.chat.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageService chatMessageService;

    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessageRequestDTO request) {
        ChatMessage savedMsg = chatMessageService.save(request);

        ChatMessageResponseDTO response = ChatMessageResponseDTO.builder()
                .id(savedMsg.getId())
                .senderId(savedMsg.getSender().getUserId())
                .recipientId(savedMsg.getRecipient().getUserId())
                .content(savedMsg.getContent())
                .status(savedMsg.getStatus())
                .sentAt(savedMsg.getCreatedAt())
                .updatedAt(savedMsg.getUpdatedAt())
                .build();

        messagingTemplate.convertAndSendToUser(request.getRecipientId().toString(), "/queue/messages", response);
    }

    @GetMapping("/messages/{publicationId}/{senderId}/{recipientId}")
    public ResponseEntity<List<ChatMessage>> findChatMessages(@PathVariable UUID publicationId, @PathVariable UUID senderId, @PathVariable UUID recipientId) {
        return ResponseEntity.ok(chatMessageService.findChatMessages(publicationId, senderId, recipientId));
    }

}
