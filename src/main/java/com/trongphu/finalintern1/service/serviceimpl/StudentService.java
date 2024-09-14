package com.trongphu.finalintern1.service.serviceimpl;

import com.trongphu.finalintern1.dto.studentdto.request.StudentRequestDTO;
import com.trongphu.finalintern1.dto.studentdto.response.StudentResponseDTO;
import com.trongphu.finalintern1.dto.studentdto.response.StudentSearchResponseDTO;
import com.trongphu.finalintern1.entity.Course;
import com.trongphu.finalintern1.entity.Student;
import com.trongphu.finalintern1.entity.StudentCourse;
import com.trongphu.finalintern1.entity.StudentCourseId;
import com.trongphu.finalintern1.mapper.studentmapper.request.StudentRequestDTOMapper;
import com.trongphu.finalintern1.mapper.studentmapper.response.StudentResponseDTOMapper;
import com.trongphu.finalintern1.mapper.studentmapper.response.StudentSearchResponseDTOMapper;
import com.trongphu.finalintern1.repository.CourseRepository;
import com.trongphu.finalintern1.repository.StudentCourseRepository;
import com.trongphu.finalintern1.repository.StudentRepository;
import com.trongphu.finalintern1.service.serviceinterface.IStudentService;
import com.trongphu.finalintern1.util.PaginationObject;

import com.trongphu.finalintern1.util.exception.DuplicateCodeException;
import com.trongphu.finalintern1.util.exception.DuplicateEntityRecordException;
import com.trongphu.finalintern1.util.exception.ResourceNotFoundException;
import com.trongphu.finalintern1.util.exception.uploadsfile.FileUploadingException;
import com.trongphu.finalintern1.util.file.FileUploadUtil;
import org.springframework.data.domain.Page;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


/**
 * Created by Trong Phu on 28/08/2024 22:19
 *
 * @author Trong Phu
 */
