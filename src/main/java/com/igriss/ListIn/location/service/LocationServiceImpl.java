package com.igriss.ListIn.location.service;

import com.igriss.ListIn.exceptions.ResourceNotFoundException;
import com.igriss.ListIn.location.dto.LocationDTO;
import com.igriss.ListIn.location.dto.node.CountryNode;
import com.igriss.ListIn.location.dto.node.CountyNode;
import com.igriss.ListIn.location.dto.node.LocationTreeNode;
import com.igriss.ListIn.location.dto.node.StateNode;
import com.igriss.ListIn.location.entity.Country;
import com.igriss.ListIn.location.entity.County;
import com.igriss.ListIn.location.entity.State;
import com.igriss.ListIn.location.repository.CountryRepository;
import com.igriss.ListIn.location.repository.CountyRepository;
import com.igriss.ListIn.location.repository.StateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final CountryRepository countryRepository;
    private final CountyRepository countyRepository;
    private final StateRepository stateRepository;

    @Override
    @Cacheable("location-tree")
    public LocationTreeNode getLocationTree() {
        LocationTreeNode root = new LocationTreeNode();

        List<CountryNode> countries = countryRepository.findAll().stream()
                .map(country -> {
                    List<StateNode> states = stateRepository.findAllByCountry_Id(country.getId()).stream()
                            .map(state -> {
                                List<CountyNode> counties = countyRepository.findAllByState_Id(state.getId()).stream()
                                        .map(county -> CountyNode.builder()
                                                .countyId(county.getId())
                                                .value(county.getValue())
                                                .valueUz(county.getValueUz())
                                                .valueRu(county.getValueRu())
                                                .build())
                                        .collect(Collectors.toList());

                                return StateNode.builder()
                                        .stateId(state.getId())
                                        .value(state.getValue())
                                        .valueUz(state.getValueUz())
                                        .valueRu(state.getValueRu())
                                        .counties(counties)
                                        .build();
                            })
                            .collect(Collectors.toList());

                    return CountryNode.builder()
                            .countryId(country.getId())
                            .value(country.getValue())
                            .valueUz(country.getValueUz())
                            .valueRu(country.getValueRu())
                            .states(states)
                            .build();
                })
                .collect(Collectors.toList());

        root.setCountries(countries);
        return root;
    }

    @Override
    public LocationDTO getLocation(String countryName, String stateName, String countyName, String language) {
        Country country = findEntityByName(
                countryName,
                countryRepository::findByValueIgnoreCase,
                countryRepository::findByValueUzIgnoreCase,
                countryRepository::findByValueRuIgnoreCase,
                "Country",
                language
        );

        State state = findEntityByName(
                stateName,
                stateRepository::findByValueIgnoreCase,
                stateRepository::findByValueUzIgnoreCase,
                stateRepository::findByValueRuIgnoreCase,
                "State",
                language
        );

        County county = findEntityByName(
                countyName,
                countyRepository::findByValueIgnoreCase,
                countyRepository::findByValueUzIgnoreCase,
                countyRepository::findByValueRuIgnoreCase,
                "County",
                language
        );

        return LocationDTO.builder()
                .country(country)
                .state(state)
                .county(county)
                .build();
    }

    private <T> T findEntityByName(
            String name,
            Function<String, Optional<T>> defaultFinder,
            Function<String, Optional<T>> uzFinder,
            Function<String, Optional<T>> ruFinder,
            String entityType,
            String language
    ) {
        if (name == null || name.isBlank()) {
            return null;
        }

        Optional<T> entity;
        switch (language.toLowerCase()) {
            case "uz" -> entity = uzFinder.apply(name);
            case "ru" -> entity = ruFinder.apply(name);
            default -> entity = defaultFinder.apply(name);
        }

        if (entity.isEmpty()) {
            log.error("Entity '{}' with name '{}' not found for language '{}'", entityType, name, language);
            throw new ResourceNotFoundException("No " + entityType + " found in the database!");
        }

        return entity.get();
    }
}
