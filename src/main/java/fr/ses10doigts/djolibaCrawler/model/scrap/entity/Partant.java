package fr.ses10doigts.djolibaCrawler.model.scrap.entity;

import fr.ses10doigts.djolibaCrawler.model.scrap.AbstractCourseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"courseId", "numCheval"}))
public class Partant extends AbstractCourseEntity implements Cloneable{


    private Integer numCheval;
    private String nomCheval;
    private String ageSexe;
    private String musique;
    private String gains;
    private Integer iGains;
    private Float   probableGeny;


    public Partant() {
    }



    public Integer getNumCheval() {
	return numCheval;
    }
    public void setNumCheval(Integer numCheval) {
	this.numCheval = numCheval;
    }

    public String getNomCheval() {
	return nomCheval;
    }
    public void setNomCheval(String nomCheval) {
	this.nomCheval = nomCheval;
    }


    public String getAgeSexe() {
	return ageSexe;
    }
    public void setAgeSexe(String ageSexe) {
	this.ageSexe = ageSexe;
    }


    public String getMusique() {
	return musique;
    }
    public void setMusique(String musique) {
	this.musique = musique;
    }

    public String getGains(){
	return gains;
    }
    public void setGains(String legains) {
	this.gains = legains;

	try{
	    if( gains != null ){
		gains = gains.replace(" ", "");

		this.iGains = Integer.parseInt(gains);
	    }
	}catch( Exception e ){
	    ;
	}

    }


    public Integer getiGains() {
	return iGains;
    }
    public void setiGains(Integer iGains) {
	this.iGains = iGains;
    }

    public void setProbableGeny(Float probGeny) {
	this.probableGeny = probGeny;

    }

    public Float getProbableGeny() {
	return probableGeny;
    }


    @Override
    public Partant clone() {
	Partant o = new Partant();

	o.ageSexe = ageSexe;
	o.setCourseID(this.getCourseID());
	o.gains = gains;
	o.setId(getId());
	o.iGains = iGains.intValue();
	o.musique = musique;
	o.nomCheval = nomCheval;
	o.numCheval = numCheval.intValue();


	// on renvoie le clone
	return o;
    }

    @Override
    public String toString() {
	return "Partant [id=" + getId() + ", courseID=" + getCourseID() + ", numCheval=" + numCheval + ", nomCheval="
		+ nomCheval
		+ ", ageSexe=" + ageSexe + ", musique=" + musique + ", gains=" + gains + ", iGains=" + iGains + "]";
    }


}
