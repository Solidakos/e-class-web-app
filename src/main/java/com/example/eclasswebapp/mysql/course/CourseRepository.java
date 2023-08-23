package com.example.eclasswebapp.mysql.course;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CourseRepository extends JpaRepository<Course, Integer> {
    @Query("SELECT COUNT(*) FROM Course WHERE name = ?1")
    public int doesCourseExist(@Param("name") String name);
    
}
