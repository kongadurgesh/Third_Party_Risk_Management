package com.tprm.spi.dto;

import lombok.Data;

@Data
public class ThirdPartyFinancialsDTO {
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
