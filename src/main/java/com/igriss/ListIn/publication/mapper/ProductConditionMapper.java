package com.igriss.ListIn.publication.mapper;

import com.igriss.ListIn.publication.entity.static_entity.ProductCondition;
import com.igriss.ListIn.publication.repository.ProductConditionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductConditionMapper {

    private final ProductConditionRepository productConditionRepository;

    public ProductCondition toProductCondition(String productConditionName) {

        UUID productConditionId = productConditionRepository.getIdByName(productConditionName)
                .orElseThrow(() -> new RuntimeException("No product condition provided"));

        return ProductCondition.builder()
                .id(productConditionId)
                .name(productConditionName)
                .build();
    }
}
