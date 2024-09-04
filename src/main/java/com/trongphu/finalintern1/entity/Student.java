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
@AllArgsConstructor
@NoArgsConstructor
@Entity
//@EqualsAndHashCode
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String studentCode;

    private String name;

    private String email;

    private String img;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Integer status;

    @ManyToMany(
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "Student_Course",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> courses;

}
