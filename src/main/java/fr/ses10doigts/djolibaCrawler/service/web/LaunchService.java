package fr.ses10doigts.djolibaCrawler.service.web;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.ses10doigts.djolibaCrawler.model.crawl.Report;
import fr.ses10doigts.djolibaCrawler.model.crawl.enumerate.FinalState;
import fr.ses10doigts.djolibaCrawler.model.crawl.enumerate.RunningState;
import fr.ses10doigts.djolibaCrawler.model.web.Configuration;
import fr.ses10doigts.djolibaCrawler.service.crawl.CrawlService;

@Component
public class LaunchService {

    @Autowired
    private ConfigurationService conf;
    @Autowired
    private CrawlService	 crawl;

    public Report manageLaunch() {
	Configuration configuration = conf.getConfiguration();

	Report cr = new Report();
	try {
	    if (configuration.isLaunchCrawl()) {
		cr = crawl.launchCrawl();
	    }


	} catch (IOException e) {

	    cr.setFinalState(FinalState.ERROR);
	    cr.setRunningState(RunningState.ENDED);
	    cr.setMessage("Error reading seeds file");
	}

	return cr;
    }

}
