package com.trongphu.finalintern1.controller;


import com.trongphu.finalintern1.dto.coursedto.request.CourseRequestDTO;
import com.trongphu.finalintern1.dto.coursedto.response.CourseResponseDTO;
import com.trongphu.finalintern1.dto.coursedto.response.CourseResponseShortDTO;

import com.trongphu.finalintern1.service.serviceinterface.ICourseService;
import com.trongphu.finalintern1.util.PaginationObject;
import com.trongphu.finalintern1.util.exception.ResponseErrorObject;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Qualifier;
import com.trongphu.finalintern1.entity.Course;

import org.springframework.data.domain.Page;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;


/**
 * Created by Trong Phu on 28/08/2024 22:29
 *
 * @author Trong Phu
 */
@RestController
@RequestMapping(value = "api/course")
public class CourseController {

    private final ICourseService courseService;

    public CourseController(
            @Qualifier("CourseService") ICourseService courseService
    ) {
        this.courseService = courseService;
    }

    /**
     * @apiNote  API lấy course theo id: GET http://localhost:8080/api/course/{{id}}
     *
     * @param id Khóa chính {@link Course}
     */
    @GetMapping("{id}")
    public ResponseEntity<CourseResponseDTO> getCourseById(
            @PathVariable Long id
    ){
        return ResponseEntity.ok(courseService.getById(id));
    }

    /**
     * @apiNote API tìm kiếm course: GET http://localhost:8080/api/course/search
     * @param title       tên course
     * @param courseCode  mã course
     * @param createdFrom ngày tạo từ
     * @param createdTo   ngày tạo cuối
     */
    @GetMapping("search")
    public ResponseEntity<?> searchCourse(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String courseCode,
            @RequestParam(required = false) LocalDate createdFrom,
            @RequestParam(required = false) LocalDate createdTo,
            @RequestBody PaginationObject paginationObject
    ) {
        paginationObject.setEntityClass(Course.class);
        Page<CourseResponseShortDTO> shortDTOList = courseService.findByParam(title, courseCode, createdFrom, createdTo, paginationObject);
        return ResponseEntity.ok(shortDTOList);
    }

    /**
     * @apiNote API lấy phân trang course: GET http://localhost:8080/api/course/get-page
     * @param paginationObject {@link PaginationObject} đối tượng hỗ trợ phân trang
     * */
    @RequestMapping(value = "get-page", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<?> getPageCourse(
            @RequestBody PaginationObject paginationObject
    ) {
        paginationObject.setEntityClass(Course.class);
        Page<CourseResponseDTO> responseDTOPage = courseService.getPage(paginationObject);
        return ResponseEntity.ok(responseDTOPage);
    }

    /**
     * @apiNote API tạo 1 course mới: POST http://localhost:8080/api/course/create
     * @param courseRequestDTO {@link CourseRequestDTO} hứng dữ liệu từ form data
     */
    @PostMapping("create")
    public ResponseEntity<CourseResponseDTO> createNewCourse(
            @ModelAttribute @Valid CourseRequestDTO courseRequestDTO
    ) {
        return ResponseEntity.created(URI.create("/")).body(courseService.createNew(courseRequestDTO));
    }

    /**
     * @apiNote API update course: PUT http://localhost:8080/api/course/update/{{id}}
     * @param id khóa chính entity {@link Course}
     * @param courseRequestDTO đối tượng dto hứng dữ liệu từ request form data
     * */
    @PutMapping("update/{id}")
    public ResponseEntity<CourseResponseDTO> updateCourse(
            @PathVariable Long id,
            @ModelAttribute @Valid CourseRequestDTO courseRequestDTO
    ) {
        CourseResponseDTO courseUpdated = courseService.update(id, courseRequestDTO);
        return ResponseEntity.ok(courseUpdated);
    }

    /**
     * @apiNote API xóa mềm course: DELETE http://localhost:8080/api/course/delete/{{id}}
     *
     * @param id khóa chính {@link Course}
     */
    @DeleteMapping("delete/{id}")
    public ResponseEntity<ResponseErrorObject> softDelete(
            @PathVariable(value = "id") Long id
    ) {
        courseService.softDelete(id, 0);
        return ResponseEntity.noContent().build();
    }
}