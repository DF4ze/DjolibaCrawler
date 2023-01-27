package fr.ses10doigts.coursesCrawler.model.course.entity;

import fr.ses10doigts.coursesCrawler.model.course.AbstractCourseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"courseID", "numChv"}))
public class Arrivee extends AbstractCourseEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Long courseID;

    private Integer numArrivee;
    private Integer numChv;
    private String nomChv;

    public Arrivee(){
	super();
    }

    public Arrivee(Long courseId, Integer numArrivee, Integer numChv, String nomChv) {
	super();
	this.courseID = courseId;
	this.numArrivee = numArrivee;
	this.numChv = numChv;
	this.nomChv = nomChv;
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
    public void setCourseID(Long courseID) {
	this.courseID = courseID;
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
	return "Arrivee [id=" + id + ", CourseId=" + courseID + ", numArrivee=" + numArrivee + ", numChv=" + numChv
		+ ", nomChv=" + nomChv + "]";
    }





}
