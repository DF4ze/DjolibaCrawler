package fr.ses10doigts.djolibaCrawler.model.crawl.entity;

import fr.ses10doigts.djolibaCrawler.model.crawl.enumerate.ChainState;
import fr.ses10doigts.djolibaCrawler.model.crawl.enumerate.FinalState;
import fr.ses10doigts.djolibaCrawler.model.crawl.enumerate.RunningState;

public class PageReport {

    public ChainState	chainState;
    public RunningState	runningState;
    public FinalState   finalState;
}
