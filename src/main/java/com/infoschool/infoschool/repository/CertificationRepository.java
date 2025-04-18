package com.infoschool.infoschool.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infoschool.infoschool.model.Certification;

public interface CertificationRepository extends JpaRepository<Certification, Long> {

    Optional<Certification> findByName(String name);

    boolean existsByName(String name);
}
