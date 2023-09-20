package fr.ses10doigts.djolibaCrawler.model.web;

import lombok.Data;

@Data
public class MainTableFiltersDTO {

    private String   animal;
    private Integer  frameSize;
    private String  frameWood;
    private Boolean available;
}
