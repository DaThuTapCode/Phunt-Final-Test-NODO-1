package com.trongphu.finalintern1.mapper.coursemapper.response;

import com.trongphu.finalintern1.dto.coursedto.response.CourseResponseShortDTO;
import com.trongphu.finalintern1.entity.Course;
import com.trongphu.finalintern1.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * Created by Trong Phu on 31/08/2024 22:41
 * interface mapper giữa {@link Course} và {@link CourseResponseShortDTO}
 * @author Trong Phu
 */
@Mapper(componentModel = "spring")
public interface CourseResponseShortDTOMapper extends BaseMapper<Course, CourseResponseShortDTO> {
}
