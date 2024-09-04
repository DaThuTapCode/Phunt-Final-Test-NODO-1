package com.trongphu.finalintern1.mapper.studentmapper.request;

import com.trongphu.finalintern1.dto.studentdto.request.StudentRequestDTO;
import com.trongphu.finalintern1.entity.Student;
import com.trongphu.finalintern1.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * Created by Trong Phu on 28/08/2024 22:29
 * interface mapper giữa {@link Student} và {@link StudentRequestDTO}
 * @author Trong Phu
 */
@Mapper(componentModel = "spring")
public interface StudentRequestDTOMapper extends BaseMapper<Student, StudentRequestDTO> {

}
