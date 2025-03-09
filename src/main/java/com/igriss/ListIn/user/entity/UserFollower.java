package com.igriss.ListIn.user.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_followers")
public class UserFollower {

    @EmbeddedId
    private UserFollowerId id;

    @ManyToOne
    @MapsId("followerId")
    @JoinColumn(name = "follower_id")
    private User follower;

    @ManyToOne
    @MapsId("followingId")
    @JoinColumn(name = "following_id")
    private User following;

    @Builder
    public UserFollower(User follower, User following) {
        this.follower = follower;
        this.following = following;
        this.id = new UserFollowerId(follower.getUserId(), following.getUserId());
    }

    @Data
    @Embeddable
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserFollowerId implements Serializable {
        @Column(name = "follower_id")
        private UUID followerId;

        @Column(name = "following_id")
        private UUID followingId;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            UserFollowerId that = (UserFollowerId) o;
            return Objects.equals(followerId, that.followerId) &&
                    Objects.equals(followingId, that.followingId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(followerId, followingId);
        }
    }
}