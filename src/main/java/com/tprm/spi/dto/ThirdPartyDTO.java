package com.tprm.spi.dto;

import lombok.Data;

@Data
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
}
