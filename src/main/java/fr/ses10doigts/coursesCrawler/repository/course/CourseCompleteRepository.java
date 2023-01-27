package fr.ses10doigts.coursesCrawler.repository.course;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.ses10doigts.coursesCrawler.model.course.entity.CourseComplete;

public interface CourseCompleteRepository extends JpaRepository<CourseComplete, Long> {

}
