package fr.ses10doigts.djolibaCrawler.repository.scrap;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.ses10doigts.djolibaCrawler.model.scrap.entity.Skin;


public interface SkinRepository extends JpaRepository<Skin, Long> {

    Skin findBySku(String sku);

}
