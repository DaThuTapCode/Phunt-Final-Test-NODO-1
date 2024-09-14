package com.trongphu.finalintern1.service.serviceinterface;

import com.trongphu.finalintern1.dto.studentdto.request.StudentRequestDTO;
import com.trongphu.finalintern1.dto.studentdto.response.StudentResponseDTO;

import com.trongphu.finalintern1.dto.studentdto.response.StudentSearchResponseDTO;
import com.trongphu.finalintern1.entity.Student;
import com.trongphu.finalintern1.util.PaginationObject;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by Trong Phu on 28/08/2024 22:18
 *
 * @author Trong Phu
 */
public interface IStudentService extends IBaseService<Student, Long, StudentRequestDTO, StudentResponseDTO> {
    Page<StudentSearchResponseDTO> searchPage(String name, String studentCode, String email, LocalDate createdFrom, LocalDate createdTo, PaginationObject paginationObject);
    List<StudentResponseDTO> getAll();
}
