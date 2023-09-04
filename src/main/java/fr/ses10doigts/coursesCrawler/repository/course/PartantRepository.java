package fr.ses10doigts.coursesCrawler.repository.course;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.ses10doigts.coursesCrawler.model.scrap.entity.Partant;

public interface PartantRepository extends JpaRepository<Partant, Long> {

    Set<Partant> findByCourseID( Long courseID );

}
