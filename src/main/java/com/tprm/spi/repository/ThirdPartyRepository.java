package com.tprm.spi.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.tprm.spi.entity.ThirdParty;

@Repository
public interface ThirdPartyRepository extends MongoRepository<ThirdParty, String> {
    Optional<ThirdParty> findByName(String name);

}
