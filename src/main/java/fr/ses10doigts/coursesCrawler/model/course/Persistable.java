package fr.ses10doigts.coursesCrawler.model.course;

import java.io.Serializable;

public interface Persistable <T extends Serializable> {
	public T getId();
}
