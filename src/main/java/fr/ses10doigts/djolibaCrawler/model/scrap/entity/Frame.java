package fr.ses10doigts.djolibaCrawler.model.scrap.entity;

import fr.ses10doigts.djolibaCrawler.model.scrap.AbstractPageEntity;
import fr.ses10doigts.djolibaCrawler.model.scrap.entity.enumarate.FrameFormat;
import fr.ses10doigts.djolibaCrawler.model.scrap.entity.enumarate.WoodType;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class Frame extends AbstractPageEntity {

    private Integer	sizeInch;
    private Integer	sizeCm;
    private FrameFormat	format;
    private WoodType	woodType;
    private Integer	disponibility;
    private Double	price;
}
