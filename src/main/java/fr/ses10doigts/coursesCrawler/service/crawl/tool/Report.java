package fr.ses10doigts.coursesCrawler.service.crawl.tool;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import fr.ses10doigts.coursesCrawler.model.crawl.Page;
import fr.ses10doigts.coursesCrawler.model.crawl.PageReport;
import fr.ses10doigts.coursesCrawler.model.crawl.enumerate.ChainState;
import fr.ses10doigts.coursesCrawler.model.crawl.enumerate.FinalState;
import fr.ses10doigts.coursesCrawler.model.crawl.enumerate.RunningState;

public class Report {
    private static Map<String, PageReport> urlsReport = new HashMap<>();

    private Report() {

    }

    public static Report getInstance() {
	return new Report();
    }

    public void setSeeds(Set<String> urls) {
	for (String url : urls) {
	    addAndSetPending(url);
	}
    }

    public void addNewPages(Set<Page> pages) {
	for (Page page : pages) {
	    addAndSetPending(page.getUrl());
	}
    }

    public int getSuccessCrawled() {
	int count = 0;
	for (Entry<String, PageReport> urlReport : urlsReport.entrySet()) {
	    if (urlReport.getValue().chainState == ChainState.CRAWLING
		    && urlReport.getValue().runningState == RunningState.ENDED
		    && urlReport.getValue().finalState == FinalState.SUCCESS) {
		count++;
	    }
	}
	return count;
    }

    public int size() {
	return urlsReport.size();
    }

    public int getErrorCrawled() {
	int count = 0;
	for (Entry<String, PageReport> urlReport : urlsReport.entrySet()) {
	    if (urlReport.getValue().chainState == ChainState.CRAWLING
		    && (urlReport.getValue().finalState == FinalState.ERROR
		    || urlReport.getValue().finalState == FinalState.WARNING)) {
		count++;
	    }
	}
	return count;
    }

    public int getRetryCrawled() {
	int count = 0;
	for (Entry<String, PageReport> urlReport : urlsReport.entrySet()) {
	    if (urlReport.getValue().chainState == ChainState.CRAWLING
		    && urlReport.getValue().runningState == RunningState.PENDING_RETRY) {
		count++;
	    }
	}
	return count;
    }

    public int getPendingCrawled() {
	int count = 0;
	for (Entry<String, PageReport> urlReport : urlsReport.entrySet()) {
	    if (urlReport.getValue().chainState == ChainState.CRAWLING
		    && (urlReport.getValue().runningState == RunningState.PENDING_RETRY
		    || urlReport.getValue().runningState == RunningState.PENDING)) {
		count++;
	    }
	}
	return count;
    }

    public void startCrawl(String url) {
	PageReport pr = new PageReport();
	pr.chainState = ChainState.CRAWLING;
	pr.runningState = RunningState.PROCESSING;
	urlsReport.put(url, pr);
    }

    public void errorCrawl(String url) {
	PageReport pr = new PageReport();
	pr.chainState = ChainState.CRAWLING;
	pr.finalState = FinalState.ERROR;
	pr.runningState = RunningState.PENDING_RETRY;
	urlsReport.put(url, pr);
    }

    public void stopCrawl(String url) {
	PageReport pr = new PageReport();
	pr.chainState = ChainState.CRAWLING;
	pr.finalState = FinalState.SUCCESS;
	pr.runningState = RunningState.ENDED;
	urlsReport.put(url, pr);
    }

    public void startStore(String url) {
	PageReport pr = new PageReport();
	pr.chainState = ChainState.STORING;
	pr.runningState = RunningState.ENDED;
	urlsReport.put(url, pr);

    }

    public void errorStore(String url) {
	PageReport pr = new PageReport();
	pr.chainState = ChainState.STORING;
	pr.finalState = FinalState.ERROR;
	pr.runningState = RunningState.PENDING_RETRY;
	urlsReport.put(url, pr);

    }

    public void stopStore(String url) {
	PageReport pr = new PageReport();
	pr.chainState = ChainState.STORING;
	pr.finalState = FinalState.SUCCESS;
	pr.runningState = RunningState.ENDED;
	urlsReport.put(url, pr);
    }

    private void addAndSetPending(String url) {
	PageReport pr = new PageReport();
	pr.chainState = ChainState.CRAWLING;
	pr.runningState = RunningState.PENDING;
	urlsReport.put(url, pr);
    }

}
