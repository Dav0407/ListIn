package com.igriss.ListIn.chat.controller;

import com.igriss.ListIn.user.dto.WSUserResponseDTO;
import com.igriss.ListIn.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatUserController {

    private final UserService userService;

    @MessageMapping("/user.connectUser")
    @SendTo("/user/public")
    public WSUserResponseDTO connectUser(@Payload WSUserResponseDTO user) {

        return userService.connect(user.getEmail());
    }

    @MessageMapping("/user.disconnectUser")
    @SendTo("/user/public")
    public WSUserResponseDTO disconnectUser(@Payload WSUserResponseDTO user) {

        return userService.disconnect(user.getEmail());
    }

    @GetMapping("/connected-users") // todo -> to be modified for custom channel per user
    public ResponseEntity<List<WSUserResponseDTO>> getConnectedUsers() {
        return ResponseEntity.ok(userService.findConnectedUsers());
    }
}
