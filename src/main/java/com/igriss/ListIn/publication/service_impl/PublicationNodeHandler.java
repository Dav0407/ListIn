package com.igriss.ListIn.publication.service_impl;

import com.igriss.ListIn.publication.dto.PublicationResponseDTO;
import com.igriss.ListIn.publication.mapper.PublicationMapper;
import com.igriss.ListIn.search.dto.PublicationNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PublicationNodeHandler {

    private PublicationResponseDTO waitingPublication;
    private final PublicationMapper publicationMapper;

    public List<PublicationNode> handlePublicationNodes(List<PublicationResponseDTO> responses, Boolean isLast) {
        List<PublicationNode> result = new ArrayList<>();

        for (PublicationResponseDTO current : responses) {
            if (current.getVideoUrl() != null) {
                result.add(publicationMapper.toPublicationNode(current, null));
                continue;
            }

            if (waitingPublication == null) {
                waitingPublication = current;
            } else {
                result.add(publicationMapper.toPublicationNode(waitingPublication, current));
                waitingPublication = null;
            }
        }

        if (isLast && waitingPublication != null) {
            result.add(publicationMapper.toPublicationNode(waitingPublication, null));
            waitingPublication = null;
        }

        if (!result.isEmpty()) {
            result.get(result.size() - 1).setIsLast(isLast);
        }

        return result;
    }
}
