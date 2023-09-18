package fr.ses10doigts.djolibaCrawler.model.scrap.entity;

import fr.ses10doigts.djolibaCrawler.model.scrap.AbstractPageEntity;
import fr.ses10doigts.djolibaCrawler.model.scrap.entity.enumarate.FrameFormat;
import fr.ses10doigts.djolibaCrawler.model.scrap.entity.enumarate.WoodType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "frame", uniqueConstraints = @UniqueConstraint(columnNames = { "sku" }))
public class Frame extends AbstractPageEntity {

    private Integer	sizeInch;
    private Integer	sizeCm;
    private FrameFormat	format;
    private WoodType	woodType;
    private Boolean	disponibility;
    private Double	price;
    private String	sku;
}
