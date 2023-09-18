package fr.ses10doigts.djolibaCrawler.model.scrap.entity;

import fr.ses10doigts.djolibaCrawler.model.scrap.AbstractPageEntity;
import fr.ses10doigts.djolibaCrawler.model.scrap.entity.enumarate.FatnessType;
import fr.ses10doigts.djolibaCrawler.model.scrap.entity.enumarate.SkinFormat;
import fr.ses10doigts.djolibaCrawler.model.scrap.entity.enumarate.SkinSize;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "skin", uniqueConstraints = @UniqueConstraint(columnNames = { "sku" }))
public class Skin extends AbstractPageEntity implements Cloneable {

    private String	name;
    private String	animal;
    private SkinFormat	skinFormat;
    private FatnessType	fatness;
    @Embedded
    private SkinSize	size;
    private Double	price;
    private String	urlImg;
    private String	sku;
    private Boolean	available;

    @Override
    public Object clone() {
	Skin skin = null;
	try {
	    skin = (Skin) super.clone();
	} catch (CloneNotSupportedException e) {
	    // return new Skin(this.street, this.getCity(), this.getCountry());
	    System.out.println("Error cloning : " + e.getMessage());
	}

	return skin;
    }
}
