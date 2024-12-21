package com.igriss.ListIn.publication.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PublicationStatus {

    ACTIVE("The listing is live and visible to other users. This means it can be viewed, and buyers can contact the seller."),
    PAUSED("The listing is temporarily hidden but can be reactivated by the seller at any time. Useful if the seller wants to take a break from receiving inquiries."),
    PENDING_APPROVAL("The listing is waiting for approval from admins or moderators before being visible to the public."),
    EXPIRED("Some platforms automatically expire listings after a certain period. Sellers may need to renew or repost the listing if they want it active again.");

    private final String description;
}
