package fr.ses10doigts.coursesCrawler.model.course.entity;

import fr.ses10doigts.coursesCrawler.model.scrap.AbstractCourseEntity;
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
    private Long    id;
    private Long    courseID;
    private Integer numCheval;
    private Integer arrivee;
    private Double  placeGeny;
    private Double  gagnantGeny;
    private Double  placePmu;
    private Double  gagnantPmu;


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


    public Double getPlaceGeny() {
	return placeGeny;
    }

    public void setPlaceGeny(Double placeGeny) {
	this.placeGeny = placeGeny;
    }

    public Double getGagnantGeny() {
	return gagnantGeny;
    }

    public void setGagnantGeny(Double gagnantGeny) {
	this.gagnantGeny = gagnantGeny;
    }

    public Double getPlacePmu() {
	return placePmu;
    }

    public void setPlacePmu(Double placePmu) {
	this.placePmu = placePmu;
    }

    public Double getGagnantPmu() {
	return gagnantPmu;
    }

    public void setGagnantPmu(Double gagantPmu) {
	this.gagnantPmu = gagantPmu;
    }

    @Override
    public String toString() {
	return "Rapport [id=" + id + ", courseID=" + courseID + ", numCheval=" + numCheval + ", arrivee=" + arrivee
		+ ", placeGeny=" + placeGeny + ", gagnantGeny=" + gagnantGeny + ", placePmu=" + placePmu
		+ ", gagantPmu=" + gagnantPmu + "]";
    }


}
