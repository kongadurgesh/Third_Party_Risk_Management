package com.tprm.spi.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tprm.spi.dto.ThirdPartyFinancialsDTO;
import com.tprm.spi.entity.ThirdPartyFinancials;
import com.tprm.spi.repository.ThirdPartyFinancialsRepository;

@Service
public class ThirdPartyFinancialsService {
    @Autowired
    private ThirdPartyFinancialsRepository thirdPartyFinancialsRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ThirdPartyFinancialsDTO saveFinancials(ThirdPartyFinancialsDTO thirdPartyFinancialsDTO) {
        ThirdPartyFinancials thirdPartyFinancials = convertToThirdPartyFinancialsEntity(thirdPartyFinancialsDTO);
        return convertToThirdPartyFinancialsDTO(thirdPartyFinancialsRepository.save(thirdPartyFinancials));
    }

    private ThirdPartyFinancials convertToThirdPartyFinancialsEntity(ThirdPartyFinancialsDTO thirdPartyFinancialsDTO) {
        return modelMapper.map(thirdPartyFinancialsDTO, ThirdPartyFinancials.class);
    }

    private ThirdPartyFinancialsDTO convertToThirdPartyFinancialsDTO(ThirdPartyFinancials thirdPartyFinancials) {
        return modelMapper.map(thirdPartyFinancials, ThirdPartyFinancialsDTO.class);
    }
}
