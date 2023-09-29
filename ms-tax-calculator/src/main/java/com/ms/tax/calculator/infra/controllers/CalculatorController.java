package com.ms.tax.calculator.infra.controllers;

import com.ms.tax.calculator.domain.entities.TaxResume;
import com.ms.tax.calculator.infra.controllers.docs.ApiOperationCalculate;
import com.ms.tax.calculator.infra.controllers.docs.ApiOperationPay;
import com.ms.tax.calculator.infra.services.CalculatorService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/calculator")
@Tag(name = "Calculator", description = "Endpoints for managing taxes")
public class CalculatorController {

	@Autowired
	private CalculatorService service;

	@ApiOperationCalculate
	@GetMapping("/v1/taxes/{cnpj}")
	public ResponseEntity<TaxResume> calculate(@PathVariable("cnpj") String cnpj) {
		var result = service.calculate(cnpj);
		return ResponseEntity.ok(result);
	}

	@ApiOperationPay
	@PatchMapping("/v1/pay/tax/{id}")
	public ResponseEntity<TaxResume> pay(@PathVariable("id") String id) {
		var result = service.pay(id);
		return ResponseEntity.ok(result);
	}
}
