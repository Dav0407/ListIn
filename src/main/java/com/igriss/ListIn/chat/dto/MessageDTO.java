package com.igriss.ListIn.chat.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class MessageDTO {
    private UUID chatId;
    private UUID senderId;
    private String content;
}