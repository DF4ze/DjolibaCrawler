package fr.ses10doigts.djolibaCrawler.model.scrap.entity;

import fr.ses10doigts.djolibaCrawler.model.scrap.AbstractPageEntity;
import fr.ses10doigts.djolibaCrawler.model.scrap.entity.enumarate.FrameFormat;
import fr.ses10doigts.djolibaCrawler.model.scrap.entity.enumarate.WoodType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
    @Enumerated(EnumType.STRING)
    private FrameFormat	format;
    @Enumerated(EnumType.STRING)
    private WoodType	woodType;
    private Boolean	available;
    private Double	price;
    private String	sku;
}
