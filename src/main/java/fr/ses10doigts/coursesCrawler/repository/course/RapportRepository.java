package fr.ses10doigts.coursesCrawler.repository.course;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.ses10doigts.coursesCrawler.model.course.entity.Rapport;

public interface RapportRepository extends JpaRepository<Rapport, Long> {

    Set<Rapport> findByCourseID( Long courseID );
}
