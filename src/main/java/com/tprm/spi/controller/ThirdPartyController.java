package com.tprm.spi.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tprm.spi.client.AuthorizationClient;
import com.tprm.spi.constants.ThirdPartyConstants;
import com.tprm.spi.dto.IssueDTO;
import com.tprm.spi.dto.ThirdPartyDTO;
import com.tprm.spi.dto.ThirdPartyFinancialsDTO;
import com.tprm.spi.dto.ThirdPartyRelationshipDTO;
import com.tprm.spi.exception.ThirdPartyCreationFailureException;
import com.tprm.spi.exception.ThirdPartyNotFoundException;
import com.tprm.spi.exception.ThirdPartyRelationshipNotFoundException;
import com.tprm.spi.exception.ThirdpartyNameConflictException;
import com.tprm.spi.exception.UnauthorizedException;
import com.tprm.spi.service.ThirdPartyService;
import com.tprm.spi.utils.AuthenticationResponse;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/thirdparties")
public class ThirdPartyController {
    @Autowired
    private ThirdPartyService thirdPartyService;

    @Autowired
    private AuthorizationClient authorizationClient;

    // @GetMapping
    public ResponseEntity<List<ThirdPartyDTO>> getAllThirdParties() {
        List<ThirdPartyDTO> thirdPartyDTOs = thirdPartyService.getAllThirdParties();
        return ResponseEntity.ok(thirdPartyDTOs);
    }
    

    @GetMapping
    public ResponseEntity<List<ThirdPartyDTO>> getAllThirdParties(
            @RequestHeader("username") String userName,
            @RequestHeader("password") String password)
            throws UnauthorizedException {
        List<ThirdPartyDTO> thirdPartyDTOs = thirdPartyService.getAllThirdParties(userName, password);
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
            return ResponseEntity.status(HttpStatus.CREATED).body(ThirdPartyConstants.THIRD_PARTY_CREATED_SUCCESSFULLY);
        } catch (ThirdpartyNameConflictException thirdpartyNameConflictException) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(thirdpartyNameConflictException.getMessage());
        } catch (ThirdPartyCreationFailureException thirdPartyCreationFailureException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(thirdPartyCreationFailureException.getMessage());
        } catch (ConstraintViolationException constraintViolationException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(constraintViolationException.getMessage());
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
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ThirdPartyConstants.THIRD_PARTY_NOT_FOUND);
        }
    }

    @PostMapping("/filters")
    public ResponseEntity<List<ThirdPartyDTO>> getThirdPartiesByFilter(@RequestBody ThirdPartyDTO thirdPartyDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(thirdPartyService.getThirdPartiesByFilter(thirdPartyDTO));

    }

    @GetMapping("/pages")
    public ResponseEntity<Page<ThirdPartyDTO>> getAllThirdParties(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.status(HttpStatus.OK).body(thirdPartyService.getAllThirdParties(page, size));
    }

    @GetMapping("/{id}/financials")
    public ResponseEntity<ThirdPartyFinancialsDTO> getThirdPartyFinancials(
            @PathVariable(name = "id") String thirdPartyId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(thirdPartyService.getThirdPartyFinancials(thirdPartyId));
    }

    @GetMapping("/financials/revenue")
    public ResponseEntity<List<ThirdPartyDTO>> getThirdPartiesByRevenueRange(
            @RequestParam(required = false) Double fromRange, @RequestParam(required = false) Double toRange) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(thirdPartyService.getThirdPartiesByRevenueRange(fromRange, toRange));
    }

    @GetMapping("/financials/profits")
    public ResponseEntity<List<ThirdPartyDTO>> getThirdPartiesByProfitMargins(
            @RequestParam(required = false) Double profitMargins) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(thirdPartyService.getThirdPartiesByProfitMargins(profitMargins));
    }

    @PostMapping("/financials/filters")
    public ResponseEntity<List<ThirdPartyDTO>> getThirdPartiesByProfitMargins(
            @RequestBody ThirdPartyFinancialsDTO thirdPartyFinancialsDTO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(thirdPartyService.getThirdPartiesByFinancialFilters(thirdPartyFinancialsDTO));
    }

    @PostMapping("/{thirdPartyId}/relationships")
    public ResponseEntity<ThirdPartyDTO> addRelationshipToThirdParty(
            @PathVariable(name = "thirdPartyId") String thirdPartyId,
            @RequestBody ThirdPartyRelationshipDTO thirdPartyRelationshipDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(thirdPartyService.addRelationshipToThirdParty(thirdPartyId, thirdPartyRelationshipDTO));
    }

    @DeleteMapping("/{thirdPartyId}/relationships/{relationshipId}")
    public ResponseEntity<String> deleteThirdPartyRelationshipOfThirdPartyById(
            @PathVariable(name = "thirdPartyId") String thirdPartyId,
            @PathVariable(name = "relationshipId") String relationshipId) {
        try {
            thirdPartyService.deleteThirdPartyRelationshipOfThirdPartyById(thirdPartyId, relationshipId);
        } catch (ThirdPartyRelationshipNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(ThirdPartyConstants.THIRD_PARTY_RELATIONSHIP_DELETED_SUCCESSFULLY);
    }

    @PostMapping("/relationships/filters")
    public ResponseEntity<List<ThirdPartyDTO>> getThirdPartiesByRelationshipFilter(
            @RequestBody ThirdPartyRelationshipDTO thirdPartyRelationshipDTO) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(thirdPartyService.getThirdPartiesByRelationshipFilter(thirdPartyRelationshipDTO));
    }

    @DeleteMapping("/{thirdPartyId}/relationships")
    public ResponseEntity<String> deleteAllThirdPartyRelationships(@PathVariable String thirdPartyId) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(thirdPartyService.deleteAllThirdPartyRelationships(thirdPartyId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/{thirdPartyId}/issues")
    public ResponseEntity<String> linkIssuesToThirdParty(@PathVariable String thirdPartyId,
            @RequestBody List<IssueDTO> issueDTOs) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(thirdPartyService.linkIssuesToThirdParty(thirdPartyId, issueDTOs));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/test")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestHeader("username") String userName,
            @RequestHeader("password") String password) {
        return authorizationClient.authenticate(userName, password);
    }

}
