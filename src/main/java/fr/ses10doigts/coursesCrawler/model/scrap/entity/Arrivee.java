package fr.ses10doigts.coursesCrawler.model.scrap.entity;

import fr.ses10doigts.coursesCrawler.model.scrap.AbstractCourseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"courseID", "numChv"}))
public class Arrivee extends AbstractCourseEntity {



    private Integer numArrivee;
    private Integer numChv;
    private String nomChv;

    public Arrivee(){
	super();
    }

    public Arrivee(String url, Long courseId, Integer numArrivee, Integer numChv, String nomChv) {
	super();
	setUrl(url);
	setCourseID(courseId);
	this.numArrivee = numArrivee;
	this.numChv = numChv;
	this.nomChv = nomChv;
    }




    public Integer getNumArrivee() {
	return numArrivee;
    }
    public void setNumArrivee(Integer numArrivee) {
	this.numArrivee = numArrivee;
    }

    public Integer getNumChv() {
	return numChv;
    }
    public void setNumChv(Integer numChv) {
	this.numChv = numChv;
    }

    public String getNomChv() {
	return nomChv;
    }
    public void setNomChv(String nomChv) {
	this.nomChv = nomChv;
    }



    @Override
    public String toString() {
	return "Arrivee [id=" + getId() + ", CourseId=" + getCourseID() + ", numArrivee=" + numArrivee + ", numChv="
		+ numChv + ", nomChv=" + nomChv + "]";
    }





}
