package com.igriss.ListIn.search.mapper;


import com.igriss.ListIn.publication.entity.AttributeValue;
import com.igriss.ListIn.publication.entity.CategoryAttribute;
import com.igriss.ListIn.search.document.InputPredictionDocument;
import com.igriss.ListIn.search.dto.InputPredictionResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InputPredictionMapper {

    public InputPredictionResponseDTO toInputPredictionResponseDTO(InputPredictionDocument document){
        return InputPredictionResponseDTO.builder()
                .childAttributeValueId(document.getId())
                .childAttributeValue(document.getChildAttributeValue())
                .parentAttributeValueId(document.getParentAttributeValueId())
                .parentAttributeValue(document.getParentAttributeValue())
                .parentAttributeKeyId(document.getParentAttributeKeyId())
                .childAttributeKeyId(document.getChildAttributeKeyId())
                .parentCategoryId(document.getParentCategoryId())
                .parentCategoryName(document.getParentCategoryName())
                .categoryId(document.getCategoryId())
                .categoryName(document.getCategoryName())
                .build();
    }

    public InputPredictionDocument toInputPredictionDocument(AttributeValue av, CategoryAttribute ca){

        return InputPredictionDocument.builder()
                .id(av.getId())
                .childAttributeValue(av.getValue())
                .parentAttributeValueId(av.getParentValue() != null ? av.getParentValue().getId() : null)
                .parentAttributeValue(av.getParentValue() != null ? av.getParentValue().getValue() : null)
                .parentAttributeKeyId(av.getParentValue() != null ? av.getParentValue().getAttributeKey().getId(): null)
                .childAttributeKeyId(ca.getAttributeKey().getId())
                .categoryId(ca.getCategory().getId())
                .categoryName(ca.getCategory().getName())
                .parentCategoryId(ca.getCategory().getParentCategory().getId())
                .parentCategoryName(ca.getCategory().getParentCategory().getName())
                .build();
    }
}
