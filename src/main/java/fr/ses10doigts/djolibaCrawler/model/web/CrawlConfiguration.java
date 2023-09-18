package fr.ses10doigts.djolibaCrawler.model.web;

import fr.ses10doigts.djolibaCrawler.model.crawl.enumerate.Agressivity;

public class CrawlConfiguration {

    private String	txtSeeds;
    private String	authorized;
    private int		maxHop;
    private int		maxRetry;
    private Agressivity	agressivity;
    private boolean	launchCrawl;
    private boolean	launchRefacto;

    private String	startGenDate;
    private String	endGenDate;

    public String getTxtSeeds() {
	return txtSeeds;
    }

    public void setTxtSeeds(String txtSeeds) {
	this.txtSeeds = txtSeeds;
    }

    public String getAuthorized() {
	return authorized;
    }

    public void setAuthorized(String authorized) {
	this.authorized = authorized;
    }

    public int getMaxHop() {
	return maxHop;
    }

    public void setMaxHop(int maxHop) {
	this.maxHop = maxHop;
    }

    public int getMaxRetry() {
	return maxRetry;
    }

    public void setMaxRetry(int maxRetry) {
	this.maxRetry = maxRetry;
    }

    public Agressivity getAgressivity() {
	return agressivity;
    }

    public void setAgressivity(Agressivity agressivity) {
	this.agressivity = agressivity;
    }

    public boolean isLaunchCrawl() {
	return launchCrawl;
    }

    public void setLaunchCrawl(boolean launchCrawl) {
	this.launchCrawl = launchCrawl;
    }

    public boolean isLaunchRefacto() {
	return launchRefacto;
    }

    public void setLaunchRefacto(boolean launchRefacto) {
	this.launchRefacto = launchRefacto;
    }

    public String getStartGenDate() {
	return startGenDate;
    }

    public void setStartGenDate(String startGenDate) {
	this.startGenDate = startGenDate;
    }

    public String getEndGenDate() {
	return endGenDate;
    }

    public void setEndGenDate(String endGenDate) {
	this.endGenDate = endGenDate;
    }

    @Override
    public String toString() {
	return "Configuration [txtSeeds=" + txtSeeds + ", authorized=" + authorized + ", maxHop=" + maxHop
		+ ", maxRetry=" + maxRetry + ", agressivity=" + agressivity + ", launchCrawl=" + launchCrawl
		+ ", launchRefacto=" + launchRefacto + "]";
    }

}
