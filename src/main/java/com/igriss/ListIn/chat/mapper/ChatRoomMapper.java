package com.igriss.ListIn.chat.mapper;

import com.igriss.ListIn.chat.dto.ChatRoomResponseDTO;
import com.igriss.ListIn.chat.entity.ChatRoom;
import com.igriss.ListIn.publication.entity.Publication;
import com.igriss.ListIn.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class ChatRoomMapper {
    public ChatRoomResponseDTO toDTO(ChatRoom chatRoom) {

        Publication publication = chatRoom.getPublication();
        User recipient = chatRoom.getRecipient();

        return ChatRoomResponseDTO.builder()
                .chatRoomId(chatRoom.getChatRoomId())
                .publicationId(publication.getId())
                .publicationTitle(publication.getTitle())
                .publicationPrice(publication.getPrice())
                .recipientId(recipient.getUserId())
                .recipientImagePath(recipient.getProfileImagePath())
                .recipientNickname(recipient.getNickName())
                .unreadMessages(chatRoom.getUnreadMessagesCount())
                .build();
    }
}
