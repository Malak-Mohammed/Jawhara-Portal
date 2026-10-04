package com.Jawhara.Portal.service;

import com.Jawhara.Portal.model.Grade;
import com.Jawhara.Portal.model.Lesson;
import com.Jawhara.Portal.model.Subject;
import com.Jawhara.Portal.model.Material;
import com.Jawhara.Portal.repository.GradeRepository;
import com.Jawhara.Portal.repository.LessonRepository;
import com.Jawhara.Portal.repository.SubjectRepository;
import com.Jawhara.Portal.repository.MaterialRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class PortalService {

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private MaterialRepository materialRepository;

    private final String uploadDir = "uploads";

    // --- Student / Read Methods ---

    public List<Grade> getAllGrades() {
        return gradeRepository.findAllByOrderByOrderIndexAsc();
    }

    public List<Subject> getSubjectsByGrade(Long gradeId) {
        return subjectRepository.findByGradeId(gradeId);
    }

    public List<Lesson> getLessonsBySubject(Long subjectId) {
        return lessonRepository.findBySubjectIdOrderByOrderIndexAsc(subjectId);
    }

    public List<Material> getMaterialsByLesson(Long lessonId) {
        return materialRepository.findByLessonId(lessonId);
    }

    public Grade getGradeById(Long id) {
        return gradeRepository.findById(id).orElse(null);
    }

    public Subject getSubjectById(Long id) {
        return subjectRepository.findById(id).orElse(null);
    }

    public Lesson getLessonById(Long id) {
        return lessonRepository.findById(id).orElse(null);
    }

    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    public List<Lesson> getAllLessons() {
        return lessonRepository.findAll();
    }

    // --- Admin / Write Methods ---

    public void saveGrade(String name, Integer orderIndex) {
        Grade grade = new Grade();
        grade.setName(name);
        grade.setOrderIndex(orderIndex);
        gradeRepository.save(grade);
    }

    public void saveSubject(String name, Long gradeId) {
        Grade grade = getGradeById(gradeId);
        if (grade != null) {
            Subject subject = new Subject();
            subject.setName(name);
            subject.setGrade(grade);
            subjectRepository.save(subject);
        }
    }

    public void saveLesson(String title, Integer orderIndex, Long subjectId) {
        Subject subject = getSubjectById(subjectId);
        if (subject != null) {
            Lesson lesson = new Lesson();
            lesson.setTitle(title);
            lesson.setOrderIndex(orderIndex);
            lesson.setSubject(subject);
            lessonRepository.save(lesson);
        }
    }
    // Grades
    public void updateGrade(Long id, String name, Integer orderIndex) {
        Grade grade = gradeRepository.findById(id).orElseThrow();
        grade.setName(name);
        grade.setOrderIndex(orderIndex);
        gradeRepository.save(grade);
    }
    public void deleteGrade(Long id) { gradeRepository.deleteById(id); }

    // Subjects
    public void updateSubject(Long id, String name, Long gradeId) {
        Subject subject = subjectRepository.findById(id).orElseThrow();
        Grade grade = gradeRepository.findById(gradeId).orElseThrow();
        subject.setName(name);
        subject.setGrade(grade);
        subjectRepository.save(subject);
    }
    public void deleteSubject(Long id) { subjectRepository.deleteById(id); }

    // Lessons
    public void updateLesson(Long id, String title, Integer orderIndex) {
        Lesson lesson = lessonRepository.findById(id).orElseThrow();
        lesson.setTitle(title);
        lesson.setOrderIndex(orderIndex);
        lessonRepository.save(lesson);
    }
    public void deleteLesson(Long id) { lessonRepository.deleteById(id); }

    // Materials
    public void updateMaterial(Long id, String title, String fileType) {
        Material material = materialRepository.findById(id).orElseThrow();
        material.setTitle(title);
        material.setFileType(fileType);
        materialRepository.save(material);
    }
    public void deleteMaterial(Long id) { materialRepository.deleteById(id); }
    public void saveMaterial(String title, String fileType, MultipartFile file, Long lessonId) {
        Lesson lesson = getLessonById(lessonId);
        if (lesson != null && file != null && !file.isEmpty()) {
            try {
                Path uploadPath = Paths.get(uploadDir);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                String originalFilename = file.getOriginalFilename();
                String uniqueFilename = UUID.randomUUID().toString() + "_" + originalFilename;
                Path filePath = uploadPath.resolve(uniqueFilename);

                Files.copy(file.getInputStream(), filePath);

                Material material = new Material();
                material.setTitle(title);
                material.setFileType(fileType);
                material.setFilePath("/files/" + uniqueFilename);
                material.setLesson(lesson);

                materialRepository.save(material);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}