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

    default List<String> getThirdPartyFinancialIdsByProfitMargins(Double profitMargins, MongoTemplate mongoTemplate) {
        Query query = new Query();
        if (profitMargins != null)
            query.addCriteria(Criteria.where("profitMargins").gte(profitMargins));
        List<ThirdPartyFinancials> thirdPartyFinancials = mongoTemplate.find(query, ThirdPartyFinancials.class);
        return thirdPartyFinancials.stream()
                .map(ThirdPartyFinancials::getFinancialID)
                .collect(Collectors.toList());
    }

    default List<String> getThirdPartyFinancialIdsByFilters(ThirdPartyFinancials thirdPartyFinancials,
            MongoTemplate mongoTemplate) {
        Query query = new Query();
        if (thirdPartyFinancials.getCashFlow() != 0) {
            query.addCriteria(Criteria.where("cashFlow").is(thirdPartyFinancials.getCashFlow()));
        }
        if (thirdPartyFinancials.getCurrentRatio() != 0) {
            query.addCriteria(Criteria.where("currentRatio").is(thirdPartyFinancials.getCurrentRatio()));
        }
        if (thirdPartyFinancials.getDebtToEquityRatio() != 0) {
            query.addCriteria(Criteria.where("debtToEquityRatio").is(thirdPartyFinancials.getDebtToEquityRatio()));
        }
        if (thirdPartyFinancials.getEbitda() != 0) {
            query.addCriteria(Criteria.where("ebitda").is(thirdPartyFinancials.getEbitda()));
        }
        if (thirdPartyFinancials.getGrossMargin() != 0) {
            query.addCriteria(Criteria.where("grossMargin").is(thirdPartyFinancials.getGrossMargin()));
        }
        if (thirdPartyFinancials.getNetIncome() != 0) {
            query.addCriteria(Criteria.where("netIncome").is(thirdPartyFinancials.getNetIncome()));
        }
        if (thirdPartyFinancials.getOperatingExpenses() != 0) {
            query.addCriteria(Criteria.where("operatingExpenses").is(thirdPartyFinancials.getOperatingExpenses()));
        }
        if (thirdPartyFinancials.getProfitMargins() != 0) {
            query.addCriteria(Criteria.where("profitMargins").is(thirdPartyFinancials.getProfitMargins()));
        }
        if (thirdPartyFinancials.getQuickRatio() != 0) {
            query.addCriteria(Criteria.where("quickRatio").is(thirdPartyFinancials.getQuickRatio()));
        }
        if (thirdPartyFinancials.getRevenue() != 0) {
            query.addCriteria(Criteria.where("revenue").is(thirdPartyFinancials.getRevenue()));
        }
        List<ThirdPartyFinancials> filteredThirdPartyFinancials = mongoTemplate.find(query, ThirdPartyFinancials.class);
        return filteredThirdPartyFinancials.stream()
                .map(ThirdPartyFinancials::getFinancialID)
                .collect(Collectors.toList());
    }
}
