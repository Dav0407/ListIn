package com.igriss.ListIn.chat.service;

import com.igriss.ListIn.chat.mapper.ChatMessageMapper;
import com.igriss.ListIn.chat.mapper.ChatRoomMapper;
import com.igriss.ListIn.chat.dto.ChatRoomResponseDTO;
import com.igriss.ListIn.chat.entity.ChatRoom;
import com.igriss.ListIn.chat.repository.ChatRoomRepository;
import com.igriss.ListIn.exceptions.ResourceNotFoundException;
import com.igriss.ListIn.publication.entity.Publication;
import com.igriss.ListIn.publication.service.ProductFileService;
import com.igriss.ListIn.publication.service.PublicationService;
import com.igriss.ListIn.user.entity.User;
import com.igriss.ListIn.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatMessageMapper messageMapper;
    private final ChatMessageService chatMessageService;
    private final ChatRoomMapper chatRoomMapper;
    private final ChatRoomRepository chatRoomRepository;
    private final UserService userService;
    private final PublicationService publicationService;
    private final ProductFileService productFileService;

    public Optional<ChatRoom> getChatRoom(UUID publicationId, UUID senderId, UUID recipientId, boolean createNewRoomIfNotExists) {
        return chatRoomRepository.findByPublication_IdAndSender_UserIdAndRecipient_UserId(publicationId, senderId, recipientId)
                .or(() -> {
                    if (createNewRoomIfNotExists) {
                        ChatRoom chatRoom = createRoom(publicationId, senderId, recipientId);
                        return Optional.of(chatRoom);
                    }
                    return Optional.empty();
                });
    }

    private ChatRoom createRoom(UUID publicationId, UUID senderId, UUID recipientId) {

        String chatId = String.format("%s_%s_%s", publicationId, senderId, recipientId);

        Publication publication = publicationService.getById(publicationId);

        User sender = userService.getById(senderId);
        User recipient = userService.getById(recipientId);

        ChatRoom senderRecipient = ChatRoom.builder()
                .chatRoomId(chatId)
                .sender(sender)
                .recipient(recipient)
                .publication(publication)
                .build();

        ChatRoom recipientSender = ChatRoom.builder()
                .chatRoomId(chatId)
                .sender(recipient)
                .recipient(sender)
                .publication(publication)
                .build();

        chatRoomRepository.save(senderRecipient);
        chatRoomRepository.save(recipientSender);

        return senderRecipient;

    }

    public List<ChatRoomResponseDTO> getUserChatRooms(UUID userId) {
        List<ChatRoom> chatRooms = chatRoomRepository.findBySender_UserId(userId);

        return chatRooms.stream()
                .map(chatRoomMapper::toDTO)
                .peek(chatRoomDTO -> {
                    // Set publication image
                    String imageUrl = productFileService.findImagesByPublicationId(chatRoomDTO.getPublicationId())
                            .stream()
                            .findFirst()
                            .orElseThrow(() -> new ResourceNotFoundException("Publication images not found"))
                            .getImageUrl();
                    chatRoomDTO.setPublicationImagePath(imageUrl);

                    // Set last message
                    chatMessageService.findLastMessage(chatRoomDTO.getChatRoomId())
                            .map(messageMapper::toDTO)
                            .ifPresent(chatRoomDTO::setLastMessage);

                })
                .toList();
    }


}
