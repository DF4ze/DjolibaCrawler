package fr.ses10doigts.djolibaCrawler.repository.scrap;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import fr.ses10doigts.djolibaCrawler.model.scrap.entity.Skin;


public interface SkinRepository extends JpaRepository<Skin, Long> {

    Skin findBySku(String sku);

    List<Skin> findByAvailable(Boolean available);


//    @Query("SELECT s FROM Skin s WHERE  (:actif is null or s.actif = :actif) "
    //	    + "and (:animal is null or s.animal = :animal) and (:available is null or s.available = :available)")

    @Query("from Skin where (?1 is null or ?1 = actif) and (?2 is null or ?2 = animal)  and (?3 is null or ?3 = available)")
    List<Skin> findByActifAndAnimalAndAvailable(boolean actif, String animal, Boolean available);
}
