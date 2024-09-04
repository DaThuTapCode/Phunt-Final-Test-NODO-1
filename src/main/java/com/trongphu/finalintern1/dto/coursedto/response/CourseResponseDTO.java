package com.trongphu.finalintern1.dto.coursedto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.trongphu.finalintern1.dto.studentdto.response.StudentResponseShortDTO;
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
public class CourseResponseDTO {

    private Long id;

    private String courseCode;

    private String title;

    private String description;

    private String img;

    @JsonFormat(pattern = "hh:mm:ss dd-MM-yyyy")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "hh:mm:ss dd-MM-yyyy")
    private LocalDateTime updatedAt;

    private Integer status;

    private List<StudentResponseShortDTO> students;
}
