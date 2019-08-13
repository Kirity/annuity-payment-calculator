package com.lendico.challenge.annuityplangenerator.controller;

import lombok.extern.slf4j.Slf4j;
import com.lendico.challenge.annuityplangenerator.model.LoanInputParams;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class AnnuityPlanController {

    @PostMapping("/generate-plan")
    public void generatePlan(@RequestBody LoanInputParams loanInputParams){
        System.out.println(loanInputParams);
    }

}
