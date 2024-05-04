package com.tprm.spi.dto.builder;

import java.util.List;

import com.tprm.spi.dto.ThirdPartyDTO;
import com.tprm.spi.dto.ThirdPartyFinancialsDTO;
import com.tprm.spi.dto.ThirdPartyRelationshipDTO;

public class ThirdPartyDTOBuilder {
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

    public ThirdPartyDTOBuilder setId(String id) {
        this.id = id;
        return this;

    }

    public ThirdPartyDTOBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ThirdPartyDTOBuilder setAddress(String address) {
        this.address = address;
        return this;
    }

    public ThirdPartyDTOBuilder setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public ThirdPartyDTOBuilder setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }

    public ThirdPartyDTOBuilder setPrimaryContactName(String primaryContactName) {
        this.primaryContactName = primaryContactName;
        return this;
    }

    public ThirdPartyDTOBuilder setPrimaryContactTitle(String primaryContactTitle) {
        this.primaryContactTitle = primaryContactTitle;
        return this;
    }

    public ThirdPartyDTOBuilder setPrimaryContactEmail(String primaryContactEmail) {
        this.primaryContactEmail = primaryContactEmail;
        return this;
    }

    public ThirdPartyDTOBuilder setLegalStructure(String legalStructure) {
        this.legalStructure = legalStructure;
        return this;
    }

    public ThirdPartyDTOBuilder setFinancials(ThirdPartyFinancialsDTO financials) {
        this.financials = financials;
        return this;
    }

    public ThirdPartyDTOBuilder setRelationships(List<ThirdPartyRelationshipDTO> relationships) {
        this.relationships = relationships;
        return this;
    }

    public ThirdPartyDTO getThirdPartyDTO() {
        return new ThirdPartyDTO(id, name, address, phoneNumber, emailAddress, primaryContactName, primaryContactTitle,
                primaryContactEmail, legalStructure, financials, relationships);
    }
}
