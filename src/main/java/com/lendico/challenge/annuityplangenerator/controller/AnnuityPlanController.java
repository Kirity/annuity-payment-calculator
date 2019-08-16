package com.lendico.challenge.annuityplangenerator.controller;

import com.lendico.challenge.annuityplangenerator.model.LoanInputParams;
import com.lendico.challenge.annuityplangenerator.model.LoanRepayment;
import com.lendico.challenge.annuityplangenerator.service.AnnuityPlanComputationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
public class AnnuityPlanController {

    @GetMapping("/")
    public String hello(){
        return "Welcome to loan repayment schedule service";
    }

    @PostMapping("/generate-plan")
    public ResponseEntity<List<LoanRepayment>> generatePlan(@Valid @RequestBody LoanInputParams loanInputParams){
        AnnuityPlanComputationService computationService = new AnnuityPlanComputationService(
                                                                loanInputParams.getLoanAmount(),
                                                                loanInputParams.getNominalRate(),
                                                                loanInputParams.getDuration(),
                                                                loanInputParams.getStartDate());
        return ResponseEntity.ok(computationService.computePlan());
    }
}
