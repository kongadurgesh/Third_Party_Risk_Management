package com.tprm.spi.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tprm.spi.dto.ThirdPartyDTO;
import com.tprm.spi.entity.ThirdParty;
import com.tprm.spi.exception.ThirdpartyNameConflictException;
import com.tprm.spi.repository.ThirdPartyRepository;

public class ThirdPartyServiceTest {
    @InjectMocks
    private ThirdPartyService thirdPartyService;

    @Mock
    private ThirdPartyRepository thirdPartyRepository;

    @BeforeEach
    public void SetUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateThirdPartySuccess() throws ThirdpartyNameConflictException {
        ThirdPartyDTO thirdPartyDTO = new ThirdPartyDTO();

        thirdPartyDTO.setName("Test Third Party");
        thirdPartyDTO.setAddress("India");
        thirdPartyDTO.setEmailAddress("test@mail.com");
        thirdPartyDTO.setLegalStructure("test setLegalStructure");
        thirdPartyDTO.setPhoneNumber("+1 (123) 456-7890");
        thirdPartyDTO.setPrimaryContactEmail("pc@email.com");
        thirdPartyDTO.setPrimaryContactName("test");
        thirdPartyDTO.setPrimaryContactTitle("pc title");

        when(thirdPartyRepository.findByName("Test Third Party")).thenReturn(Optional.empty());
        when(thirdPartyRepository.save(any(ThirdParty.class))).thenReturn(new ThirdParty());

        ThirdPartyDTO partyDTO = thirdPartyService.createThirdParty(thirdPartyDTO);

        assertNotNull(partyDTO);
        // assertEquals("Test Third Party", partyDTO.getName());

        verify(thirdPartyRepository, times(1)).findByName("Test Third Party");
        verify(thirdPartyRepository, times(1)).save(any(ThirdParty.class));
    }
}
