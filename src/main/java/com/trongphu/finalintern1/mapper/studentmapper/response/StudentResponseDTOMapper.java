package com.trongphu.finalintern1.mapper.studentmapper.response;

import com.trongphu.finalintern1.dto.studentdto.response.StudentResponseDTO;
import com.trongphu.finalintern1.entity.Student;
import com.trongphu.finalintern1.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
 * Created by Trong Phu on 28/08/2024 22:29
 * interface mapper giữa {@link Student} và {@link StudentResponseDTO}
 * @author Trong Phu
 */
@Mapper(componentModel = "spring")
public interface StudentResponseDTOMapper extends BaseMapper<Student, StudentResponseDTO> {

}


