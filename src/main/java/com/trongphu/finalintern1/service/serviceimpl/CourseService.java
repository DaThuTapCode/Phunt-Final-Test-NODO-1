package com.trongphu.finalintern1.service.serviceimpl;

import com.trongphu.finalintern1.dto.coursedto.request.CourseRequestDTO;
import com.trongphu.finalintern1.dto.coursedto.response.CourseResponseDTO;
import com.trongphu.finalintern1.dto.coursedto.response.CourseResponseShortDTO;
import com.trongphu.finalintern1.entity.Course;
import com.trongphu.finalintern1.mapper.coursemapper.request.CourseRequestDTOMapper;
import com.trongphu.finalintern1.mapper.coursemapper.response.CourseResponseDTOMapper;
import com.trongphu.finalintern1.mapper.coursemapper.response.CourseResponseShortDTOMapper;
import com.trongphu.finalintern1.repository.CourseRepository;
import com.trongphu.finalintern1.repository.StudentCourseRepository;
import com.trongphu.finalintern1.service.serviceinterface.ICourseService;
import com.trongphu.finalintern1.util.PaginationObject;
import com.trongphu.finalintern1.util.exception.DuplicateCodeException;
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

/**
 * Created by Trong Phu on 28/08/2024 22:19
 *
 * @author Trong Phu
 */
@Service("CourseService")
public class CourseService implements ICourseService {
    private final CourseRepository courseRepository;
    private final StudentCourseRepository studentCourseRepository;

    private final CourseRequestDTOMapper courseRequestDTOMapper;
    private final CourseResponseDTOMapper courseResponseDTOMapper;
    private final CourseResponseShortDTOMapper courseResponseShortDTOMapper;

    public CourseService(
            CourseRepository courseRepository,
            StudentCourseRepository studentCourseRepository,
            CourseRequestDTOMapper courseRequestDTOMapper,
            CourseResponseDTOMapper courseResponseDTOMapper,
            CourseResponseShortDTOMapper courseResponseShortDTOMapper
    ) {
        this.courseRepository = courseRepository;
        this.studentCourseRepository = studentCourseRepository;
        this.courseRequestDTOMapper = courseRequestDTOMapper;
        this.courseResponseDTOMapper = courseResponseDTOMapper;
        this.courseResponseShortDTOMapper = courseResponseShortDTOMapper;
    }

    @Override
    public Page<CourseResponseDTO> getPage(PaginationObject paginationObject) {
        Page<CourseResponseDTO> responseDTOPage = courseRepository.findAll(paginationObject.toPageable()).map(courseResponseDTOMapper::toDTO);
        if (responseDTOPage.isEmpty()) {
            throw new ResourceNotFoundException("error.resource_not_found", "PageCourse");
        }
        return responseDTOPage;
    }

    @Override
    public CourseResponseDTO getById(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("error.resource_not_found", Course.class.getSimpleName()));
        return courseResponseDTOMapper.toDTO(course);
    }

    @Override
    @Transactional
    public void softDelete(Long id, int status) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("error.resource_not_found", Course.class.getSimpleName()));
        courseRepository.softDelete(id, status);
        studentCourseRepository.updateStatusByStudentOrCourse(null, course.getId(), 0);
    }

    @Override
    @Transactional
    public CourseResponseDTO createNew(CourseRequestDTO requestDTO) {
        Course courseNew = courseRequestDTOMapper.toEntity(requestDTO);
        validateCourseCode(courseNew.getCourseCode());
        handleFileUpload(requestDTO.getFileImg(), courseNew);
        LocalDateTime now = LocalDateTime.now();
        courseNew.setStatus(1);
        courseNew.setCreatedAt(now);
        courseNew.setUpdatedAt(now);
        Course courseSaved = courseRepository.save(courseNew);
        return courseResponseDTOMapper.toDTO(courseSaved);
    }

    @Override
    @Transactional
    public CourseResponseDTO update(Long id, CourseRequestDTO requestDTO) {
        Course courseUpdate = courseRequestDTOMapper.toEntity(requestDTO);
        Course courseExisting = courseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("error.resource_not_found", Course.class.getSimpleName()));
        //Giữ lại các thuộc tính không cần thay đổi
        courseUpdate.setImg(courseExisting.getImg());
        courseUpdate.setCourseCode(courseExisting.getCourseCode());
        courseUpdate.setCreatedAt(courseExisting.getCreatedAt());
        courseUpdate.setUpdatedAt(LocalDateTime.now());
        courseUpdate.setStatus(1);
        handleFileUpload(requestDTO.getFileImg(), courseUpdate, courseExisting.getImg());
        Course courseUpdated = courseRepository.save(courseUpdate);
        return courseResponseDTOMapper.toDTO(courseUpdated);
    }

    @Override
    public Page<CourseResponseShortDTO> findByParam(String title, String courseCode, LocalDate createdFrom, LocalDate createdTo, PaginationObject paginationObject) {
        Page<CourseResponseShortDTO> page = courseRepository.searchCourses(
                title
                , courseCode
                , createdFrom == null ? null : createdFrom.atStartOfDay()
                , createdTo == null ? null : LocalDateTime.of(createdTo, LocalTime.MAX)
                , paginationObject.toPageable())
                .map(courseResponseShortDTOMapper::toDTO);
        if (page.isEmpty()) {
            throw new ResourceNotFoundException("error.resource_not_found", "");
        }
        return page;
    }


    /**
     * Method check Course Code đã tồn tại hay chưa
     *
     * @param courseCode được truyền vào từ đối tượng request so sánh với course code trong csdl
     */
    private void validateCourseCode(String courseCode) {
        if (courseRepository.findByCourseCode(courseCode).isPresent()) {
            throw new DuplicateCodeException("error.duplicate_code_entity", Course.class.getSimpleName(), courseCode);
        }
    }

    /**
     * Method sử dụng cho thêm course mới
     *
     * @param file file truyền vào qua request
     */
    private void handleFileUpload(MultipartFile file, Course course) {
        handleFileUpload(file, course, null);
    }

    /**
     * Method sử dụng chung cho việc thêm, xóa, sửa ảnh
     *
     * @param file         file truyền vào qua request
     * @param course       đối tượng
     * @param oldImageName tên ảnh cũ cần xóa
     */
    private void handleFileUpload(MultipartFile file, Course course, String oldImageName) {
        if (FileUploadUtil.checkFileUpload(file, 10, FileUploadUtil.IMAGE)) {
            try {
                String imgName = FileUploadUtil.uploadFile(file);
                course.setImg(imgName);

                if (oldImageName != null) {
                    FileUploadUtil.deleteFileByName(oldImageName);
                }
            } catch (IOException e) {
                throw new FileUploadingException("error.file_uploading");
            }
        }
    }
}
