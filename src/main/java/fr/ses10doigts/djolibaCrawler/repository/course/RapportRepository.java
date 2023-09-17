package fr.ses10doigts.djolibaCrawler.repository.course;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.ses10doigts.djolibaCrawler.model.scrap.entity.Rapport;

public interface RapportRepository extends JpaRepository<Rapport, Long> {

    Set<Rapport> findByCourseID( Long courseID );
}
