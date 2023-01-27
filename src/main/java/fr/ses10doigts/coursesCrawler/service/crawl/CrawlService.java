package fr.ses10doigts.coursesCrawler.service.crawl;

import java.io.IOException;
import java.lang.Thread.State;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.ses10doigts.coursesCrawler.CustomProperties;
import fr.ses10doigts.coursesCrawler.model.crawl.CrawlReport;
import fr.ses10doigts.coursesCrawler.model.crawl.enumerate.FinalState;
import fr.ses10doigts.coursesCrawler.model.crawl.enumerate.RunningState;
import fr.ses10doigts.coursesCrawler.repository.web.WebCrawlingProxy;
import fr.ses10doigts.coursesCrawler.service.crawl.tool.LineReader;
import fr.ses10doigts.coursesCrawler.service.crawl.tool.Report;

@Service
public class CrawlService {
    @Autowired
    private WebCrawlingProxy webService;

    @Autowired
    private LineReader		reader;

    @Autowired
    private CustomProperties	props;

    @Autowired
    private ProcessorChain	pc;

    private static Thread	treatment = null;

    private static final Logger	logger = LoggerFactory.getLogger(CrawlService.class);

    public String getPage(String url) {
	return webService.getRawPage(url);
    }



    public CrawlReport launchCrawl() throws IOException {
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

    public CrawlReport getReportCurrentCrawl() {
	Report report = pc.getReport();
	CrawlReport cr = new CrawlReport();

	if (treatment != null) {
	    State state = treatment.getState();
	    // if thread is terminated ?
	    if (state.equals(State.TERMINATED)) {
		// has it (not) been stop by user ?
		if (pc.getRunning()) {
		    cr.setFinalState(FinalState.SUCCESS);
		} else {
		    cr.setFinalState(FinalState.WARNING);
		}
		cr.setRunningState(RunningState.ENDED);
	    } else {
		cr.setRunningState(RunningState.PROCESSING);
	    }
	} else {
	    cr.setRunningState(RunningState.PENDING);
	    cr.setFinalState(FinalState.SUCCESS);
	}

	// @formatter:off
	String message = "Crawl status : "+cr.getRunningState()+"\n"+
		"- Total page : "+report.size()+"\n"+
		"- Page pending : "+report.getPendingCrawled()+"\n"+
		"- Page ended :   "+report.getSuccessCrawled()+"\n"+
		"- Page error :   "+report.getErrorCrawled()+"\n";
	//@formatter:on
	if(pc.getChrono() != null) {
	    message += "- Time :   "+pc.getChrono().compareToHour()+"\n";
	}

	cr.setMessage(message);
	return cr;
    }

    public CrawlReport stopCurrentCrawl() {
	pc.askToStop();

	return getReportCurrentCrawl();
    }

    public Thread getTreatment() {
	return treatment;
    }

}
