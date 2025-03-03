package com.igriss.ListIn.location.service;

import co.elastic.clients.util.MapBuilder;
import com.igriss.ListIn.exceptions.ResourceNotFoundException;
import com.igriss.ListIn.location.dto.*;
import com.igriss.ListIn.location.entity.City;
import com.igriss.ListIn.location.entity.Country;
import com.igriss.ListIn.location.entity.County;
import com.igriss.ListIn.location.entity.State;
import com.igriss.ListIn.location.repository.CityRepository;
import com.igriss.ListIn.location.repository.CountryRepository;
import com.igriss.ListIn.location.repository.CountyRepository;
import com.igriss.ListIn.location.repository.StateRepository;
import com.igriss.ListIn.publication.dto.PublicationRequestDTO;
import com.igriss.ListIn.security.security_dto.RegisterRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final CountryRepository countryRepository;
    private final CountyRepository countyRepository;
    private final StateRepository stateRepository;
    private final CityRepository cityRepository;

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
                                                .value(county.getValue())
                                                .valueUz(county.getValueUz())
                                                .valueRu(county.getValueRu())
                                                .build())
                                        .collect(Collectors.toList());

                                List<CityNode> cities = cityRepository.findAllByState_Id(state.getId()).stream()
                                        .map(city -> CityNode.builder()
                                                .value(city.getValue())
                                                .valueUz(city.getValueUz())
                                                .valueRu(city.getValueRu())
                                                .build())
                                        .collect(Collectors.toList());

                                return StateNode.builder()
                                        .value(state.getValue())
                                        .valueUz(state.getValueUz())
                                        .valueRu(state.getValueRu())
                                        .counties(counties)
                                        .cities(cities)
                                        .build();
                            })
                            .collect(Collectors.toList());

                    return CountryNode.builder()
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
    public LocationDTO getLocation(RegisterRequestDTO request) {

        Country country = countryRepository.findByValueIgnoreCase(request.getCountry())
                .orElseThrow(() -> new ResourceNotFoundException("No country found in the database!"));

        State state = stateRepository.findByValueIgnoreCase(request.getState())
                .orElseThrow(() -> new ResourceNotFoundException("No state found in the database!"));

        County county = countyRepository.findByValueIgnoreCase(request.getCounty())
                .orElseThrow(() -> new ResourceNotFoundException("No county found in the database!"));

        City city = request.getCity().equalsIgnoreCase("Tashkent city") ?
                City.builder()
                        .id(UUID.fromString("1ce4b796-74c9-45ac-ae6e-206cdd69e97c"))
                        .build()
                : cityRepository.findByValueIgnoreCase(request.getCity())
                .orElseThrow(() -> new ResourceNotFoundException("No city found in the database!"));

        return LocationDTO.builder()
                .country(country)
                .state(state)
                .county(county)
                .city(city)
                .build();
    }
}
