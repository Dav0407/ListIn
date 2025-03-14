package com.igriss.ListIn.location.mapper;

import com.igriss.ListIn.location.dto.CountryDTO;
import com.igriss.ListIn.location.dto.CountyDTO;
import com.igriss.ListIn.location.dto.StateDTO;
import com.igriss.ListIn.location.entity.Country;
import com.igriss.ListIn.location.entity.County;
import com.igriss.ListIn.location.entity.State;
import org.springframework.stereotype.Service;

@Service
public class LocationMapper {

    public CountryDTO toCountryDTO(Country country) {
        return CountryDTO.builder()
                .id(country.getId())
                .value(country.getValue())
                .valueRu(country.getValueRu())
                .valueUz(country.getValueUz())
                .build();
    }

    public StateDTO toStateDTO(State state) {
        return StateDTO.builder()
                .id(state.getId())
                .value(state.getValue())
                .valueRu(state.getValueRu())
                .valueUz(state.getValueUz())
                .build();
    }

    public CountyDTO toCountyDTO(County county) {
        return CountyDTO.builder()
                .id(county.getId())
                .value(county.getValue())
                .valueRu(county.getValueRu())
                .valueUz(county.getValueUz())
                .build();
    }
}
