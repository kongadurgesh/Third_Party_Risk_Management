package com.tprm.spi.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    public String addRelationshipToThirdParty(String thirdPartyId,
            ThirdPartyRelationshipDTO thirdPartyRelationshipDTO) {
        ThirdPartyRelationship savedThirdPartyRelationship = thirdPartyRelationshipRepository
                .save(convertToThirdPartyRelationship(thirdPartyRelationshipDTO));

        return convertToThirdPartyRelationshipDTO(savedThirdPartyRelationship).getRelationshipId();

    }

    private ThirdPartyRelationshipDTO convertToThirdPartyRelationshipDTO(
            ThirdPartyRelationship thirdPartyRelationship) {
        return modelMapper.map(thirdPartyRelationship, ThirdPartyRelationshipDTO.class);
    }

    private ThirdPartyRelationship convertToThirdPartyRelationship(
            ThirdPartyRelationshipDTO thirdPartyRelationshipDTO) {
        return modelMapper.map(thirdPartyRelationshipDTO, ThirdPartyRelationship.class);
    }
}
