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

    // --- Grade Endpoints ---
    @PostMapping("/grades/add")
    public String addGrade(@RequestParam String name, @RequestParam Integer orderIndex) {
        portalService.saveGrade(name, orderIndex);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/grades/edit/{id}")
    public String updateGrade(@PathVariable Long id, @RequestParam String name, @RequestParam Integer orderIndex) {
        portalService.updateGrade(id, name, orderIndex);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/grades/delete/{id}")
    public String deleteGrade(@PathVariable Long id) {
        portalService.deleteGrade(id);
        return "redirect:/admin/dashboard";
    }

    // --- Subject Endpoints ---
    @PostMapping("/subjects/add")
    public String addSubject(@RequestParam String name, @RequestParam Long gradeId) {
        portalService.saveSubject(name, gradeId);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/subjects/edit/{id}")
    public String updateSubject(@PathVariable Long id, @RequestParam String name, @RequestParam Long gradeId) {
        portalService.updateSubject(id, name, gradeId);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/subjects/delete/{id}")
    public String deleteSubject(@PathVariable Long id) {
        portalService.deleteSubject(id);
        return "redirect:/admin/dashboard";
    }

    // --- Lesson Endpoints ---
    @PostMapping("/lessons/add")
    public String addLesson(@RequestParam String title, @RequestParam Integer orderIndex, @RequestParam Long subjectId) {
        portalService.saveLesson(title, orderIndex, subjectId);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/lessons/edit/{id}")
    public String updateLesson(@PathVariable Long id, @RequestParam String title, @RequestParam Integer orderIndex) {
        portalService.updateLesson(id, title, orderIndex);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/lessons/delete/{id}")
    public String deleteLesson(@PathVariable Long id) {
        portalService.deleteLesson(id);
        return "redirect:/admin/dashboard";
    }

    // --- Material Endpoints ---
    @PostMapping("/materials/add")
    public String addMaterial(@RequestParam String title,
                              @RequestParam String fileType,
                              @RequestParam MultipartFile file,
                              @RequestParam Long lessonId) {
        portalService.saveMaterial(title, fileType, file, lessonId);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/materials/edit/{id}")
    public String updateMaterial(@PathVariable Long id, @RequestParam String title, @RequestParam String fileType) {
        portalService.updateMaterial(id, title, fileType);
        return "redirect:/admin/dashboard";
    }

    @GetMapping("/materials/delete/{id}")
    public String deleteMaterial(@PathVariable Long id) {
        portalService.deleteMaterial(id);
        return "redirect:/admin/dashboard";
    }
}