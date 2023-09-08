package com.ms.email.domain.entities;

import com.ms.email.domain.validation.EmailValidator;
import com.ms.email.domain.errors.IncorrectValueException;
import com.ms.email.domain.types.Status;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "email_log")
public class Email implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	@Column(name = "owner_ref")
	private String ownerRef;

	@Column(name = "recipient")
	private String from;

	@Column(name = "sender")
	private String to;

	private String subject;
	private String content;

	@Enumerated(EnumType.STRING)
	private Status status;

	@Column(name = "created_at")
	private long createdAt;

	public Email() {}

	public Email(
	  String id,
	  String ownerRef,
	  String from,
	  String to,
	  String subject,
	  String content,
	  Status status,
	  long createdAt
	) throws IncorrectValueException {
		setId(id);
		setOwnerRef(ownerRef);
		setFrom(from);
		setTo(to);
		setSubject(subject);
		setContent(content);
		setStatus(status);
		setCreatedAt(createdAt);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		EmailValidator.validate(id)
		  .isEmpty("id")
		  .isRef("id");
		this.id = id;
	}

	public String getOwnerRef() {
		return ownerRef;
	}

	public void setOwnerRef(String ownerRef) {
		EmailValidator.validate(ownerRef)
		  .isEmpty("ownerRef")
		  .isRef("ownerRef");
		this.ownerRef = ownerRef;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		EmailValidator.validate(from)
		  .isEmail();
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		EmailValidator.validate(to)
		  .isEmail()
		  .isEmailToYourself(this.from);
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		EmailValidator.validate(subject)
		  .isEmpty("subject");
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		EmailValidator.validate(content)
		  .isEmpty("content");
		this.content = content;
	}

	public long getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(long createdAt) {
		EmailValidator.validate(String.valueOf(createdAt))
		  .isFuture("createdAt");
		this.createdAt = createdAt;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Email)) return false;
		Email email = (Email) o;
		return Objects.equals(id, email.id) && Objects.equals(ownerRef, email.ownerRef);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, ownerRef);
	}
}
