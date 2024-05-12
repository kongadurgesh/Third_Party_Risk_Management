package com.tprm.spi.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.tprm.spi.dto.IssueDTO;

@FeignClient(name = "issue-service", url = "${application.config.issues-url}")
public interface IssuesClient {
    @PostMapping
    List<IssueDTO> linkIssuestoThirdParty(@RequestBody List<IssueDTO> issueDTOs);

    @GetMapping("/{thirdPartyId}/issues")
    List<IssueDTO> getAllIssuesLinkedToThirdPartyId(@PathVariable(name = "thirdPartyId") String thirdPartyId);

}
