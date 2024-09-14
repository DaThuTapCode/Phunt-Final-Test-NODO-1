package com.trongphu.finalintern1.repository;

import com.trongphu.finalintern1.entity.StudentCourse;
import com.trongphu.finalintern1.entity.StudentCourseId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Created by Trong Phu on 31/08/2024 21:50
 *
 * @author Trong Phu
 */
public interface StudentCourseRepository extends JpaRepository<StudentCourse, StudentCourseId> {

    @Override
    Optional<StudentCourse> findById(StudentCourseId studentCourseId);


    @Modifying
    @Query("""
            UPDATE  StudentCourse sc SET sc.status = :status WHERE 
            (:studentId IS NULL OR sc.id.studentId = :studentId)
            AND (:courseId IS NULL OR sc.id.courseId = :courseId)
            """)
    void updateStatusByStudentOrCourse(
            @Param("studentId") Long studentId,
            @Param("courseId") Long courseId,
            @Param("status") Integer status
    );

    @Query("""
            SELECT sc FROM StudentCourse sc WHERE  sc.student.id = :studentId 
            """)
    List<StudentCourse> findByStudentId(@Param("studentId") Long studentId);

    @Query("""
            SELECT sc FROM StudentCourse sc WHERE   sc.course.id = :courseId
            """)
    List<StudentCourse> findByCourseId(@Param("courseId") Long courseId);

}
