package com.tprm.spi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.tprm.spi.entity.ThirdPartyRelationship;

@Repository
public interface ThirdPartyRelationshipRepository extends MongoRepository<ThirdPartyRelationship, String> {
}
