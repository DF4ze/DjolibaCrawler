package fr.ses10doigts.coursesCrawler.model.course.entity;

import fr.ses10doigts.coursesCrawler.model.scrap.AbstractCourseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"courseID", "numCheval"}))
public class Cote extends AbstractCourseEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long    id;
    private Long    courseID;
    private Integer numCheval;
    private Float   coteDepart;
    private Float   coteAvant;
    private Float   enjeuxDepart;
    private Float   enjeuxAvant;
    private Float   rapportProbableGeny;

    public Cote() {
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

    public Float getCoteDepart() {
	return coteDepart;
    }

    public void setCoteDepart(Float coteDepart) {
	this.coteDepart = coteDepart;
    }

    public Float getCoteAvant() {
	return coteAvant;
    }

    public void setCoteAvant(Float coteAvant) {
	this.coteAvant = coteAvant;
    }

    public Float getEnjeuxDepart() {
	return enjeuxDepart;
    }

    public void setEnjeuxDepart(Float enjeuxDepart) {
	this.enjeuxDepart = enjeuxDepart;
    }

    public Float getEnjeuxAvant() {
	return enjeuxAvant;
    }

    public void setEnjeuxAvant(Float enjeuxAvant) {
	this.enjeuxAvant = enjeuxAvant;
    }

    public Float getRapportProbableGeny() {
	return rapportProbableGeny;
    }

    public void setRapportProbableGeny(Float rapportProbableGeny) {
	this.rapportProbableGeny = rapportProbableGeny;
    }

    @Override
    public String toString() {
	return "Cote [id=" + id + ", courseID=" + courseID + ", numCheval=" + numCheval + ", coteDepart=" + coteDepart
		+ ", coteAvant=" + coteAvant + ", enjeuxDepart=" + enjeuxDepart + ", enjeuxAvant=" + enjeuxAvant + "]";
    }


}
