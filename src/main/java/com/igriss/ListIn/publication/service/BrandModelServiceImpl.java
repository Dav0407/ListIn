package com.igriss.ListIn.publication.service;

import com.igriss.ListIn.publication.dto.BrandModelDTO;
import com.igriss.ListIn.publication.entity.AttributeKey;
import com.igriss.ListIn.publication.entity.AttributeValue;
import com.igriss.ListIn.publication.entity.brand_models.*;
import com.igriss.ListIn.publication.repository.AttributeValueRepository;
import com.igriss.ListIn.publication.repository.brand_models_repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BrandModelServiceImpl implements BrandModelService {

    private final ConsoleBrandModelRepository consoleBrandModelRepository;
    private final LaptopBrandModelRepository laptopBrandModelRepository;
    private final PCBrandModelRepository pcBrandModelRepository;
    private final SmartphoneBrandModelRepository smartphoneBrandModelRepository;
    private final SmartWatchBrandModelRepository smartWatchBrandModelRepository;
    private final TabletBrandModelRepository tabletBrandModelRepository;
    private final LaptopProcessorModelRepository processorModelRepository;
    private final PCProcessorModelRepository pcProcessorModelRepository;
    private final LaptopGPUModelRepository laptopGPUModelRepository;
    private final PCGPUModelRepository pcgpuModelRepository;

    private final AttributeValueRepository attributeValueRepository;

    @Override
    public List<BrandModelDTO> getModels(AttributeValue attributeValue) {
        if (attributeValue.getId() == null) return List.of(new BrandModelDTO());

        List<AttributeValue> childAttributeValues = attributeValueRepository.findByParentValue(attributeValue);

        return childAttributeValues.stream()
                .map(element -> BrandModelDTO.builder()
                        .modelId(element.getId())
                        .name(element.getValue())
                        .nameUz(element.getValueUz())
                        .nameRu(element.getValueRu())
                        .attributeId(element.getAttributeKey().getId().toString())
                        .build()
                ).toList();
    }

    @Override
    public List<BrandModelDTO> getCorrespondingModels(AttributeKey attributeKey, AttributeValue attributeValue) {

        switch (attributeKey.getName()) {
            case "Smartphone Brand" -> {
                List<SmartphoneBrandModel> models = smartphoneBrandModelRepository.findByAttributeValue(attributeValue);
                return models.stream().map(element ->
                        BrandModelDTO.builder()
                                .modelId(element.getId())
                                .name(element.getValue())
                                .attributeId(element.getAttributeValue().getId().toString())
                                .build()
                ).toList();
            }
            case "Laptop Brand" -> {
                List<LaptopBrandModel> models = laptopBrandModelRepository.findByAttributeValue(attributeValue);
                return models.stream().map(element ->
                        BrandModelDTO.builder()
                                .modelId(element.getId())
                                .name(element.getValue())
                                .attributeId(element.getAttributeValue().getId().toString())
                                .build()
                ).toList();
            }
            case "Laptop Processor Brands" -> {
                List<LaptopProcessorModel> models = processorModelRepository.findByAttributeValue(attributeValue);
                return models.stream().map(element ->
                        BrandModelDTO.builder()
                                .modelId(element.getId())
                                .name(element.getValue())
                                .attributeId(element.getAttributeValue().getId().toString())
                                .build()
                ).toList();
            }
            case "Laptop GPU Brands" -> {
                List<LaptopGPUModel> models = laptopGPUModelRepository.findByAttributeValue(attributeValue);
                return models.stream().map(element ->
                        BrandModelDTO.builder()
                                .modelId(element.getId())
                                .name(element.getValue())
                                .attributeId(element.getAttributeValue().getId().toString())
                                .build()
                ).toList();
            }
            case "Smartwatch Brand" -> {
                List<SmartWatchBrandModel> models = smartWatchBrandModelRepository.findByAttributeValue(attributeValue);
                return models.stream().map(element ->
                        BrandModelDTO.builder()
                                .modelId(element.getId())
                                .name(element.getValue())
                                .attributeId(element.getAttributeValue().getId().toString())
                                .build()
                ).toList();
            }
            case "Tablet Brand" -> {
                List<TabletBrandModel> models = tabletBrandModelRepository.findByAttributeValue(attributeValue);
                return models.stream().map(element ->
                        BrandModelDTO.builder()
                                .modelId(element.getId())
                                .name(element.getValue())
                                .attributeId(element.getAttributeValue().getId().toString())
                                .build()
                ).toList();
            }
            case "PC Brand" -> {
                List<PCBrandModel> models = pcBrandModelRepository.findByAttributeValue(attributeValue);
                return models.stream().map(element ->
                        BrandModelDTO.builder()
                                .modelId(element.getId())
                                .name(element.getValue())
                                .attributeId(element.getAttributeValue().getId().toString())
                                .build()
                ).toList();
            }
            case "PC Processor Brand" -> {
                List<PCProcessorModel> models = pcProcessorModelRepository.findByAttributeValue(attributeValue);
                return models.stream().map(element ->
                        BrandModelDTO.builder()
                                .modelId(element.getId())
                                .name(element.getValue())
                                .attributeId(element.getAttributeValue().getId().toString())
                                .build()
                ).toList();
            }
            case "PC GPU Brands" -> {
                List<PCGPUModel> models = pcgpuModelRepository.findByAttributeValue(attributeValue);
                return models.stream().map(element ->
                        BrandModelDTO.builder()
                                .modelId(element.getId())
                                .name(element.getValue())
                                .attributeId(element.getAttributeValue().getId().toString())
                                .build()
                ).toList();
            }
            case "Console Brand" -> {
                List<ConsoleBrandModel> models = consoleBrandModelRepository.findByAttributeValue(attributeValue);
                return models.stream().map(element ->
                        BrandModelDTO.builder()
                                .modelId(element.getId())
                                .name(element.getValue())
                                .attributeId(element.getAttributeValue().getId().toString())
                                .build()
                ).toList();
            }
            default -> {
                return Collections.singletonList(new BrandModelDTO());
            }
        }
    }

}
