package fr.ses10doigts.djolibaCrawler.model.crawl;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Page implements Comparable<Page> {

    private String	url;
    private Set<String>	urlsContained = new HashSet<>();
    //    private String	content;
    private Integer	hop;
    private Integer	nbRetry;

    public Page(String url, int hop) {
	super();
	this.url = url;
	this.hop = hop;
	nbRetry = 0;
    }

    public String getUrl() {
	return url;
    }

    public void setUrl(String url) {
	this.url = url;
    }

    public Set<String> getUrlsContained() {
	return urlsContained;
    }

    public void setUrlsContained(Set<String> urlsContained) {
	this.urlsContained = urlsContained;
    }

    //    public String getContent() {
    //	return content;
    //    }
    //
    //    public void setContent(String content) {
    //	this.content = content;
    //    }

    public Integer getHop() {
	return hop;
    }

    public void setHop(Integer hop) {
	this.hop = hop;
    }

    public Integer getNbRetry() {
	return nbRetry;
    }

    public void setNbRetry(Integer nbRetry) {
	this.nbRetry = nbRetry;
    }

    @Override
    public int compareTo(Page page) {
	return getUrl().compareTo(page.getUrl());
    }

    @Override
    public int hashCode() {
	return Objects.hash(url);
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	Page other = (Page) obj;
	return Objects.equals(url, other.url);
    }

}
