package com.tprm.spi.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotEmpty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "third_parties")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThirdParty {
    @Id
    private String id;

    @NotEmpty(message = "Name is required")
    private String name;

    @NotEmpty(message = "Address is required")
    private String address;

    @Pattern(regexp = "\\+\\d{1,3} \\(\\d{1,3}\\) \\d{1,3}-\\d{4}", message = "Invalid phone number format")
    private String phoneNumber;

    @Email(message = "Invalid email address")
    @NotEmpty(message = "Email is required")
    private String emailAddress;

    @NotEmpty(message = "Invalid Primary Contact Name")
    private String primaryContactName;

    @NotEmpty(message = "Invalid Primary Contact Title")
    private String primaryContactTitle;

    @Email(message = "Invalid Primary Contact Email")
    @NotEmpty(message = "Primary Contact Email is required")
    private String primaryContactEmail;

    @NotEmpty(message = "Legal Structure is required")
    private String legalStructure;

    @DBRef
    private ThirdPartyFinancials financials;

    @DBRef
    private List<ThirdPartyRelationship> thirdPartyRelationships;

}
