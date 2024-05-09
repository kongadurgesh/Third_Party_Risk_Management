package com.tprm.spi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tprm.spi.dto.ThirdPartyDTO;
import com.tprm.spi.service.ThirdPartyService;

@RestController
@RequestMapping(path = "/demo")
public class DemoController {
    @Autowired
    private ThirdPartyService thirdPartyService;

    @GetMapping
    public ResponseEntity<List<ThirdPartyDTO>> getAllThirdParties() {
        List<ThirdPartyDTO> thirdPartyDTOs = thirdPartyService.getAllThirdParties();
        return ResponseEntity.ok(thirdPartyDTOs);
    }
}
