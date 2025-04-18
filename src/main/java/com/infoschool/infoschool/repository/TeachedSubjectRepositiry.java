package com.infoschool.infoschool.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infoschool.infoschool.model.TeachedSubject;

public interface TeachedSubjectRepositiry extends JpaRepository<TeachedSubject, Long> {
    
    List<TeachedSubject> findByTeacherId(Long teacherId);

    List<TeachedSubject> findBySubjectId(Long subjectId);
}
