package fr.ses10doigts.djolibaCrawler.repository.scrap;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import fr.ses10doigts.djolibaCrawler.model.scrap.entity.Frame;
import fr.ses10doigts.djolibaCrawler.model.scrap.entity.enumarate.WoodType;


public interface FrameRepository extends JpaRepository<Frame, Long> {

    Frame findBySku(String frameSKU);

    List<Frame> findByAvailable(Boolean available);

    /**
     * Seach frame with all parameters facultatives
     *
     * @param actif    {@link Boolean}
     * @param size     {@link Integer}
     * @param woodType {@link WoodType}
     * @return
     */
    //    @Query("SELECT f FROM Frame f WHERE (:actif is null or f.actif = :actif) "
    //	    + "and (:size is null or f.size = :size) and (:woodType is null or f.woodType = :woodType) "
    //	    + "and (:available is null or f.available = :available)")

    @Query("from Frame where (?1 is null or ?1 = actif) and (?2 is null or ?2 = sizeCm)  and (?3 is null or ?3 = woodType) and (?4 is null or ?4 = available)")
    List<Frame> findByActifAndSizeCmAndWoodTypeAndAvailable(
	    Boolean actif, Integer size, WoodType woodType, Boolean available
	    );
}
