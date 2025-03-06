package com.igriss.ListIn.location.dto;

import com.igriss.ListIn.location.entity.Country;
import com.igriss.ListIn.location.entity.County;
import com.igriss.ListIn.location.entity.State;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LocationDTO {
    private Country country;
    private State state;
    private County county;
}
