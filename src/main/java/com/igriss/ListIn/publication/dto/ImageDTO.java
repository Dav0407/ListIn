package com.igriss.ListIn.publication.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageDTO {
    public Boolean isPrimary;
    public String url;
}
