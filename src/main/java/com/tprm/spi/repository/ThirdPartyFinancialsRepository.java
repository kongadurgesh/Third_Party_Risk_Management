package com.tprm.spi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.tprm.spi.entity.ThirdPartyFinancials;

@Repository
public interface ThirdPartyFinancialsRepository extends MongoRepository<ThirdPartyFinancials, String> {

}
