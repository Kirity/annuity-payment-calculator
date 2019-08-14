package com.lendico.challenge.annuityplangenerator.model;

import lombok.Data;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class LoanInputParams {
    @NotNull(message = "Please provide loanAmount")
    private BigDecimal loanAmount;
    @NotNull(message = "Please provide nominalRate")
    private BigDecimal nominalRate;
    @NotNull(message = "Please provide duration")
    private int duration;
    @NotNull(message = "Please provide startDate")
    private String startDate;
}
