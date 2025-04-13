package com.igriss.ListIn.chat.controller;

import com.igriss.ListIn.chat.dto.MessageDTO;
import com.igriss.ListIn.chat.entity.Message;
import com.igriss.ListIn.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class WebSocketChatController {
    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload MessageDTO messageDTO) {
        Message message = chatService.sendMessage(
                messageDTO.getChatId(),
                messageDTO.getSenderId(),
                messageDTO.getContent()
        );
        messagingTemplate.convertAndSend(
                "/topic/chat/" + messageDTO.getChatId(),
                message
        );
    }
}