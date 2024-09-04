package com.trongphu.finalintern1.repository;

import com.trongphu.finalintern1.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
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
public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("SELECT s FROM Student s WHERE s.studentCode = :studentCode AND s.status = 1")
    Optional<Student> findStudentByStudentCode(@Param("studentCode") String studentCode);

    @Modifying //Đánh dấu đây không phải 1 câu SELECT mà là UPDATE hoặc DELETE
    @Query("UPDATE Student s SET s.status = :status WHERE s.id = :id")
    void softDeleteStudent(
            @Param(value = "id") Long id,
            @Param(value = "status") Integer status
    );

    @Override
    @Query("SELECT s FROM Student s LEFT JOIN FETCH s.courses WHERE s.status = 1")
    List<Student> findAll();

    @Override
    @EntityGraph(attributePaths = "courses")
    @Query("""
            SELECT s FROM Student s 
             WHERE s.status = 1
            """)
    Page<Student> findAll(Pageable pageable);

    @Override
    @Query("""
            SELECT  s FROM Student s
             LEFT JOIN FETCH s.courses c
             WHERE
              s.id = :id
              AND s.status = 1
              AND (c IS NULL OR c.status = 1 OR c.status = 0)
            """)
    Optional<Student> findById(@Param("id") Long id);

    /**
     * Tìm kiếm student theo thuộc tính
     */
    @Query("""
        SELECT DISTINCT s FROM Student s
        LEFT JOIN FETCH s.courses sc
        WHERE
        s.status = 1 
        AND 
        (:name IS NULL OR LOWER(s.name) LIKE LOWER(CONCAT('%', :name, '%')))
        AND 
        (:studentCode IS NULL OR LOWER(s.studentCode) LIKE LOWER(CONCAT('%', :studentCode, '%')))
        AND 
        (:email IS NULL OR LOWER(s.email) LIKE LOWER(CONCAT('%', :email, '%')))
        AND 
        (:createdFrom IS NULL OR s.createdAt >= :createdFrom)
        AND 
        (:createdTo IS NULL OR s.createdAt <= :createdTo)
        """)
    Page<Student> searchStudentByParam(
            @Param(value = "name") String name,
            @Param(value = "studentCode") String studentCode,
            @Param(value = "email") String email,
            @Param(value = "createdFrom") LocalDateTime createdFrom,
            @Param(value = "createdTo") LocalDateTime createdTo,
            Pageable pageable
    );

}
