package com.trongphu.finalintern1.dto.studentcoursedto.response;

import com.trongphu.finalintern1.dto.coursedto.response.CourseResponseShortDTO;
import com.trongphu.finalintern1.dto.studentdto.response.StudentResponseShortDTO;
import com.trongphu.finalintern1.entity.StudentCourseId;
import lombok.*;

/**
 * Created by Trong Phu on 31/08/2024 22:05
 *
 * @author Trong Phu
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentCourseResponseDTO {
//    private StudentCourseId id = new StudentCourseId();

//    private StudentResponseShortDTO student;

    private CourseResponseShortDTO course;

    private Integer status;
}
