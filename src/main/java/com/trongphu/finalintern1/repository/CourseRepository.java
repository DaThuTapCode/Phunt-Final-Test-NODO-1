package com.trongphu.finalintern1.repository;

import com.trongphu.finalintern1.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Created by Trong Phu on 28/08/2024 22:20
 *
 * @author Trong Phu
 */
public interface CourseRepository extends JpaRepository<Course, Long> {

    @Override
    @Query("SELECT c FROM Course c LEFT JOIN FETCH c.students WHERE c.id = :id AND c.status = 1")
    Optional<Course> findById(@Param(value = "id") Long id);

    @Override
    @Query("SELECT c FROM Course c WHERE c.id IN :ids AND c.status = 1")
    List<Course> findAllById(@Param("ids") Iterable<Long> ids);


    @Override
    @Query("SELECT c FROM Course c WHERE c.status = 1")
    List<Course> findAll();

    @Query("SELECT c FROM Course c WHERE c.courseCode = :courseCode and c.status = 1")
    Optional<Course> findByCourseCode(
            @Param("courseCode") String courseCode
    );

    @Override
    @Query("""
            SELECT DISTINCT c FROM Course c 
            LEFT JOIN FETCH c.students s
            WHERE 
                c.status = 1
            """)
    Page<Course> findAll(Pageable pageable);

    @Modifying //Đánh dấu đây không phải 1 câu SELECT mà là UPDATE hoặc DELETE
    @Query("UPDATE Course c SET c.status = :status WHERE c.id = :id")
    void softDelete(
            @Param("id") Long id,
            @Param("status") Integer status);

    @Query("""
        SELECT c FROM Course c WHERE 
        c.status = 1 
        AND 
        (:title IS NULL OR LOWER(c.title) LIKE LOWER(CONCAT('%', :title, '%')))
        AND 
        (:courseCode IS NULL OR LOWER(c.courseCode) LIKE LOWER(CONCAT('%', :courseCode, '%')))
        AND 
        (:createdFrom IS NULL OR c.createdAt >= :createdFrom) 
        AND 
        (:createdTo IS NULL OR c.createdAt <= :createdTo)
    """)
    Page<Course> searchCourses(
            @Param("title") String title,
            @Param("courseCode") String courseCode,
            @Param("createdFrom") LocalDateTime createdFrom,
            @Param("createdTo") LocalDateTime createdTo,
            Pageable pageable
    );
}
