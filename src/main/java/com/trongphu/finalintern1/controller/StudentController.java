package com.trongphu.finalintern1.controller;

import com.trongphu.finalintern1.config.i18nconfig.Translator;
import com.trongphu.finalintern1.dto.studentdto.request.StudentRequestDTO;
import com.trongphu.finalintern1.dto.studentdto.response.StudentResponseDTO;
import com.trongphu.finalintern1.dto.studentdto.response.StudentSearchResponseDTO;
import com.trongphu.finalintern1.entity.Student;
import com.trongphu.finalintern1.service.serviceinterface.IStudentService;
import com.trongphu.finalintern1.util.PaginationObject;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


/**
 * Created by Trong Phu on 28/08/2024 22:29
 * Controller điều hướng, trả dữ liệu các thao tác với Entity {@link Student}
 *
 * @author Trong Phu
 */
@RestController
@RequestMapping(value = "api/student")
public class StudentController {

    private final IStudentService studentService;

    public StudentController(
            @Qualifier(value = "StudentService") IStudentService studentService
    ) {
        this.studentService = studentService;
    }

    /**
     * @param id của {@link Student}
     * @apiNote API lấy {@link Student} theo id
     * GET http://localhost:8080/api/student/{{id}}
     */
    @GetMapping(value = "{id}")
    public ResponseEntity<StudentResponseDTO> getStudentById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(studentService.getById(id));
    }

    @GetMapping(value = "get-all")
    private ResponseEntity<?> getAll(){
        return ResponseEntity.ok(studentService.getAll());
    }

    /**
     * @param paginationObject {@link PaginationObject}  đối tượng hỗ trợ các tham số cho phân trang
     * @apiNote API lấy phân trang {@link Student}
     * POST http://localhost:8080/api/student/get-page
     */
    @PostMapping(value = "get-page")
    public ResponseEntity<?> getPageStudent(
            @RequestBody @Valid PaginationObject paginationObject
    )  {
        paginationObject.setEntityClass(Student.class);
        Page<StudentResponseDTO> studentResponseDTOPage = studentService.getPage(paginationObject);
        return ResponseEntity.ok(studentResponseDTOPage);
    }

    /**
     * @param name        thuộc tính của {@link Student}
     * @param studentCode thuộc tính của {@link Student}
     * @param email       thuộc tính của {@link Student}
     * @param createdFrom thuộc tính của {@link Student}
     * @param createdTo   thuộc tính của {@link Student}
     * @apiNote API lấy phân trang tìm kiếm {@link Student}
     */
    @GetMapping(value = "search")
    public ResponseEntity<?> searchStudentByParam(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String studentCode,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) LocalDate createdFrom,
            @RequestParam(required = false) LocalDate createdTo,
            @RequestBody PaginationObject paginationObject
    ) {
        paginationObject.setEntityClass(Student.class);
        Page<StudentSearchResponseDTO> searchPage = studentService.searchPage(name, studentCode, email, createdFrom, createdTo, paginationObject);
        return ResponseEntity.ok(searchPage);
    }

    /**
     * @param studentRequestDTO {@link StudentRequestDTO} đối tượng student request dto
     * @apiNote API tạo 1 {@link Student} mới
     * POST http://localhost:8080/api/student
     */
    @PostMapping(value = "create")
    public ResponseEntity<StudentResponseDTO> createNewStudent(
            @Valid @ModelAttribute StudentRequestDTO studentRequestDTO
    ) {
        StudentResponseDTO studentSaved = studentService.createNew(studentRequestDTO);
        return new ResponseEntity<>(studentSaved, HttpStatus.CREATED);
    }

    /**
     * @param id                khóa chính {@link Student}
     * @param studentRequestDTO {@link StudentRequestDTO} đối tượng hứng dữ liệu từ request
     * @apiNote API Update {@link Student} theo id PUT http://localhost:8080/api/student/{{idStudent}}
     */
    @PutMapping(value = "update/{id}")
    private ResponseEntity<StudentResponseDTO> updateStudent(
            @PathVariable Long id,
            @Valid @ModelAttribute StudentRequestDTO studentRequestDTO
    ) {
        StudentResponseDTO studentResponseDTO = studentService.update(id, studentRequestDTO);
        return ResponseEntity.ok(studentResponseDTO);
    }

    /**
     * @param id khóa chính {@link Student}
     * @apiNote API Xóa mềm {@link Student}
     * DELETE http://localhost:8080/api/student/soft-delete/{{id}}
     */
    @DeleteMapping(value = "soft-delete/{id}")
    public ResponseEntity<?> softDeleteStudent(
            @PathVariable(value = "id") Long id
    ) {
        studentService.softDelete(id, 0);
        return ResponseEntity.noContent().build();
    }

    /**
     * @apiNote API test i18n
     * GET http://localhost:8080/api/student
     */
    @GetMapping
    public ResponseEntity<String> greeting() {
        return ResponseEntity.ok(Translator.toLocale("greeting"));
    }


}
