package fr.ses10doigts.coursesCrawler.service.course.tool;

public class RefactorerReport {

    private int			    totalCourse	= 0;
    private int			    nbTreated	= 0;
    private int			    nbSkipped	= 0;
    private long		    time	= 0;
    private boolean		    running	= false;
    private Chrono		    chrono	= new Chrono();

    private static RefactorerReport report;

    private RefactorerReport() {

    }

    public static RefactorerReport getInstance() {
	if (report == null) {
	    report = new RefactorerReport();
	}
	return report;
    }

    public void startRefacto() {
	totalCourse = 0;
	nbTreated = 0;
	nbSkipped = 0;
	running = true;
	chrono.pick();
    }

    public void stopRefacto() {
	running = false;
	time = chrono.compare();
    }

    public void addTotalCourse(int add) {
	totalCourse += add;
    }

    public void addTreated(int add) {
	nbTreated += add;
    }

    public void addSkipped(int add) {
	nbSkipped += add;
    }
    public int getTotalCourse() {
	return totalCourse;
    }

    public void setTotalCourse(int totalCourse) {
	this.totalCourse = totalCourse;
    }

    public int getNbTreated() {
	return nbTreated;
    }

    public void setNbTreated(int nbTreated) {
	this.nbTreated = nbTreated;
    }

    public int getNbSkipped() {
	return nbSkipped;
    }

    public void setNbSkipped(int nbSkipped) {
	this.nbSkipped = nbSkipped;
    }

    public int totalCourse() {

	return totalCourse;
    }

    public int getTreated() {

	return nbTreated;
    }

    public int getSkipped() {
	return nbSkipped;
    }

    public void setTime(long time) {
	this.time = time;

    }

    public long getTime() {
	if (running) {
	    return chrono.compare();
	}
	return time;
    }

}
