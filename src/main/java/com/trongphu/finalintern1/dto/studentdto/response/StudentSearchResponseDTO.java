package com.trongphu.finalintern1.dto.studentdto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Created by Trong Phu on 04/09/2024 14:58
 *
 * @author Trong Phu
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentSearchResponseDTO {
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

    private String course;
}
