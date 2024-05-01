package com.tprm.spi.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.tprm.spi.entity.ThirdPartyRelationship;

@Repository
public interface ThirdPartyRelationshipRepository extends MongoRepository<ThirdPartyRelationship, String> {

    default List<String> getThirdPartyRelationshipsIdsByFilter(ThirdPartyRelationship thirdPartyRelationship,
            MongoTemplate mongoTemplate) {
        Query query = new Query();
        if (thirdPartyRelationship.getRelationshipType() != null)
            query.addCriteria(Criteria.where("relationshipType").is(thirdPartyRelationship.getRelationshipType()));

        if (thirdPartyRelationship.getStatus() != null)
            query.addCriteria(Criteria.where("status").is(thirdPartyRelationship.getStatus()));
        if (thirdPartyRelationship.getAdditionalContacts() != null)
            query.addCriteria(Criteria.where("additionalContacts").is(thirdPartyRelationship.getAdditionalContacts()));
        if (thirdPartyRelationship.getAssignedAccountManager() != null)
            query.addCriteria(
                    Criteria.where("assignedAccountManager").is(thirdPartyRelationship.getAssignedAccountManager()));
        if (thirdPartyRelationship.getAssociatedProjectsOrInitiatives() != null)
            query.addCriteria(Criteria.where("associatedProjectsOrInitiatives")
                    .is(thirdPartyRelationship.getAssociatedProjectsOrInitiatives()));
        if (thirdPartyRelationship.getAuditTrail() != null)
            query.addCriteria(Criteria.where("auditTrail").is(thirdPartyRelationship.getAuditTrail()));
        if (thirdPartyRelationship.getContractDetails() != null)
            query.addCriteria(Criteria.where("contractDetails").is(thirdPartyRelationship.getContractDetails()));
        if (thirdPartyRelationship.getDescription() != null)
            query.addCriteria(Criteria.where("description").is(thirdPartyRelationship.getDescription()));
        if (thirdPartyRelationship.getEndDate() != null)
            query.addCriteria(Criteria.where("endDate").is(thirdPartyRelationship.getEndDate()));
        if (thirdPartyRelationship.getRenewalTerms() != null)
            query.addCriteria(Criteria.where("renewalTerms").is(thirdPartyRelationship.getRenewalTerms()));
        if (thirdPartyRelationship.getServiceLevelAgreements() != null)
            query.addCriteria(
                    Criteria.where("serviceLevelAgreements").is(thirdPartyRelationship.getServiceLevelAgreements()));
        if (thirdPartyRelationship.getStartDate() != null)
            query.addCriteria(Criteria.where("startDate").is(thirdPartyRelationship.getStartDate()));

        List<ThirdPartyRelationship> filteredThirdPartyRelationships = mongoTemplate.find(query,
                ThirdPartyRelationship.class);

        return filteredThirdPartyRelationships.stream()
                .map(ThirdPartyRelationship::getRelationshipId)
                .collect(Collectors.toList());
    }
}
