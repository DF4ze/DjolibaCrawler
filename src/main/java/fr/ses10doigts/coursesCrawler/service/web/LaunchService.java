package fr.ses10doigts.coursesCrawler.service.web;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.ses10doigts.coursesCrawler.model.crawl.CrawlReport;
import fr.ses10doigts.coursesCrawler.model.crawl.enumerate.FinalState;
import fr.ses10doigts.coursesCrawler.model.crawl.enumerate.RunningState;
import fr.ses10doigts.coursesCrawler.model.web.Configuration;
import fr.ses10doigts.coursesCrawler.service.course.RefactorerService;
import fr.ses10doigts.coursesCrawler.service.crawl.CrawlService;

@Component
public class LaunchService {

    @Autowired
    private ConfigurationService conf;
    @Autowired
    private CrawlService	 crawl;
    @Autowired
    private RefactorerService	 refacto;

    public CrawlReport manageLaunch() {
	Configuration configuration = conf.getConfiguration();

	CrawlReport cr = new CrawlReport();
	Thread t = null;
	try {
	    if (configuration.isLaunchCrawl()) {
		cr = crawl.launchCrawl();
		t = crawl.getTreatment();
	    }

	    if (configuration.isLaunchRefacto()) {
		if (t == null) {
		    refacto.launchRefactorer();
		} else {
		    refacto.launchRefactorer(t);
		}
	    }
	} catch (IOException e) {

	    cr.setFinalState(FinalState.ERROR);
	    cr.setRunningState(RunningState.ENDED);
	    cr.setMessage("Error reading seeds file");
	}

	return cr;
    }

}
