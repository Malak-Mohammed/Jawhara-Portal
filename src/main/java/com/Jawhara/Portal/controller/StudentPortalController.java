package com.Jawhara.Portal.controller;

import com.Jawhara.Portal.model.Grade;
import com.Jawhara.Portal.model.Lesson;
import com.Jawhara.Portal.model.Subject;
import com.Jawhara.Portal.model.Material;
import com.Jawhara.Portal.service.PortalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class StudentPortalController {

    @Autowired
    private PortalService portalService;

    // Step 1: Home page - Choose a Grade
    @GetMapping("/")
    public String index(Model model) {
        List<Grade> grades = portalService.getAllGrades();
        model.addAttribute("grades", grades);
        return "index"; // maps to src/main/resources/templates/index.html
    }

    // Step 2: Choose a Subject inside a Grade
    @GetMapping("/grades/{gradeId}")
    public String viewSubjects(@PathVariable Long gradeId, Model model) {
        Grade grade = portalService.getGradeById(gradeId);
        List<Subject> subjects = portalService.getSubjectsByGrade(gradeId);

        model.addAttribute("grade", grade);
        model.addAttribute("subjects", subjects);
        return "subjects"; // maps to templates/subjects.html
    }

    // Step 3: Choose a Lesson inside a Subject
    @GetMapping("/subjects/{subjectId}")
    public String viewLessons(@PathVariable Long subjectId, Model model) {
        Subject subject = portalService.getSubjectById(subjectId);
        List<Lesson> lessons = portalService.getLessonsBySubject(subjectId);

        model.addAttribute("subject", subject);
        model.addAttribute("lessons", lessons);
        return "lessons"; // maps to templates/lessons.html
    }

    // Step 4: View Materials inside a Lesson (PDFs, PPTXs, Videos, Pictures)
    @GetMapping("/lessons/{lessonId}/materials")
    public String viewMaterials(@PathVariable Long lessonId, Model model) {
        Lesson lesson = portalService.getLessonById(lessonId);
        List<Material> materials = portalService.getMaterialsByLesson(lessonId);

        model.addAttribute("lesson", lesson);
        model.addAttribute("materials", materials);
        return "materials"; // maps to templates/materials.html
    }
}