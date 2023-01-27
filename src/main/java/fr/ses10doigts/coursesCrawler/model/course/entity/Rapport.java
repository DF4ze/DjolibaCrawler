package fr.ses10doigts.coursesCrawler.model.course.entity;

import fr.ses10doigts.coursesCrawler.model.course.AbstractCourseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"courseId", "numCheval"}))
public class Rapport extends AbstractCourseEntity{


    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Long courseID;
    private Integer numCheval;
    private Integer arrivee;
    private Double place;
    private Double gagnant;


    public Rapport() {
    }

    @Override
    public Long getId() {
	return id;
    }
    public void setId(Long id) {
	this.id = id;
    }

    @Override
    public Long getCourseID() {
	return courseID;
    }
    @Override
    public void setCourseID(Long courseId) {
	this.courseID = courseId;
    }


    public Integer getNumCheval() {
	return numCheval;
    }
    public void setNumCheval(Integer numCheval) {
	this.numCheval = numCheval;
    }



    public Integer getArrivee() {
	return arrivee;
    }
    public void setArrivee(Integer arrivee) {
	this.arrivee = arrivee;
    }


    public Double getPlace() {
	return place;
    }
    public void setPlace(Double place) {
	this.place = place;
    }


    public Double getGagnant() {
	return gagnant;
    }
    public void setGagnant(Double gagnant) {
	this.gagnant = gagnant;
    }

    @Override
    public String toString() {
	return "Rapport [id=" + id + ", courseID=" + courseID + ", numCheval=" + numCheval + ", arrivee=" + arrivee
		+ ", place=" + place + ", gagnant=" + gagnant + "]";
    }

}
