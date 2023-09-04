package com.ms.client.infra.repositories;

import com.ms.client.domain.entities.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, String> {

	Optional<Manager> findByEmail(String email);
}
