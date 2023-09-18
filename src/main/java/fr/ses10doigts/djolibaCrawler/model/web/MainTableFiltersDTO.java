package fr.ses10doigts.djolibaCrawler.model.web;

import fr.ses10doigts.djolibaCrawler.model.scrap.entity.enumarate.WoodType;
import lombok.Data;

@Data
public class MainTableFiltersDTO {

    private String   animal;
    private Integer  frameSize;
    private WoodType frameWood;
    private Boolean  avalaible;
}
