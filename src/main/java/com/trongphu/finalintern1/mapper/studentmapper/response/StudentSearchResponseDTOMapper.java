package com.trongphu.finalintern1.mapper.studentmapper.response;


import com.trongphu.finalintern1.dto.studentdto.response.StudentSearchResponseDTO;
import com.trongphu.finalintern1.entity.Course;
import com.trongphu.finalintern1.entity.Student;
import com.trongphu.finalintern1.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Trong Phu on 04/09/2024 14:59
 *
 * @author Trong Phu
 */
@Mapper(componentModel = "spring")
public interface StudentSearchResponseDTOMapper extends BaseMapper<Student, StudentSearchResponseDTO>{
    @Override
    @Mapping(target = "course", source = "courses", qualifiedByName = "mapCourseCodes")
    StudentSearchResponseDTO toDTO(Student student);

    @Override
    List<StudentSearchResponseDTO> listToDTO(List<Student> listE);

    @Override
    Student toEntity(StudentSearchResponseDTO studentSearchResponseDTO);

    @Override
    List<Student> toListEntity(List<StudentSearchResponseDTO> d);

    @Named("mapCourseCodes")
    default String mapCourseCodes(List<Course> courses) {
        return courses.stream()
                .map(Course::getCourseCode)
                .collect(Collectors.joining(", "));
    }
}
