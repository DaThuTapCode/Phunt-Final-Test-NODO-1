package com.trongphu.finalintern1.dto.coursedto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Created by Trong Phu on 31/08/2024 19:47
 *
 * @author Trong Phu
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseResponseShortDTO {
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
}
