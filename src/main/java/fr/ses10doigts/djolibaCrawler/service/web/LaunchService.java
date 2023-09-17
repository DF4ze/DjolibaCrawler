package fr.ses10doigts.djolibaCrawler.service.web;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.ses10doigts.djolibaCrawler.model.crawl.Report;
import fr.ses10doigts.djolibaCrawler.model.crawl.enumerate.FinalState;
import fr.ses10doigts.djolibaCrawler.model.crawl.enumerate.RunningState;
import fr.ses10doigts.djolibaCrawler.model.web.Configuration;
import fr.ses10doigts.djolibaCrawler.service.crawl.CrawlService;
import fr.ses10doigts.djolibaCrawler.service.scrap.RefactorerService;

@Component
public class LaunchService {

    @Autowired
    private ConfigurationService conf;
    @Autowired
    private CrawlService	 crawl;
    @Autowired
    private RefactorerService	 refacto;

    public Report manageLaunch() {
	Configuration configuration = conf.getConfiguration();

	Report cr = new Report();
	Thread t = null;
	try {
	    if (configuration.isLaunchCrawl()) {
		cr = crawl.launchCrawl();
		t = crawl.getTreatment();
	    }

	    if (configuration.isLaunchRefacto()) {
		// TODO rapport
		refacto.launchRefactorer(t);

	    }
	} catch (IOException e) {

	    cr.setFinalState(FinalState.ERROR);
	    cr.setRunningState(RunningState.ENDED);
	    cr.setMessage("Error reading seeds file");
	}

	return cr;
    }

}
