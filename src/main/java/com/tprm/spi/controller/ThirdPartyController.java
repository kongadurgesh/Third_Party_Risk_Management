package com.tprm.spi.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tprm.spi.dto.ThirdPartyDTO;
import com.tprm.spi.exception.ThirdPartyNotFoundException;
import com.tprm.spi.exception.ThirdpartyNameConflictException;
import com.tprm.spi.service.ThirdPartyService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/thirdparties")
public class ThirdPartyController {
    @Autowired
    private ThirdPartyService thirdPartyService;

    @GetMapping
    public ResponseEntity<List<ThirdPartyDTO>> getAllThirdParties() {
        List<ThirdPartyDTO> thirdPartyDTOs = thirdPartyService.getAllThirdParties();
        return ResponseEntity.ok(thirdPartyDTOs);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ThirdPartyDTO> getThirdpartyById(@PathVariable String id) {
        Optional<ThirdPartyDTO> thirdPartyDTO = thirdPartyService.getThirdPartyById(id);
        return thirdPartyDTO.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<String> createThirdParty(@RequestBody @Valid ThirdPartyDTO thirdPartyDTO) {
        try {
            thirdPartyService.createThirdParty(thirdPartyDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Third Party Created Successfully");
        } catch (ThirdpartyNameConflictException thirdpartyNameConflictException) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(thirdpartyNameConflictException.getMessage());
        }

    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<ThirdPartyDTO> updateThirdParty(@PathVariable String id,
            @RequestBody @Valid ThirdPartyDTO thirdPartyDTO) {
        Optional<ThirdPartyDTO> updatedThirdPartyDTO = thirdPartyService.updateThirdParty(id, thirdPartyDTO);
        return updatedThirdPartyDTO.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteThirdParty(@PathVariable String id) {
        try {
            String thirdPartyDeletionStatus = thirdPartyService.deleteThirdParty(id);
            return ResponseEntity.ok(thirdPartyDeletionStatus);
        } catch (ThirdPartyNotFoundException thirdPartyNotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Third Party Does Not Exist in DataBase...");
        }
    }

    @PostMapping("/filters")
    public ResponseEntity<List<ThirdPartyDTO>> getThirdPartiesByFilter(@RequestBody ThirdPartyDTO thirdPartyDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(thirdPartyService.getThirdPartiesByFilter(thirdPartyDTO));

    }

}
