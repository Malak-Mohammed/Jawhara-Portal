package com.Jawhara.Portal.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "materials")
@Data
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;      // e.g., "Lecture Slides"
    private String fileType;   // e.g., "pdf", "pptx", "video", "image"
    private String filePath;   // Stored file path or URL

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;
}