package fr.ses10doigts.coursesCrawler.model.crawl;

import fr.ses10doigts.coursesCrawler.model.crawl.enumerate.ChainState;
import fr.ses10doigts.coursesCrawler.model.crawl.enumerate.FinalState;
import fr.ses10doigts.coursesCrawler.model.crawl.enumerate.RunningState;

public class PageReport {

    public ChainState	chainState;
    public RunningState	runningState;
    public FinalState   finalState;
}
