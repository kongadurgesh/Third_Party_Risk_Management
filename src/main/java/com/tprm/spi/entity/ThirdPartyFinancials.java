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
    private double revenue;
    private double profitMargins;
    private double netIncome;
    private double grossMargin;
    private double operatingExpenses;
    private double ebitda;
    private double currentRatio;
    private double quickRatio;
    private double debtToEquityRatio;
    private double cashFlow;
}
