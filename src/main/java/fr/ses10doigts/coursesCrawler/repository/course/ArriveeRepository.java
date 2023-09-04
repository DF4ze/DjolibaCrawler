package fr.ses10doigts.coursesCrawler.repository.course;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.ses10doigts.coursesCrawler.model.scrap.entity.Arrivee;

public interface ArriveeRepository extends JpaRepository<Arrivee, Long>{

    Set<Arrivee> findByCourseID( Long courseID );
}
