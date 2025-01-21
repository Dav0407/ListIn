package com.igriss.ListIn.search.service;

import com.igriss.ListIn.exceptions.SearchQueryException;
import com.igriss.ListIn.search.dto.InputPredictionRequestDTO;
import com.igriss.ListIn.search.dto.InputPredictionResponseDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface InputPredictionService {
     List<InputPredictionResponseDTO> getInputPredictions(String model) throws SearchQueryException;

     void saveInputMatchDocument(List<InputPredictionRequestDTO> matchRequestDTO);

     String indexInputPredictionDocuments();
}
