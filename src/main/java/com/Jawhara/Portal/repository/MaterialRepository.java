package com.Jawhara.Portal.repository;

import com.Jawhara.Portal.model.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {
    List<Material> findByLessonId(Long lessonId);
}