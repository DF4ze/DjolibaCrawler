package fr.ses10doigts.djolibaCrawler.model.scrap;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractCourseEntity extends AbstractEntity {

    private String url;
    private Long   courseID;

    public String getUrl() {
	return url;
    }

    public void setUrl(String url) {
	this.url = url;
    }


    public Long getCourseID() {
	return courseID;
    }

    public void setCourseID(Long courseID) {
	this.courseID = courseID;
    }


}
