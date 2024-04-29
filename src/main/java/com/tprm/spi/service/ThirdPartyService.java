package com.tprm.spi.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.tprm.spi.dto.ThirdPartyDTO;
import com.tprm.spi.entity.ThirdParty;
import com.tprm.spi.exception.ThirdPartyNotFoundException;
import com.tprm.spi.exception.ThirdpartyNameConflictException;
import com.tprm.spi.repository.ThirdPartyRepository;

@Service
public class ThirdPartyService {
    @Autowired
    private ThirdPartyRepository thirdPartyRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<ThirdPartyDTO> getAllThirdParties() {
        List<ThirdParty> thirdParties = thirdPartyRepository.findAll();

        return thirdParties.stream()
                .map(this::convertToThirdPartyDTO)
                .collect(Collectors.toList());
    }

    public Optional<ThirdPartyDTO> getThirdPartyById(String id) {
        Optional<ThirdParty> thirdparty = thirdPartyRepository.findById(id);
        return thirdparty.map(this::convertToThirdPartyDTO);
    }

    public ThirdPartyDTO createThirdParty(ThirdPartyDTO thirdPartyDTO) throws ThirdpartyNameConflictException {
        if (thirdPartyRepository.findByName(thirdPartyDTO.getName()).isPresent()) {
            throw new ThirdpartyNameConflictException("Third Party already exists in the DataBase...");
        }
        ThirdParty thirdParty = convertToThirdPartyEntity(thirdPartyDTO);
        return convertToThirdPartyDTO(thirdPartyRepository.save(thirdParty));
    }

    public Optional<ThirdPartyDTO> updateThirdParty(String id, ThirdPartyDTO thirdPartyDTO) {
        return Optional.of(thirdPartyRepository.findById(id)
                .map(existingThirdParty -> {
                    BeanUtils.copyProperties(thirdPartyDTO, existingThirdParty);
                    existingThirdParty.setId(id);
                    return convertToThirdPartyDTO(thirdPartyRepository.save(existingThirdParty));
                }).orElse(null));
    }

    public String deleteThirdParty(String id) throws ThirdPartyNotFoundException {
        try {
            if (getThirdPartyById(id).isPresent()) {
                thirdPartyRepository.deleteById(id);
                return "Third Party Deleted Successfully";
            } else {
                throw new ThirdPartyNotFoundException("Third Party Not Found in the DataBase");
            }
        } finally {

        }

    }

    private ThirdParty convertToThirdPartyEntity(ThirdPartyDTO thirdPartyDTO) {
        ThirdParty thirdParty = new ThirdParty();
        BeanUtils.copyProperties(thirdPartyDTO, thirdParty);
        return thirdParty;
    }

    private ThirdPartyDTO convertToThirdPartyDTO(ThirdParty thirdParty) {
        ThirdPartyDTO thirdPartyDTO = new ThirdPartyDTO();
        BeanUtils.copyProperties(thirdParty, thirdPartyDTO);
        return thirdPartyDTO;
    }

    public List<ThirdPartyDTO> getThirdPartybyFilter(ThirdPartyDTO thirdPartyDTO) {
        // List<ThirdParty> filteredThirdParties =
        // thirdPartyRepository.findThirdPartiesByFilter(
        // Optional.ofNullable(thirdPartyDTO.getName()),
        // Optional.ofNullable(thirdPartyDTO.getAddress()),
        // Optional.ofNullable(thirdPartyDTO.getPhoneNumber()),
        // Optional.ofNullable(thirdPartyDTO.getEmailAddress()),
        // Optional.ofNullable(thirdPartyDTO.getPrimaryContactName()),
        // Optional.ofNullable(thirdPartyDTO.getPrimaryContactEmail()),
        // Optional.ofNullable(thirdPartyDTO.getPrimaryContactTitle()),
        // Optional.ofNullable(thirdPartyDTO.getLegalStructure()), mongoTemplate);
        // System.out.println(filteredThirdParties.toString());
        // return filteredThirdParties.stream()
        // .map(this::convertToThirdPartyDTO)
        // .collect(Collectors.toList());
        Query query = new Query();
        query.addCriteria(new Criteria("name").is(thirdPartyDTO.getName()));

        List<ThirdParty> thirdPartyDTOs = mongoTemplate.find(query, ThirdParty.class);

        System.out.println(thirdPartyDTOs.toString());
        return thirdPartyDTOs.stream()
                .map(this::convertToThirdPartyDTO)
                .collect(Collectors.toList());
    }
}