@Service("StudentService")
public class StudentService implements IStudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    private final StudentCourseRepository studentCourseRepository;

    private final StudentRequestDTOMapper studentRequestDTOMapper;
    private final StudentResponseDTOMapper studentResponseDTOMapper;
    private final StudentSearchResponseDTOMapper studentSearchResponseDTOMapper;

    public StudentService(
            StudentRepository studentRepository,
            StudentCourseRepository studentCourseRepository,
            CourseRepository courseRepository,
            StudentRequestDTOMapper studentRequestDTOMapper,
            StudentResponseDTOMapper studentResponseDTOMapper, StudentSearchResponseDTOMapper studentSearchResponseDTOMapper) {
        this.studentRepository = studentRepository;
        this.studentCourseRepository = studentCourseRepository;
        this.courseRepository = courseRepository;
        this.studentRequestDTOMapper = studentRequestDTOMapper;
        this.studentResponseDTOMapper = studentResponseDTOMapper;
        this.studentSearchResponseDTOMapper = studentSearchResponseDTOMapper;
    }

    @Override
    @Transactional
    public void softDelete(Long id, int status) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("error.resource_not_found", Student.class.getSimpleName()));
        studentRepository.softDeleteStudent(student.getId(), status);
        studentCourseRepository.updateStatusByStudentOrCourse(student.getId(), null, 0);
    }

    @Override
    public Page<StudentResponseDTO> getPage(PaginationObject paginationObject) {
        Page<Student> studentPage = studentRepository.findAll(paginationObject.toPageable());
        if (studentPage.isEmpty()) {
            throw new ResourceNotFoundException("error.resource_not_found", "StudentPage");
        }
        return studentPage.map(studentResponseDTOMapper::toDTO);
    }

    @Override
    public StudentResponseDTO getById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("error.resource_not_found", Student.class.getSimpleName()));
        return studentResponseDTOMapper.toDTO(student);
    }

    @Override
    @Transactional
    public StudentResponseDTO createNew(StudentRequestDTO studentRequestDTO) {
        validateCourseCode(studentRequestDTO.getStudentCode());
        Student student = studentRequestDTOMapper.toEntity(studentRequestDTO);
        handleFileUpload(studentRequestDTO.getFileImg(), student);
        LocalDateTime now = LocalDateTime.now();
        student.setCreatedAt(now);
        student.setUpdatedAt(now);
        student.setStatus(1);
        Student studentSaved = studentRepository.save(student);
        if (studentRequestDTO.getCoursesId() != null) {
            if (!studentRequestDTO.getCoursesId().isEmpty()) {
                List<Course> courses = courseRepository.findAllById(studentRequestDTO.getCoursesId());
                if (courses.size() < studentRequestDTO.getCoursesId().size()) {
                    throw new ResourceNotFoundException("error.resource_not_found", Course.class.getSimpleName());
                }
                studentSaved.setCourses(courses);
                studentSaved = studentRepository.save(studentSaved);
            }
        }
        return studentResponseDTOMapper.toDTO(studentSaved);
    }

    @Override
    @Transactional
    public StudentResponseDTO update(Long id, StudentRequestDTO studentRequestDTO) {
        // Tìm sinh viên theo ID
        Student studentExisting = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("error.resource_not_found", Student.class.getSimpleName()));

        Student updatedStudent = studentRequestDTOMapper.toEntity(studentRequestDTO);

        // Giữ nguyên các trường không được cập nhật từ DTO
        updatedStudent.setId(studentExisting.getId());
        updatedStudent.setStudentCode(studentExisting.getStudentCode());
        updatedStudent.setImg(studentExisting.getImg());
        updatedStudent.setCreatedAt(studentExisting.getCreatedAt());

        // Cập nhật các khóa học
        if (studentRequestDTO.getCoursesId() != null) {
            if (!studentRequestDTO.getCoursesId().isEmpty()) {
                List<Course> courses = courseRepository.findAllById(studentRequestDTO.getCoursesId());
                if (courses.size() < studentRequestDTO.getCoursesId().size()) {
                    throw new ResourceNotFoundException("error.resource_not_found", Course.class.getSimpleName());
                }

                for (Course c : courses) {
                    //Kiểm tra các mối quan hệ đã có từ trước chưa
                    StudentCourseId studentCourseId = new StudentCourseId(updatedStudent.getId(), c.getId());

                    StudentCourse studentCourseExisting = studentCourseRepository.findById(studentCourseId).orElse(null);
                    //Nếu mqh đã có mà status = 0 thì update lại thành 1
                    if (studentCourseExisting != null) {
                        System.out.println("mqh đã tồn tại");
                        studentCourseRepository.updateStatusByStudentOrCourse(updatedStudent.getId(), c.getId(), 1);
                    } else {
                        //mqh chưa tồn tại tạo 1 mqh mới
                        System.out.println("mqh chưa tồn tại");
                        StudentCourse studentCourseNew = new StudentCourse();
                        studentCourseNew.setCourse(c);
                        studentCourseNew.setStudent(updatedStudent);
                        studentCourseNew.setStatus(1);
                        studentCourseRepository.save(studentCourseNew);
                    }
                }
                updatedStudent.setCourses(courses);
            }
        }

        // Xử lý hình ảnh nếu có file đính kèm
        handleFileUpload(studentRequestDTO.getFileImg(), updatedStudent, studentExisting.getImg());

        // Cập nhật thời gian sửa đổi
        updatedStudent.setUpdatedAt(LocalDateTime.now());

        // Lưu sinh viên đã cập nhật vào cơ sở dữ liệu
        Student savedStudent = studentRepository.save(updatedStudent);

        // Chuyển đổi entity thành DTO và trả về
        return studentResponseDTOMapper.toDTO(savedStudent);
    }

    @Override
    public Page<StudentSearchResponseDTO> searchPage(String name, String studentCode, String email, LocalDate createdFrom, LocalDate createdTo, PaginationObject paginationObject) {
        Page<StudentSearchResponseDTO> page = studentRepository.searchStudentByParam
                (name, studentCode, email, createdFrom == null ? null : createdFrom.atStartOfDay(), createdTo == null ? null : createdTo.atTime(LocalTime.MAX), paginationObject.toPageable())
                .map(studentSearchResponseDTOMapper::toDTO);
        if (page.isEmpty()) {
            throw new ResourceNotFoundException("error.resource_not_found", "Search Page Student");
        }
        return page;
    }

    @Override
    public List<StudentResponseDTO> getAll() {
        return studentRepository.findAll().stream().map(studentResponseDTOMapper::toDTO).toList();
    }

    /**
     * Method check Course Code đã tồn tại hay chưa
     *
     * @param studentCode được truyền vào từ đối tượng request so sánh với course code trong csdl
     */
    private void validateCourseCode(String studentCode) {
        if (studentRepository.findStudentByStudentCode(studentCode).isPresent()) {
            throw new DuplicateCodeException("error.duplicate_code_entity", Student.class.getSimpleName(), studentCode);
        }
    }

    /**
     * Method sử dụng cho thêm course mới
     *
     * @param file file truyền vào qua request
     */
    private void handleFileUpload(MultipartFile file, Student student) {
        handleFileUpload(file, student, null);
    }

    /**
     * Method sử dụng chung cho việc thêm, xóa, sửa ảnh
     *
     * @param file         file truyền vào qua request
     * @param student      đối tượng
     * @param oldImageName tên ảnh cũ cần xóa
     */
    private void handleFileUpload(MultipartFile file, Student student, String oldImageName) {
        if (FileUploadUtil.checkFileUpload(file, 10, FileUploadUtil.IMAGE)) {
            try {
                String imgName = FileUploadUtil.uploadFile(file);
                student.setImg(imgName);

                if (oldImageName != null) {
                    FileUploadUtil.deleteFileByName(oldImageName);
                }
            } catch (IOException e) {
                throw new FileUploadingException("error.file_uploading");
            }
        }
    }

}
