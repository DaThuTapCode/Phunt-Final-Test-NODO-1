package com.trongphu.finalintern1.dto.studentdto.request;

import com.trongphu.finalintern1.dto.coursedto.request.CourseRequestDTO;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;

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
public class StudentRequestDTO {

    private Long id;

    @NotBlank(message = "NotBlank")
    private String name;

    @Length(min = 10, max = 10, message = "Length")
    private String studentCode;

    @NotBlank(message = "NotBlank")
    @Email(message = "Email")
    private String email;

    private MultipartFile fileImg;

    @NotNull(message = "NotNull")
    @Range(min = 0, max = 1, message = "Range")
    private Integer status;

    private List<Long> coursesId;
}
