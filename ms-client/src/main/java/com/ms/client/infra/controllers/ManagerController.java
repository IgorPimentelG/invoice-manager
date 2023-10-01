package com.ms.client.infra.controllers;

import com.ms.client.domain.entities.Manager;
import com.ms.client.infra.controllers.docs.manager.*;
import com.ms.client.infra.dtos.UpdateManagerDto;
import com.ms.client.infra.services.ManagerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/manager")
@Tag(name = "Manager", description = "Endpoints for managing the managers")
public class ManagerController {

	@Autowired
	private ManagerService service;

	@Autowired
	private PasswordEncoder encoder;

	@ApiOperationUpdate
	@PutMapping("/v1/update/{id}")
	public ResponseEntity<Manager> update(
	  @PathVariable("id") String id,
	  @RequestBody @Valid UpdateManagerDto data
	) {
		var entity = new Manager();
		entity.setId(id);

		if (data.getPhone() != null) entity.setPhone(data.getPhone());
		if (data.getPassword() != null) entity.setPassword(encoder.encode(data.getPassword()));

		var result = service.update(entity);

		result.add(
		  linkTo(methodOn(ManagerController.class).find(id)).withSelfRel()
		);

		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	@ApiOperationFind
	@GetMapping("/v1/find/{id}")
	public ResponseEntity<Manager> find(@PathVariable("id") String id) {
		var result = service.findById(id);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	@ApiOperationDisable
	@PatchMapping("/v1/disable/{id}")
	public ResponseEntity<Manager> disable(@PathVariable("id") String id) {
		var result = service.disable(id);

		result.add(
		  linkTo(methodOn(ManagerController.class).find(id)).withSelfRel()
		);

		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
}
