package com.trongphu.finalintern1.mapper.coursemapper.request;

import com.trongphu.finalintern1.dto.coursedto.request.CourseRequestDTO;
import com.trongphu.finalintern1.entity.Course;
import com.trongphu.finalintern1.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * Created by Trong Phu on 28/08/2024 22:28 <br>
 * interface mapper giữa {@link Course} và {@link CourseRequestDTO}
 * @author Trong Phu
 */
@Mapper(componentModel = "spring")
public interface CourseRequestDTOMapper extends BaseMapper<Course, CourseRequestDTO> {

}
