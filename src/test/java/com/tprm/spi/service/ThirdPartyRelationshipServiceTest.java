package com.tprm.spi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.tprm.spi.dto.ThirdPartyRelationshipDTO;
import com.tprm.spi.dto.builder.ThirdPartyRelationshipDTOBuilder;
import com.tprm.spi.entity.ThirdPartyRelationship;
import com.tprm.spi.repository.ThirdPartyRelationshipRepository;

@ExtendWith(MockitoExtension.class)
public class ThirdPartyRelationshipServiceTest {

    @Mock
    private ThirdPartyRelationshipRepository thirdPartyRelationshipRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ThirdPartyRelationshipService thirdPartyRelationshipService;

    @Test
    public void testAddRelationshipToThirdParty() {

        ThirdPartyRelationshipDTO thirdPartyRelationshipDTO = new ThirdPartyRelationshipDTOBuilder()
                .setRelationshipId("TPR-123") // Replace with your actual ID
                .setRelationshipType("Vendor") // Replace with actual relationship type
                .setStartDate(LocalDate.of(2024, 01, 01)) // Replace with start date
                .setEndDate(LocalDate.of(2025, 12, 31)) // Replace with end date
                .setStatus("Active")
                .setDescription("This is a sample third-party relationship for a vendor.")
                .setContractDetails("Contract details document reference") // Replace with actual reference
                .setRenewalTerms("Renewal terms document reference") // Replace with actual reference
                .setServiceLevelAgreements("Service Level Agreements document reference") // Replace with actual
                                                                                          // reference
                .setAssignedAccountManager("John Doe") // Replace with actual account manager name
                .setAssociatedProjectsOrInitiatives(Arrays.asList("Project Alpha", "Project Beta")) // Replace with
                                                                                                    // actual projects
                .setAdditionalContacts(Arrays.asList("Jane Smith", "Mike Jones")) // Replace with actual contacts
                .setAuditTrail("Initial creation by user: admin") // Replace with actual audit trail info
                .getThirdPartyRelationshipDTO();

        ThirdPartyRelationship thirdPartyRelationship = modelMapper.map(thirdPartyRelationshipDTO,
                ThirdPartyRelationship.class);

        ThirdPartyRelationshipDTO expectedThirdPartyRelationshipDTO = new ThirdPartyRelationshipDTOBuilder()
                .setRelationshipId("TPR-123") // Replace with your actual ID
                .setRelationshipType("Vendor") // Replace with actual relationship type
                .setStartDate(LocalDate.of(2024, 01, 01)) // Replace with start date
                .setEndDate(LocalDate.of(2025, 12, 31)) // Replace with end date
                .setStatus("Active")
                .setDescription("This is a sample third-party relationship for a vendor.")
                .setContractDetails("Contract details document reference") // Replace with actual reference
                .setRenewalTerms("Renewal terms document reference") // Replace with actual reference
                .setServiceLevelAgreements("Service Level Agreements document reference") // Replace with actual
                                                                                          // reference
                .setAssignedAccountManager("John Doe") // Replace with actual account manager name
                .setAssociatedProjectsOrInitiatives(Arrays.asList("Project Alpha", "Project Beta")) // Replace with
                                                                                                    // actual projects
                .setAdditionalContacts(Arrays.asList("Jane Smith", "Mike Jones")) // Replace with actual contacts
                .setAuditTrail("Initial creation by user: admin") // Replace with actual audit trail info
                .getThirdPartyRelationshipDTO();

        when(modelMapper.map(thirdPartyRelationshipDTO, ThirdPartyRelationship.class))
                .thenReturn(thirdPartyRelationship);

        thirdPartyRelationshipRepository.save(thirdPartyRelationship);

        when(modelMapper.map(thirdPartyRelationship, ThirdPartyRelationshipDTO.class))
                .thenReturn(expectedThirdPartyRelationshipDTO);

        String relationshipId = thirdPartyRelationshipService
                .addRelationshipToThirdParty(expectedThirdPartyRelationshipDTO);

        assertEquals("TPR-123", relationshipId);

    }
}
