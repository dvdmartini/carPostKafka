package com.portal.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude
public class OwnerPostDto {

    private String name;
    private String type;
    private String contactNumber;

}
