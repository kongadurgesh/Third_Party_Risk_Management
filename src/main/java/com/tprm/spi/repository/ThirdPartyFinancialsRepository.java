package com.tprm.spi.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.tprm.spi.entity.ThirdPartyFinancials;

@Repository
public interface ThirdPartyFinancialsRepository extends MongoRepository<ThirdPartyFinancials, String> {

    default List<String> getThirdPartyFinancialIdsForRevenueRange(Double fromRange, Double toRange,
            MongoTemplate mongoTemplate) {
        Query query = new Query();
        if (fromRange != null && toRange != null)
            query.addCriteria(Criteria.where("revenue").gte(fromRange).lte(toRange));
        else if (fromRange != null)
            query.addCriteria(Criteria.where("revenue").gte(fromRange));
        else if (toRange != null)
            query.addCriteria(Criteria.where("revenue").lte(toRange));
        List<ThirdPartyFinancials> thirdPartyFinancials = mongoTemplate.find(query, ThirdPartyFinancials.class);
        return thirdPartyFinancials.stream()
                .map(ThirdPartyFinancials::getFinancialID)
                .collect(Collectors.toList());
    }

}
