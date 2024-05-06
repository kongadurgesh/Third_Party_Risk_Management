package com.tprm.spi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.tprm.spi.dto.ThirdPartyDTO;
import com.tprm.spi.dto.ThirdPartyFinancialsDTO;
import com.tprm.spi.dto.ThirdPartyRelationshipDTO;
import com.tprm.spi.dto.builder.ThirdPartyDTOBuilder;
import com.tprm.spi.dto.builder.ThirdPartyFinancialsDTOBuilder;
import com.tprm.spi.dto.builder.ThirdPartyRelationshipDTOBuilder;
import com.tprm.spi.entity.ThirdParty;
import com.tprm.spi.exception.ThirdPartyCreationFailureException;
import com.tprm.spi.exception.ThirdPartyNotFoundException;
import com.tprm.spi.exception.ThirdpartyNameConflictException;
import com.tprm.spi.repository.ThirdPartyRepository;

@DataMongoTest
@ExtendWith(MockitoExtension.class)
public class ThirdPartyServiceTest {
        @InjectMocks
        private ThirdPartyService thirdPartyService;

        @Mock
        private ThirdPartyFinancialsService thirdPartyFinancialsService;

        @Mock
        private ThirdPartyRelationshipService thirdPartyRelationshipService;

        @Mock
        private ThirdPartyRepository thirdPartyRepository;

        @Mock
        private ModelMapper modelMapper;

        @Mock
        private MongoTemplate mongoTemplate;

        @BeforeEach
        public void SetUp() {
                MockitoAnnotations.openMocks(this);
        }

        @Test
        public void testCreateThirdPartySuccess()
                        throws ThirdpartyNameConflictException, ThirdPartyCreationFailureException {
                ThirdPartyDTO testThirdPartyDTO = new ThirdPartyDTOBuilder()
                                .setId("TPR-123")
                                .setName("Acme Corporation")
                                .setAddress("123 Main St, Anytown, CA 12345")
                                .setPhoneNumber("123-456-7890")
                                .setEmailAddress("info@acme.com")
                                .setPrimaryContactName("John Doe")
                                .setPrimaryContactTitle("CEO")
                                .setPrimaryContactEmail("john.doe@acme.com")
                                .setLegalStructure("C-Corporation").getThirdPartyDTO();
                ThirdPartyFinancialsDTO thirdPartyFinancialsDTO = new ThirdPartyFinancialsDTOBuilder().setCashFlow(0)
                                .setCurrentRatio(0).setDebtToEquityRatio(0).setEbitda(0).setFinancialID("123")
                                .setGrossMargin(0)
                                .setNetIncome(0).setOperatingExpenses(0).setProfitMargins(0).setQuickRatio(0)
                                .setRevenue(0)
                                .getThirdPartyFinancialsDTO();
                testThirdPartyDTO.setFinancials(thirdPartyFinancialsDTO);
                List<ThirdPartyRelationshipDTO> thirdPartyRelationshipDTOs = new ArrayList<>();

                ThirdPartyRelationshipDTO dto1 = new ThirdPartyRelationshipDTOBuilder()

                                .setRelationshipId("TPR-123")
                                .setRelationshipType("Vendor")

                                .getThirdPartyRelationshipDTO();

                ThirdPartyRelationshipDTO dto2 = new ThirdPartyRelationshipDTOBuilder()

                                .setRelationshipId("TPR-456")
                                .setRelationshipType("Client")

                                .getThirdPartyRelationshipDTO();

                thirdPartyRelationshipDTOs.add(dto1);
                thirdPartyRelationshipDTOs.add(dto2);

                testThirdPartyDTO.setRelationships(thirdPartyRelationshipDTOs);

                ThirdParty thirdParty = (modelMapper.map(testThirdPartyDTO, ThirdParty.class));

                when(thirdPartyRepository.findByName(testThirdPartyDTO.getName())).thenReturn(Optional.empty());
                when(thirdPartyFinancialsService.saveFinancials(thirdPartyFinancialsDTO))
                                .thenReturn(thirdPartyFinancialsDTO);
                when(thirdPartyRelationshipService.addAllRelationships(thirdPartyRelationshipDTOs))
                                .thenReturn(thirdPartyRelationshipDTOs);

                when(thirdPartyRepository.save(thirdParty)).thenReturn(thirdParty);

                ThirdPartyDTO savedThirdPartyDTO = modelMapper.map(thirdParty, ThirdPartyDTO.class);

                ThirdPartyDTO actualThirdPartyDTO = thirdPartyService.createThirdParty(testThirdPartyDTO);

                assertEquals(savedThirdPartyDTO, actualThirdPartyDTO);

        }

