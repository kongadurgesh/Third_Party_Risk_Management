package com.tprm.spi.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.tprm.spi.dto.IssueDTO;

@FeignClient(name = "issue-service", url = "${application.config.issues-url}")
public interface IssuesClient {
    @PostMapping
    List<IssueDTO> linkIssuestoThirdParty(@RequestBody List<IssueDTO> issueDTOs);

    @GetMapping
    List<IssueDTO> getAllIssuesLinkedToThirdPartyId(@RequestParam(name = "thirdPartyId") String thirdPartyId);

}
