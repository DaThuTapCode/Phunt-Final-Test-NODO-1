package com.trongphu.finalintern1.service.serviceinterface;

import com.trongphu.finalintern1.dto.coursedto.request.CourseRequestDTO;
import com.trongphu.finalintern1.dto.coursedto.response.CourseResponseDTO;
import com.trongphu.finalintern1.dto.coursedto.response.CourseResponseShortDTO;
import com.trongphu.finalintern1.entity.Course;
import com.trongphu.finalintern1.util.PaginationObject;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

/**
 * Created by Trong Phu on 28/08/2024 22:18
 *
 * @author Trong Phu
 */

public interface ICourseService extends IBaseService<Course, Long, CourseRequestDTO, CourseResponseDTO> {
    Page<CourseResponseShortDTO> findByParam(String title, String courseCode, LocalDate createdFrom, LocalDate createdTo, PaginationObject paginationObject);

}
