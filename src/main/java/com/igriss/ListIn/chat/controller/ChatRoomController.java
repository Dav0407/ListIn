package com.igriss.ListIn.chat.controller;

import com.igriss.ListIn.chat.dto.ChatRoomResponseDTO;
import com.igriss.ListIn.chat.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @GetMapping("/chat-rooms/{userId}")
    public ResponseEntity<List<ChatRoomResponseDTO>> getChatRooms(@PathVariable UUID userId) {
        return ResponseEntity.ok(chatRoomService.getUserChatRooms(userId));
    }
}
