package com.trongphu.finalintern1.service.serviceinterface;

import com.trongphu.finalintern1.dto.studentcoursedto.response.StudentCourseResponseDTO;


import java.util.List;

/**
 * Created by Trong Phu on 31/08/2024 21:51
 *
 * @author Trong Phu
 */
public interface IStudentCourseService {
     List<StudentCourseResponseDTO> findEnrolledStudents();

     List<StudentCourseResponseDTO> findDroppedStudents();

     List<StudentCourseResponseDTO> findByStudentId(Long studentId);

     List<StudentCourseResponseDTO> findByCourseId(Long courseId);

     /**
      * Method update trạng thái trung gian
      * @param studentId +
      * @param courseId +
      * */
     void deleteByStudentOrCourse(Long studentId, Long courseId);

     StudentCourseResponseDTO linkStudentVsCourse(Long studentId, Long courseId);

     void cancelStudentVsCourse(Long studentId, Long courseId);

}
