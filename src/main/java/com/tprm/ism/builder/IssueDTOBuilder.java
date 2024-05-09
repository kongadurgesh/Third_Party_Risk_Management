package com.tprm.ism.builder;

import com.tprm.ism.dto.IssueDTO;
import com.tprm.ism.enums.Priority;
import com.tprm.ism.enums.Severity;
import com.tprm.ism.enums.Status;

public class IssueDTOBuilder {
    private String issueId;
    private String summary;
    private String description;
    private Severity severity;
    private Priority priority;
    private Status status;

    public IssueDTOBuilder setIssueId(String issueId) {
        this.issueId = issueId;
        return this;
    }

    public IssueDTOBuilder setSummary(String summary) {
        this.summary = summary;
        return this;
    }

    public IssueDTOBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public IssueDTOBuilder setSeverity(Severity severity) {
        this.severity = severity;
        return this;
    }

    public IssueDTOBuilder setPriority(Priority priority) {
        this.priority = priority;
        return this;
    }

    public IssueDTOBuilder setStatus(Status status) {
        this.status = status;
        return this;
    }

    public IssueDTO getIssueDTO() {
        return new IssueDTO(issueId, summary, description, severity, priority, status);
    }

}
