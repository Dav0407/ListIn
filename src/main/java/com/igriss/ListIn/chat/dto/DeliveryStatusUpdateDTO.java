package com.igriss.ListIn.chat.dto;

import com.igriss.ListIn.chat.enums.DeliveryStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

// DTO for sending delivery status updates
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryStatusUpdateDTO {
    private List<UUID> messageIds;
    private DeliveryStatus status;
    private LocalDateTime updatedAt;
}