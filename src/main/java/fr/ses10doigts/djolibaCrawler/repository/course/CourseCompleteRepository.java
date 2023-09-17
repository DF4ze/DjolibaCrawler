package fr.ses10doigts.djolibaCrawler.repository.course;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.ses10doigts.djolibaCrawler.model.scrap.entity.CourseComplete;

public interface CourseCompleteRepository extends JpaRepository<CourseComplete, Long> {

    List<CourseComplete> findAllOrderByDateCourse();
}
