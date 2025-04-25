package com.igriss.ListIn.chat.service;

import com.igriss.ListIn.chat.dto.ChatMessageRequestDTO;
import com.igriss.ListIn.chat.dto.ChatMessageResponseDTO;
import com.igriss.ListIn.chat.entity.ChatMessage;
import com.igriss.ListIn.chat.entity.ChatRoom;
import com.igriss.ListIn.chat.enums.DeliveryStatus;
import com.igriss.ListIn.chat.mapper.ChatMessageMapper;
import com.igriss.ListIn.chat.repository.ChatMessageRepository;
import com.igriss.ListIn.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageMapper chatMessageMapper;
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomService chatRoomService;

    public ChatMessage save(ChatMessageRequestDTO request) {

        UUID senderId = request.getSenderId();
        UUID recipientId = request.getRecipientId();

        ChatRoom chatRoom = chatRoomService.getChatRoom(request.getPublicationId(), senderId, recipientId, true)
                .orElseThrow(() -> new ResourceNotFoundException("Chat room not found"));

        ChatMessage chatMessage = ChatMessage.builder()
                .chatRoom(chatRoom)
                .sender(chatRoom.getSender())
                .recipient(chatRoom.getRecipient())
                .content(request.getContent())
                .status(DeliveryStatus.DELIVERED)
                .build();

        return chatMessageRepository.save(chatMessage);
    }

    public List<ChatMessageResponseDTO> findChatMessages(UUID publicationId, UUID senderId, UUID recipientId) {

        ChatRoom chatRoom = chatRoomService.getChatRoom(publicationId, senderId, recipientId, false)
                .orElseThrow(() -> new ResourceNotFoundException("Chat room not found"));

        return chatMessageRepository.findByChatRoom_ChatRoomId(chatRoom.getChatRoomId())
                .stream().map(chatMessageMapper::toDTO).toList();
    }

    public Optional<ChatMessage> findLastMessage(String chatRoomId) {
        return chatMessageRepository.findTopByChatRoom_ChatRoomIdOrderByCreatedAtDesc(chatRoomId);
    }

}
