package com.infoschool.infoschool.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infoschool.infoschool.model.Certification;

public interface CertificationRepository extends JpaRepository<Certification, Long> {

    Optional<Certification> findByName(String name);

    Optional<Certification> findByDescription(String description);

    Optional<Certification> findById(Long id);

    boolean existsByName(String name);
}
