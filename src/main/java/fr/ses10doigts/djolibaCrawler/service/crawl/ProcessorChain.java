package fr.ses10doigts.djolibaCrawler.service.crawl;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import fr.ses10doigts.djolibaCrawler.CustomProperties;
import fr.ses10doigts.djolibaCrawler.model.crawl.Page;
import fr.ses10doigts.djolibaCrawler.model.crawl.enumerate.Agressivity;
import fr.ses10doigts.djolibaCrawler.repository.web.WebCrawlingProxy;
import fr.ses10doigts.djolibaCrawler.service.crawl.tool.CrawlReport;
import fr.ses10doigts.djolibaCrawler.service.crawl.tool.PageTool;
import fr.ses10doigts.djolibaCrawler.service.scrap.HtmlVisitor;
import fr.ses10doigts.djolibaCrawler.service.scrap.tool.Chrono;

@Service
public class ProcessorChain implements Runnable {

    @Autowired
    private PageTool		pageTool;
    @Autowired
    private WebCrawlingProxy	webRepo;
    @Autowired
    private HtmlVisitor		htmlVisitor;
    @Autowired
    private CustomProperties	props;


    private Set<String>		seeds;
    private int			maxHop;
    private Set<String>		authorized;
    private Agressivity		agressivity;
    private boolean		running	= true;
    private Queue<Page>		enQueued;
    private CrawlReport		report	= CrawlReport.getInstance();
    private Chrono		chrono;

    private static final Logger	logger = LoggerFactory.getLogger(ProcessorChain.class);




    public ProcessorChain() {
    }

    public ProcessorChain(Set<String> seeds, int maxHop, Set<String> authorized, Agressivity agressivity) {
	super();
	this.seeds = seeds;
	this.maxHop = maxHop;
	this.authorized = authorized;
	this.agressivity = agressivity;
    }

    @Override
    public void run() {
	chrono = new Chrono();
	chrono.pick();
	if (seeds.isEmpty()) {
	    throw new RuntimeException("Seeds is empty!");
	}

	// Initialisation
	enQueued = new LinkedList<Page>(pageTool.url2Pages(seeds));
	report.setSeeds(seeds);

	// Informations
	String auth = "";
	for (String string : authorized) {
	    auth += string + ", ";
	}
	// @formatter:off
	logger.info("Crawl Start with parameters :\n " +
		"- " + enQueued.size() + " seed(s)\n" +
		"- maxHop : " + maxHop + "\n" +
		"- authorized : "+auth+"\n"
		+ "- agressivity : "+agressivity);
	// @formatter:on

	do {
	    for (Page page = null; (page = enQueued.poll()) != null;) {


		// Retrieve inner-links
		downloadAndSeek(page);
	    }

	} while (enQueued.size() != 0);



	// Report

	logger.info("Crawl ended");
	logger.info(
		"Crawled " + report.getSuccessCrawled() + "/" + report.size() + " pages in " + chrono.compareToHour());
	report.setTime(chrono.compare());

    }

    private void downloadAndSeek(Page page) {
	Set<Page> newPages = new HashSet<>();

	report.startCrawl(page.getUrl());

	// Wait for a user friendly crawl
	sleep(agressivity);
	// soft way to stop thread
	if (!running) {
	    return;
	}

	try {
	    // Download content
	    String content = webRepo.getRawPage(page.getUrl());

	    logger.debug("Url downloaded : " + page.getUrl());
	    report.stopCrawl(page.getUrl());

	    // Parse content
	    htmlVisitor.indexify(page.getUrl(), content);

	    // If maxhop not reached
	    if (page.getHop() < maxHop) {
		// retrieve new URL to download
		newPages = pageTool.findUrlsInContent(page, content, authorized);
		report.addNewPages(newPages);

		// Download content and search for new URL
		for (Page newPage : newPages) {
		    downloadAndSeek(newPage);
		    page.getUrlsContained().add(newPage.getUrl());

		    // soft way to stop thread
		    if (!running) {
			break;
		    }
		}
	    } else {
		logger.debug("Max hop reached, no more research of inner URL  ");

	    }
	} catch (RestClientException e) {
	    logger.error("RestClientException on page " + page.getUrl());
	    report.errorCrawl(page.getUrl());
	    setAsCrawlErrorPage(page);

	    // Launching a connectivity test to know if we still have Internet
	    // ... and wait if not
	    webRepo.connectivityTest();
	}



	// Update states
	//	if (webSiteConnexion) {
	//	    if (foundURL ) {
	//		report.stopCrawl(page.getUrl());
	//	    } else {
	//		report.errorCrawl(page.getUrl());
	//		setAsCrawlErrorPage(page);
	//	    }
	//	} else {
	//	    report.errorCrawl(page.getUrl());
	//	    setAsCrawlErrorPage(page);
	//	}

    }

    private void setAsCrawlErrorPage(Page page) {
	if (page.getNbRetry() < props.getMaxRetry()) {

	    page.setNbRetry(page.getNbRetry() + 1);
	    enQueued.add(page);
	}
    }

    private void sleep(Agressivity ag) {
	try {
	    int range = getRandomNumberInRange(ag.getMin(), ag.getMax());
	    range = range * 1000;
	    logger.debug("Thread will sleep " + range + "ms");
	    Thread.sleep(range);

	} catch (InterruptedException e) {
	    logger.warn("Thread sleep caused an Exception");
	}
    }

    private static int getRandomNumberInRange(int min, int max) {

	if (min >= max) {
	    throw new IllegalArgumentException("max must be greater than min");
	}

	Random r = new Random();
	return r.nextInt((max - min) + 1) + min;
    }

    public void askToStop() {
	running = false;
    }


    public Set<String> getSeeds() {
	return seeds;
    }

    public void setSeeds(Set<String> seeds) {
	this.seeds = seeds;
    }

    public int getMaxHop() {
	return maxHop;
    }

    public void setMaxHop(int maxHop) {
	this.maxHop = maxHop;
    }

    public Set<String> getAuthorised() {
	return authorized;
    }

    public void setAuthorised(Set<String> authorised) {
	this.authorized = authorised;
    }

    public Agressivity getAgressivity() {
	return agressivity;
    }

    public void setAgressivity(Agressivity agressivity) {
	this.agressivity = agressivity;
    }

    public boolean getRunning() {
	return running;
    }

    public CrawlReport getReport() {
	return report;
    }

    public Chrono getChrono() {
	return chrono;
    }
}
