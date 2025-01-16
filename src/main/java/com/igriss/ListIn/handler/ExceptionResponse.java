package com.igriss.ListIn.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class ExceptionResponse {

    private Integer businessErrorCode;
    private String businessErrorDescription;
    private String errorMessage;
    private Set<String> validationErrors;

}
