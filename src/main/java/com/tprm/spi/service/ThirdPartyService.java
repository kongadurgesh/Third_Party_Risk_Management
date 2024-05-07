package com.tprm.spi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.tprm.spi.constants.ThirdPartyConstants;
import com.tprm.spi.dto.ThirdPartyDTO;
import com.tprm.spi.dto.ThirdPartyFinancialsDTO;
import com.tprm.spi.dto.ThirdPartyRelationshipDTO;
import com.tprm.spi.entity.ThirdParty;
import com.tprm.spi.exception.ThirdPartyCreationFailureException;
import com.tprm.spi.exception.ThirdPartyNotFoundException;
import com.tprm.spi.exception.ThirdPartyRelationshipNotFoundException;
import com.tprm.spi.exception.ThirdpartyNameConflictException;
import com.tprm.spi.repository.ThirdPartyRepository;

import jakarta.validation.ConstraintViolationException;

@Service
public class ThirdPartyService {

    @Autowired
    private ThirdPartyRepository thirdPartyRepository;

    @Autowired
    private ThirdPartyFinancialsService thirdPartyFinancialService;

    @Autowired
    private ThirdPartyRelationshipService thirdPartyRelationshipService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ModelMapper modelMapper;

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

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @ExceptionHandler(Exception.class)
    public ThirdPartyDTO createThirdParty(ThirdPartyDTO thirdPartyDTO)
            throws ThirdpartyNameConflictException, ThirdPartyCreationFailureException, ConstraintViolationException {
        if (thirdPartyRepository.findByName(thirdPartyDTO.getName()).isPresent()) {
            throw new ThirdpartyNameConflictException("Third Party already exists in the DataBase...");
        }
        ThirdPartyFinancialsDTO thirdPartyFinancialsDTO = thirdPartyFinancialService
                .saveFinancials(thirdPartyDTO.getFinancials());
        List<ThirdPartyRelationshipDTO> thirdPartyRelationshipDTOs = thirdPartyRelationshipService
                .addAllRelationships(thirdPartyDTO.getRelationships());
        thirdPartyDTO.setFinancials(thirdPartyFinancialsDTO);
        thirdPartyDTO.setRelationships(thirdPartyRelationshipDTOs);
        ThirdParty thirdParty = convertToThirdPartyEntity(thirdPartyDTO);
        return convertToThirdPartyDTO(thirdPartyRepository.save(thirdParty));

    }

    public Optional<ThirdPartyDTO> updateThirdParty(String id, ThirdPartyDTO thirdPartyDTOToUpdate) {
        return Optional.of(thirdPartyRepository.findById(id)
                .map(existingThirdParty -> {
                    modelMapper.map(thirdPartyDTOToUpdate, existingThirdParty);
                    thirdPartyFinancialService.saveFinancials(
                            modelMapper.map(existingThirdParty.getFinancials(), ThirdPartyFinancialsDTO.class));
                    return convertToThirdPartyDTO(thirdPartyRepository.save(existingThirdParty));
                }).orElse(null));
    }

    public String deleteThirdParty(String id) throws ThirdPartyNotFoundException {
        try {
            Optional<ThirdPartyDTO> thirdPartyDTO = getThirdPartyById(id);
            if (thirdPartyDTO.isPresent()) {
                if (thirdPartyDTO.get().getFinancials() != null)
                    thirdPartyFinancialService
                            .deleteFinancialsbyId(thirdPartyDTO.get().getFinancials().getFinancialID());
                thirdPartyRepository.deleteById(id);
                return "Third Party Deleted Successfully";
            } else {
                throw new ThirdPartyNotFoundException(ThirdPartyConstants.THIRD_PARTY_NOT_FOUND);
            }
        } finally {

        }

    }

    private ThirdParty convertToThirdPartyEntity(ThirdPartyDTO thirdPartyDTO) {
        return modelMapper.map(thirdPartyDTO, ThirdParty.class);
    }

    private ThirdPartyDTO convertToThirdPartyDTO(ThirdParty thirdParty) {
        return modelMapper.map(thirdParty, ThirdPartyDTO.class);
    }

    public List<ThirdPartyDTO> getThirdPartiesByFilter(ThirdPartyDTO thirdPartyDTO) {

        List<ThirdParty> thirdParties = thirdPartyRepository
                .getThirdPartiesbyFilter(convertToThirdPartyEntity(thirdPartyDTO), mongoTemplate);

        return thirdParties.stream()
                .map(this::convertToThirdPartyDTO)
                .collect(Collectors.toList());
    }

    public Page<ThirdPartyDTO> getAllThirdParties(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ThirdParty> thirdPartiesPage = thirdPartyRepository.findAll(pageable);
        return thirdPartiesPage.map(this::convertToThirdPartyDTO);
    }

    public ThirdPartyFinancialsDTO getThirdPartyFinancials(String thirdPartyId) {
        return getThirdPartyById(thirdPartyId).get().getFinancials();
    }

    public List<ThirdPartyDTO> getThirdPartiesByRevenueRange(Double fromRange, Double toRange) {
        List<ThirdPartyDTO> thirdPartyDTOs = getAllThirdParties();
        List<String> filteredThirdPartyIds = thirdPartyFinancialService.getThirdPartyFinancialIdsForRevenueRange(
                fromRange,
                toRange);
        return thirdPartyDTOs.stream()
                .filter(thirdPartyDTO -> thirdPartyDTO.getFinancials() != null)
                .filter(thirdPartyDTO -> filteredThirdPartyIds.contains(thirdPartyDTO.getFinancials().getFinancialID()))
                .collect(Collectors.toList());
    }

