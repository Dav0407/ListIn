package com.igriss.ListIn.user.dto;

import java.util.UUID;


public interface FollowsDTO {
    UUID getUserId();
    String getNickName();
    String getProfileImagePath();
    Long getFollowing();
    Long getFollowers();
}