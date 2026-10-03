package com.Jawhara.Portal.repository;

import com.Jawhara.Portal.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    List<Subject> findByGradeId(Long gradeId);
}