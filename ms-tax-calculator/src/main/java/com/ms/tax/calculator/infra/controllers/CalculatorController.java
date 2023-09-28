package com.ms.tax.calculator.infra.controllers;

import com.ms.tax.calculator.infra.dtos.TaxResumeDto;
import com.ms.tax.calculator.infra.services.CalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/calculator")
public class CalculatorController {

	@Autowired
	private CalculatorService service;

	@GetMapping("/v1/taxes/{cnpj}")
	public ResponseEntity<TaxResumeDto> calculate(@PathVariable("cnpj") String cnpj) {
		var result = service.calculate(cnpj);
		return ResponseEntity.ok(result);
	}
}