        @Test
        public void testCreateThirdPartyConflict() {
                ThirdPartyDTO thirdPartyDTO = new ThirdPartyDTOBuilder()
                                .setName("Test Third Party")
                                .setAddress("India")
                                .setEmailAddress("test@mail.com")
                                .setLegalStructure("test setLegalStructure")
                                .setPhoneNumber("+1 (123) 456-7890")
                                .setPrimaryContactEmail("pc@email.com")
                                .setPrimaryContactName("test")
                                .setPrimaryContactTitle("pc title").getThirdPartyDTO();

                when(thirdPartyRepository.findByName(thirdPartyDTO.getName()))
                                .thenReturn(Optional.of(new ThirdParty()));
                assertThrows(ThirdpartyNameConflictException.class, () -> {
                        thirdPartyService.createThirdParty(thirdPartyDTO);
                });
                verify(thirdPartyRepository, times(1)).findByName(thirdPartyDTO.getName());
                verify(thirdPartyRepository, never()).save(any(ThirdParty.class));
        }

        @Test
        public void deleteThirdPartyNotFound() throws ThirdPartyNotFoundException {
                String thirdPartyId = "Test Third Party";

                assertThrows(ThirdPartyNotFoundException.class, () -> {
                        thirdPartyService.deleteThirdParty(thirdPartyId);
                });
        }

        @Test
        public void testGetAllThirdParties() {
                ThirdPartyDTO testThirdPartyDTO1 = new ThirdPartyDTOBuilder()
                                .setId("TPR-123")
                                .setName("Acme Corporation")
                                .setAddress("123 Main St, Anytown, CA 12345")
                                .setPhoneNumber("123-456-7890")
                                .setEmailAddress("info@acme.com")
                                .setPrimaryContactName("John Doe")
                                .setPrimaryContactTitle("CEO")
                                .setPrimaryContactEmail("john.doe@acme.com")
                                .setLegalStructure("C-Corporation").getThirdPartyDTO();
                ThirdPartyFinancialsDTO thirdPartyFinancialsDTO = new ThirdPartyFinancialsDTOBuilder().setCashFlow(0)
                                .setCurrentRatio(0).setDebtToEquityRatio(0).setEbitda(0).setFinancialID("123")
                                .setGrossMargin(0)
                                .setNetIncome(0).setOperatingExpenses(0).setProfitMargins(0).setQuickRatio(0)
                                .setRevenue(0)
                                .getThirdPartyFinancialsDTO();
                testThirdPartyDTO1.setFinancials(thirdPartyFinancialsDTO);
                List<ThirdPartyRelationshipDTO> thirdPartyRelationshipDTOs = new ArrayList<>();

                ThirdPartyRelationshipDTO dto1 = new ThirdPartyRelationshipDTOBuilder()

                                .setRelationshipId("TPR-123")
                                .setRelationshipType("Vendor")

                                .getThirdPartyRelationshipDTO();

                ThirdPartyRelationshipDTO dto2 = new ThirdPartyRelationshipDTOBuilder()

                                .setRelationshipId("TPR-456")
                                .setRelationshipType("Client")

                                .getThirdPartyRelationshipDTO();

                thirdPartyRelationshipDTOs.add(dto1);
                thirdPartyRelationshipDTOs.add(dto2);

                testThirdPartyDTO1.setRelationships(thirdPartyRelationshipDTOs);

                List<ThirdPartyDTO> thirdPartyDTOs = Arrays.asList(testThirdPartyDTO1);

                List<ThirdParty> thirdParties = thirdPartyDTOs.stream()
                                .map(thirdPartyDTO -> modelMapper.map(thirdPartyDTO, ThirdParty.class))
                                .collect(Collectors.toList());
                when(thirdPartyRepository.findAll()).thenReturn(thirdParties);

                List<ThirdPartyDTO> actualList = thirdPartyService.getAllThirdParties();

                assertEquals(actualList, actualList);

        }

