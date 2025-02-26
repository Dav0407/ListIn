package com.igriss.ListIn.location.service;

import com.igriss.ListIn.location.dto.*;
import com.igriss.ListIn.location.repository.CityRepository;
import com.igriss.ListIn.location.repository.CountryRepository;
import com.igriss.ListIn.location.repository.CountyRepository;
import com.igriss.ListIn.location.repository.StateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocationTreeServiceImpl implements LocationTreeService {

    private final CountryRepository countryRepository;
    private final CountyRepository countyRepository;
    private final StateRepository stateRepository;
    private final CityRepository cityRepository;

    @Override
    public LocationTreeNode getLocationTree() {
        LocationTreeNode root = new LocationTreeNode();

        List<CountryNode> countries = countryRepository.findAll().stream()
                .map(country -> {
                    List<StateNode> states = stateRepository.findAllByCountry_Id(country.getId()).stream()
                            .map(state -> {
                                List<CountyNode> counties = countyRepository.findAllByState_Id(state.getId()).stream()
                                        .map(county -> CountyNode.builder()
                                                .value(county.getValue())
                                                .build())
                                        .collect(Collectors.toList());

                                List<CityNode> cities = cityRepository.findAllByState_Id(state.getId()).stream()
                                        .map(city -> CityNode.builder()
                                                .value(city.getValue())
                                                .build())
                                        .collect(Collectors.toList());

                                return StateNode.builder()
                                        .value(state.getValue())
                                        .counties(counties)
                                        .cities(cities)
                                        .build();
                            })
                            .collect(Collectors.toList());

                    return CountryNode.builder()
                            .value(country.getValue())
                            .states(states)
                            .build();
                })
                .collect(Collectors.toList());

        root.setCountries(countries);
        return root;
    }
}
