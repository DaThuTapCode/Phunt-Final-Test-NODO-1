package com.trongphu.finalintern1.mapper.studentcoursemapper.response;

import com.trongphu.finalintern1.dto.studentcoursedto.response.StudentCourseResponseDTO;
import com.trongphu.finalintern1.entity.StudentCourse;
import com.trongphu.finalintern1.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * Created by Trong Phu on 31/08/2024 22:09
 * interface mapper giữa {@link StudentCourse} và {@link StudentCourseResponseDTO}
 * @author Trong Phu
 */
@Mapper(componentModel = "spring")
public interface StudentCourseResponseDTOMapper extends BaseMapper<StudentCourse, StudentCourseResponseDTO> {
}