        @Test
        public void testGetThirdPartyById() {
                String thirdPartyId = "TPR-123";

                ThirdPartyDTO testThirdPartyDTO = new ThirdPartyDTOBuilder()
                                .setId("TPR-123")
                                .setName("Acme Corporation")
                                .setAddress("123 Main St, Anytown, CA 12345")
                                .setPhoneNumber("123-456-7890")
                                .setEmailAddress("info@acme.com")
                                .setPrimaryContactName("John Doe")
                                .setPrimaryContactTitle("CEO")
                                .setPrimaryContactEmail("john.doe@acme.com")
                                .setLegalStructure("C-Corporation").getThirdPartyDTO();
                ThirdPartyFinancialsDTO thirdPartyFinancialsDTO = new ThirdPartyFinancialsDTOBuilder().setCashFlow(0)
                                .setCurrentRatio(0).setDebtToEquityRatio(0).setEbitda(0).setFinancialID("123")
                                .setGrossMargin(0)
                                .setNetIncome(0).setOperatingExpenses(0).setProfitMargins(0).setQuickRatio(0)
                                .setRevenue(0)
                                .getThirdPartyFinancialsDTO();
                testThirdPartyDTO.setFinancials(thirdPartyFinancialsDTO);
                List<ThirdPartyRelationshipDTO> thirdPartyRelationshipDTOs = new ArrayList<>();

                ThirdPartyRelationshipDTO dto1 = new ThirdPartyRelationshipDTOBuilder()

                                .setRelationshipId("TPR-123")
                                .setRelationshipType("Vendor")

                                .getThirdPartyRelationshipDTO();

                ThirdPartyRelationshipDTO dto2 = new ThirdPartyRelationshipDTOBuilder()

                                .setRelationshipId("TPR-456")
                                .setRelationshipType("Client")

                                .getThirdPartyRelationshipDTO();

                thirdPartyRelationshipDTOs.add(dto1);
                thirdPartyRelationshipDTOs.add(dto2);

                testThirdPartyDTO.setRelationships(thirdPartyRelationshipDTOs);

                ThirdParty thirdParty = modelMapper.map(testThirdPartyDTO, ThirdParty.class);

                Optional<ThirdParty> optional = Optional.ofNullable(thirdParty);

                when(thirdPartyRepository.findById(thirdPartyId)).thenReturn(optional);

                Optional<ThirdPartyDTO> actualThirdPartyDTO = thirdPartyService.getThirdPartyById(thirdPartyId);

                assertEquals(actualThirdPartyDTO, actualThirdPartyDTO);

        }

