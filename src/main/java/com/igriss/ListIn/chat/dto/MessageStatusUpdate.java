package com.igriss.ListIn.chat.dto;

import com.igriss.ListIn.chat.enums.DeliveryStatus;
import lombok.Data;

import java.util.UUID;

@Data
public class MessageStatusUpdate {
    private UUID messageId;
    private DeliveryStatus status;

    public MessageStatusUpdate(UUID messageId, DeliveryStatus status) {
        this.messageId = messageId;
        this.status = status;
    }
}