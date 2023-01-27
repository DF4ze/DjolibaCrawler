package fr.ses10doigts.coursesCrawler.model.crawl;

import fr.ses10doigts.coursesCrawler.model.crawl.enumerate.FinalState;
import fr.ses10doigts.coursesCrawler.model.crawl.enumerate.RunningState;

public class CrawlReport {

    private RunningState runningState;
    private FinalState   finalState;
    private String	      message;

    public RunningState getRunningState() {
	return runningState;
    }

    public void setRunningState(RunningState runningState) {
	this.runningState = runningState;
    }

    public FinalState getFinalState() {
	return finalState;
    }

    public void setFinalState(FinalState finalState) {
	this.finalState = finalState;
    }

    public String getMessage() {
	return message;
    }

    public void setMessage(String message) {
	this.message = message;
    }

}
