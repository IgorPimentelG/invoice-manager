package com.ms.electronic.invoice.infra.controllers;

import com.ms.electronic.invoice.domain.entities.Invoice;
import com.ms.electronic.invoice.infra.controllers.docs.ApiOperationCancel;
import com.ms.electronic.invoice.infra.controllers.docs.ApiOperationFind;
import com.ms.electronic.invoice.infra.controllers.docs.ApiOperationList;
import com.ms.electronic.invoice.infra.controllers.docs.ApiOperationRegister;
import com.ms.electronic.invoice.infra.dtos.CreateInvoiceDto;
import com.ms.electronic.invoice.infra.helpers.FormatCNPJ;
import com.ms.electronic.invoice.infra.mappers.InvoiceMapper;
import com.ms.electronic.invoice.infra.services.InvoiceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping("/api/invoice")
@Tag(name = "Invoice", description = "Endpoints for managing invoices")
public class InvoiceController {

	@Autowired
	private InvoiceService service;

	private final InvoiceMapper invoiceMapper = InvoiceMapper.INSTANCE;

	@ApiOperationRegister
	@PostMapping("/v1/register")
	public ResponseEntity<Invoice> create(
	  @RequestParam("cnpj") String cnpj,
	  @RequestBody @Valid CreateInvoiceDto data,
	  HttpServletRequest request
	) {
		Invoice invoice = invoiceMapper.createEntity(data);

		var result = service.create(
		  cnpj,
		  invoice,
		  request.getHeader("Authorization")
		);

		result.add(
		  linkTo(methodOn(InvoiceController.class).find(result.getNumber())).withSelfRel()
		);

		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}

	@ApiOperationFind
	@GetMapping("/v1/show/{number}")
	public ResponseEntity<Invoice> find(@PathVariable("number") String number) {
		var result = service.findById(number);

		result.add(
		  linkTo(methodOn(InvoiceController.class).listAll(result.getIssuer().getCnpj())).withSelfRel()
		);

		return ResponseEntity.ok(result);
	}

	@ApiOperationList
	@GetMapping("/v1/list/{cnpj}")
	public ResponseEntity<List<Invoice>> listAll(@PathVariable("cnpj") String cnpj) {
		var result = service.findByCNPJ(FormatCNPJ.format(cnpj));

		result.forEach(invoice -> {
			invoice.add(
			  linkTo(methodOn(InvoiceController.class).find(invoice.getNumber())).withSelfRel()
			);
		});

		return ResponseEntity.ok(result);
	}

	@ApiOperationCancel
	@PatchMapping("/v1/cancel/{number}")
	public ResponseEntity<?> cancelInvoice(@PathVariable("number") String number) {
		service.cancel(number);
		return ResponseEntity.noContent().build();
	}
}
