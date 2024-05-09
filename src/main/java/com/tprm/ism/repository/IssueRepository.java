package com.tprm.ism.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.tprm.ism.entity.Issue;

@Repository
public interface IssueRepository extends MongoRepository<Issue, String> {

}
