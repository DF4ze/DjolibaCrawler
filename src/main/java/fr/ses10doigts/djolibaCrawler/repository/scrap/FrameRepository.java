package fr.ses10doigts.djolibaCrawler.repository.scrap;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.ses10doigts.djolibaCrawler.model.scrap.entity.Frame;

@Repository
public interface FrameRepository extends JpaRepository<Frame, Long> {

}
