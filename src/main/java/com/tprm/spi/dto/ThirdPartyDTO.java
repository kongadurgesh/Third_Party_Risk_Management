package com.tprm.spi.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ThirdPartyDTO {
    private String id;
    private String name;
    private String address;
    private String phoneNumber;
    private String emailAddress;
    private String primaryContactName;
    private String primaryContactTitle;
    private String primaryContactEmail;
    private String legalStructure;
    private ThirdPartyFinancialsDTO financials;
    private List<ThirdPartyRelationshipDTO> relationships;
}
