package com.lendico.challenge.annuityplangenerator.model;

import lombok.Data;
import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.time.DateTimeException;

@Data
public class LoanRepayment {
    private BigDecimal borrowerPaymentAmount;
    private String date;
    private BigDecimal initialOutstandingPrincipal;
    private BigDecimal interest;
    private BigDecimal principal;
    private BigDecimal remainingOutstandingPrincipal;
}
