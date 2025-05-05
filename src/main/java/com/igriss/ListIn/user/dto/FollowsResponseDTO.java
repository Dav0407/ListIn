package com.igriss.ListIn.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
@Builder
public class FollowsResponseDTO {
    UUID userId;
    String nickName;
    String profileImagePath;
    Long following;
    Long followers;
    Boolean isFollowing;
}
