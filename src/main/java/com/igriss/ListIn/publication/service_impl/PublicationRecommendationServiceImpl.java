package com.igriss.ListIn.publication.service_impl;

import com.igriss.ListIn.publication.entity.Publication;
import com.igriss.ListIn.publication.entity.static_entity.Category;
import com.igriss.ListIn.publication.enums.PublicationStatus;
import com.igriss.ListIn.publication.repository.PublicationLikeRepository;
import com.igriss.ListIn.publication.repository.PublicationRepository;
import com.igriss.ListIn.publication.service.PublicationRecommendationService;
import com.igriss.ListIn.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PublicationRecommendationServiceImpl implements PublicationRecommendationService {


    private final PublicationRepository publicationRepository;


    private final PublicationLikeRepository publicationLikeRepository;


    @Override
    public List<Publication> getRecommendedPublications(User user, int page, int size) {

        List<Publication> recommendations = new ArrayList<>();
        recommendations.addAll(getUserCategoryBasedRecommendations(user, size/2));
//        recommendations.addAll(getLocationBasedRecommendations(user, size/2));

        return recommendations.stream()
                .distinct()
                .sorted(this::calculateRelevanceScore)
                .limit(size)
                .toList();
    }

    private List<Publication> getUserCategoryBasedRecommendations(User user, int limit) {

        List<Category> userPreferredCategories = getUserPreferredCategories(user);

        return publicationRepository.findByCategoryInAndPublicationStatus(
                userPreferredCategories,
                PublicationStatus.ACTIVE,
                PageRequest.of(0, limit, Sort.by("datePosted").descending())
        ).getContent();
    }

//    private List<Publication> getLocationBasedRecommendations(User user, int limit) {
//
//        return publicationRepository.findByLocationNearby(
//                user.getLatitude(),
//                user.getLongitude(),
//                20.0,
//                PageRequest.of(0, limit)
//
//        );
//    }

    private List<Category> getUserPreferredCategories(User user) {

        return publicationLikeRepository.findAllByUser(user)
                .stream()
                .map(like -> like.getPublication().getCategory())
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ))
                .entrySet()
                .stream()
                .sorted(Map.Entry.<Category, Long>comparingByValue().reversed())
                .limit(5)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private int calculateRelevanceScore(Publication p1, Publication p2) {

        long p1Score = p1.getLikes() * 2 + p1.getViews();
        long p2Score = p2.getLikes() * 2 + p2.getViews();


        if (p1.getDatePosted().isAfter(LocalDateTime.now().minusDays(7))) {
            p1Score *= 2;
        }
        if (p2.getDatePosted().isAfter(LocalDateTime.now().minusDays(7))) {
            p2Score *= 2;
        }

        return Long.compare(p2Score, p1Score);
    }

}
