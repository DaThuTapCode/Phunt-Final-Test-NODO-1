package com.trongphu.finalintern1.dto.coursedto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Trong Phu on 28/08/2024 22:24
 *
 * @author Trong Phu
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseRequestDTO {

    private Long id;

    @NotBlank(message = "NotBlank")
    @Length(min = 10, max = 10, message = "Length")
    private String courseCode;

    @NotBlank(message = "NotBlank")
    @Length(min = 3, max = 255, message = "Length")
    private String title;

    @NotBlank(message = "NotBlank")
    @Length(min = 3, max = 255, message = "Length")
    private String description;

    private MultipartFile fileImg;

    @NotNull(message = "NotNull")
    @Range(min = 0, max = 1, message = "Range")
    private int status;

}
