package fr.ses10doigts.djolibaCrawler;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import fr.ses10doigts.djolibaCrawler.model.crawl.enumerate.Agressivity;

@Configuration
@ConfigurationProperties(prefix = "fr.ses10doigts.crawler")
public class CustomCrawlProperties {

    // @Value("#{'${fr.ses10doigts.webapp.urls}'.split(',')}")
    // private List<String> urls;

    private Integer	maxHop;
    private String	seedsFile;
    private String	authorizedFile;
    private Integer	maxRetry;
    private Agressivity	agressivity;
    private boolean	doCrawl;
    private boolean	doRefacto;

    public Integer getMaxHop() {
	return maxHop;
    }

    public void setMaxHop(Integer maxHop) {
	this.maxHop = maxHop;
    }

    public String getSeedsFile() {
	return seedsFile;
    }

    public void setSeedsFile(String seedFile) {
	this.seedsFile = seedFile;
    }

    public String getAuthorizedFile() {
	return authorizedFile;
    }

    public void setAuthorizedFile(String authorizedFile) {
	this.authorizedFile = authorizedFile;
    }

    public Integer getMaxRetry() {
	return maxRetry;
    }

    public void setMaxRetry(Integer maxRetry) {
	this.maxRetry = maxRetry;
    }

    public Agressivity getAgressivity() {
	return agressivity;
    }

    public void setAgressivity(Agressivity agressivity) {
	this.agressivity = agressivity;
    }

    public boolean isDoCrawl() {
	return doCrawl;
    }

    public void setDoCrawl(boolean doCrawl) {
	this.doCrawl = doCrawl;
    }

    public boolean isDoRefacto() {
	return doRefacto;
    }

    public void setDoRefacto(boolean doRefacto) {
	this.doRefacto = doRefacto;
    }

}
