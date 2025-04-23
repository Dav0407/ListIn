package com.igriss.ListIn.chat.service;

import com.igriss.ListIn.chat.entity.ChatRoom;
import com.igriss.ListIn.chat.repository.ChatRoomRepository;
import com.igriss.ListIn.publication.entity.Publication;
import com.igriss.ListIn.publication.service.PublicationService;
import com.igriss.ListIn.user.entity.User;
import com.igriss.ListIn.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final UserService userService;
    private final PublicationService publicationService;

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
}
