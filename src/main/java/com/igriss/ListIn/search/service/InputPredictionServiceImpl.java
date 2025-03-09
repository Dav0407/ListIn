package com.igriss.ListIn.search.service;

import com.igriss.ListIn.publication.entity.CategoryAttribute;
import com.igriss.ListIn.publication.repository.AttributeValueRepository;
import com.igriss.ListIn.publication.repository.CategoryAttributeRepository;
import com.igriss.ListIn.search.document.InputPredictionDocument;
import com.igriss.ListIn.search.dto.InputPredictionRequestDTO;
import com.igriss.ListIn.search.dto.InputPredictionResponseDTO;
import com.igriss.ListIn.search.mapper.InputPredictionMapper;
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
    private final InputPredictionMapper inputPredictionMapper;

    private final AttributeValueRepository attributeValueRepository;
    private final CategoryAttributeRepository categoryAttributeRepository;

    private final List<String> models = List.of(
            "Smartphone Brand Model",
            "Laptop Brand Model",
            "Macbook Model",
            "Smartwatch Brand Model",
            "Tablet Brand Model",
            "PC Brand Model",
            "Console Brand Model",

            "Car brand model",
            "Vehicle Model",
            "Motorcycle Model",
            "Commercial Vehicle Model",
            "Watercraft Model",
            "Special Vehicle Model",
            "Electric Vehicle Model",
            "Agricultural & Construction Vehicle Model"
    );

    @Override
    public List<InputPredictionResponseDTO> getInputPredictions(String model) {
        PageRequest pageRequest = PageRequest.of(0, 5);

        List<InputPredictionDocument> matchPhrase = predictionDocumentRepository.findByModelValueContainingIgnoreCase(model, pageRequest);
        return matchPhrase.stream().map(inputPredictionMapper::toInputPredictionResponseDTO).toList();
    }

    @Override
    public void saveInputMatchDocument(List<InputPredictionRequestDTO> matchRequestDTO) {
        predictionDocumentRepository.saveAll(matchRequestDTO.stream().map(
                matching -> InputPredictionDocument.builder()
                        .categoryId(matching.getCategoryId())
                        .parentCategoryId(matching.getParentCategoryId())
                        .parentCategoryName(matching.getModel())
                        .childAttributeValue(matching.getModel())
                        .build()
        ).toList());
    }

    @Override
    public String indexInputPredictionDocuments() {
        models.forEach(model -> attributeValueRepository.findByAttributeKey_Name(model).parallelStream().forEach(av -> {
            CategoryAttribute ca = categoryAttributeRepository.findByAttributeKey_Id(av.getAttributeKey().getId())
                    .orElseThrow(() -> new RuntimeException("Error during the elastic indexation occurred"));
            predictionDocumentRepository.save(inputPredictionMapper.toInputPredictionDocument(av, ca));
        }));
        return "Success";
    }
}
