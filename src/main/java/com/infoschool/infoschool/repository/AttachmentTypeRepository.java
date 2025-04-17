package com.infoschool.infoschool.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infoschool.infoschool.model.AttachmentType;

public interface AttachmentTypeRepository extends JpaRepository<AttachmentType, Long> {

    Optional<AttachmentType> findByName(String name);

    List<AttachmentType> findByExtension(String extension);

    List<AttachmentType> findByMimeType(String mimeType);

    Boolean existsByName(String name);
    
}
