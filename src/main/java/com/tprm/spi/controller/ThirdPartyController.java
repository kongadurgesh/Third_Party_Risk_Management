package com.tprm.spi.controller;

import java.util.List;
import java.util.Optional;

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
import com.tprm.spi.service.ThirdPartyService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/thirdparties")
public class ThirdPartyController {
    private final ThirdPartyService thirdPartyService;

    public ThirdPartyController(ThirdPartyService thirdPartyService) {
        this.thirdPartyService = thirdPartyService;
    }

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
    public ResponseEntity<ThirdPartyDTO> createThirdParty(@RequestBody @Valid ThirdPartyDTO thirdPartyDTO) {
        ThirdPartyDTO craetedThirdPartyDTO = thirdPartyService.createThirdParty(thirdPartyDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(craetedThirdPartyDTO);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<ThirdPartyDTO> updateThirdParty(@PathVariable String id,
            @RequestBody @Valid ThirdPartyDTO thirdPartyDTO) {
        Optional<ThirdPartyDTO> updatedThirdPartyDTO = thirdPartyService.updateThirdParty(id, thirdPartyDTO);
        return updatedThirdPartyDTO.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteThirdParty(@PathVariable String id) {
        return thirdPartyService.deleteThirdParty(id);
    }
}
