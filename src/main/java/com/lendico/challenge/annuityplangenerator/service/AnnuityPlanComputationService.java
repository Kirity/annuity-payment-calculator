package com.lendico.challenge.annuityplangenerator.service;

import com.lendico.challenge.annuityplangenerator.model.LoanRepayment;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class AnnuityPlanComputationService {
    public  AnnuityPlanComputationService(BigDecimal loan_Amount, BigDecimal nominal_Rate, int duration, String start_date) {
        this.loan_Amount = loan_Amount;
        this.nominal_Rate = nominal_Rate;
        this.duration = duration;
        this.start_Date_Str = start_date;
    }
    private final BigDecimal loan_Amount;
    private final BigDecimal nominal_Rate;
    private final int duration;
    private final String start_Date_Str;
    private final BigDecimal ONE  = new BigDecimal(1);
    private final BigDecimal MONTHS_IN_YEAR = new BigDecimal(12);
    private final int PRECISION_LENGTH = 10;
    private final int SCALING_LENGTH = 2;
    private final BigDecimal DAYS_IN_MONTH = new BigDecimal(30);
    private final BigDecimal DAYS_IN_YEAR = new BigDecimal(360);
    private final DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ssz");
    private BigDecimal annuity;
    private DateTime startDate;


    private final BigDecimal PERCENTAGE = new BigDecimal("0.01");

    public List<LoanRepayment> computePlan(){
        validate();
        computeAnnuity();
        List<LoanRepayment> loanRepayments = null;
        LoanRepayment loanRepayment = null;
        BigDecimal monthlyIniOutstandingPrincipal = new BigDecimal(loan_Amount.toString());
        BigDecimal monthlyInterest = null;
        BigDecimal monthlyPrincipal = null;
        BigDecimal monthlyRemainingOutstandingPrinciple = null;
        try {
            loanRepayments = new ArrayList<>();
            for (int installment = 0; installment < duration; installment++){
                loanRepayment = new LoanRepayment();
                monthlyInterest = (nominal_Rate
                                    .multiply(PERCENTAGE)
                                    .multiply(DAYS_IN_MONTH)
                                    .multiply(monthlyIniOutstandingPrincipal))
                                    .divide(DAYS_IN_YEAR, PRECISION_LENGTH, RoundingMode.CEILING)
                                    .setScale(SCALING_LENGTH, BigDecimal.ROUND_HALF_UP);
                monthlyInterest = monthlyInterest.compareTo(monthlyIniOutstandingPrincipal) >0 ? monthlyIniOutstandingPrincipal : monthlyInterest;
                monthlyPrincipal = annuity.subtract(monthlyInterest);
                monthlyRemainingOutstandingPrinciple = monthlyIniOutstandingPrincipal.subtract(monthlyPrincipal);

                loanRepayment.setPrincipal(monthlyPrincipal);
                loanRepayment.setInterest(monthlyInterest);
                loanRepayment.setInitialOutstandingPrincipal(monthlyIniOutstandingPrincipal);
                loanRepayment.setRemainingOutstandingPrincipal(monthlyRemainingOutstandingPrinciple);
                loanRepayment.setBorrowerPaymentAmount(annuity);
                loanRepayment.setDate(startDate.plusMonths(installment).toString(fmt));

                // Exception or final settlement for last month
                if (installment+1 == duration){
                    loanRepayment.setRemainingOutstandingPrincipal(new BigDecimal(0));
                    loanRepayment.setPrincipal(monthlyIniOutstandingPrincipal);
                    loanRepayment.setBorrowerPaymentAmount(monthlyInterest.add(monthlyIniOutstandingPrincipal));
                }
                loanRepayments.add(loanRepayment);

                // Calculating for next month
                monthlyIniOutstandingPrincipal = monthlyIniOutstandingPrincipal.subtract(monthlyPrincipal);
            }
        }catch (Exception e){
            log.error("Un-expected error while computing the loan repayments ", e);
            // throw run time custom error
        }
        return loanRepayments;
    }

    private void validate() {
        try {
            this.startDate = new DateTime(start_Date_Str);
        } catch (IllegalArgumentException e) {
            log.error("Wrong date format for startDate ", e);
            //@TODO throw custom error
        }
    }


    /**
     * This method is used to compute annuity with below formula :
     * (monthly rate * loan amount) / (1- (1 + monthly rate)^(-num of installments))
     *(1 + monthly rate)^(-num of installments) is equal to 1 / ((1 + monthly rate)^(num of installments))
     * Let do it like : A / (B - C)
     * @return calculated annuity
     */
    private void computeAnnuity() {
        try {
            BigDecimal monthlyRate = nominal_Rate.divide( MONTHS_IN_YEAR, PRECISION_LENGTH,  RoundingMode.CEILING).multiply(PERCENTAGE);
            log.debug("Calculated Monthly Rate = {} ", monthlyRate);
            BigDecimal A = monthlyRate.multiply(loan_Amount);
            BigDecimal C = ONE.divide((ONE.add(monthlyRate)).pow(duration), PRECISION_LENGTH,  RoundingMode.CEILING);
            annuity = A.divide(ONE.subtract(C), RoundingMode.HALF_UP).setScale(SCALING_LENGTH, BigDecimal.ROUND_HALF_UP);
            log.info("Computed Annuity = {} ", annuity);
        } catch (Exception e) {
            log.error("Un-expected error while computing Annuity ", e);
            throw new RuntimeException("Throw 500 error with custom Calc error");
        }
    }
}
