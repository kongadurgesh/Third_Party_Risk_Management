package com.tprm.spi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.tprm.spi.dto.ThirdPartyFinancialsDTO;
import com.tprm.spi.dto.builder.ThirdPartyFinancialsDTOBuilder;
import com.tprm.spi.entity.ThirdPartyFinancials;
import com.tprm.spi.repository.ThirdPartyFinancialsRepository;

@ExtendWith(MockitoExtension.class)
public class ThirdPartyFinancialsServiceTest {

    @Mock
    private ThirdPartyFinancialsRepository thirdPartyFinancialsRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ThirdPartyFinancialsService thirdPartyFinancialsService;

    @Mock
    private MongoTemplate mongoTemplate;

    @Test
    public void testSaveFinancials() {
        // Mock data
        ThirdPartyFinancialsDTO thirdPartyFinancialsDTO = new ThirdPartyFinancialsDTOBuilder().setCashFlow(0)
                .setCurrentRatio(0).setDebtToEquityRatio(0).setEbitda(0).setFinancialID("123").setGrossMargin(0)
                .setNetIncome(0).setOperatingExpenses(0).setProfitMargins(0).setQuickRatio(0).setRevenue(0)
                .getThirdPartyFinancialsDTO();
        ThirdPartyFinancials thirdPartyFinancials = modelMapper.map(thirdPartyFinancialsDTO,
                ThirdPartyFinancials.class);
        ThirdPartyFinancialsDTO savedDto = new ThirdPartyFinancialsDTOBuilder().setCashFlow(0).setCurrentRatio(0)
                .setDebtToEquityRatio(0).setEbitda(0).setFinancialID("123").setGrossMargin(0).setNetIncome(0)
                .setOperatingExpenses(0).setProfitMargins(0).setQuickRatio(0).setRevenue(0)
                .getThirdPartyFinancialsDTO();

        // Mock behavior
        when(modelMapper.map(thirdPartyFinancialsDTO, ThirdPartyFinancials.class)).thenReturn(thirdPartyFinancials);
        when(thirdPartyFinancialsRepository.save(thirdPartyFinancials)).thenReturn(thirdPartyFinancials);
        when(modelMapper.map(thirdPartyFinancials, ThirdPartyFinancialsDTO.class)).thenReturn(savedDto);

        // Call the method
        ThirdPartyFinancialsDTO returnedDto = thirdPartyFinancialsService.saveFinancials(thirdPartyFinancialsDTO);

        // Assertions
        assertEquals(savedDto, returnedDto);
        verify(thirdPartyFinancialsRepository).save(thirdPartyFinancials);
    }

    @Test
    public void testDeleteFinancials() {
        String thirdPartyId = "testId";

        thirdPartyFinancialsService.deleteFinancialsbyId(thirdPartyId);

        verify(thirdPartyFinancialsRepository).deleteById(thirdPartyId);
    }

    @Test
    public void testGetThirdPartyFinancialIdsForRevenueRange() {
        Double fromRange = 200.00;
        Double toRange = 2000.00;

        List<String> expectedList = new ArrayList<>();
        expectedList.add("123");
        expectedList.add("456");

        when(thirdPartyFinancialsRepository.getThirdPartyFinancialIdsForRevenueRange(fromRange, toRange, mongoTemplate))
                .thenReturn(expectedList);

        List<String> actualList = thirdPartyFinancialsService.getThirdPartyFinancialIdsForRevenueRange(fromRange,
                toRange);

        assertEquals(expectedList, actualList);
    }

    @Test
    public void testGetThirdPartyFinancialIdsByProfitMargins() {
        Double profitMargins = 100.00;

        List<String> expectedList = new ArrayList<>();
        expectedList.add("123");
        expectedList.add("456");

        when(thirdPartyFinancialsRepository.getThirdPartyFinancialIdsByProfitMargins(profitMargins, mongoTemplate))
                .thenReturn(expectedList);

        List<String> actualList = thirdPartyFinancialsService.getThirdPartyFinancialIdsByProfitMargins(profitMargins);

        assertEquals(expectedList, actualList);
    }

    @Test
    public void testGetThirdPartyFinancialIdsByFilters() {
        ThirdPartyFinancialsDTO thirdPartyFinancialsDTO = new ThirdPartyFinancialsDTOBuilder().setCashFlow(0)
                .setCurrentRatio(0).setDebtToEquityRatio(0).setEbitda(0).setFinancialID("123").setGrossMargin(0)
                .setNetIncome(0).setOperatingExpenses(0).setProfitMargins(0).setQuickRatio(0).setRevenue(0)
                .getThirdPartyFinancialsDTO();

        ThirdPartyFinancials thirdPartyFinancials = modelMapper.map(thirdPartyFinancialsDTO,
                ThirdPartyFinancials.class);

        ThirdPartyFinancialsDTO expectedThirdPartyFinancialsDTO = new ThirdPartyFinancialsDTOBuilder().setCashFlow(0)
                .setCurrentRatio(0).setDebtToEquityRatio(0).setEbitda(0).setFinancialID("123").setGrossMargin(0)
                .setNetIncome(0).setOperatingExpenses(0).setProfitMargins(0).setQuickRatio(0).setRevenue(0)
                .getThirdPartyFinancialsDTO();

        List<String> expectedList = new ArrayList<>();
        expectedList.add("123");
        expectedList.add("456");

        when(modelMapper.map(expectedThirdPartyFinancialsDTO, ThirdPartyFinancials.class))
                .thenReturn(thirdPartyFinancials);
        when(thirdPartyFinancialsRepository.getThirdPartyFinancialIdsByFilters(thirdPartyFinancials, mongoTemplate))
                .thenReturn(expectedList);

        List<String> actualList = thirdPartyFinancialsService
                .getThirdPartyFinancialIdsByFilters(thirdPartyFinancialsDTO);

        assertEquals(expectedList, actualList);
    }
}
