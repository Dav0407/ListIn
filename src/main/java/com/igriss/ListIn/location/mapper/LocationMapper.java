package com.igriss.ListIn.location.mapper;

import com.igriss.ListIn.location.dto.CityResponseDTO;
import com.igriss.ListIn.location.dto.CountryResponseDTO;
import com.igriss.ListIn.location.dto.CountyResponseDTO;
import com.igriss.ListIn.location.dto.StateResponseDTO;
import com.igriss.ListIn.location.entity.City;
import com.igriss.ListIn.location.entity.Country;
import com.igriss.ListIn.location.entity.County;
import com.igriss.ListIn.location.entity.State;
import org.springframework.stereotype.Service;

@Service
public class LocationMapper {

    public CountryResponseDTO toCountryDTO(Country country) {
        return CountryResponseDTO.builder()
                .valueEng(country.getValue())
                .valueUz(country.getValueUz())
                .valueRu(country.getValueRu())
                .build();
    }

    public StateResponseDTO toStateDTO(State state) {
        return StateResponseDTO.builder()
                .valueEng(state.getValue())
                .valueUz(state.getValueUz())
                .valueRu(state.getValueRu())
                .build();
    }

    public CountyResponseDTO toCountyDTO(County county) {
        return CountyResponseDTO.builder()
                .valueEng(county.getValue())
                .valueUz(county.getValueUz())
                .valueRu(county.getValueRu())
                .build();
    }

    public CityResponseDTO toCityDTO(City city) {
        return CityResponseDTO.builder()
                .valueEng(city.getValue())
                .valueUz(city.getValueUz())
                .valueRu(city.getValueRu())
                .build();
    }

}
