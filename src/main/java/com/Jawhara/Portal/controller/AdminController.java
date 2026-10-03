package com.Jawhara.Portal.controller;

import com.Jawhara.Portal.service.PortalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private PortalService portalService;

    // Custom Login Page
    @GetMapping("/login")
    public String adminLogin() {
        return "admin/login";
    }

    // Admin Dashboard - Overview & Forms
    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {
        model.addAttribute("grades", portalService.getAllGrades());
        model.addAttribute("subjects", portalService.getAllSubjects());
        model.addAttribute("lessons", portalService.getAllLessons());
        return "admin/dashboard";
    }

    // Handle Adding Grade
    @PostMapping("/grades/add")
    public String addGrade(@RequestParam String name, @RequestParam Integer orderIndex) {
        portalService.saveGrade(name, orderIndex);
        return "redirect:/admin/dashboard";
    }

    // Handle Adding Subject
    @PostMapping("/subjects/add")
    public String addSubject(@RequestParam String name, @RequestParam Long gradeId) {
        portalService.saveSubject(name, gradeId);
        return "redirect:/admin/dashboard";
    }

    // Handle Adding Lesson
    @PostMapping("/lessons/add")
    public String addLesson(@RequestParam String title, @RequestParam Integer orderIndex, @RequestParam Long subjectId) {
        portalService.saveLesson(title, orderIndex, subjectId);
        return "redirect:/admin/dashboard";
    }

    // Handle Adding Material / File Upload
    @PostMapping("/materials/add")
    public String addMaterial(@RequestParam String title,
                              @RequestParam String fileType,
                              @RequestParam MultipartFile file,
                              @RequestParam Long lessonId) {
        portalService.saveMaterial(title, fileType, file, lessonId);
        return "redirect:/admin/dashboard";
    }
}