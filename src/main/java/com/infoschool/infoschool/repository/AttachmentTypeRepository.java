package com.infoschool.infoschool.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infoschool.infoschool.model.AttachmentType;

public interface AttachmentTypeRepository extends JpaRepository<AttachmentType, Long> {

    Optional<AttachmentType> findByName(String name);

    Optional<AttachmentType> findByExtension(String extension);

    Optional<AttachmentType> findByMimeType(String mimeType);
    
}
