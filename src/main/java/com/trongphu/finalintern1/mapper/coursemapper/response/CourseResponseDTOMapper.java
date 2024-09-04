package com.trongphu.finalintern1.mapper.coursemapper.response;

import com.trongphu.finalintern1.dto.coursedto.response.CourseResponseDTO;
import com.trongphu.finalintern1.entity.Course;
import com.trongphu.finalintern1.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * Created by Trong Phu on 28/08/2024 22:28
 * interface mapper giữa {@link Course} và {@link CourseResponseDTO}
 * @author Trong Phu
 */
@Mapper(componentModel = "spring")
public interface CourseResponseDTOMapper extends BaseMapper<Course, CourseResponseDTO> {
}
