package com.tprm.spi.dto.builder;

import com.tprm.spi.dto.ThirdPartyFinancialsDTO;

public class ThirdPartyFinancialsDTOBuilder {
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

    public ThirdPartyFinancialsDTOBuilder setFinancialID(String financialID) {
        this.financialID = financialID;
        return this;
    }

    public ThirdPartyFinancialsDTOBuilder setRevenue(double revenue) {
        this.revenue = revenue;
        return this;
    }

    public ThirdPartyFinancialsDTOBuilder setProfitMargins(double profitMargins) {
        this.profitMargins = profitMargins;
        return this;
    }

    public ThirdPartyFinancialsDTOBuilder setNetIncome(double netIncome) {
        this.netIncome = netIncome;
        return this;
    }

    public ThirdPartyFinancialsDTOBuilder setGrossMargin(double grossMargin) {
        this.grossMargin = grossMargin;
        return this;
    }

    public ThirdPartyFinancialsDTOBuilder setOperatingExpenses(double operatingExpenses) {
        this.operatingExpenses = operatingExpenses;
        return this;
    }

    public ThirdPartyFinancialsDTOBuilder setEbitda(double ebitda) {
        this.ebitda = ebitda;
        return this;
    }

    public ThirdPartyFinancialsDTOBuilder setCurrentRatio(double currentRatio) {
        this.currentRatio = currentRatio;
        return this;
    }

    public ThirdPartyFinancialsDTOBuilder setQuickRatio(double quickRatio) {
        this.quickRatio = quickRatio;
        return this;
    }

    public ThirdPartyFinancialsDTOBuilder setDebtToEquityRatio(double debtToEquityRatio) {
        this.debtToEquityRatio = debtToEquityRatio;
        return this;
    }

    public ThirdPartyFinancialsDTOBuilder setCashFlow(double cashFlow) {
        this.cashFlow = cashFlow;
        return this;
    }

    public ThirdPartyFinancialsDTO getThirdPartyFinancialsDTO() {
        return new ThirdPartyFinancialsDTO(financialID, revenue, profitMargins, netIncome, grossMargin,
                operatingExpenses, ebitda, currentRatio, quickRatio, debtToEquityRatio, cashFlow);
    }

}
