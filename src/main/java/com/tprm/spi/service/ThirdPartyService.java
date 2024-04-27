package com.tprm.spi.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.tprm.spi.dto.ThirdPartyDTO;
import com.tprm.spi.entity.ThirdParty;
import com.tprm.spi.exception.ThirdPartyNotFoundException;
import com.tprm.spi.repository.ThirdPartyRepository;

@Service
public class ThirdPartyService {

    private final ThirdPartyRepository thirdPartyRepository;

    public ThirdPartyService(ThirdPartyRepository thirdPartyRepository) {
        this.thirdPartyRepository = thirdPartyRepository;
    }

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

    public ThirdPartyDTO createThirdParty(ThirdPartyDTO thirdPartyDTO) {
        try {

        } catch (Exception e) {

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

    public ResponseEntity<String> deleteThirdParty(String id) {
        try {
            if (getThirdPartyById(id).isPresent()) {
                thirdPartyRepository.deleteById(id);
                return ResponseEntity.ok("Third Party Deleted Successfully..");
            } else {
                throw new ThirdPartyNotFoundException("Third Party Not Found in the DataBase");
            }
        } catch (ThirdPartyNotFoundException thirdPartyNotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(thirdPartyNotFoundException.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete Third Party: " + e.getMessage());
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
}
