package fr.ses10doigts.djolibaCrawler.model.business.entity;

import lombok.Data;

@Data
public class MainTableFiltersDto {

    private String   animal;
    private Integer  frameSize;
    private String  frameWood;
    private Boolean available;
}
