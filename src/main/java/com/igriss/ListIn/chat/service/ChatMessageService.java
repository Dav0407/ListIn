package com.igriss.ListIn.chat.service;

import com.igriss.ListIn.chat.dto.ChatMessageRequestDTO;
import com.igriss.ListIn.chat.dto.ChatMessageResponseDTO;
import com.igriss.ListIn.chat.entity.ChatMessage;
import com.igriss.ListIn.chat.entity.ChatRoom;
import com.igriss.ListIn.chat.enums.DeliveryStatus;
import com.igriss.ListIn.chat.mapper.ChatMessageMapper;
import com.igriss.ListIn.chat.repository.ChatMessageRepository;
import com.igriss.ListIn.exceptions.ResourceNotFoundException;
import com.igriss.ListIn.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
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

        ChatRoom chatRoomReflection = chatRoomService.getChatRoom(request.getPublicationId(), recipientId, senderId, true)
                .orElseThrow(() -> new ResourceNotFoundException("Chat room not found"));

        User originalSender = chatRoom.getSender();
        User originalRecipient = chatRoom.getRecipient();

        ChatMessage chatMessage = ChatMessage.builder()
                .id(request.getId())
                .chatRoom(chatRoom)
                .sender(originalSender)
                .recipient(originalRecipient)
                .content(request.getContent())
                .status(DeliveryStatus.DELIVERED)
                .build();

        ChatMessage chatMessageReflection = ChatMessage.builder()
                .id(generateReflectionId(request.getId()))
                .chatRoom(chatRoomReflection)
                .sender(originalSender)
                .recipient(originalRecipient)
                .content(request.getContent())
                .status(DeliveryStatus.DELIVERED)
                .build();

        chatMessageRepository.save(chatMessageReflection);
        chatRoomService.incrementUnreadCount(chatRoomReflection);

        return chatMessageRepository.save(chatMessage);
    }


    public List<UUID> markMessagesAsViewed(List<UUID> messageIds) {
        if (messageIds == null || messageIds.isEmpty()) return Collections.emptyList();

        List<ChatMessage> messages = chatMessageRepository.findAllById(messageIds);
        messages.forEach(message -> message.setStatus(DeliveryStatus.VIEWED));
        chatMessageRepository.saveAll(messages);


        List<UUID> originalIdFromReflection = getOriginalIdFromReflection(messageIds);

        List<ChatMessage> messagesReflections = chatMessageRepository.findAllById(originalIdFromReflection);
        messagesReflections.forEach(messagesReflection -> messagesReflection.setStatus(DeliveryStatus.VIEWED));
        chatMessageRepository.saveAll(messagesReflections);

        // Group messages by chatRoomId
        Map<UUID, Long> countByChatRoom = messages.stream()
                .collect(Collectors.groupingBy(m -> m.getChatRoom().getId(), Collectors.counting()));

        countByChatRoom.forEach((chatRoomId, count) ->
                chatRoomService.getChatRoomById(chatRoomId)
                        .ifPresent(chatRoom ->
                                chatRoomService.decrementUnreadCount(chatRoom, count)
                        )
        );

        return originalIdFromReflection;
    }

    public List<ChatMessageResponseDTO> findChatMessages(UUID publicationId, UUID senderId, UUID recipientId) {
        Optional<ChatRoom> chatRoomOptional = chatRoomService.getChatRoom(publicationId, senderId, recipientId, false);

        if (chatRoomOptional.isEmpty()) {
            return Collections.emptyList();
        }

        ChatRoom chatRoom = chatRoomOptional.get();
        return chatMessageRepository.findByChatRoom_Id(chatRoom.getId()).stream()
                .map(chatMessageMapper::toDTO).toList();
    }

    public Optional<ChatMessage> findLastMessage(String chatRoomId) {
        return chatMessageRepository.findTopByChatRoom_ChatRoomIdOrderByCreatedAtDesc(chatRoomId);
    }


    private UUID generateReflectionId(UUID originalId) {
        String originalIdStr = originalId.toString();
        String reflectionIdStr = originalIdStr + "-reflection";

        return UUID.nameUUIDFromBytes(reflectionIdStr.getBytes());
    }

    private List<UUID> getOriginalIdFromReflection(List<UUID> reflectionIds) {
        try {
            return reflectionIds.stream()
                    .map(id -> UUID.fromString(
                            id.toString().replace("-reflection", "")))
                    .toList();
        } catch (IllegalArgumentException e) {
            log.warn(e.getMessage());
            return Collections.emptyList();
        }
    }

}
