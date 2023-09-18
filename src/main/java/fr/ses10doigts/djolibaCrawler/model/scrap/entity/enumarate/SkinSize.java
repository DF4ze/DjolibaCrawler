package fr.ses10doigts.djolibaCrawler.model.scrap.entity.enumarate;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class SkinSize {

    private Double height;
    private Double width;

    public void setDiameter(Double size) {
	height = size;
	width = size;
    }
}
