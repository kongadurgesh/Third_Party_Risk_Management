package com.tprm.spi.entity;

import com.tprm.spi.enums.Priority;
import com.tprm.spi.enums.Severity;
import com.tprm.spi.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Issue {
    private String issueId;
    private String summary;
    private String description;
    private Severity severity;
    private Priority priority;
    private Status status;
    private String issueSource;
    private String issueSourceObjectId;
}
