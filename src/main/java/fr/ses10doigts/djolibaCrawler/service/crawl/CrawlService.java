package fr.ses10doigts.djolibaCrawler.service.crawl;

import java.io.IOException;
import java.lang.Thread.State;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.ses10doigts.djolibaCrawler.CustomCrawlProperties;
import fr.ses10doigts.djolibaCrawler.model.crawl.entity.Report;
import fr.ses10doigts.djolibaCrawler.model.crawl.enumerate.FinalState;
import fr.ses10doigts.djolibaCrawler.model.crawl.enumerate.RunningState;
import fr.ses10doigts.djolibaCrawler.repository.web.WebCrawlingProxy;
import fr.ses10doigts.djolibaCrawler.service.crawl.tool.CrawlReport;
import fr.ses10doigts.djolibaCrawler.service.crawl.tool.LineReader;
import fr.ses10doigts.djolibaCrawler.service.scrap.tool.Chrono;

@Service
public class CrawlService {
    @Autowired
    private WebCrawlingProxy	  webService;

    @Autowired
    private LineReader		  reader;

    @Autowired
    private CustomCrawlProperties props;

    @Autowired
    private ProcessorChain	  pc;

    private static Thread	  treatment = null;

    private static final Logger	  logger    = LoggerFactory.getLogger(CrawlService.class);

    public String getPage(String url) {
	return webService.getRawPage(url);
    }



    public Report launchCrawl() throws IOException {
	if (treatment == null || treatment.getState().equals(State.TERMINATED)) {
	    // Retrieve seeds
	    reader.setFilePath(props.getSeedsFile());
	    Set<String> urls = reader.fileToSet();

	    // retrieve authorized words in url
	    reader.setFilePath(props.getAuthorizedFile());
	    Set<String> urlAuthorised = reader.fileToSet();

	    logger.info("Following SEEDS will be crawled with a maxHop of " + props.getMaxHop());
	    for (String string : urls) {
		logger.info("   - " + string);
	    }

	    // Creating and launching thread
	    // ProcessorChain pc = new ProcessorChain(urls, props.getMaxHop(),
	    // urlAuthorised, Agressivity.REALLY_SOFT);
	    pc.setSeeds(urls);
	    pc.setMaxHop(props.getMaxHop());
	    pc.setAuthorised(urlAuthorised);
	    pc.setAgressivity(props.getAgressivity());
	    treatment = new Thread(pc);
	    treatment.start();
	    logger.info("Thread started");

	    //	CrawlReport report = new CrawlReport();
	    //	report.setRunningState(RunningState.PROCESSING);
	    //	report.setFinalState(FinalState.SUCCESS);
	    //	report.setMessage("Will run " + urls.size() + " urls");
	}
	return getReportCurrentCrawl();
    }

    public Report getReportCurrentCrawl() {
	CrawlReport crawlReport = pc.getReport();
	Report report = new Report();

	if (treatment != null) {
	    State state = treatment.getState();
	    // if thread is terminated ?
	    if (state.equals(State.TERMINATED)) {
		// has it (not) been stop by user ?
		if (pc.getRunning()) {
		    report.setFinalState(FinalState.SUCCESS);
		} else {
		    report.setFinalState(FinalState.WARNING);
		}
		report.setRunningState(RunningState.ENDED);
	    } else {
		report.setRunningState(RunningState.PROCESSING);
	    }
	} else {
	    report.setRunningState(RunningState.PENDING);
	    report.setFinalState(FinalState.SUCCESS);
	}

	// @formatter:off
	String message = "Crawl status : "+report.getRunningState()+"\n"+
		"- Total page : "+crawlReport.size()+"\n"+
		"- Page pending : "+crawlReport.getPendingCrawled()+"\n"+
		"- Page ended :   "+crawlReport.getSuccessCrawled()+"\n"+
		"- Page error :   "+crawlReport.getErrorCrawled()+"\n"+
		"- Time :   " + Chrono.longMillisToHour( crawlReport.getTime() )+ "\n";
	//@formatter:on


	report.setMessage(message);
	return report;
    }

    public Report stopCurrentCrawl() {
	pc.askToStop();

	return getReportCurrentCrawl();
    }

    public Thread getTreatment() {
	return treatment;
    }

    public boolean testConnectivity() {
	return webService.connectivityTest();
    }

}
