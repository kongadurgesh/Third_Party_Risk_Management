package com.tprm.spi.dto.builder;

import java.time.LocalDate;
import java.util.List;

import com.tprm.spi.dto.ThirdPartyRelationshipDTO;

public class ThirdPartyRelationshipDTOBuilder {
    private String relationshipId;
    private String relationshipType;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private String description;
    private String contractDetails;
    private String renewalTerms;
    private String serviceLevelAgreements;
    private String assignedAccountManager;
    private List<String> associatedProjectsOrInitiatives;
    private List<String> additionalContacts;
    private String auditTrail;

    public ThirdPartyRelationshipDTOBuilder setRelationshipId(String relationshipId) {
        this.relationshipId = relationshipId;
        return this;
    }

    public ThirdPartyRelationshipDTOBuilder setRelationshipType(String relationshipType) {
        this.relationshipType = relationshipType;
        return this;
    }

    public ThirdPartyRelationshipDTOBuilder setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public ThirdPartyRelationshipDTOBuilder setEndDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public ThirdPartyRelationshipDTOBuilder setStatus(String status) {
        this.status = status;
        return this;
    }

    public ThirdPartyRelationshipDTOBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public ThirdPartyRelationshipDTOBuilder setContractDetails(String contractDetails) {
        this.contractDetails = contractDetails;
        return this;
    }

    public ThirdPartyRelationshipDTOBuilder setRenewalTerms(String renewalTerms) {
        this.renewalTerms = renewalTerms;
        return this;
    }

    public ThirdPartyRelationshipDTOBuilder setServiceLevelAgreements(String serviceLevelAgreements) {
        this.serviceLevelAgreements = serviceLevelAgreements;
        return this;
    }

    public ThirdPartyRelationshipDTOBuilder setAssignedAccountManager(String assignedAccountManager) {
        this.assignedAccountManager = assignedAccountManager;
        return this;
    }

    public ThirdPartyRelationshipDTOBuilder setAssociatedProjectsOrInitiatives(
            List<String> associatedProjectsOrInitiatives) {
        this.associatedProjectsOrInitiatives = associatedProjectsOrInitiatives;
        return this;
    }

    public ThirdPartyRelationshipDTOBuilder setAdditionalContacts(List<String> additionalContacts) {
        this.additionalContacts = additionalContacts;
        return this;
    }

    public ThirdPartyRelationshipDTOBuilder setAuditTrail(String auditTrail) {
        this.auditTrail = auditTrail;
        return this;
    }

    public ThirdPartyRelationshipDTO getThirdPartyRelationshipDTO() {
        return new ThirdPartyRelationshipDTO(relationshipId, relationshipType, startDate, endDate, status, description,
                contractDetails, renewalTerms, serviceLevelAgreements, assignedAccountManager,
                associatedProjectsOrInitiatives, additionalContacts, auditTrail);
    }

}
