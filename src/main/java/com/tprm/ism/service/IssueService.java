package com.tprm.ism.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tprm.ism.dto.IssueDTO;
import com.tprm.ism.entity.Issue;
import com.tprm.ism.repository.IssueRepository;

@Service
public class IssueService {
    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private ModelMapper modelMapper;

    public IssueDTO createIssue(IssueDTO issueDTO) {
        Issue createdIssue = issueRepository.save(convertToIssue(issueDTO));

        return convertToIssueDTO(createdIssue);

    }

    public List<IssueDTO> getAllIssues() {
        return issueRepository.findAll().stream().map(this::convertToIssueDTO).collect(Collectors.toList());
    }

    private Issue convertToIssue(IssueDTO issueDTO) {
        return modelMapper.map(issueDTO, Issue.class);
    }

    private IssueDTO convertToIssueDTO(Issue issue) {
        return modelMapper.map(issue, IssueDTO.class);
    }
}
