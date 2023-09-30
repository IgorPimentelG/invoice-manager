package com.ms.tax.calculator.infra.controllers;

import com.ms.tax.calculator.domain.entities.TaxResume;
import com.ms.tax.calculator.infra.controllers.docs.ApiOperationCalculate;
import com.ms.tax.calculator.infra.controllers.docs.ApiOperationDownload;
import com.ms.tax.calculator.infra.controllers.docs.ApiOperationPay;
import com.ms.tax.calculator.infra.services.CalculatorService;
import com.ms.tax.calculator.infra.services.PDFService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/calculator")
@Tag(name = "Calculator", description = "Endpoints for managing taxes")
public class CalculatorController {

	@Autowired
	private CalculatorService service;

	@Autowired
	private PDFService pdfService;

	@ApiOperationCalculate
	@GetMapping("/v1/taxes/{cnpj}")
	public ResponseEntity<TaxResume> calculate(
	  @PathVariable("cnpj") String cnpj,
	  HttpServletRequest request
  ) throws IOException {
		var result = service.calculate(cnpj);
		pdfService.printSummary(result);

		var fileName = result.getId() + ".pdf";
		result.add(
		  linkTo(methodOn(CalculatorController.class).downloadTax(fileName, request)).withSelfRel()
		);

		return ResponseEntity.ok(result);
	}

	@ApiOperationPay
	@PatchMapping("/v1/pay/tax/{id}")
	public ResponseEntity<TaxResume> pay(@PathVariable("id") String id) {
		var result = service.pay(id);
		return ResponseEntity.ok(result);
	}

	@ApiOperationDownload
	@GetMapping("/v1/download/tax/{filename:.+}")
	public ResponseEntity<Resource> downloadTax(
	  @PathVariable("filename") String filename,
	  HttpServletRequest request
	) throws IOException {
		var file = pdfService.loadFile(filename);
		var contentType = request.getServletContext().getMimeType(file.getFile().getAbsolutePath());

		if (contentType.isBlank()) {
			contentType = "application/octet-stream";
		}

		return ResponseEntity.ok()
		  .contentType(MediaType.parseMediaType(contentType))
		  .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
		  .body(file);
	}
}
