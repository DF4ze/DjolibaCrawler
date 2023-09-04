package fr.ses10doigts.coursesCrawler.model.scrap;

public abstract class AbstractEntity implements Persistable<Long>{
	
	protected static final long serialVersionUID = 1L;

	public abstract Long getId() ;
}