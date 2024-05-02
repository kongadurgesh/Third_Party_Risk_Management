package com.tprm.spi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.tprm.spi.dto.ThirdPartyRelationshipDTO;
import com.tprm.spi.entity.ThirdPartyRelationship;
import com.tprm.spi.repository.ThirdPartyRelationshipRepository;

@Service
public class ThirdPartyRelationshipService {

    @Autowired
    private ThirdPartyRelationshipRepository thirdPartyRelationshipRepository;

    @Autowired
    private ModelMapper modelMapper;

    public String addRelationshipToThirdParty(
            ThirdPartyRelationshipDTO thirdPartyRelationshipDTO) {
        ThirdPartyRelationship savedThirdPartyRelationship = thirdPartyRelationshipRepository
                .save(convertToThirdPartyRelationship(thirdPartyRelationshipDTO));

        return convertToThirdPartyRelationshipDTO(savedThirdPartyRelationship).getRelationshipId();

    }

    public List<ThirdPartyRelationshipDTO> addAllRelationships(
            List<ThirdPartyRelationshipDTO> thirdPartyRelationshipDTOs) {
        return thirdPartyRelationshipRepository.saveAll(thirdPartyRelationshipDTOs.stream()
                .map(this::convertToThirdPartyRelationship).collect(Collectors.toList())).stream()
                .map(this::convertToThirdPartyRelationshipDTO).collect(Collectors.toList());
    }

    public List<String> getThirdPartyIdsByRelationshipFilter(ThirdPartyRelationshipDTO thirdPartyRelationshipDTO,
            MongoTemplate mongoTemplate) {
        return thirdPartyRelationshipRepository.getThirdPartyRelationshipsIdsByFilter(
                convertToThirdPartyRelationship(thirdPartyRelationshipDTO), mongoTemplate);
    }

    public void deleteRelationshipbyId(String thirdPartyRelationshipId) {
        thirdPartyRelationshipRepository.deleteById(thirdPartyRelationshipId);
    }

    private ThirdPartyRelationshipDTO convertToThirdPartyRelationshipDTO(
            ThirdPartyRelationship thirdPartyRelationship) {
        return modelMapper.map(thirdPartyRelationship, ThirdPartyRelationshipDTO.class);
    }

    private ThirdPartyRelationship convertToThirdPartyRelationship(
            ThirdPartyRelationshipDTO thirdPartyRelationshipDTO) {
        return modelMapper.map(thirdPartyRelationshipDTO, ThirdPartyRelationship.class);
    }

    public void deleteAllRelationships(List<String> thirdPartyRelationhshipIds) {
        thirdPartyRelationshipRepository.deleteAllById(thirdPartyRelationhshipIds);
    }
}
