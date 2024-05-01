package com.tprm.spi.entity;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "relationships")
public class ThirdPartyRelationship {
    @Id
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
