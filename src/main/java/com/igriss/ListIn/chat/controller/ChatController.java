package com.igriss.ListIn.chat.controller;

import com.igriss.ListIn.chat.entity.Chat;
import com.igriss.ListIn.chat.entity.Message;
import com.igriss.ListIn.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/chats")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @PostMapping("/create")

    public ResponseEntity<Chat> createChat(@RequestParam UUID publicationId, @RequestParam UUID buyerId) {
        Chat chat = chatService.createChat(publicationId, buyerId);
        return ResponseEntity.ok(chat);
    }

    @PostMapping("/{chatId}/message")

    public ResponseEntity<Message> sendMessage(@PathVariable UUID chatId, @RequestParam UUID senderId, @RequestBody String content) {
        Message message = chatService.sendMessage(chatId, senderId, content);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/{chatId}/view")

    public ResponseEntity<Void> markMessagesAsViewed(@PathVariable UUID chatId, @RequestParam UUID viewerId) {
        chatService.markChatMessagesAsViewed(chatId, viewerId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/{userId}")

    public ResponseEntity<List<Chat>> getUserChats(@PathVariable UUID userId) {
        return ResponseEntity.ok(chatService.getUserChats(userId));
    }

    @GetMapping("/{chatId}/messages")

    public ResponseEntity<Page<Message>> getChatMessages(@PathVariable UUID chatId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "50") int size) {
        return ResponseEntity.ok(chatService.getChatMessages(
                chatId, PageRequest.of(page, size, Sort.by("sentAt").ascending())
        ));
    }
}