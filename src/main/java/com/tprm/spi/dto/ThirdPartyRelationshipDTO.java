package com.tprm.spi.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class ThirdPartyRelationshipDTO {
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
}