    public List<ThirdPartyDTO> getThirdPartiesByProfitMargins(Double profitMargins) {
        List<ThirdPartyDTO> thirdPartyDTOs = getAllThirdParties();
        List<String> filteredThirdPartyIds = thirdPartyFinancialService
                .getThirdPartyFinancialIdsByProfitMargins(profitMargins);
        return thirdPartyDTOs.stream()
                .filter(thirdPartyDTO -> thirdPartyDTO.getFinancials() != null)
                .filter(thirdPartyDTO -> filteredThirdPartyIds.contains(thirdPartyDTO.getFinancials().getFinancialID()))
                .collect(Collectors.toList());
    }

    public List<ThirdPartyDTO> getThirdPartiesByFinancialFilters(ThirdPartyFinancialsDTO thirdPartyFinancialsDTO) {
        List<ThirdPartyDTO> thirdPartyDTOs = getAllThirdParties();
        List<String> filteredThirdPartyIds = thirdPartyFinancialService
                .getThirdPartyFinancialIdsByFilters(thirdPartyFinancialsDTO);

        return thirdPartyDTOs.stream()
                .filter(thirdPartyDTO -> filteredThirdPartyIds.contains(thirdPartyDTO.getFinancials().getFinancialID()))
                .collect(Collectors.toList());
    }

    public ThirdPartyDTO addRelationshipToThirdParty(String thirdPartyId,
            ThirdPartyRelationshipDTO thirdPartyRelationshipDTO) {
        String thirdPartyRelationshipId = thirdPartyRelationshipService.addRelationshipToThirdParty(
                thirdPartyRelationshipDTO);

        ThirdPartyDTO thirdPartyDTO = getThirdPartyById(thirdPartyId).get();
        thirdPartyRelationshipDTO.setRelationshipId(thirdPartyRelationshipId);
        if (thirdPartyDTO.getRelationships() == null) {
            List<ThirdPartyRelationshipDTO> thirdPartyRelationshipDTOs = new ArrayList<>();
            thirdPartyRelationshipDTOs.add(thirdPartyRelationshipDTO);
            thirdPartyDTO.setRelationships(thirdPartyRelationshipDTOs);
        } else
            thirdPartyDTO.getRelationships().add(thirdPartyRelationshipDTO);
        return convertToThirdPartyDTO(thirdPartyRepository.save(convertToThirdPartyEntity(thirdPartyDTO)));
    }

    public ThirdPartyDTO deleteThirdPartyRelationshipOfThirdPartyById(String thirdPartyId,
            String thirdPartyRelationshipId) throws ThirdPartyRelationshipNotFoundException {
        ThirdPartyDTO thirdPartyDTO = getThirdPartyById(thirdPartyId).get();
        if (thirdPartyDTO.getRelationships().size() == 0)
            throw new ThirdPartyRelationshipNotFoundException(
                    ThirdPartyConstants.NO_THIRD_PARTY_RELATIONSHIPS);
        thirdPartyRelationshipService.deleteRelationshipbyId(thirdPartyRelationshipId);

        thirdPartyDTO.getRelationships().removeIf(thirdPartyRelationshipDTO -> thirdPartyRelationshipDTO
                .getRelationshipId().equals(thirdPartyRelationshipId));

        return convertToThirdPartyDTO(thirdPartyRepository.save(convertToThirdPartyEntity(thirdPartyDTO)));
    }

    public List<ThirdPartyDTO> getThirdPartiesByRelationshipFilter(
            ThirdPartyRelationshipDTO thirdPartyRelationshipDTO) {
        List<String> filteredThirdPartyRelationshipIds = thirdPartyRelationshipService
                .getThirdPartyIdsByRelationshipFilter(thirdPartyRelationshipDTO, mongoTemplate);
        List<ThirdPartyDTO> thirdPartyDTOs = getAllThirdParties();

        return thirdPartyDTOs.stream()
                .filter(thirdPartyDTO -> thirdPartyDTO.getRelationships() != null)
                .filter(thirdPartyDTO -> thirdPartyDTO.getRelationships().stream()
                        .anyMatch(thirdPartyRelationshipDto -> filteredThirdPartyRelationshipIds
                                .contains(thirdPartyRelationshipDto.getRelationshipId())))
                .collect(Collectors.toList());
    }

    public String deleteAllThirdPartyRelationships(String thirdPartyId) throws ThirdPartyRelationshipNotFoundException {
        if (getThirdPartyById(thirdPartyId).get().getRelationships().isEmpty()) {
            throw new ThirdPartyRelationshipNotFoundException(ThirdPartyConstants.NO_THIRD_PARTY_RELATIONSHIPS);
        }
        ThirdPartyDTO thirdPartyDTO = getThirdPartyById(thirdPartyId).get();
        List<String> thirdPartyRelationhshipIds = thirdPartyDTO.getRelationships().stream()
                .map(ThirdPartyRelationshipDTO::getRelationshipId).collect(Collectors.toList());
        thirdPartyDTO.setRelationships(new ArrayList<>());
        thirdPartyRelationshipService.deleteAllRelationships(thirdPartyRelationhshipIds);
        thirdPartyRepository.save(convertToThirdPartyEntity(thirdPartyDTO));
        return ThirdPartyConstants.ALL_RELATIONSHIPS_DELETED;
    }
}
