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

    default List<ThirdParty> findThirdPartiesByFilter(Optional<String> name,
            Optional<String> address,
            Optional<String> phoneNumber,
            Optional<String> emailAddress,
            Optional<String> primaryContactName,
            Optional<String> primaryContactTitle,
            Optional<String> primaryContactEmail,
            Optional<String> legalStructure,
            MongoTemplate mongoTemplate) {
        Query query = new Query();
        name.ifPresent(value -> query.addCriteria(Criteria.where("name").is(value)));
        address.ifPresent(value -> query.addCriteria(Criteria.where("address").is(value)));
        phoneNumber.ifPresent(value -> query.addCriteria(Criteria.where("phoneNumber").is(value)));
        emailAddress.ifPresent(value -> query.addCriteria(Criteria.where("emailAddress").is(value)));
        primaryContactName.ifPresent(value -> query.addCriteria(Criteria.where("primaryContactName").is(value)));
        primaryContactTitle.ifPresent(value -> query.addCriteria(Criteria.where("primaryContactTitle").is(value)));
        primaryContactEmail.ifPresent(value -> query.addCriteria(Criteria.where("primaryContactEmail").is(value)));
        legalStructure.ifPresent(value -> query.addCriteria(Criteria.where("legalStructure").is(value)));
        List<ThirdParty> thirdParties = mongoTemplate.find(query, ThirdParty.class);
        return thirdParties;
    }
}
