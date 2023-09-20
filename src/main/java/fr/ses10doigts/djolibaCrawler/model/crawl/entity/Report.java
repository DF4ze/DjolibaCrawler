package fr.ses10doigts.djolibaCrawler.model.crawl.entity;

import fr.ses10doigts.djolibaCrawler.model.crawl.enumerate.FinalState;
import fr.ses10doigts.djolibaCrawler.model.crawl.enumerate.RunningState;

public class Report {

    private RunningState runningState;
    private FinalState   finalState;
    private String	 message;

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
