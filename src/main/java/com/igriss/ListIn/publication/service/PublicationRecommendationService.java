package com.igriss.ListIn.publication.service;

import com.igriss.ListIn.publication.entity.Publication;
import com.igriss.ListIn.user.entity.User;

import java.util.List;

public interface PublicationRecommendationService {
    List<Publication> getRecommendedPublications(User user, int page, int size);
}
