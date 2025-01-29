package com.igriss.ListIn.search.service;

import com.igriss.ListIn.exceptions.SearchQueryException;
import com.igriss.ListIn.publication.entity.CategoryAttribute;
import com.igriss.ListIn.publication.repository.AttributeValueRepository;
import com.igriss.ListIn.publication.repository.CategoryAttributeRepository;
import com.igriss.ListIn.search.document.InputPredictionDocument;
import com.igriss.ListIn.search.dto.InputPredictionRequestDTO;
import com.igriss.ListIn.search.dto.InputPredictionResponseDTO;
import com.igriss.ListIn.search.repository.InputPredictionDocumentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class InputPredictionServiceImpl implements InputPredictionService {

    private final InputPredictionDocumentRepository predictionDocumentRepository;

    private final AttributeValueRepository attributeValueRepository;
    private final CategoryAttributeRepository categoryAttributeRepository;

    private final List<String> models = List.of(
            "Smartphone Brand Model",
            "Laptop Brand Model",
            "Macbook Model",
            "Smartwatch Brand Model",
            "Tablet Brand Model",
            "PC Brand Model",
            "Console Brand Model"
    );

    @Override
    public List<InputPredictionResponseDTO> getInputPredictions(String model) {
        PageRequest pageRequest = PageRequest.of(0, 5);

        List<InputPredictionDocument> matchPhrase = predictionDocumentRepository.findByModelValueContainingIgnoreCase(model, pageRequest);

        return matchPhrase.stream().map(document ->
                        InputPredictionResponseDTO.builder()
                                .modelId(document.getId())
                                .modelValue(document.getModelValue())
                                .brandId(document.getBrandId())
                                .brandValue(document.getBrandValue())
                                .parentCategoryId(document.getParentCategoryId())
                                .categoryId(document.getCategoryId())
                                .build())
                .toList();
    }

    @Override
    public void saveInputMatchDocument(List<InputPredictionRequestDTO> matchRequestDTO) {
        predictionDocumentRepository.saveAll(matchRequestDTO.stream().map(
                matching -> InputPredictionDocument.builder()
                        .categoryId(matching.getCategoryId())
                        .parentCategoryId(matching.getParentCategoryId())
                        .modelValue(matching.getModel())
                        .build()
        ).toList());
    }

    @Override
    public String indexInputPredictionDocuments() {
        models.forEach(model -> attributeValueRepository.findByAttributeKey_Name(model).parallelStream().forEach(av -> {

            CategoryAttribute ca = categoryAttributeRepository.findByAttributeKey_Id(av.getAttributeKey().getId())
                    .orElseThrow(() -> new RuntimeException("Error during the elastic indexation occurred"));

            predictionDocumentRepository.save(InputPredictionDocument.builder()
                    .id(av.getId())
                    .modelValue(av.getValue())
                    .brandId(av.getParentValue() != null ? av.getParentValue().getId() : null)
                    .brandValue(av.getParentValue() != null ? av.getParentValue().getValue() : null)
                    .categoryId(ca.getCategory().getId())
                    .parentCategoryId(ca.getCategory().getParentCategory().getId())
                    .build()
            );

        }));
        return "Success";
    }
}
