package com.tprm.spi.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document(collection = "financials")
@AllArgsConstructor
@NoArgsConstructor
public class ThirdPartyFinancials {
    @Id
    private String financialID;
    private Double revenue;
    private Double profitMargins;
    private Double netIncome;
    private Double grossMargin;
    private Double operatingExpenses;
    private Double ebitda;
    private Double currentRatio;
    private Double quickRatio;
    private Double debtToEquityRatio;
    private Double cashFlow;
}
