package fr.ses10doigts.djolibaCrawler.repository.course;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.ses10doigts.djolibaCrawler.model.scrap.entity.Cote;

public interface CoteRepository extends JpaRepository<Cote, Long>{

    Set<Cote> findByCourseID( Long courseID );

}