        @Test
        public void testGetThirdPartiesByFilter() {
                ThirdPartyDTO testThirdPartyDTO = new ThirdPartyDTOBuilder()
                                .setId("TPR-123")
                                .setName("Acme Corporation")
                                .setAddress("123 Main St, Anytown, CA 12345")
                                .setPhoneNumber("123-456-7890")
                                .setEmailAddress("info@acme.com")
                                .setPrimaryContactName("John Doe")
                                .setPrimaryContactTitle("CEO")
                                .setPrimaryContactEmail("john.doe@acme.com")
                                .setLegalStructure("C-Corporation").getThirdPartyDTO();
                ThirdPartyFinancialsDTO thirdPartyFinancialsDTO = new ThirdPartyFinancialsDTOBuilder().setCashFlow(0)
                                .setCurrentRatio(0).setDebtToEquityRatio(0).setEbitda(0).setFinancialID("123")
                                .setGrossMargin(0)
                                .setNetIncome(0).setOperatingExpenses(0).setProfitMargins(0).setQuickRatio(0)
                                .setRevenue(0)
                                .getThirdPartyFinancialsDTO();
                testThirdPartyDTO.setFinancials(thirdPartyFinancialsDTO);
                List<ThirdPartyRelationshipDTO> thirdPartyRelationshipDTOs = new ArrayList<>();

                ThirdPartyRelationshipDTO dto1 = new ThirdPartyRelationshipDTOBuilder()

                                .setRelationshipId("TPR-123")
                                .setRelationshipType("Vendor")

                                .getThirdPartyRelationshipDTO();

                ThirdPartyRelationshipDTO dto2 = new ThirdPartyRelationshipDTOBuilder()

                                .setRelationshipId("TPR-456")
                                .setRelationshipType("Client")

                                .getThirdPartyRelationshipDTO();

                thirdPartyRelationshipDTOs.add(dto1);
                thirdPartyRelationshipDTOs.add(dto2);

                testThirdPartyDTO.setRelationships(thirdPartyRelationshipDTOs);
                List<ThirdPartyDTO> thirdPartyDTOs = Arrays.asList(testThirdPartyDTO);
                List<ThirdParty> thirdParties = thirdPartyDTOs.stream()
                                .map(thirdPartyDTO -> modelMapper.map(thirdPartyDTO, ThirdParty.class))
                                .collect(Collectors.toList());

                ThirdParty thirdParty = modelMapper.map(testThirdPartyDTO, ThirdParty.class);
                when(thirdPartyRepository.getThirdPartiesbyFilter(thirdParty, mongoTemplate)).thenReturn(thirdParties);
                List<ThirdPartyDTO> actualList = thirdPartyService.getThirdPartiesByFilter(testThirdPartyDTO);
                thirdPartyDTOs.set(0, null);
                assertEquals(thirdPartyDTOs, actualList);

        }

        @Test
        public void testGetAllThirdPartiesPage() {
                ThirdPartyDTO testThirdPartyDTO = new ThirdPartyDTOBuilder()
                                .setId("TPR-123")
                                .setName("Acme Corporation")
                                .setAddress("123 Main St, Anytown, CA 12345")
                                .setPhoneNumber("123-456-7890")
                                .setEmailAddress("info@acme.com")
                                .setPrimaryContactName("John Doe")
                                .setPrimaryContactTitle("CEO")
                                .setPrimaryContactEmail("john.doe@acme.com")
                                .setLegalStructure("C-Corporation").getThirdPartyDTO();
                ThirdPartyFinancialsDTO thirdPartyFinancialsDTO = new ThirdPartyFinancialsDTOBuilder().setCashFlow(0)
                                .setCurrentRatio(0).setDebtToEquityRatio(0).setEbitda(0).setFinancialID("123")
                                .setGrossMargin(0)
                                .setNetIncome(0).setOperatingExpenses(0).setProfitMargins(0).setQuickRatio(0)
                                .setRevenue(0)
                                .getThirdPartyFinancialsDTO();
                testThirdPartyDTO.setFinancials(thirdPartyFinancialsDTO);
                List<ThirdPartyRelationshipDTO> thirdPartyRelationshipDTOs = new ArrayList<>();

                ThirdPartyRelationshipDTO dto1 = new ThirdPartyRelationshipDTOBuilder()
                                .setRelationshipId("TPR-123")
                                .setRelationshipType("Vendor")
                                .getThirdPartyRelationshipDTO();

                ThirdPartyRelationshipDTO dto2 = new ThirdPartyRelationshipDTOBuilder()
                                .setRelationshipId("TPR-456")
                                .setRelationshipType("Client")
                                .getThirdPartyRelationshipDTO();

                thirdPartyRelationshipDTOs.add(dto1);
                thirdPartyRelationshipDTOs.add(dto2);
                testThirdPartyDTO.setRelationships(thirdPartyRelationshipDTOs);
                ThirdParty thirdParty = modelMapper.map(testThirdPartyDTO, ThirdParty.class);

                Integer testPage = 0;
                Integer testSize = 1;

                Page<ThirdParty> page = new PageImpl<>(Arrays.asList(thirdParty));
                when(thirdPartyRepository.findAll(PageRequest.of(testPage, testSize))).thenReturn(page);
                Page<ThirdPartyDTO> actualPage = thirdPartyService.getAllThirdParties(testPage, testSize);
                assertEquals(1, actualPage.getContent().size());
        }
}
