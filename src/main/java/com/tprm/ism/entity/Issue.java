package com.tprm.ism.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.tprm.ism.enums.Priority;
import com.tprm.ism.enums.Severity;
import com.tprm.ism.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "issues")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Issue {
    @Id
    private String issueId;
    private String summary;
    private String description;
    private Severity severity;
    private Priority priority;
    private Status status;

}
