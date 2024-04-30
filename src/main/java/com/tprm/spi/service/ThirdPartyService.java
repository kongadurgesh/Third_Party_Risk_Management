package com.tprm.spi.service;

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

import com.tprm.spi.dto.ThirdPartyDTO;
import com.tprm.spi.dto.ThirdPartyFinancialsDTO;
import com.tprm.spi.entity.ThirdParty;
import com.tprm.spi.exception.ThirdPartyNotFoundException;
import com.tprm.spi.exception.ThirdpartyNameConflictException;
import com.tprm.spi.repository.ThirdPartyRepository;

@Service
public class ThirdPartyService {
    @Autowired
    private ThirdPartyRepository thirdPartyRepository;

    @Autowired
    private ThirdPartyFinancialsService thirdPartyFinancialsService;

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

    public ThirdPartyDTO createThirdParty(ThirdPartyDTO thirdPartyDTO) throws ThirdpartyNameConflictException {
        if (thirdPartyRepository.findByName(thirdPartyDTO.getName()).isPresent()) {
            throw new ThirdpartyNameConflictException("Third Party already exists in the DataBase...");
        }
        ThirdParty thirdParty = convertToThirdPartyEntity(thirdPartyDTO);
        ThirdPartyFinancialsDTO thirdPartyFinancialsDTO = thirdPartyFinancialsService
                .saveFinancials(thirdPartyDTO.getFinancials());
        thirdParty.getFinancials().setFinancialID(thirdPartyFinancialsDTO.getFinancialID());
        return convertToThirdPartyDTO(thirdPartyRepository.save(thirdParty));
    }

    public Optional<ThirdPartyDTO> updateThirdParty(String id, ThirdPartyDTO thirdPartyDTOToUpdate) {
        return Optional.of(thirdPartyRepository.findById(id)
                .map(existingThirdParty -> {
                    modelMapper.map(thirdPartyDTOToUpdate, existingThirdParty);
                    existingThirdParty.setId(id);
                    thirdPartyFinancialsService.saveFinancials(
                            modelMapper.map(existingThirdParty.getFinancials(), ThirdPartyFinancialsDTO.class));
                    return convertToThirdPartyDTO(thirdPartyRepository.save(existingThirdParty));
                }).orElse(null));
    }

    public String deleteThirdParty(String id) throws ThirdPartyNotFoundException {
        try {
            Optional<ThirdPartyDTO> thirdPartyDTO = getThirdPartyById(id);
            if (thirdPartyDTO.isPresent()) {
                thirdPartyFinancialsService.deleteFinancialsbyId(thirdPartyDTO.get().getFinancials().getFinancialID());
                thirdPartyRepository.deleteById(id);
                return "Third Party Deleted Successfully";
            } else {
                throw new ThirdPartyNotFoundException("Third Party Not Found in the DataBase");
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

    public Page<ThirdPartyDTO> getAllThirdParties(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ThirdParty> thirdPartiesPage = thirdPartyRepository.findAll(pageable);
        return thirdPartiesPage.map(this::convertToThirdPartyDTO);
    }

    public ThirdPartyFinancialsDTO getThirdPartyFinancials(String thirdPartyId) {
        return getThirdPartyById(thirdPartyId).get().getFinancials();
    }

    public List<ThirdPartyDTO> getThirdPartiesByRevenueRange(Double fromRange, Double toRange) {
        List<ThirdPartyDTO> thirdPartyDTOs = getAllThirdParties();
        List<String> filteredThirdPartyIds = thirdPartyFinancialsService.getThirdPartyFinancialIdsForRevenueRange(
                fromRange,
                toRange);
        return thirdPartyDTOs.stream()
                .filter(thirdPartyDTO -> thirdPartyDTO.getFinancials() != null)
                .filter(thirdPartyDTO -> filteredThirdPartyIds.contains(thirdPartyDTO.getFinancials().getFinancialID()))
                .collect(Collectors.toList());
    }
}
