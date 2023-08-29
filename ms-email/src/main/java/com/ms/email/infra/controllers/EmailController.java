package com.ms.email.infra.controllers;

import com.ms.email.domain.entities.Email;
import com.ms.email.domain.errors.InvalidValueException;
import com.ms.email.domain.errors.NotFoundException;
import com.ms.email.domain.factories.EmailFactory;
import com.ms.email.infra.controllers.dtos.EmailDTO;
import com.ms.email.infra.services.EmailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
public class EmailController {

	@Autowired
	private EmailService service;

	@PostMapping("/v1/send")
	public ResponseEntity<Email> sendEmail(@RequestBody @Valid EmailDTO emailDTO) throws InvalidValueException {
		Email email = EmailFactory.create(
		  emailDTO.to(),
		  emailDTO.subject(),
		  emailDTO.content(),
		  emailDTO.ownerRef()
		);

		var body = service.send(email);

		return ResponseEntity.status(HttpStatus.CREATED).body(body);
	}

	@GetMapping("/v1/{id}")
	public ResponseEntity<Email> findById(@PathVariable("id") String id) throws NotFoundException {
		var body =  service.findById(id);
		return ResponseEntity.status(HttpStatus.OK).body(body);
	}

	@GetMapping("/v1/list-registers")
	public ResponseEntity<Page<Email>> findAll(
	  @RequestParam(value = "page", defaultValue = "0") int page,
	  @RequestParam(value = "limit", defaultValue = "10") int limit
	) {
		Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "createdAt"));

		var body = service.findAll(pageable);

		return ResponseEntity.status(HttpStatus.OK).body(body);
	}
}
