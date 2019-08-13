package com.lendico.challenge.annuityplangenerator.model;

import lombok.Data;

@Data
public class LoanInputParams {
    private String loanAmount;
    private String nominalRate;
    private String duration;
    private String startDate;
}
