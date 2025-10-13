package com.onlinecoursehub.impl.repository;

import com.onlinecoursehub.impl.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Repository;

@Repository
public interface  CourseRepository extends JpaRepository<Course,Long> {

    Course findByTitle(String name);

    void deleteByTitle(String name);

    boolean existsByTitle(String title);
}
