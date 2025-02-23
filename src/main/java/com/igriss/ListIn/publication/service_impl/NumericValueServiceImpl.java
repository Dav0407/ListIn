package com.igriss.ListIn.publication.service_impl;

import com.igriss.ListIn.exceptions.ResourceNotFoundException;
import com.igriss.ListIn.publication.dto.NumericValueRequestDTO;
import com.igriss.ListIn.publication.entity.NumericValue;
import com.igriss.ListIn.publication.entity.Publication;
import com.igriss.ListIn.publication.repository.NumericFieldRepository;
import com.igriss.ListIn.publication.repository.NumericValueRepository;
import com.igriss.ListIn.publication.service.NumericValueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NumericValueServiceImpl implements NumericValueService {

    private final NumericFieldRepository numericFieldRepository;
    private final NumericValueRepository numericValueRepository;

    @Override
    public List<NumericValue> savePublicationNumericValues(List<NumericValueRequestDTO> request, Publication publication) {
        if (request != null && !request.isEmpty()) {
            List<NumericValue> numericValues = request.stream()
                    .map(numericDto -> NumericValue.builder()
                            .publication(publication)
                            .numericField(numericFieldRepository.findById(numericDto.getNumericFieldId()).orElseThrow(() ->
                                    new ResourceNotFoundException(String.format("Numeric filed with ID [%s] does not exist!!!", numericDto.getNumericFieldId()))))
                            .value(numericDto.getNumericValue())
                            .build())
                    .toList();
            return numericValueRepository.saveAll(numericValues);
        }
        return null;
    }

    @Override
    public void deletePublicationNumericFields(UUID publicationId) {
        numericValueRepository.deleteAllByPublication_Id(publicationId);
    }

    @Override
    public List<NumericValue> findNumericFields(UUID publicationId) {
        return numericValueRepository.findAllByPublication_Id(publicationId);
    }
}
