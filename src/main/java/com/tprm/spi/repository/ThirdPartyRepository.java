package com.tprm.spi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.tprm.spi.entity.ThirdParty;

@Repository
public interface ThirdPartyRepository extends MongoRepository<ThirdParty, String> {
    Optional<ThirdParty> findByName(String name);

    default List<ThirdParty> getThirdPartiesbyFilter(ThirdParty thirdParty, MongoTemplate mongoTemplate) {
        Query query = new Query();
        if (thirdParty.getName() != null)
            query.addCriteria(Criteria.where("name").is(thirdParty.getName()));
        if (thirdParty.getAddress() != null)
            query.addCriteria(Criteria.where("address").is(thirdParty.getAddress()));
        if (thirdParty.getEmailAddress() != null)
            query.addCriteria(Criteria.where("emailAddress").is(thirdParty.getEmailAddress()));
        if (thirdParty.getLegalStructure() != null)
            query.addCriteria(Criteria.where("legalStructure").is(thirdParty.getLegalStructure()));
        if (thirdParty.getPhoneNumber() != null)
            query.addCriteria(Criteria.where("phoneNumber").is(thirdParty.getPhoneNumber()));
        if (thirdParty.getPrimaryContactName() != null)
            query.addCriteria(Criteria.where("primaryContactName").is(thirdParty.getPrimaryContactName()));
        if (thirdParty.getPrimaryContactEmail() != null)
            query.addCriteria(Criteria.where("primaryContactEmail").is(thirdParty.getPrimaryContactEmail()));
        if (thirdParty.getPrimaryContactTitle() != null)
            query.addCriteria(Criteria.where("primaryContactTitle").is(thirdParty.getPrimaryContactTitle()));

        List<ThirdParty> thirdParties = mongoTemplate.find(query, ThirdParty.class);
        return thirdParties;
    }
}
