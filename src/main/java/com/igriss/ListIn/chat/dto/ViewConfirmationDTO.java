package com.igriss.ListIn.chat.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ViewConfirmationDTO {
    private UUID senderId;        // Original sender whose messages were viewed
    private List<UUID> messageIds; // IDs of messages that were viewed
}