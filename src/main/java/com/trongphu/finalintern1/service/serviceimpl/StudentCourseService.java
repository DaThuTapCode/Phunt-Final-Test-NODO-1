package com.trongphu.finalintern1.service.serviceimpl;

import com.trongphu.finalintern1.dto.studentcoursedto.response.StudentCourseResponseDTO;
import com.trongphu.finalintern1.entity.Course;
import com.trongphu.finalintern1.entity.Student;
import com.trongphu.finalintern1.entity.StudentCourse;
import com.trongphu.finalintern1.entity.StudentCourseId;
import com.trongphu.finalintern1.mapper.studentcoursemapper.response.StudentCourseResponseDTOMapper;
import com.trongphu.finalintern1.repository.CourseRepository;
import com.trongphu.finalintern1.repository.StudentCourseRepository;
import com.trongphu.finalintern1.repository.StudentRepository;
import com.trongphu.finalintern1.service.serviceinterface.IStudentCourseService;
import com.trongphu.finalintern1.util.exception.DuplicateEntityRecordException;
import com.trongphu.finalintern1.util.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

/**
 * Created by Trong Phu on 31/08/2024 21:55
 *
 * @author Trong Phu
 */
@Service("StudentCourseService")
public class StudentCourseService implements IStudentCourseService {

    private final StudentCourseRepository studentCourseRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    private final StudentCourseResponseDTOMapper studentCourseResponseDTOMapper;

    public StudentCourseService(
            StudentCourseRepository studentCourseRepository,
            StudentRepository studentRepository, CourseRepository courseRepository,
            StudentCourseResponseDTOMapper studentCourseResponseDTOMapper
    ) {
        this.studentCourseRepository = studentCourseRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.studentCourseResponseDTOMapper = studentCourseResponseDTOMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentCourseResponseDTO> findEnrolledStudents() {
//        return studentCourseResponseDTOMapper.listToDTO(studentCourseRepository.findByStatus(1));
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentCourseResponseDTO> findDroppedStudents() {
//        return studentCourseResponseDTOMapper.listToDTO(studentCourseRepository.findByStatus(0));
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentCourseResponseDTO> findByStudentId(Long studentId) {
        List<StudentCourseResponseDTO> responseDTOList = studentCourseResponseDTOMapper.listToDTO(studentCourseRepository.findByStudentId(studentId));
        if (responseDTOList.isEmpty()) {
            throw new ResourceNotFoundException("error.resource_not_found", StudentCourse.class.getSimpleName());
        }
        return responseDTOList;
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentCourseResponseDTO> findByCourseId(Long courseId) {
        List<StudentCourseResponseDTO> responseDTOList = studentCourseResponseDTOMapper.listToDTO(studentCourseRepository.findByCourseId(courseId));
        if (responseDTOList.isEmpty()) {
            throw new ResourceNotFoundException("error.resource_not_found", StudentCourse.class.getSimpleName());
        }
        return responseDTOList;
    }

    /**
     * Method update trạng thái trung gian
     *
     * @param studentId +
     * @param courseId  +
     */
    @Override
    @Transactional
    public void deleteByStudentOrCourse(Long studentId, Long courseId) {
        studentCourseRepository.updateStatusByStudentOrCourse(studentId, courseId);
    }

    @Override
    @Transactional
    public StudentCourseResponseDTO linkStudentVsCourse(Long studentId, Long courseId) {

        //Kiểm tra các mối quan hệ đã có từ trước chưa
        Student studentExisting = studentRepository.findById(studentId).orElseThrow(() -> new ResourceNotFoundException("error.resource_not_found", Student.class.getSimpleName()));
        Course courseExisting = courseRepository.findById(courseId).orElseThrow(() -> new ResourceNotFoundException("error.resource_not_found", Course.class.getSimpleName()));
        StudentCourseId studentCourseId = new StudentCourseId(studentExisting.getId(), courseExisting.getId());

        StudentCourse studentCourseExisting = studentCourseRepository.findById(studentCourseId).orElse(null);
        //Nếu mqh đã có mà status = 0 thì update lại thành 1
        if (studentCourseExisting != null) {

            throw new DuplicateEntityRecordException("error.DuplicateEntityRecord", StudentCourse.class.getSimpleName());
        }

        //mqh chưa tồn tại tạo 1 mqh mới
        StudentCourse studentCourseNew = new StudentCourse();
        studentCourseNew.setCourse(courseExisting);
        studentCourseNew.setStudent(studentExisting);

        return studentCourseResponseDTOMapper.toDTO(studentCourseRepository.save(studentCourseNew));
    }

    @Override
    @Transactional
    public void cancelStudentVsCourse(Long studentId, Long courseId) {
        StudentCourseId studentCourseId = new StudentCourseId(studentId, courseId);
        StudentCourse studentCourse = studentCourseRepository.findById(studentCourseId).orElseThrow(() -> new ResourceNotFoundException("error.resource_not_found", StudentCourse.class.getSimpleName()));
        studentCourseRepository.delete(studentCourse);
    }


}
