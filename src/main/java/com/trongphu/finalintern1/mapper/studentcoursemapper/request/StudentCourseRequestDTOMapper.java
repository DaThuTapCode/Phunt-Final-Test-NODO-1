package com.trongphu.finalintern1.mapper.studentcoursemapper.request;

import com.trongphu.finalintern1.dto.studentcoursedto.request.StudentCourseRequestDTO;
import com.trongphu.finalintern1.entity.StudentCourse;
import com.trongphu.finalintern1.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * Created by Trong Phu on 31/08/2024 22:09 <br>
 * interface mapper giữa {@link StudentCourse} và {@link StudentCourseRequestDTO}
 * @author Trong Phu
 */
@Mapper(componentModel = "spring")
public interface StudentCourseRequestDTOMapper extends BaseMapper<StudentCourse, StudentCourseRequestDTO> {
}
