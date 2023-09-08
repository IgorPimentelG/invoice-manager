package com.ms.client.infra.controllers;

import com.ms.client.domain.entities.Company;
import com.ms.client.infra.dtos.CreateAddressDto;
import com.ms.client.infra.dtos.CreateCompanyDto;
import com.ms.client.infra.dtos.UpdateCompanyDto;
import com.ms.client.infra.mappers.AddressMapper;
import com.ms.client.infra.mappers.CompanyMapper;
import com.ms.client.infra.services.CompanyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/company")
public class CompanyController {

	@Autowired
	private CompanyService service;

	private final AddressMapper addressMapper = AddressMapper.INSTANCE;
	private final CompanyMapper companyMapper = CompanyMapper.INSTANCE;

	@PostMapping("/v1/register")
	public ResponseEntity<Company> create(@RequestBody @Valid CreateCompanyDto data) {
		var address = addressMapper.createAddress(data.address());
		var company = companyMapper.createCompany(data);

		var result = service.create(company, address);

		result.add(
		  linkTo(methodOn(CompanyController.class).listAll()).withSelfRel()
		);

		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}

	@PatchMapping("/v1/add-address/{companyId}")
	public ResponseEntity<Company> addAddress(
	  @PathVariable("companyId") String companyId,
	  @RequestBody @Valid CreateAddressDto data
	) {
		var address = addressMapper.createAddress(data);
		var result = service.addAddress(address, companyId);

		result.add(
		  linkTo(methodOn(CompanyController.class).listAll()).withSelfRel()
		);

		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}

	@PutMapping("/v1/update/{id}")
	public ResponseEntity<Company> update(
	  @PathVariable("id") String id,
	  @RequestBody @Valid UpdateCompanyDto data
	) {
		var company = companyMapper.map(data);
		company.setId(id);

		var result = service.update(company);

		result.add(
		  linkTo(methodOn(CompanyController.class).listAll()).withSelfRel()
		);

		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	@PatchMapping("/v1/transfer/{companyId}/{managerId}")
	public ResponseEntity<Company> transfer(
	  @PathVariable("companyId") String companyId,
	  @PathVariable("managerId") String managerId
	) {
		var result = service.transfer(companyId, managerId);

		result.add(
		  linkTo(methodOn(CompanyController.class).listAll()).withSelfRel()
		);

		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	@GetMapping("/v1/find/{id}")
	public ResponseEntity<Company> find(@PathVariable("id") String id) {
		var result = service.findById(id);

		result.add(
		  linkTo(methodOn(CompanyController.class).listAll()).withSelfRel()
		);

		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	@GetMapping("/v1/list")
	public ResponseEntity<List<Company>> listAll() {
		var result = service.findAll();

		result.forEach(company -> {
			company.add(
			  linkTo(methodOn(CompanyController.class).find(company.getId())).withSelfRel()
			);
		});

		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	@DeleteMapping("/v1/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") String id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/v1/delete-address/{companyId}/{addressId}")
	public ResponseEntity<Company> deleteAddress(
	  @PathVariable("companyId") String companyId,
	  @PathVariable("addressId") long addressId
	) {
		var result = service.deleteAddress(companyId, addressId);

		result.add(
		  linkTo(methodOn(CompanyController.class).listAll()).withSelfRel()
		);

		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
}
