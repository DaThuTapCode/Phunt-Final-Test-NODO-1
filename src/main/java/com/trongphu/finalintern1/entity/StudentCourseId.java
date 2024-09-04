package com.trongphu.finalintern1.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

/**
 * Created by Trong Phu on 31/08/2024 19:15
 *
 * @author Trong Phu
 */
@Embeddable
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
//@EqualsAndHashCode
public class StudentCourseId implements Serializable {
    private Long studentId;
    private Long courseId;
}
