package fr.ses10doigts.coursesCrawler.repository.course;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import fr.ses10doigts.coursesCrawler.model.course.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findByCourseID( Long courseID );

    @Query("from Course where ?1 <= courseID ")
    List<Course> findAllFrom( Long courseID );

    @Query("from Course where ?1 <= courseID and ?2 >= courseID")
    List<Course> findAllBetween( Long courseIDStart, Long courseIDStop );
}
