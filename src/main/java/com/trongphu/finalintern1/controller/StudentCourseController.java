package com.trongphu.finalintern1.controller;

import com.trongphu.finalintern1.dto.studentcoursedto.response.StudentCourseResponseDTO;
import com.trongphu.finalintern1.service.serviceinterface.IStudentCourseService;
import com.trongphu.finalintern1.entity.Student;
import com.trongphu.finalintern1.entity.StudentCourse;
import com.trongphu.finalintern1.entity.Course;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Trong Phu on 28/08/2024 22:54
 *
 * @author Trong Phu
 */
@RestController
@RequestMapping(value = "api/student-course")
public class StudentCourseController {
    private final IStudentCourseService studentCourseService;

    public StudentCourseController(
           @Qualifier(value = "StudentCourseService") IStudentCourseService studentCourseService) {
        this.studentCourseService = studentCourseService;
    }

    @GetMapping(value = "enrolled")
    public ResponseEntity<List<StudentCourseResponseDTO>> getEnrolledStudent(){
        return ResponseEntity.ok(studentCourseService.findEnrolledStudents());
    }

    @GetMapping(value = "dropped")
    public ResponseEntity<List<StudentCourseResponseDTO>> getDroppedStudent(){
        return ResponseEntity.ok(studentCourseService.findDroppedStudents());
    }

    @GetMapping(value = "get-by-student/{studentId}")
    public ResponseEntity<List<StudentCourseResponseDTO>> getByStudentId(
            @PathVariable("studentId") Long studentId
    ){
        return ResponseEntity.ok(studentCourseService.findByStudentId(studentId));
    }

    @GetMapping(value = "get-by-course/{courseId}")
    public ResponseEntity<List<StudentCourseResponseDTO>> getByCourseId(
            @PathVariable("courseId") Long courseId
    ){
        return ResponseEntity.ok(studentCourseService.findByCourseId(courseId));
    }

    /**
     * @apiNote API tạo bản ghi vào bảng trung gian {@link StudentCourse}: POST http://localhost:8080/api/student-course/{{studentId}}/link/{{courseId}}
     * @param studentId khóa chính của {@link Student}
     * @param courseId khóa chính của {@link Course}
     * */
    @PostMapping(value = "{studentId}/link/{courseId}")
    public ResponseEntity<?> linkStudentCourse(
            @PathVariable Long studentId,
            @PathVariable Long courseId
    ){
        StudentCourseResponseDTO responseDTO=  studentCourseService.linkStudentVsCourse(studentId, courseId);
        return ResponseEntity.created(URI.create("")).body(responseDTO);
    }
 /**
     * @apiNote API hủy bản ghi  bảng trung gian {@link StudentCourse}: POST http://localhost:8080/api/student-course/{{studentId}}/cancel/{{courseId}}
     * @param studentId khóa chính của {@link Student}
     * @param courseId khóa chính của {@link Course}
     * */
    @PostMapping(value = "{studentId}/cancel/{courseId}")
    public ResponseEntity<?> cancelStudentCourse(
            @PathVariable Long studentId,
            @PathVariable Long courseId
    ){
        studentCourseService.cancelStudentVsCourse(studentId, courseId);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("message", "Hủy liên kết thành công!");
        return ResponseEntity.ok(hashMap);
    }

}
