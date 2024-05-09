package com.tprm.ism.dto;

import com.tprm.ism.enums.Priority;
import com.tprm.ism.enums.Severity;
import com.tprm.ism.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueDTO {
    private String issueId;
    private String summary;
    private String description;
    private Severity severity;
    private Priority priority;
    private Status status;
}
