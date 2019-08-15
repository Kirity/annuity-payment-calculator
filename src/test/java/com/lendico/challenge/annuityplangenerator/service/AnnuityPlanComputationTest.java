package com.lendico.challenge.annuityplangenerator.service;

import com.lendico.challenge.annuityplangenerator.errorhandling.InputValidationException;
import com.lendico.challenge.annuityplangenerator.model.LoanRepayment;
import org.junit.Test;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

public class AnnuityPlanComputationTest {

	@Test(expected = InputValidationException.class)
	public void expecting_InputValidationException_Test(){
		AnnuityPlanComputationService service = new AnnuityPlanComputationService(new BigDecimal(-5000),
				new BigDecimal(-5),
				-24,
				"TEST-2018-01-01T00:00:01Z");
		service.validate();
	}

	@Test
	public void erroneousInputs_Test() {
		String errors = null;
		try {
			AnnuityPlanComputationService service = new AnnuityPlanComputationService(
					new BigDecimal(-5000),
					new BigDecimal(-5),
					-24,
					"TEST-2018-01-01T00:00:01Z");
			service.validate();
		} catch (InputValidationException e) {
			errors = e.getMessage();
		}
		assertNotNull(errors);
	}

	@Test
	public void validInput_Test() {
		String errors = null;
		try {
			AnnuityPlanComputationService service = new AnnuityPlanComputationService(
					new BigDecimal(5000),
					new BigDecimal(5),
					24,
					"2018-01-01T00:00:01Z");
			service.validate();
		} catch (InputValidationException e) {
			errors = e.getMessage();
		}
		assertNull(errors);
	}

	@Test
	public void annuity_And_MonthlyPlan_Test(){
		AnnuityPlanComputationService service = new AnnuityPlanComputationService(new BigDecimal(5000),
				new BigDecimal(5),
				24,
				"2018-01-01T00:00:01Z");
		service.computeAnnuity();
		assertEquals(service.getAnnuity().toString(), "219.36");

		//Verifying the size
		List<LoanRepayment> repayments =  service.computePlan();
		assertEquals(24, repayments.size());

		//Verifying the repayment's first month values
		assertEquals("219.36", repayments.get(0).getBorrowerPaymentAmount().toString());
		assertEquals("2018-01-01T01:00:01CET", repayments.get(0).getDate().toString());
		assertEquals("5000", repayments.get(0).getInitialOutstandingPrincipal().toString());
		assertEquals("20.83", repayments.get(0).getInterest().toString());
		assertEquals("198.53", repayments.get(0).getPrincipal().toString());
		assertEquals("4801.47", repayments.get(0).getRemainingOutstandingPrincipal().toString());

		//Verifying the repayment's last month values
		assertEquals("219.28", repayments.get(23).getBorrowerPaymentAmount().toString());
		assertEquals("2019-12-01T01:00:01CET", repayments.get(23).getDate().toString());
		assertEquals("218.37", repayments.get(23).getInitialOutstandingPrincipal().toString());
		assertEquals("0.91", repayments.get(23).getInterest().toString());
		assertEquals("218.37", repayments.get(23).getPrincipal().toString());
		assertEquals("0", repayments.get(23).getRemainingOutstandingPrincipal().toString());
	}
}
