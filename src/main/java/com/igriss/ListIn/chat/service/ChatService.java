package com.igriss.ListIn.chat.service;

import com.igriss.ListIn.chat.dto.MessageStatusUpdate;
import com.igriss.ListIn.chat.entity.Chat;
import com.igriss.ListIn.chat.entity.Message;
import com.igriss.ListIn.chat.enums.DeliveryStatus;
import com.igriss.ListIn.chat.repository.ChatRepository;
import com.igriss.ListIn.chat.repository.MessageRepository;
import com.igriss.ListIn.publication.entity.Publication;
import com.igriss.ListIn.publication.repository.PublicationRepository;
import com.igriss.ListIn.user.entity.User;
import com.igriss.ListIn.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;
    private final PublicationRepository publicationRepository;
    private final UserRepository userRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private final SimpMessagingTemplate messagingTemplate;

    private static final String CHATS_CACHE_KEY = "user:chats:";
    private static final String MESSAGES_CACHE_KEY = "chat:messages:";

    @Transactional
    public Chat createChat(UUID publicationId, UUID buyerId) {
        Publication publication = publicationRepository.findById(publicationId)
                .orElseThrow(() -> new IllegalArgumentException("Publication not found"));
        User buyer = userRepository.findById(buyerId)
                .orElseThrow(() -> new IllegalArgumentException("Buyer not found"));
        User seller = publication.getSeller();

        if (buyer.getUserId().equals(seller.getUserId())) {
            throw new IllegalArgumentException("Cannot create chat with yourself");
        }

        return chatRepository.findByPublication_IdAndBuyer_userIdAndSeller_userId(publicationId, buyerId, seller.getUserId())
                .orElseGet(() -> {
                    Chat chat = Chat.builder()
                            .publication(publication)
                            .buyer(buyer)
                            .seller(seller)
                            .build();
                    return chatRepository.save(chat);
                });
    }

    @Transactional
    public Message sendMessage(UUID chatId, UUID senderId, String content) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new IllegalArgumentException("Chat not found"));
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (!sender.getUserId().equals(chat.getBuyer().getUserId()) &&
                !sender.getUserId().equals(chat.getSeller().getUserId())) {
            throw new IllegalArgumentException("Sender is not part of this chat");
        }

        Message message = Message.builder()
                .chat(chat)
                .sender(sender)
                .content(content)
                .deliveryStatus(DeliveryStatus.DELIVERED)
                .build();
        message = messageRepository.save(message);

        // Invalidate cache
        redisTemplate.delete(MESSAGES_CACHE_KEY + chatId + "*");

        return message;
    }

    @Transactional
    public void markChatMessagesAsViewed(UUID chatId, UUID viewerId) {
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new IllegalArgumentException("Chat not found"));

        if (!viewerId.equals(chat.getBuyer().getUserId()) &&
                !viewerId.equals(chat.getSeller().getUserId())) {
            throw new IllegalArgumentException("Viewer is not part of this chat");
        }

        // Find unviewed messages to notify sender
        List<Message> unviewedMessages = messageRepository.findUnviewedMessagesByChatIdAndViewerId(chatId, viewerId);
        UUID senderId = viewerId.equals(chat.getBuyer().getUserId()) ?
                chat.getSeller().getUserId() : chat.getBuyer().getUserId();

        // Mark messages as viewed
        messageRepository.markMessagesAsViewed(chatId, viewerId);

        // Notify sender of viewed status
        unviewedMessages.forEach(message ->
                messagingTemplate.convertAndSend(
                        "/topic/chat/" + chatId + "/status/" + senderId,
                        new MessageStatusUpdate(message.getId(), DeliveryStatus.VIEWED)
                )
        );

        // Invalidate cache
        redisTemplate.delete(MESSAGES_CACHE_KEY + chatId + "*");
    }

    public List<Chat> getUserChats(UUID userId) {
        String cacheKey = CHATS_CACHE_KEY + userId;
        @SuppressWarnings("unchecked")
        List<Chat> cached = (List<Chat>) redisTemplate.opsForValue().get(cacheKey);
        if (cached != null) {
            return cached;
        }

        List<Chat> chats = chatRepository.findByBuyer_userIdOrSeller_userId(userId, userId);
        redisTemplate.opsForValue().set(cacheKey, chats, 5, TimeUnit.MINUTES);
        return chats;
    }

    public Page<Message> getChatMessages(UUID chatId, Pageable pageable) {
        String cacheKey = MESSAGES_CACHE_KEY + chatId + ":" + pageable.getPageNumber();
        @SuppressWarnings("unchecked")
        Page<Message> cached = (Page<Message>) redisTemplate.opsForValue().get(cacheKey);
        if (cached != null) {
            return cached;
        }

        Page<Message> messages = messageRepository.findByChat_IdOrderBySentAtAsc(chatId, pageable);
        redisTemplate.opsForValue().set(cacheKey, messages, 10, TimeUnit.MINUTES);
        return messages;
    }
}
