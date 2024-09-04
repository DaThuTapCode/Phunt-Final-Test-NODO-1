package com.trongphu.finalintern1.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Trong Phu on 28/08/2024 22:19
 *
 * @author Trong Phu
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
//@EqualsAndHashCode
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String courseCode;

    private String title;

    private String description;

    private String img;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Integer status;

    @ManyToMany(
            mappedBy = "courses",
            fetch = FetchType.LAZY
    )
    private List<Student> students;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    private List<StudentCourse> studentCourses;
}
