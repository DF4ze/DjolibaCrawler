package fr.ses10doigts.djolibaCrawler.model.scrap;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;

@MappedSuperclass
@Data
@EqualsAndHashCode(callSuper = false)
public abstract class AbstractPageEntity extends AbstractEntity {

    private String  url;
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date    date;
    private boolean actif;

}
