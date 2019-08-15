package com.lendico.challenge.annuityplangenerator.model;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class LoanInputParams {
    @NotNull(message = "Please provide loanAmount")
    @Min(1)
    private BigDecimal loanAmount;
    @NotNull(message = "Please provide nominalRate")
    @Min(0)
    private BigDecimal nominalRate;
    @NotNull(message = "Please provide duration")
    @Min(1)
    private int duration;
    @NotNull(message = "Please provide startDate")
    private String startDate;
}
