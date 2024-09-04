package com.trongphu.finalintern1.dto.studentdto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.trongphu.finalintern1.dto.coursedto.response.CourseResponseShortDTO;
import com.trongphu.finalintern1.dto.studentcoursedto.response.StudentCourseResponseDTO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Trong Phu on 28/08/2024 22:25
 *
 * @author Trong Phu
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentResponseDTO {

    private Long id;

    private String studentCode;

    private String name;

    private String email;

    private String img;

    @JsonFormat(pattern = "hh:mm:ss dd-MM-yyyy")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "hh:mm:ss dd-MM-yyyy")
    private LocalDateTime updatedAt;

    private Integer status;

    private List<CourseResponseShortDTO> courses;
}
